package algo;

import dao.DaoFactory;
import dao.InstanceDao;
import dao.PersistenceType;
import java.io.BufferedReader;
import java.io.FileReader;
import modele.Customer;
import modele.Depot;
import modele.Instance;
import modele.MachineType;
import modele.Planning;
import modele.Point;
import modele.Technician;
import modele.Vehicle;

/**
 *
 * @author Henri, Lucas, Louis
 */
public class ReadInstance {

    public static Instance readInstance(String file) {
        // Init
        Instance instance = null;
        Planning planning = null;

        double technicianDistanceCost = -1,
                technicianDayCost = -1,
                technicianCost = -1,
                truckCost = -1,
                truckDayCost = -1,
                truckDistanceCost = -1,
                truckMaxDistance = -1,
                truckCapacity = -1;

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            String[] arg = null;
            String val1 = "";
            int nbMachines = -1, nbLocations = -1;
            Point points[] = null;
            while ((line = in.readLine()) != null) {
                //System.out.println (line);
                arg = line.split(" = ");
                /*for(int i = 0; i < arg.length; i++){
                    System.out.println ("SPLIT: " + arg[i]);
                }*/
                DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);
                switch (arg[0]) {
                    case "DATASET":
                        val1 = arg[1];
                        break;
                    case "NAME":
                        if (val1 != "") {
                            InstanceDao instanceManager = factory.getInstanceDao();
                            instance = new Instance(arg[1], val1);
                            instanceManager.create(instance);
                            System.out.println("Instance IMPORTED: named '" + arg[1] + "'");
                        } else {
                            System.err.println("Instance ERROR : DATASET value doesn't exist.");
                        }
                        break;
                    case "DAYS":
                        planning = new Planning(instance, Integer.parseInt(arg[1]));
                        instance.addPlanning(planning);
                        if (planning.getNbDays() == Integer.parseInt(arg[1]) && Integer.parseInt(arg[1]) > 0) {
                            System.out.println("Planning IMPORTED: composed of " + arg[1] + " days.");
                        } else {
                            System.err.println("Planning ERROR: during the creation.");
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
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    size = Integer.parseInt(argument[1]),
                                    penalty = Integer.parseInt(argument[2]);
                            instance.addMachine(new MachineType(id, size, penalty, instance));
                        }
                        break;
                    case "LOCATIONS":
                        nbLocations = Integer.parseInt(arg[1]);
                        points = new Point[nbLocations];
                        for (int i = 0; i < nbLocations; i++) {
                            line = in.readLine();
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    x = Integer.parseInt(argument[1]),
                                    y = Integer.parseInt(argument[2]);
                            points[i] = new Point(id, id, -1, x, y, instance);
                            if (Integer.parseInt(argument[0]) == 1) {
                                instance.addPoint(new Depot(1, 1, Integer.parseInt(argument[1]), Integer.parseInt(argument[2]), instance));
                            }
                        }
                        break;
                    case "REQUESTS":
                        for (int i = 0; i < Integer.parseInt(arg[1]); i++) {
                            line = in.readLine();
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    idLocation = Integer.parseInt(argument[1]),
                                    firstDay = Integer.parseInt(argument[2]),
                                    lastDay = Integer.parseInt(argument[3]),
                                    machineId = Integer.parseInt(argument[4]),
                                    nbMachinesRequested = Integer.parseInt(argument[5]);
                            MachineType m = instance.getMachineType(machineId);
                            for (int j = 0; j < points.length; j++) {
                                Customer c = new Customer(id, idLocation, points[j].getX(), points[j].getY(), instance);
                                if (points[j].getIdLocation() == idLocation) {
                                    if (!instance.containsPoint(c)) {
                                        instance.addPoint(c);
                                        c.addDemand(id, firstDay, lastDay, m, nbMachinesRequested, planning);
                                    } else if (instance.getPoint(c) instanceof Customer) {
                                        ((Customer) instance.getPoint(c)).addDemand(id, firstDay, lastDay, m, nbMachinesRequested, planning);
                                    }
                                }
                            }
                        }
                        break;
                    case "TECHNICIANS":
                        for (int i = 0; i < Integer.parseInt(arg[1]); i++) {
                            line = in.readLine();
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    idLocation = Integer.parseInt(argument[1]),
                                    distanceMax = Integer.parseInt(argument[2]),
                                    demandMax = Integer.parseInt(argument[3]);
                            for (int j = 0; j < points.length; j++) {
                                if (points[j].getIdLocation() == idLocation) {
                                    Technician t = new Technician(id, idLocation, points[j].getX(), points[j].getY(), distanceMax, demandMax, technicianCost, technicianDistanceCost, technicianDayCost, instance);
                                    instance.addPoint(t);
                                    for (int k = 4; k < nbMachines + 4; k++) {
                                        if (Integer.parseInt(argument[k]) == 1) {
                                            t.addAccreditation(instance.getMachineType(k - 3));
                                        }
                                    }
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
//            System.out.println(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // VEHICLE TYPE ASSOCIATED TO THE INSTANCE ///////
        Vehicle vehicleType = new Vehicle(1, instance.getDepot(), truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        vehicleType.setvInstance(instance);

        // ROUTES ////////////////////////////////////////
        for (Point p1 : instance.getPointList()) {
            for (Point p2 : instance.getPointList()) {
                p1.addDestination(p2, p1.computeDistance(p2));
            }
        }

        return instance;
    }
}
