package algo;

import modele.Instance;
import modele.Planning;

/**
 * Outputs the solution to a text file
 *
 * @author Henri, Lucas, Louis
 */
public class PrintSolution {

    public static void print(Instance instance, Planning planning) {
        String dataset = instance.getDataset(), instanceName = instance.getName();
        
        int truckDistance = planning.computeTruckDistance(), 
                truckDays = planning.computeNbTruckDays(),
                trucksUsed = planning.computeMaxTrucksUsed(),
                technicianDistance = planning.computeTechnicianDistance(),
                technicianDays = planning.computeNbTechnicianDays(),
                techniciansUsed = planning.computeTotalNbTechniciansUsed(),
                idleMachineCosts = planning.computeIdleMachineCosts(),
                totalCost = (int) (planning.getCost());
        
        // Outputs the solution in text file
        // For each day in planning ..
    }
}
