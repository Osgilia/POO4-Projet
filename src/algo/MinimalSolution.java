package algo;

import dao.*;
import dao.PersistenceType;
import dao.PlanningDao;
import java.io.IOException;
import java.util.ArrayList;
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
        InstanceDao instancemanager = factory.getInstanceDao();
        PlanningDao planningManager = factory.getPlanningDao();
        VehicleDao vehicleManager = factory.getVehicleDao();
        VehicleItineraryDao vehicleItineraryManager = factory.getVehicleItineraryDao();
        TechnicianDao technicianManager = factory.getTechnicianDao();
        TechnicianItineraryDao technicianItineraryManager = factory.getTechnicianItineraryDao();
        DemandDao demandManager = factory.getDemandDao();
        DayHorizonDao daysManager = factory.getDayHorizonDao();
        PlannedDemandDao plannedDemandManager = factory.getPlannedDemandDao();

        //get instance
        Instance instance = instancemanager.findById(instanceId);

        //Initiate planning
        Planning planning = InitiatePlanning.createPlanning(instance, instancemanager, planningManager, demandManager, plannedDemandManager);

        //get vehicle informations
        Vehicle vehicleInstance = vehicleManager.findbyInstance(instance);
        //create the first sued vehicle
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicleInstance);

        List<PlannedDemand> plannedDemands = planning.getPlannedDemands();
        //DayHorizons creation
        for (int i = 1; i <= instance.getNbDays(); i++) {
            DayHorizon day = new DayHorizon(i);
            planning.addDayHorizon(day);
            daysManager.create(day);

            //vehicle Itinerary creation
            //for each vehicle we create a vehicleItinerary for this day
            Set<VehicleItinerary> vehicleItineraries = new HashSet<>();
            int index = 0;

            for (Vehicle v : vehicles) {
                VehicleItinerary vehicleItinerary = new VehicleItinerary(v);

                day.addItinerary(vehicleItinerary);
                vehicleItineraries.add(vehicleItinerary);
                vehicleItineraryManager.create(vehicleItinerary);

            }

            //technician itinerary creation
            //for each technician we create a technicianItinerary for this day
            Set<TechnicianItinerary> technicianItineraries = new HashSet<>();
            for (Technician t : technicianManager.findbyInstance(instance)) {
                TechnicianItinerary technicianItinerary = new TechnicianItinerary(t);
                day.addItinerary(technicianItinerary);
                technicianItineraries.add(technicianItinerary);
                technicianItineraryManager.create(technicianItinerary);

            }

            for (PlannedDemand demand : plannedDemands) {

                if (demand.getStateDemand() == 0) { // if demand is to be supplied

                    boolean notEnoughVehicles = false;

                    //we check each vehicleItinerary of the day
                    // if there is enough room in the truck we add the demand to this itinerary
                    // else we create a new used vehicle to carry the demand
                    for (VehicleItinerary vehicleItinerary : vehicleItineraries) {
                        if (vehicleItinerary.checkVehicle(demand)) {
                            notEnoughVehicles = true;
                            continue;
                        }
                        if (!notEnoughVehicles && vehicleItinerary.addDemandVehicle(demand)) {

                            break;
                        }
                    }

                    if (notEnoughVehicles) {
                        //If we don't have enough vehicles to answer the demand
                        vehicles.add(vehicleInstance);
                        VehicleItinerary vehicleItinerary = new VehicleItinerary(vehicleInstance);
                        day.addItinerary(vehicleItinerary);
                        vehicleItinerary.addDemandVehicle(demand);
                        vehicleItineraries.add(vehicleItinerary);
                        vehicleItineraryManager.create(vehicleItinerary);
                    }
                } else if (demand.getStateDemand() == 1) { // if demand is to be installed
                    for (TechnicianItinerary technicianItinerary : technicianItineraries) {
                        if (technicianItinerary.addDemandTechnician(demand)) {
                            technicianItineraryManager.update(technicianItinerary);
                            break;
                        }
                    }
                }
                plannedDemandManager.update(demand);
            }

            daysManager.update(day);

        }
        planningManager.update(planning);

        // CHECKS SEQUENCING /////////////////////////////////////////////
//        for (Map.Entry<PlannedDemand, Integer> demand : planning.getDemands().entrySet()) {
//            if (demand.getValue() == 0) {
//                System.err.println("Demand not supplied");
//            }
//            if (demand.getValue() == 1) {
//                System.err.println("Demand not installed");
//            }
//        }
        System.out.println(planning);
        PrintSolution.print(instance, planning);
    }
}
