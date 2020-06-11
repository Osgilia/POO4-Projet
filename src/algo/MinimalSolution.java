package algo;

import dao.*;
import dao.PersistenceType;
import dao.PlanningDao;
import java.io.IOException;
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
        VehicleItineraryDao vehicleItineraryManager = factory.getVehicleItineraryDao();
        TechnicianDao technicianManager = factory.getTechnicianDao();
        TechnicianItineraryDao technicianItineraryManager = factory.getTechnicianItineraryDao();
        DemandDao demandManager = factory.getDemandDao();
        DayHorizonDao daysManager = factory.getDayHorizonDao();
        PlannedDemandDao plannedDemandManager = factory.getPlannedDemandDao();

        //get instance
        InstanceDao instancemanager = factory.getInstanceDao();
        Instance instance = instancemanager.findById(instanceId);
        System.out.println(instance.getNbDays());

        //Initiate planning
        Planning planning = new Planning(instance, instance.getNbDays());
        instance.addPlanning(planning);
        planningManager.create(planning);
        instancemanager.update(instance);

        //get vehicle informations
        Vehicle vehicleInstance = instance.getVehicle();
        double truckCost = vehicleInstance.getUsageCost(),
                truckDayCost = vehicleInstance.getDayCost(),
                truckDistanceCost = vehicleInstance.getDistanceCost(),
                truckMaxDistance = vehicleInstance.getDistanceMax(),
                truckCapacity = vehicleInstance.getCapacity();

        Set<Vehicle> vehicles = new HashSet<>();
        Vehicle firstVehicle = new Vehicle(1, instance.getDepot(), truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        vehicles.add(firstVehicle);

        //DayHorizons creation
        for (int i = 1; i <= instance.getNbDays(); i++) {
            DayHorizon day = new DayHorizon(i);
            planning.addDayHorizon(day);

            //vehicle Itinerary creation
            Set<VehicleItinerary> vehicleItineraries = new HashSet<>();
            for (Vehicle v : vehicles) {
                VehicleItinerary vehicleItinerary = new VehicleItinerary(v);

                vehicleItineraryManager.create(vehicleItinerary);
                day.addItinerary(vehicleItinerary);
                vehicleItineraryManager.update(vehicleItinerary);

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

            //set Planning Demands
            for (Customer c : instance.getCustomers()) {
                for (Demand d : c.getCustomerDemands()) {
                    planning.addDemand(d, plannedDemandManager);
                    demandManager.update(d);
                }
            }
            
           // System.out.println(planning.getDemands());
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
                        Vehicle lastVehicle = new Vehicle(vehicles.size() + 1, instance.getDepot(), truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
                        vehicles.add(lastVehicle);
                        VehicleItinerary vehicleItinerary = new VehicleItinerary(lastVehicle);
                        vehicleItineraryManager.create(vehicleItinerary);

                        day.addItinerary(vehicleItinerary);
                        vehicleItinerary.addDemandVehicle(demand.getKey());
                        vehicleItineraries.add(vehicleItinerary);
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

            daysManager.create(day);

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
