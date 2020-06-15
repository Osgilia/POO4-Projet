package algo;

import dao.DaoFactory;
import dao.PersistenceType;
import dao.PlannedDemandDao;
import dao.VehicleItineraryDao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import modele.DayHorizon;
import modele.Instance;
import modele.Itinerary;
import modele.PlannedDemand;
import modele.Planning;
import modele.Point;
import modele.TechnicianItinerary;
import modele.VehicleItinerary;

/**
 * Outputs the solution to a text file
 *
 * @author Henri, Lucas, Louis
 */
public class PrintSolution {

    public static void print(Instance instance, Planning planning, String path) throws IOException {
        String dataset = instance.getDataset(), instanceName = instance.getName();
        DaoFactory factory = DaoFactory.getDaoFactory(PersistenceType.Jpa);

        VehicleItineraryDao vehicleItineraryManager = factory.getVehicleItineraryDao();
        PlannedDemandDao plannedDemandManager = factory.getPlannedDemandDao();

        int truckDistance = planning.computeTruckDistance(),
                truckDays = planning.computeNbTruckDays(),
                trucksUsed = planning.computeMaxTrucksUsed(),
                technicianDistance = planning.computeTechnicianDistance(),
                technicianDays = planning.computeNbTechnicianDays(),
                techniciansUsed = planning.computeTotalNbTechniciansUsed(),
                idleMachineCosts = planning.computeIdleMachineCosts();
        
        double totalCost = planning.getCost() + (double) idleMachineCosts;
        // Outputs the solution in text file
        // For each day in planning ..
        FileWriter fileToPrint = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileToPrint);
        try {

            printWriter.printf("DATASET = %s\nNAME = %s\n", dataset, instanceName);

            printWriter.printf("TRUCK_DISTANCE = %d\n", truckDistance);
            printWriter.printf("NUMBER_OF_TRUCK_DAYS = %d\n", truckDays);
            printWriter.printf("NUMBER_OF_TRUCKS_USED = %d\n", trucksUsed);
            printWriter.printf("TECHNICIAN_DISTANCE = %d\n", technicianDistance);
            printWriter.printf("NUMBER_OF_TECHNICIAN_DAYS = %d\n", technicianDays);
            printWriter.printf("NUMBER_OF_TECHNICIANS_USED = %d\n", techniciansUsed);
            printWriter.printf("IDLE_MACHINE_COSTS  = %d\n", idleMachineCosts);
            printWriter.printf("TOTAL_COST = %s\n", String.format("%.0f", totalCost));

            for (DayHorizon day : planning.getDays()) {
                printWriter.printf("DAY = %d\n", day.getDayNumber());

                printWriter.printf("NUMBER_OF_TRUCKS  = %d\n", day.computeTruckUsed());
                printWriter.printf(day.displayTruckActivity());

                printWriter.printf("NUMBER_OF_TECHNICIANS  = %d\n", day.computeTechnicianUsed());
                printWriter.printf(day.displayTechniciansActivity());

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileToPrint != null) {
                    fileToPrint.flush();
                    printWriter.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
