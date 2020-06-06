package tests;

import java.util.Map;
import modele.*;

/**
 * Initial testing of the object model
 *
 * @author Lucas, Louis, Henri
 */
public class Test1 {

    public static void main(String[] args) {
        // Création de l'instance
        Instance instance = new Instance("Nom1","Dataset1");
        
       // Création d'un planning de 3 jours
        Planning p = new Planning(instance, 3);
        
        // Caractéristique des véhicules
        double technicianDistanceCost = 10,
                technicianDayCost = 500,
                technicianCost = 10,
                truckCost = 1000,
                truckDayCost = 100,
                truckDistanceCost = 10,
                truckMaxDistance = 960,
                truckCapacity = 60;
        
        // Création des machines
        MachineType m1 = new MachineType(1, 5, 100, instance);
        MachineType m2 = new MachineType(2, 10, 200, instance);
        
        // Création du dépot
        Depot d = new Depot(1, 0, 0, instance);
        
        // Création des clients
        Customer c1 = new Customer(2, 10, 10, instance);
        Customer c2 = new Customer(3, -10, 10, instance);
        Customer c3 = new Customer(4, 10, -10, instance);
        
        // Création des véhicules
        Vehicle v1 = new Vehicle(1, d, truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        Vehicle v2 = new Vehicle(2, d, truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        
        // Création des techniciens
        Technician t1 = new Technician(5, 10, 0, 200, 4, technicianCost, technicianDistanceCost, technicianDayCost, instance);
        Technician t2 = new Technician(6, 10, -10, 100, 4, technicianCost, technicianDistanceCost, technicianDayCost, instance);
        
        // Habilitation des techniciens à travailler sur certains type de machine
        t1.addAccreditation(m1);
        t2.addAccreditation(m2);
        
        // Affectation des Machines, Véhicules et Points à l'instance
        
        // Création des routes
        d.addDestination(c1, 15);
        d.addDestination(c2, 15);
        d.addDestination(c3, 15);
        c1.addDestination(d, 15);
        c1.addDestination(c2, 20);
        c1.addDestination(c3, 20);
        c1.addDestination(t1, c1.computeDistance(t1));
        c1.addDestination(t2, c1.computeDistance(t2));
        c2.addDestination(d, 15);
        c2.addDestination(c1, 20);
        c2.addDestination(c3, 20);
        c2.addDestination(t1, c2.computeDistance(t1));
        c2.addDestination(t2, c2.computeDistance(t2));
        c3.addDestination(d, 15);
        c3.addDestination(c1, 20);
        c3.addDestination(c2, 20);
        c3.addDestination(t1, c3.computeDistance(t1));
        c3.addDestination(t2, c3.computeDistance(t2)); // same position as t2
        t1.addDestination(c1, t1.computeDistance(c1));
        t1.addDestination(c2, t1.computeDistance(c2));
        t1.addDestination(c3, t1.computeDistance(c3));
        t2.addDestination(c1, t2.computeDistance(c1));
        t2.addDestination(c2, t2.computeDistance(c2));
        t2.addDestination(c3, t2.computeDistance(c3));
        
        c1.addDemand(1, 5, m1, 1, p);
        c1.addDemand(1, 3, m2, 2, p);
        c2.addDemand(2, 4, m1, 3, p);
        c3.addDemand(2, 8, m2, 2, p);
        c3.addDemand(2, 8, m1, 4, p);
        
        //Création des jours de l'horizon
        for (int i = 1; i <= p.getNbDays(); i++) {
            DayHorizon day = new DayHorizon(i);

            VehicleItinerary vehicleItinerary1 = new VehicleItinerary(v1);
            VehicleItinerary vehicleItinerary2 = new VehicleItinerary(v2);
            day.addItinerary(vehicleItinerary1);
            day.addItinerary(vehicleItinerary2);

            TechnicianItinerary technicianItinerary1 = new TechnicianItinerary(t1);
            TechnicianItinerary technicianItinerary2 = new TechnicianItinerary(t2);
            day.addItinerary(technicianItinerary1);
            day.addItinerary(technicianItinerary2);

            p.addDayHorizon(day);

            for (Map.Entry<PlannedDemand, Integer> demand : p.getDemands().entrySet()) {
                if (demand.getValue() == 0) { // if demand is to be supplied
                    if (!vehicleItinerary1.addDemandVehicle(demand.getKey())) {
                        vehicleItinerary2.addDemandVehicle(demand.getKey());
                    }
                }
                if (demand.getValue() == 1) { // if demand is to be installed
                    if (!technicianItinerary1.addDemandTechnician(demand.getKey())) {
                        technicianItinerary2.addDemandTechnician(demand.getKey());
                    }
                }
            }
        }
        
        System.out.println(p);

    }
}
