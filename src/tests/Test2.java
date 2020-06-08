package tests;

import algo.MinimalSolution;
import algo.ReadInstance;
import java.io.IOException;
import modele.*;

/**
 * Initial testing of the object model with an instance in a text file
 *
 * @author Henri, Lucas, Louis
 */
public class Test2 {

    public static void main(String[] args) throws IOException {
        Instance instance = ReadInstance.readInstance("A:\\Lucas\\Desktop\\testInstance.txt");
         //System.out.println(instance);

        /**
         * Minimal solution : 
         * - each request is supplied as soon as possible
         * - a technician installs each request the day after it's supplied
         * - no rest days for technicians
         */
        MinimalSolution.minimalSolution(instance);
        
        // More solutions coming soon
    }
}
