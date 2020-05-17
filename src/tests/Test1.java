package tests;

import modele.Customer;
import modele.DayHorizon;
import modele.Demand;
import modele.Depot;
import modele.Machine;
import modele.Planning;
import modele.Technician;
import modele.Vehicle;
import modele.VehicleItinerary;

/**
 * Initial testing of the object model
 *
 * @author Lucas, Louis, Henri
 */
public class Test1 {

    public static void main(String[] args) {
        Depot d = new Depot(1, 0, 0);
        Customer c1 = new Customer(2, 10, 10, 10);
        Customer c2 = new Customer(3, -10, 10, 5);
        Customer c3 = new Customer(4, 10, -10, 10);

        d.addDestination(c1, 14.1);
        d.addDestination(c2, 14.1);
        d.addDestination(c3, 14.1);
        c1.addDestination(d, 14.1);
        c1.addDestination(c2, 20);
        c1.addDestination(c3, 20);
        c2.addDestination(d, 14.1);
        c2.addDestination(c1, 20);
        c2.addDestination(c3, 20);
        c3.addDestination(d, 14.1);
        c3.addDestination(c1, 20);
        c3.addDestination(c2, 20);

        Machine m1 = new Machine(1, 5, 100);
        Machine m2 = new Machine(2, 10, 200);

        c1.addDemand(1, 5, m1, 2);
        c1.addDemand(2, 3, m2, 2);
        c2.addDemand(2, 4, m1, 3);
        c3.addDemand(4, 8, m2, 1);

//        System.out.println(c1);
//        System.out.println(c2);
//        System.out.println(c3);

        // General attributes
        double technicianDistanceCost = 10,
                technicianDayCost = 10000,
                technicianCost = 10,
                truckCost = 1000,
                truckDayCost = 100,
                truckDistanceCost = 100000,
                truckMaxDistance = 960,
                truckCapacity = 100;

        Technician t1 = new Technician(1, 10, 0, 200, 500, technicianCost, technicianDistanceCost, technicianDayCost);
        Technician t2 = new Technician(2, 10, -10, 100, 300, technicianCost, technicianDistanceCost, technicianDayCost);
        t1.addPotentialInstallation(m1);
        t2.addPotentialInstallation(m2);

//        System.out.println(t1);
//        System.out.println(t2 + "\n");

        Vehicle v1 = new Vehicle(1, d, truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        Vehicle v2 = new Vehicle(2, d, truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);

        Planning p = new Planning(3);
        for (int i = 1; i <= p.getNbDays(); i++) {
            DayHorizon day = new DayHorizon(i);
            VehicleItinerary vehicleItinerary1 = new VehicleItinerary(v1);
            VehicleItinerary vehicleItinerary2 = new VehicleItinerary(v2);
            day.addVehiculeItinerary(vehicleItinerary1);
            day.addVehiculeItinerary(vehicleItinerary2);
            for (Demand d1 : c1.getCustomerDemands()) {
                if (!vehicleItinerary1.addDemandVehicle(d1)) {
                    vehicleItinerary2.addDemandVehicle(d1);
                }
            }
            for (Demand d2 : c2.getCustomerDemands()) {
                if (!vehicleItinerary1.addDemandVehicle(d2)) {
                    vehicleItinerary2.addDemandVehicle(d2);
                }
            }
            for (Demand d3 : c3.getCustomerDemands()) {
                if (!vehicleItinerary1.addDemandVehicle(d3)) {
                    vehicleItinerary2.addDemandVehicle(d3);
                }
            }
            

            // Todo : technician itineraries
            p.addDayHorizon(day);
        }

        System.out.println(p);

    }
}
