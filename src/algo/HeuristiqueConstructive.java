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
    private DaoFactory factory;
    private InstanceDao instanceManager;
    private PlanningDao planningManager;
    private VehicleItineraryDao vehicleItineraryManager;
    private TechnicianDao technicianManager;
    private TechnicianItineraryDao technicianItineraryManager;
    private DemandDao demandManager;
    private DayHorizonDao daysManager;
    private PlannedDemandDao plannedDemandManager;
    private Vehicle vehicleInstance;
    private List<Vehicle> vehiclesUsed;
    private List<TechnicianItinerary> technicianItineraries;
    private List<VehicleItinerary> vehicleItineraries;
    private List<PlannedDemand> plannedDemands;

    /**
     * Parameterized constructor
     *
     * @param instance
     */
    public HeuristiqueConstructive(Instance instance) {
        this.instance = instance;
        this.factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);
        this.instanceManager = this.factory.getInstanceDao();
        this.planningManager = this.factory.getPlanningDao();
        this.vehicleItineraryManager = this.factory.getVehicleItineraryDao();
        this.technicianManager = this.factory.getTechnicianDao();
        this.technicianItineraryManager = this.factory.getTechnicianItineraryDao();
        this.demandManager = this.factory.getDemandDao();
        this.daysManager = this.factory.getDayHorizonDao();
        this.plannedDemandManager = this.factory.getPlannedDemandDao();
        this.vehicleInstance = instance.getVehicle();
        this.technicianItineraries = new ArrayList<>();
        this.vehicleItineraries = new ArrayList<>();
        this.plannedDemands = new ArrayList<>();
        this.vehiclesUsed = new ArrayList<>();
    }

    /**
     * Minimal Solution
     *
     * @param day
     * @throws IOException
     */
    public void minimalSolution(DayHorizon day) throws IOException {

        for (PlannedDemand demand : this.plannedDemands) {
            if (demand.getStateDemand() == 0) { // if demand is to be supplied
                boolean notEnoughVehicles = false;
                //we check each vehicleItinerary of the day
                // if there is enough room in the truck we add the demand to this itinerary
                // else we create a new used vehicle to carry the demand
                for (VehicleItinerary vehicleItinerary : this.vehicleItineraries) {
                    if (vehicleItinerary.checkVehicle(demand)) {
                        notEnoughVehicles = true;
                        continue;
                    }
                    if (!notEnoughVehicles && vehicleItinerary.addDemandVehicle(demand)) {
                        break;
                    }
                }

                if (notEnoughVehicles) {
                    this.vehiclesUsed.add(this.vehicleInstance);
                    VehicleItinerary vehicleItinerary = new VehicleItinerary(this.vehicleInstance);
                    day.addItinerary(vehicleItinerary);
                    vehicleItinerary.addDemandVehicle(demand);
                    this.vehicleItineraries.add(vehicleItinerary);
                    this.vehicleItineraryManager.create(vehicleItinerary);
                }
            } else if (demand.getStateDemand() == 1) { // if demand is to be installed
                for (TechnicianItinerary technicianItinerary : this.technicianItineraries) {
                    if (technicianItinerary.addDemandTechnician(demand)) {
                        this.technicianItineraryManager.create(technicianItinerary);
                        break;
                    }
                }
            }
            this.plannedDemandManager.create(demand);
        }
    }

    public void bestTechnicianItinerarySolution(DayHorizon day) throws IOException {

        for (PlannedDemand demand : this.plannedDemands) {
            if (demand.getStateDemand() == 0) { // if demand is to be supplied
                boolean notEnoughVehicles = false;
                //we check each vehicleItinerary of the day
                // if there is enough room in the truck we add the demand to this itinerary
                // else we create a new used vehicle to carry the demand
                for (VehicleItinerary vehicleItinerary : this.vehicleItineraries) {
                    if (vehicleItinerary.checkVehicle(demand)) {
                        notEnoughVehicles = true;
                        continue;
                    }
                    if (!notEnoughVehicles && vehicleItinerary.addDemandVehicle(demand)) {
                        break;
                    }
                }

                if (notEnoughVehicles) {
                    this.vehiclesUsed.add(this.vehicleInstance);
                    VehicleItinerary vehicleItinerary = new VehicleItinerary(this.vehicleInstance);
                    day.addItinerary(vehicleItinerary);
                    vehicleItinerary.addDemandVehicle(demand);
                    this.vehicleItineraries.add(vehicleItinerary);
                    this.vehicleItineraryManager.create(vehicleItinerary);
                }
            } else if (demand.getStateDemand() == 1) { // if demand is to be installed
                double bestCost = Double.MAX_VALUE, tmpCost;
                int bestTechItinerary = 0;
                for (int itineraryIndex = 0; itineraryIndex < this.technicianItineraries.size(); itineraryIndex++) {
                    tmpCost = this.technicianItineraries.get(itineraryIndex).checkTechnician(demand);
                    if (bestCost > tmpCost) {
                        bestCost = tmpCost;
                        bestTechItinerary = itineraryIndex;

                    }
                }
                if (this.technicianItineraries.get(bestTechItinerary).addDemandTechnician(demand)) {
                    this.technicianItineraryManager.create(this.technicianItineraries.get(bestTechItinerary));
                }
            }
            this.plannedDemandManager.create(demand);
        }
    }

    public void bestVehicleItinerarySolution(DayHorizon day) throws IOException {

        for (PlannedDemand demand : this.plannedDemands) {
            if (demand.getStateDemand() == 0) { // if demand is to be supplied
                boolean notEnoughVehicles = false;
                //we check each vehicleItinerary of the day
                // if there is enough room in the truck we add the demand to this itinerary
                // else we create a new used vehicle to carry the demand
                double bestCost = Double.MAX_VALUE, tmpCost;
                int bestVehicleItinerary = 0;
                for (int itineraryIndex = 0; itineraryIndex < this.vehicleItineraries.size(); itineraryIndex++) {
                    if (this.vehicleItineraries.get(itineraryIndex).checkVehicle(demand)) {
                        notEnoughVehicles = true;
                        continue;
                    }
                    tmpCost = this.vehicleItineraries.get(itineraryIndex).computeDistanceDemands(demand);
                    if (bestCost > tmpCost) {
                        bestCost = tmpCost;
                        bestVehicleItinerary = itineraryIndex;
                    }
                }
                if (notEnoughVehicles) {
                    this.vehiclesUsed.add(this.vehicleInstance);
                    VehicleItinerary vehicleItinerary = new VehicleItinerary(this.vehicleInstance);
                    day.addItinerary(vehicleItinerary);
                    vehicleItinerary.addDemandVehicle(demand);
                    this.vehicleItineraries.add(vehicleItinerary);
                    this.vehicleItineraryManager.create(vehicleItinerary);
                } else {
                    this.vehicleItineraries.get(bestVehicleItinerary).addDemandVehicle(demand);
                }
            } else if (demand.getStateDemand() == 1) { // if demand is to be installed
                for (TechnicianItinerary technicianItinerary : this.technicianItineraries) {
                    if (technicianItinerary.addDemandTechnician(demand)) {
                        this.technicianItineraryManager.create(technicianItinerary);
                        break;
                    }
                }
            }
            this.plannedDemandManager.create(demand);
        }
    }

    public void bestItinerarySolution(DayHorizon day) throws IOException {
        for (PlannedDemand demand : this.plannedDemands) {
            if (demand.getStateDemand() == 0) { // if demand is to be supplied
                boolean notEnoughVehicles = false;
                //we check each vehicleItinerary of the day
                // if there is enough room in the truck we add the demand to this itinerary
                // else we create a new used vehicle to carry the demand
                double bestCost = Double.MAX_VALUE, tmpCost;
                int bestVehicleItinerary = 0;
                for (int itineraryIndex = 0; itineraryIndex < this.vehicleItineraries.size(); itineraryIndex++) {
                    if (this.vehicleItineraries.get(itineraryIndex).checkVehicle(demand)) {
                        notEnoughVehicles = true;
                        continue;
                    }
                    tmpCost = this.vehicleItineraries.get(itineraryIndex).computeDistanceDemands(demand);
                    if (bestCost > tmpCost) {
                        bestCost = tmpCost;
                        bestVehicleItinerary = itineraryIndex;
                    }
                }
                if (notEnoughVehicles) {
                   this.vehiclesUsed.add(this.vehicleInstance);
                    VehicleItinerary vehicleItinerary = new VehicleItinerary(this.vehicleInstance);
                    day.addItinerary(vehicleItinerary);
                    vehicleItinerary.addDemandVehicle(demand);
                    this.vehicleItineraries.add(vehicleItinerary);
                    this.vehicleItineraryManager.create(vehicleItinerary);
                } else {
                    this.vehicleItineraries.get(bestVehicleItinerary).addDemandVehicle(demand);
                }
            } else if (demand.getStateDemand() == 1) { // if demand is to be installed
                double bestCost = Double.MAX_VALUE, tmpCost;
                int bestTechItinerary = 0;
                for (int itineraryIndex = 0; itineraryIndex < this.technicianItineraries.size(); itineraryIndex++) {
                    tmpCost = this.technicianItineraries.get(itineraryIndex).checkTechnician(demand);
                    if (bestCost > tmpCost) {
                        bestCost = tmpCost;
                        bestTechItinerary = itineraryIndex;

                    }
                }
                if (this.technicianItineraries.get(bestTechItinerary).addDemandTechnician(demand)) {
                    this.technicianItineraryManager.create(this.technicianItineraries.get(bestTechItinerary));
                }
            }
            this.plannedDemandManager.create(demand);
        }
    }

    public void generateSolution(String algoName) throws IOException {

        // Initiate planning
        Planning planning = InitiatePlanning.createPlanning(this.instance, this.instanceManager, this.planningManager, this.demandManager, this.plannedDemandManager, algoName);

        //create the first sued vehicle
        vehiclesUsed.add(this.vehicleInstance);

        //get demands to plannified
        this.plannedDemands = planning.getPlannedDemands();
        // Starts requests sequencing
        for (int i = 1; i <= this.instance.getNbDays(); i++) {
            DayHorizon day = new DayHorizon(i);
            planning.addDayHorizon(day);
            this.daysManager.create(day);

            // Vehicle Itineraries setup depending on available vehiclesUsed
            for (Vehicle v : this.vehiclesUsed) {
                VehicleItinerary vehicleItinerary = new VehicleItinerary(v);
                day.addItinerary(vehicleItinerary);
                this.vehicleItineraries.add(vehicleItinerary);
                this.vehicleItineraryManager.create(vehicleItinerary);
            }

            // technician itineraries setup
            for (Technician t : this.technicianManager.findbyInstance(this.instance)) {
                TechnicianItinerary technicianItinerary = new TechnicianItinerary(t);
                day.addItinerary(technicianItinerary);
                this.technicianItineraries.add(technicianItinerary);
            }

            switch (algoName) {
                case "MinimalSolution":
                    this.minimalSolution(day);
                    break;
                case "BestTechnicianItinerarySolution":
                    this.bestTechnicianItinerarySolution(day);
                    break;
                case "BestVehicleItinerarySolution":
                    this.bestVehicleItinerarySolution(day);
                    break;
                case "BestItinerarySolution":
                    this.bestItinerarySolution(day);
                    break;

            }

            this.daysManager.update(day);
        }

        this.planningManager.update(planning);
        //System.out.println(planning);

        for (PlannedDemand demand : this.plannedDemands) {
            if (demand.getStateDemand() != 2) {
                System.out.println("ERREUR " + demand + " " + demand.getStateDemand());
            }
        }
    }
}
