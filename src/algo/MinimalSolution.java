package algo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import modele.DayHorizon;
import modele.Instance;
import modele.PlannedDemand;
import modele.Planning;
import modele.Technician;
import modele.TechnicianItinerary;
import modele.Vehicle;
import modele.VehicleItinerary;

/**
 * Sequence schedule of a minimal solution
 *
 * @author Henri, Lucas, Louis
 */
public class MinimalSolution {

    public static void minimalSolution(Instance instance) {
        Planning planning = instance.getLastPlanning();
        Vehicle vehicleInstance = instance.getVehicle();
        double truckCost = vehicleInstance.getUsageCost(),
                truckDayCost = vehicleInstance.getDayCost(),
                truckDistanceCost = vehicleInstance.getDistanceCost(),
                truckMaxDistance = vehicleInstance.getDistanceMax(),
                truckCapacity = vehicleInstance.getCapacity();

        Set<Vehicle> vehicles = new HashSet<>();
        Vehicle firstVehicle = new Vehicle(1, instance.getDepot(), truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        vehicles.add(firstVehicle);

        for (int i = 1; i <= planning.getNbDays(); i++) {
            DayHorizon day = new DayHorizon(i);
            planning.addDayHorizon(day);
            Set<VehicleItinerary> vehicleItineraries = new HashSet<>();
            for (Vehicle v : vehicles) {
                VehicleItinerary vehicleItinerary = new VehicleItinerary(v);
                day.addItinerary(vehicleItinerary);
                vehicleItineraries.add(vehicleItinerary);
            }
            Set<TechnicianItinerary> technicianItineraries = new HashSet<>();
            for (Technician t : instance.getTechnicians()) {
                TechnicianItinerary technicianItinerary = new TechnicianItinerary(t);
                day.addItinerary(technicianItinerary);
                technicianItineraries.add(technicianItinerary);
            }
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
                        Vehicle lastVehicle = new Vehicle(vehicles.size() + 1, instance.getDepot(), truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
                        vehicles.add(lastVehicle);
                        VehicleItinerary vehicleItinerary = new VehicleItinerary(lastVehicle);
                        day.addItinerary(vehicleItinerary);
                        vehicleItinerary.addDemandVehicle(demand.getKey());
                        vehicleItineraries.add(vehicleItinerary);
                    }
                }
                if (demand.getValue() == 1) { // if demand is to be installed
                    for (TechnicianItinerary technicianItinerary : technicianItineraries) {
                        if (technicianItinerary.addDemandTechnician(demand.getKey())) {
                            break;
                        }
                    }
                }
            }
        }

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
