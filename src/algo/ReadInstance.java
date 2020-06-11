package algo;

import dao.*;
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

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            String[] arg = null;
            String instanceName = "", instanceDataSet = "";

            int nbMachines = -1, nbLocations = -1;
            Point points[] = null;

            while ((line = in.readLine()) != null) {
                //System.out.println (line);
                arg = line.split(" = ");
                /*for(int i = 0; i < arg.length; i++){
                    System.out.println ("SPLIT: " + arg[i]);
                }*/
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
                            String[] argument = line.split(" ");
                            int id = Integer.parseInt(argument[0]),
                                    x = Integer.parseInt(argument[1]),
                                    y = Integer.parseInt(argument[2]);
                            points[i] = new Point(id, id, -1, x, y, instance);
                            pointManager.create(points[i]);
                            if (Integer.parseInt(argument[0]) == 1) {
                                Depot d = new Depot(1, 1, Integer.parseInt(argument[1]), Integer.parseInt(argument[2]), instance);
                                depotManager.create(d);
                                instance.addPoint(d);
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
                                Customer c = new Customer(points[j].getId(), idLocation, points[j].getX(), points[j].getY(), instance);
                                if (points[j].getIdLocation() == idLocation) {
                                    Customer customerInstance = customerManager.find(points[j].getId());
                                    if (customerInstance == null) {
                                        instance.addPoint(c);
                                        c.addDemand(id, firstDay, lastDay, m, nbMachinesRequested, demandManager);
                                        customerManager.create(c);
                                    } else {
                                        customerInstance.addDemand(id, firstDay, lastDay, m, nbMachinesRequested, demandManager);
                                        customerManager.update(customerInstance);
                                    }
                                    machineManager.update(m);
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
                                    Technician t = new Technician(id, id, points[j].getX(), points[j].getY(), distanceMax, demandMax, technicianCost, technicianDistanceCost, technicianDayCost, instance);
                                    instance.addPoint(t);
                                    for (int k = 4; k < nbMachines + 4; k++) {
                                        if (Integer.parseInt(argument[k]) == 1) {
                                            t.addAccreditation(instance.getMachineType(k - 3));
                                        }
                                    }
                                    technicianManager.create(t);
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
        vehicleManager.create(vehicleType);

        RouteDao routeManager = factory.getRouteDao();
        // ROUTES ////////////////////////////////////////
        for (Point p1 : instance.getPointList()) {
            for (Point p2 : instance.getPointList()) {
                p1.addDestination(p2, p1.computeDistance(p2), routeManager);
                pointManager.update(p2);
            }
            pointManager.update(p1);
        }

        instanceManager.update(instance);

        return instance;
    }
}
