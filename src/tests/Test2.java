package tests;

import java.io.File;
import java.util.Scanner;
import modele.Instance;

/**
 * Initial testing of the object model with an instance in a text file
 *
 * @author Henri, Lucas, Louis
 */
public class Test2 {

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
    }
}
