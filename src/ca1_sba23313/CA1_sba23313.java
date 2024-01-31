/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca1_sba23313;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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

    private static boolean validateData(String firstName, String secondName, int numberOfClasses, String studentNumber) {
        // Validates the input data based on given criteria
        if (!firstName.matches("[a-zA-Z]+")) {
            displayErrorMessage("Invalid first name. The first name should only contain letters (A-Z, a-z)");
            return false;
        }
        if (!secondName.matches("[a-zA-Z0-9 ]+")) {
            displayErrorMessage("Invalid second name. The second name can contain letters (A-Z, a-z), numbers (0-9), and spaces.");
            return false;
        }
        if (numberOfClasses < 1 || numberOfClasses > 8) {
            displayErrorMessage("Invalid number of classes. Please choose from 1 to 8.");
            return false;
        }
        // Check student number format
        if (!studentNumber.matches("^\\d{2}[a-zA-Z]{2,3}\\d+$")) {
            displayErrorMessage("Invalid student number format. The format should be: 2 digits, followed by 2 to 3 letters, and then numbers.");
            return false;
        }

        // Check if the year in the student number is 20 or higher
        int startingYear = Integer.parseInt(studentNumber.substring(0, 2));
        if (startingYear < 20) {
            displayErrorMessage("Student number year must start with 20 or higher");
            return false;
        }

        // Check if the numeric part at the end is between 1 and 200
        String numericPart = studentNumber.replaceAll("[^0-9]", "");
        int numericPartValue = Integer.parseInt(numericPart.substring(2));
        if (numericPartValue < 1 || numericPartValue > 200) {
            displayErrorMessage("Student number's numeric part must be between 1 and 200");
            return false;
        }
        return true;
    }

    private static void processFileData(String filePath) {
        // Reads and processes student data
        try ( BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String fullName;
            while ((fullName = br.readLine()) != null) {
                // Check if the full name is valid
                if (fullName.trim().isEmpty() || !fullName.contains(" ")) {
                    System.out.println("Invalid or missing full name. Skipping to next record.");
                    br.readLine();
                    br.readLine();
                    continue;
                }

                // Check and validate the class number
                String classNumberString = br.readLine();
                if (classNumberString == null || classNumberString.trim().isEmpty()) {
                    System.out.println("Missing or invalid number of classes for student: " + fullName + ". Skipping to next record.");
                    br.readLine();
                    continue;
                }

                // Check and validate the student number
                String studentNumber = br.readLine();
                if (studentNumber == null || studentNumber.trim().isEmpty()) {
                    System.out.println("Missing student number for student: " + fullName + ". Skipping to next record.");
                    continue;
                }

                String[] names = fullName.trim().split(" ");
                String firstName = names[0];
                String secondName = names[1];
                int numberOfClasses = Integer.parseInt(classNumberString.trim());

                // Validate the data and write to file if valid
                if (validateData(firstName, secondName, numberOfClasses, studentNumber.trim())) {
                    writeDataToFile(studentNumber.trim(), secondName, numberOfClasses);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in file. Please check the number of classes or student number format.");
        }
    }

    private static void addData(Scanner scanner) {
        // Allows the user to add data directly via the console
        System.out.println("Enter student details: ");

        // Get and validate the first name
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        if (!firstName.matches("[a-zA-Z]+")) {
            displayErrorMessage("Invalid first name. The first name should only contain letters.");
            return;
        }

        // Get and validate the second name
        System.out.print("Second Name: ");
        String secondName = scanner.nextLine();
        if (!secondName.matches("[a-zA-Z0-9 ]+")) {
            displayErrorMessage("Invalid second name. The second name can contain letters and numbers.");
            return;
        }

        // Get and validate the number of classes
        System.out.print("Number of Classes (1-8): ");
        if (!scanner.hasNextInt()) {
            displayErrorMessage("Invalid input for number of classes. Please enter an integer.");
            scanner.nextLine();
            return;
        }
        int numberOfClasses = scanner.nextInt();
        scanner.nextLine();

        if (numberOfClasses < 1 || numberOfClasses > 8) {
            displayErrorMessage("Invalid number of classes. Please choose from 1 to 8.");
            return;
        }

        // Get and validate the student number
        System.out.print("Student Number (ex: 25DAP123): ");
        String studentNumber = scanner.nextLine();
        if (validateData(firstName, secondName, numberOfClasses, studentNumber)) {
            writeDataToFile(studentNumber, secondName, numberOfClasses);
        }

}
