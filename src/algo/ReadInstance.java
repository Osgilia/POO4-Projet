package algo;

import dao.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import modele.Customer;
import modele.Demand;
import modele.Depot;
import modele.Instance;
import modele.MachineType;
import modele.Point;
import modele.Technician;
import modele.Vehicle;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class ReadInstance {

    public static String formattingOrtec(String S) {
        if (S.contains("\t")) {
            S = S.replaceAll("\t", " ");
        }
//        System.out.println(S);
        char chars[] = S.toCharArray();
        char prev = '\0';
        int k = 0;
        for (char c : chars) {
            if (prev != c || c != ' ') {
                chars[k++] = c;
                prev = c;
            }
        }
        String S2 = new String(chars).substring(0, k);
        char chars2[] = S2.toCharArray();
        if (chars2[0] == ' ') {
            S2 = S2.substring(1);
        }
        //System.out.println(S2);
        return S2;
    }

    public static Instance readInstance(String file) {
        // Init
        Instance instance = null;

        double technicianDistanceCost = -1,
                technicianDayCost = -1,
                technicianCost = -1,
                truckCost = -1,
                truckDayCost = -1,
                truckDistanceCost = -1,
                truckMaxDistance = -1,
                truckCapacity = -1;

        //DAO Manager initialisation
        DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);

        InstanceDao instanceManager = factory.getInstanceDao();
        MachineTypeDao machineManager = factory.getMachineTypeDao();
        PointDao pointManager = factory.getPointDao();
        DepotDao depotManager = factory.getDepotDao();
        CustomerDao customerManager = factory.getCustomerDao();
        DemandDao demandManager = factory.getDemandDao();
        TechnicianDao technicianManager = factory.getTechnicianDao();
        VehicleDao vehicleManager = factory.getVehicleDao();
        Point points[] = null;
        Depot d = null;
        List<Customer> customersAdded = new ArrayList<>();
        List<Technician> techniciansAdded = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            String[] arg = null;
            String instanceName = "", instanceDataSet = "";

            int nbMachines = -1, nbLocations = -1;

            while ((line = in.readLine()) != null) {
                arg = line.split(" = ");
                switch (arg[0]) {
                    case "DATASET":
                        instanceDataSet = arg[1];
                        break;
                    case "NAME":
                        if (!"".equals(instanceDataSet)) {
                            instanceName = arg[1];
                            System.out.println("Instance IMPORTED: named '" + arg[1] + "'");
                        } else {
                            System.err.println("Instance ERROR : DATASET value doesn't exist.");
                        }
                        break;
                    case "DAYS":
                        instance = new Instance(instanceName, instanceDataSet, Integer.parseInt(arg[1]));
                        instanceManager.create(instance);
                        if (instance.getNbDays() == Integer.parseInt(arg[1]) && Integer.parseInt(arg[1]) > 0) {
                            System.out.println("Instance IMPORTED: composed of " + arg[1] + " days.");
                        } else {
                            System.err.println("Instance ERROR: during the creation.");
                        }
                        break;
                    case "TRUCK_CAPACITY":
                        truckCapacity = Integer.parseInt(arg[1]);
                        break;
                    case "TRUCK_MAX_DISTANCE":
                        truckMaxDistance = Integer.parseInt(arg[1]);
                        break;
                    case "TRUCK_DISTANCE_COST":
                        truckDistanceCost = Integer.parseInt(arg[1]);
                        break;
                    case "TRUCK_DAY_COST":
                        truckDayCost = Integer.parseInt(arg[1]);
                        break;
                    case "TRUCK_COST":
                        truckCost = Integer.parseInt(arg[1]);
                        break;
                    case "TECHNICIAN_DISTANCE_COST":
                        technicianDistanceCost = Integer.parseInt(arg[1]);
                        break;
                    case "TECHNICIAN_DAY_COST":
                        technicianDayCost = Integer.parseInt(arg[1]);
                        break;
                    case "TECHNICIAN_COST":
                        technicianCost = Integer.parseInt(arg[1]);
                        break;
                    case "MACHINES":
                        nbMachines = Integer.parseInt(arg[1]);
                        for (int i = 0; i < nbMachines; i++) {
                            line = in.readLine();
                            line = formattingOrtec(line);
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    size = Integer.parseInt(argument[1]),
                                    penalty = Integer.parseInt(argument[2]);
                            MachineType m = new MachineType(id, size, penalty, instance);
                            machineManager.create(m);
                            instance.addMachine(m);
                        }
                        break;
                    case "LOCATIONS":
                        nbLocations = Integer.parseInt(arg[1]);
                        points = new Point[nbLocations];
                        for (int i = 0; i < nbLocations; i++) {
                            line = in.readLine();
                            line = formattingOrtec(line);
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    x = Integer.parseInt(argument[1]),
                                    y = Integer.parseInt(argument[2]);
                            points[i] = new Point(id, id, -1, x, y, instance);
                            pointManager.create(points[i]);
                            if (Integer.parseInt(argument[0]) == 1) {
                                d = new Depot(1, 1, Integer.parseInt(argument[1]), Integer.parseInt(argument[2]), instance);
                                depotManager.create(d);
                                points[i] = d;
                            }
                        }
                        break;
                    case "REQUESTS":
                        for (int i = 0; i < Integer.parseInt(arg[1]); i++) {
                            line = in.readLine();
                            line = formattingOrtec(line);
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    idLocation = Integer.parseInt(argument[1]),
                                    firstDay = Integer.parseInt(argument[2]),
                                    lastDay = Integer.parseInt(argument[3]),
                                    machineId = Integer.parseInt(argument[4]),
                                    nbMachinesRequested = Integer.parseInt(argument[5]);
                            MachineType m = instance.getMachineType(machineId);
                            for (int j = 0; j < points.length; j++) {
                                Customer c = new Customer(points[j].getIdPoint(), idLocation, points[j].getX(), points[j].getY(), instance);
                                if (points[j].getIdLocation() == idLocation) {
                                    if (!customersAdded.contains(c)) {
                                        customerManager.create(c);
                                        Demand newDemand = new Demand(id, firstDay, lastDay, c, m, nbMachinesRequested);
                                        demandManager.create(newDemand);
                                        customersAdded.add(c);
                                    } else {
                                        Demand newDemand = new Demand(id, firstDay, lastDay, c, m, nbMachinesRequested);
                                        demandManager.create(newDemand);
                                    }
                                }
                            }
                        }
                        break;
                    case "TECHNICIANS":
                        for (int i = 0; i < Integer.parseInt(arg[1]); i++) {
                            line = in.readLine();
                            line = formattingOrtec(line);
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    idLocation = Integer.parseInt(argument[1]),
                                    distanceMax = Integer.parseInt(argument[2]),
                                    demandMax = Integer.parseInt(argument[3]);
                            for (int j = 0; j < points.length; j++) {
                                if (points[j].getIdLocation() == idLocation) {
                                    Technician t = new Technician(id, id, points[j].getX(), points[j].getY(), distanceMax, demandMax, technicianCost, technicianDistanceCost, technicianDayCost, instance);
                                    for (int k = 4; k < nbMachines + 4; k++) {
                                        if (Integer.parseInt(argument[k]) == 1) {
                                            t.addAccreditation(instance.getMachineType(k - 3));
                                        }
                                    }
                                    technicianManager.create(t);
                                    techniciansAdded.add(t);
                                }
                            }
                        }
                        break;
                }
            }
            in.close();
            if (truckCapacity == -1 || truckMaxDistance == -1 || truckDistanceCost == -1 || truckDayCost == -1 || truckCost == -1) {
                System.err.println("TRUCK ERROR: during the creation of characteristics.");
            } else {
                System.out.println("TRUCK CHARACTERISTICS IMPORTED");
            }
            if (technicianDistanceCost == -1 || technicianDayCost == -1 || technicianCost == -1) {
                System.err.println("TECHNICIAN ERROR: during the creation of characteristics.");
            } else {
                System.out.println("TECHNICIAN CHARACTERISTICS IMPORTED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // VEHICLE TYPE ASSOCIATED TO THE INSTANCE ///////
        Vehicle vehicleType = new Vehicle(1, d, truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        vehicleType.setvInstance(instance);
        vehicleManager.create(vehicleType);

        instanceManager.update(instance);
        System.out.println("MAINTENANT FINI");
        return instance;
    }
}
