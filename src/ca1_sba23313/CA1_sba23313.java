/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca1_sba23313;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author palic
 */
public class CA1_sba23313 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    private static void writeDataToFile(String studentNumber, String secondName, int numberOfClasses) {
        // Writes the validated data to "status.txt" file
        String workload = typeWorkload(numberOfClasses);
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("status.txt", true))) {
            writer.write(studentNumber + " - " + secondName + "\n" + workload + "\n");
        } catch (Exception e) {
            System.out.println("An error occurred while writing to the file.");
        }
    }

}
