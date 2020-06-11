package algo;

import dao.*;
import dao.PersistenceType;
import dao.PlanningDao;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modele.*;

/**
 * Sequence schedule of a minimal solution
 *
 * @author Henri, Lucas, Louis
 */
public class MinimalSolution {

    public static void minimalSolution(Integer instanceId) throws IOException {

        //DAO Manager initialisation
        DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);

        PlanningDao planningManager = factory.getPlanningDao();
        
        VehicleDao vehicleManager  = factory.getVehicleDao();
        VehicleItineraryDao vehicleItineraryManager = factory.getVehicleItineraryDao();
        TechnicianDao technicianManager = factory.getTechnicianDao();
        TechnicianItineraryDao technicianItineraryManager = factory.getTechnicianItineraryDao();
        DemandDao demandManager = factory.getDemandDao();
        DayHorizonDao daysManager = factory.getDayHorizonDao();
        PlannedDemandDao plannedDemandManager = factory.getPlannedDemandDao();
        CustomerDao customerManager = factory.getCustomerDao();

        //get instance
        InstanceDao instancemanager = factory.getInstanceDao();
        Instance instance = instancemanager.findById(instanceId);

        //Initiate planning
        Planning planning = InitiatePlanning.createPlanning(instance, instancemanager, planningManager, demandManager, plannedDemandManager);

        //get vehicle informations
        Vehicle vehicleInstance = vehicleManager.findbyInstance(instance);
        double truckCost = vehicleInstance.getUsageCost(),
                truckDayCost = vehicleInstance.getDayCost(),
                truckDistanceCost = vehicleInstance.getDistanceCost(),
                truckMaxDistance = vehicleInstance.getDistanceMax(),
                truckCapacity = vehicleInstance.getCapacity();

        Set<Vehicle> vehicles = new HashSet<>();
        Vehicle firstVehicle = new Vehicle(1, instance.getDepot(), truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        vehicles.add(vehicleInstance);

        //DayHorizons creation
        for (int i = 1; i <= instance.getNbDays(); i++) {
            DayHorizon day = new DayHorizon(i);
            daysManager.create(day);
            planning.addDayHorizon(day);
            daysManager.update(day);

            //vehicle Itinerary creation
            Set<VehicleItinerary> vehicleItineraries = new HashSet<>();
            for (Vehicle v : vehicles) {
                VehicleItinerary vehicleItinerary = new VehicleItinerary(v);

                day.addItinerary(vehicleItinerary);
                vehicleItineraryManager.create(vehicleItinerary);

                vehicleItineraries.add(vehicleItinerary);
            }

            //technician itinerary creation
            Set<TechnicianItinerary> technicianItineraries = new HashSet<>();
            for (Technician t : instance.getTechnicians()) {
                TechnicianItinerary technicianItinerary = new TechnicianItinerary(t);
                technicianItineraryManager.create(technicianItinerary);
                day.addItinerary(technicianItinerary);
                technicianItineraries.add(technicianItinerary);
                technicianItineraryManager.update(technicianItinerary);

                technicianManager.update(t);

            }
            System.out.println("GET PLANNING DEMANDS = " + planning.getDemands());

             
            for (Map.Entry<PlannedDemand, Integer> demand : planning.getDemands().entrySet()) {
                if (demand.getValue() == 0) { // if demand is to be supplied
                    boolean notEnoughVehicles = false;
                    for (VehicleItinerary vehicleItinerary : vehicleItineraries) {
                        if (vehicleItinerary.checkVehicle(demand.getKey())) {
                            notEnoughVehicles = true;
                            continue;
                        }
                        if (vehicleItinerary.addDemandVehicle(demand.getKey())) {
                            notEnoughVehicles = false;
                            break;
                        }
                    }
                    if (notEnoughVehicles) {
                        //If we don't have enough vehicles to answer the demand
                        // 
                        // Vehicle lastVehicle = new Vehicle(vehicles.size() + 1, instance.getDepot(), truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
                        vehicles.add(vehicleInstance);
                        VehicleItinerary vehicleItinerary = new VehicleItinerary(vehicleInstance);

                        day.addItinerary(vehicleItinerary);
                        vehicleItinerary.addDemandVehicle(demand.getKey());
                        vehicleItineraries.add(vehicleItinerary);
                        System.out.println(vehicleItinerary);
                        vehicleItineraryManager.create(vehicleItinerary);

                    }
                }
                if (demand.getValue() == 1) { // if demand is to be installed
                    for (TechnicianItinerary technicianItinerary : technicianItineraries) {
                        if (technicianItinerary.addDemandTechnician(demand.getKey())) {
                            technicianItineraryManager.update(technicianItinerary);

                            break;
                        }
                    }
                }
                plannedDemandManager.update(demand.getKey());
            }

            daysManager.update(day);

        }
        planningManager.update(planning);

        // CHECKS SEQUENCING /////////////////////////////////////////////
        for (Map.Entry<PlannedDemand, Integer> demand : planning.getDemands().entrySet()) {
            if (demand.getValue() == 0) {
                System.err.println("Demand not supplied");
            }
            if (demand.getValue() == 1) {
                System.err.println("Demand not installed");
            }
        }

        System.out.println(planning);
        PrintSolution.print(instance, planning);
    }
}
