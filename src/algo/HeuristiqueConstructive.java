package algo;

import dao.DaoFactory;
import dao.DayHorizonDao;
import dao.DemandDao;
import dao.InstanceDao;
import dao.PersistenceType;
import dao.PlannedDemandDao;
import dao.PlanningDao;
import dao.TechnicianDao;
import dao.TechnicianItineraryDao;
import dao.VehicleItineraryDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import modele.DayHorizon;
import modele.Instance;
import modele.Itinerary;
import modele.PlannedDemand;
import modele.Planning;
import modele.Technician;
import modele.TechnicianItinerary;
import modele.Vehicle;
import modele.VehicleItinerary;

/**
 *
 * @author Lucas, Henri, Louis
 */
public class HeuristiqueConstructive {

    private Instance instance;

    /**
     * Parameterized constructor
     *
     * @param instance
     */
    public HeuristiqueConstructive(Instance instance) {
        this.instance = instance;
    }

    public void minimalSolution() throws IOException {
        //DAO Manager initialisation
        DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);
        InstanceDao instancemanager = factory.getInstanceDao();
        PlanningDao planningManager = factory.getPlanningDao();
        VehicleItineraryDao vehicleItineraryManager = factory.getVehicleItineraryDao();
        TechnicianDao technicianManager = factory.getTechnicianDao();
        TechnicianItineraryDao technicianItineraryManager = factory.getTechnicianItineraryDao();
        DemandDao demandManager = factory.getDemandDao();
        DayHorizonDao daysManager = factory.getDayHorizonDao();
        PlannedDemandDao plannedDemandManager = factory.getPlannedDemandDao();

        // Initiate planning
        Planning planning = InitiatePlanning.createPlanning(instance, instancemanager, planningManager, demandManager, plannedDemandManager, "MinimalSolution");

        // Get vehicle information
        Vehicle vehicleInstance = instance.getVehicle();

        //create the first sued vehicle
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicleInstance);
        List<PlannedDemand> plannedDemands = planning.getPlannedDemands();

        // Starts requests sequencing
        for (int i = 1; i <= instance.getNbDays(); i++) {
            DayHorizon day = new DayHorizon(i);
            planning.addDayHorizon(day);
            daysManager.create(day);

            // Vehicle Itineraries setup depending on available vehicles
            Set<VehicleItinerary> vehicleItineraries = new HashSet<>();
            for (Vehicle v : vehicles) {
                VehicleItinerary vehicleItinerary = new VehicleItinerary(v);
                day.addItinerary(vehicleItinerary);
                vehicleItineraries.add(vehicleItinerary);
                vehicleItineraryManager.create(vehicleItinerary);
            }

            // technician itineraries setup
            Set<TechnicianItinerary> technicianItineraries = new HashSet<>();
            for (Technician t : technicianManager.findbyInstance(instance)) {
                TechnicianItinerary technicianItinerary = new TechnicianItinerary(t);
                day.addItinerary(technicianItinerary);
                technicianItineraries.add(technicianItinerary);
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
                            technicianItineraryManager.create(technicianItinerary);
                            break;
                        }
                    }
                }
                plannedDemandManager.create(demand);
            }
            daysManager.update(day);
        }
        
        planningManager.update(planning);
        System.out.println(planning);
    }
}