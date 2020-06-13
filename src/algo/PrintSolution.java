package algo;

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

    public static void print(Instance instance, Planning planning) throws IOException {
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
        FileWriter fileToPrint = new FileWriter("A:\\lucas\\Desktop\\solution.txt");
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
            printWriter.printf("TOTAL_COST = %d\n", totalCost);

            for (DayHorizon day : planning.getDays()) {
                printWriter.printf("DAY = %d\n", day.getDayNumber());

                printWriter.printf("NUMBER_OF_TRUCKS  = %d\n", computeTruckUsed(day.getItineraries()));
                printWriter.printf(displayTruckActivity(day.getItineraries()));

                printWriter.printf("NUMBER_OF_TECHNICIANS  = %d\n", computeTechnicianUsed(day.getItineraries()));
                printWriter.printf(displayTechniciansActivity(day.getItineraries()));

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

    public static int computeTruckUsed(List<Itinerary> itineraries) {
        int trucksUsed = 0;
        for (Itinerary i : itineraries) {
            if (i instanceof VehicleItinerary) {
                if (((VehicleItinerary) i).getCustomersDemands().size() > 0) {
                    trucksUsed++;
                }
            }
        }
        return trucksUsed;
    }

    public static int computeTechnicianUsed(List<Itinerary> itineraries) {
        int techniciansUsed = 0;
        for (Itinerary i : itineraries) {
            if (i instanceof TechnicianItinerary) {
                if (((TechnicianItinerary) i).getCustomersDemands().size() > 0) {
                    techniciansUsed++;
                }
            }
        }
        return techniciansUsed;
    }

    public static String displayTruckActivity(List<Itinerary> itineraries) {
        int index = 0;
        String str = "";
        for (Itinerary i : itineraries) {
            if (i instanceof VehicleItinerary) {
                index++;
                if (((VehicleItinerary) i).getCustomersDemands().size() > 0) {
                    str += index;
                    for (PlannedDemand d : ((VehicleItinerary) i).getCustomersDemands()) {
                        str += " " + d.getDemand().getId();
                    }
                    str += "\n";
                }
            }
        }
        return str;
    }
    
        public static String displayTechniciansActivity(List<Itinerary> itineraries) {
        int index = 0;
        String str = "";
        for (Itinerary i : itineraries) {
            if (i instanceof TechnicianItinerary) {
                if (((TechnicianItinerary) i).getCustomersDemands().size() > 0) {
                    str += ((TechnicianItinerary) i).getTechnician().getId();
                    for (PlannedDemand d : ((TechnicianItinerary) i).getCustomersDemands()) {
                        str += " " + d.getDemand().getId();
                    }
                    str += "\n";
                }
            }
        }
        return str;
    }

}
