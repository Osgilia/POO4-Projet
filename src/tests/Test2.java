package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import modele.Instance;
import modele.Planning;

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
        
        //Init
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
            String val1 = "", val2 = "", val3 = "";
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
                            instance = new Instance(val1,arg[1]);
                            System.out.println("Instance CREATED: named '" + arg[1] + "'");
                        }else{
                            System.err.println("Instance ERROR : DATASET value doesn't exist.");
                        }
                        break;
                    case "DAYS" :
                        planning = new Planning(Integer.parseInt(arg[1]));
                        if(planning.getNbDays() == Integer.parseInt(arg[1]) && Integer.parseInt(arg[1]) > 0){
                            System.out.println("Planning CREATED: composed of "+arg[1]+" days.");
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
                }
               
            }
            in.close();
        }catch(Exception e){
            System.err.println(e.toString());
            System.out.println("ERROR");
        }
    }
}
