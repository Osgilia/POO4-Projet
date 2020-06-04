package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import modele.*;

/**
 * Initial testing of the object model with an instance in a text file
 *
 * @author Henri, Lucas, Louis
 */
public class Test2 {

    public static void main(String[] args) {
        /* try {
            Scanner input = new Scanner(new File("D:\\Documents\\_LE4\\POO\\PROJET\\test.txt"));
            input.useDelimiter("\n");
            String dataset = input.next().split(" = ")[1];
            String instanceName = input.next().split(" = ")[1];
            //Instance instance = new Instance(instanceName, dataset);
            //while(input.hasNext()) {
                
           // }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
        //Initialisation
        Instance instance = null;
        Planning planning = null;
        //Caractéristique des véhicules
        double  technicianDistanceCost = -1,
                technicianDayCost = -1,
                technicianCost = -1,
                truckCost = -1,
                truckDayCost = -1,
                truckDistanceCost = -1,
                truckMaxDistance = -1,
                truckCapacity = -1;
        
        try{
            BufferedReader in = new BufferedReader(new FileReader("D:\\Documents\\_LE4\\POO\\PROJET\\test.txt"));
            String line;
            String[] arg = null;
            String val1 = "";
            int nbMachines = -1;
            while ((line = in.readLine()) != null)
            {
                //System.out.println (line);
                arg = line.split(" = ");
                /*for(int i = 0; i < arg.length; i++){
                    System.out.println ("SPLIT: " + arg[i]);
                }*/
                switch(arg[0] ){
                    case "DATASET" : 
                        val1 = arg[1];
                        break;
                    case "NAME" :
                        if(val1 != ""){
                            //Instanciation de l'instance
                            instance = new Instance(val1,arg[1]);
                            System.out.println("Instance IMPORTED: named '" + arg[1] + "'");
                        }else{
                            System.err.println("Instance ERROR : DATASET value doesn't exist.");
                        }
                        break;
                    case "DAYS" :
                        //Instanciation du planning
                        planning = new Planning(Integer.parseInt(arg[1]));
                        if(planning.getNbDays() == Integer.parseInt(arg[1]) && Integer.parseInt(arg[1]) > 0){
                            System.out.println("Planning IMPORTED: composed of "+arg[1]+" days.");
                        }else{
                            System.err.println("Planning ERROR: during the creation.");
                        }
                        break;
                    case "TRUCK_CAPACITY" :
                        truckCapacity = Integer.parseInt(arg[1]);
                        break;
                    case "TRUCK_MAX_DISTANCE" :
                        truckMaxDistance = Integer.parseInt(arg[1]);
                        break;
                    case "TRUCK_DISTANCE_COST" :
                        truckDistanceCost = Integer.parseInt(arg[1]);
                        break;
                    case "TRUCK_DAY_COST" :
                        truckDayCost = Integer.parseInt(arg[1]);
                        break;
                    case "TRUCK_COST" :
                        truckCost = Integer.parseInt(arg[1]);
                        break;
                    case "TECHNICIAN_DISTANCE_COST" :
                        technicianDistanceCost = Integer.parseInt(arg[1]);
                        break;
                    case "TECHNICIAN_DAY_COST" :
                        technicianDayCost = Integer.parseInt(arg[1]);
                        break;
                    case "TECHNICIAN_COST" :
                        technicianCost = Integer.parseInt(arg[1]);
                        break;
                    case "MACHINES" :
                        nbMachines = Integer.parseInt(arg[1]);
                        for(int i=0; i < nbMachines; i++){
                            line = in.readLine();
                            String[] argument = line.split(" ");
                            //Instanciation de la machine
                            int     id = Integer.parseInt(argument[0]),
                                    size = Integer.parseInt(argument[1]),
                                    penalty = Integer.parseInt(argument[2]);
                            Machine machine = new Machine(id, size, penalty);
                            instance.addMachine(machine);
                        }
                        break;
                    case "LOCATIONS" :
                        for(int i=0; i < Integer.parseInt(arg[1]); i++){
                            line = in.readLine();
                            String[] argument = line.split(" ");
                            if(Integer.parseInt(argument[0]) == 1){
                                Depot d = new Depot(1, Integer.parseInt(argument[1]), Integer.parseInt(argument[2]));
                            }
                            //Instanciation des points
                            int     id = Integer.parseInt(argument[0]),
                                    x = Integer.parseInt(argument[1]),
                                    y = Integer.parseInt(argument[2]);
                            Customer customer = new Customer(id, x, y);
                            instance.addPoint(customer);
                        }
                        break;
                    case "REQUESTS" :
                        for(int i=0; i < Integer.parseInt(arg[1]); i++){
                            line = in.readLine();
                            String[] argument = line.split(" ");
                            if(Integer.parseInt(argument[0]) == 1){
                                Depot d = new Depot(1, Integer.parseInt(argument[1]), Integer.parseInt(argument[2]));
                            }
                            //Instanciation des demandes
                            int     id = Integer.parseInt(argument[0]),
                                    idcustomer = Integer.parseInt(argument[1]),
                                    firstDay = Integer.parseInt(argument[2]),
                                    lastDay = Integer.parseInt(argument[3]),
                                    machine = Integer.parseInt(argument[4]),
                                    nbMachCmd = Integer.parseInt(argument[5]);
                            // TO DO
                        }
                        
                        break;
                    case "TECHNICIANS" :
                        for(int i=0; i < Integer.parseInt(arg[1]); i++){
                            line = in.readLine();
                            String[] argument = line.split(" ");
                            if(Integer.parseInt(argument[0]) == 1){
                                Depot d = new Depot(1, Integer.parseInt(argument[1]), Integer.parseInt(argument[2]));
                            }
                            //Instanciation des demandes
                            int     id = Integer.parseInt(argument[0]),
                                    idtech = Integer.parseInt(argument[1]),
                                    distanceMax = Integer.parseInt(argument[2]),
                                    demandMax = Integer.parseInt(argument[3]);
                            // TO DO
                            for(int j=4; j < nbMachines+3; j++){
                                if(Integer.parseInt(argument[i]) == 1){
                                    // Peut installer
                                }else{
                                    // NE PEUT PAS installer
                                }
                            }
                        }
                        
                        break;
                }
               
            }
            in.close();
            if(truckCapacity == -1 || truckMaxDistance == -1 || truckDistanceCost == -1 || truckDayCost == -1 || truckCost == -1){
                System.err.println("TRUCK ERROR: during the creation of caracteristics.");
            }else{
                System.out.println("TRUCK CARACTERISTICS IMPORTED");
            }
            if(technicianDistanceCost == -1 || technicianDayCost == -1 || technicianCost == -1){
                System.err.println("TECHNICIAN ERROR: during the creation of caracteristics.");
            }else{
                System.out.println("TECHNICIAN CARACTERISTICS IMPORTED");
            }
            System.out.println(instance.toString());
        }catch(Exception e){
            System.err.println(e.toString());
            System.out.println("ERROR");
        }
    }
}
