package tests;

import java.io.File;
import java.util.Scanner;
import modele.Customer;
import modele.Depot;
import modele.Instance;
import modele.Machine;
import modele.Planning;
import modele.Technician;
import modele.Vehicle;

/**
 * Initial testing of the object model
 *
 * @author Lucas, Louis, Henri
 */
public class Test1 {

    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(new File("A:\\lucas\\Desktop\\VSC2019_ORTEC_early_01_easy.txt"));
            input.useDelimiter("\n");
            String dataset = input.next().split(" = ")[1];
            String instanceName = input.next().split(" = ")[1];
            Instance instance = new Instance(instanceName, dataset);
//            while(input.hasNext()) {
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Essais : à compléter avec fichier texte
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

        System.out.println(c1);
        System.out.println(c2);

        double technicianDistanceCost = 10,
                technicianDayCost = 10000,
                technicianCost = 10,
                truckCost = 1000,
                truckDayCost = 100,
                truckDistanceCost = 100000,
                truckMaxDistance = 960,
                truckCapacity = 18;

        Vehicle v1 = new Vehicle(1, d, truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);
        Vehicle v2 = new Vehicle(2, d, truckCapacity, truckMaxDistance, truckDistanceCost, truckDayCost, truckCost);

        Technician t1 = new Technician(1, 10, 0, 200, 500, technicianCost, technicianDistanceCost, technicianDayCost);
        Technician t2 = new Technician(2, 10, -10, 100, 300, technicianCost, technicianDistanceCost, technicianDayCost);

        t1.addPotentialInstallation(m1);
        t2.addPotentialInstallation(m2);

        System.out.println(t1);
        System.out.println(t2);

    }
}
