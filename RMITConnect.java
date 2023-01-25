import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RMITConnect {
    private String[] facultyName;
    private String[] facultyStaffName;
    private int[] facultyExtension;
    private String[] appointmentDate;
    private String[] appointmentTime;
    private String[] appointmentDepartment;
    private String[] appointmentName;
    private String[] queryQuestion;
    private String[] queryKeyword;
    private String[] queryDepartment;
    private int[] queryExtension;
    private Scanner keyboard;

    public RMITConnect() {
        this.keyboard = new Scanner(System.in);
        this.initialiseArrays();
        String userMainMenuChoice;
        String welcomeText = "Welcome to RMIT Connect\n";
        welcomeText += "==========================";
        System.out.println(welcomeText);

        do {
            userMainMenuChoice = this.getMainMenuChoice();
            if (userMainMenuChoice.equalsIgnoreCase("A")) {
                this.displayStudentDetails("studentdetails.csv");
            } else if (userMainMenuChoice.equalsIgnoreCase("B")) {
                this.displayFacultyChoice();
            } else if (userMainMenuChoice.equalsIgnoreCase("C")) {
                this.displayAcademicHistory("academichistory.csv");
            } else if (userMainMenuChoice.equalsIgnoreCase("D")) {
                this.appointmentsMenu("appointments.csv");
            } else if (userMainMenuChoice.equalsIgnoreCase("E")) {
                this.customerServiceQAndA();
            } else {
                System.out.println("Goodbye.");
            }
        } while (!userMainMenuChoice.equalsIgnoreCase("X"));
    }

    private void initialiseArrays() {
        this.populateFacultyArrays("facultycontacts.csv");
        this.populateAppointmentArrays("appointments.csv");
        this.populateQueryArrays("generalCustomerServiceQA.csv");
    }

    private String getMainMenuChoice() {
        String userChoice;
        String mainMenu = "\nMenu\n";
        mainMenu += "==========================\n";
        mainMenu += "[A] Check Enrolment\n";
        mainMenu += "[B] Academic Faculties\n";
        mainMenu += "[C] Academic Results\n";
        mainMenu += "[D] Appointments\n";
        mainMenu += "[E] Something Else\n";
        mainMenu += "[X] Exit\n";
        mainMenu += "==========================\n";
        mainMenu += "Enter your choice : ";

        userChoice = this.getUserInput(mainMenu);
        while (!userChoice.equalsIgnoreCase("A") &&
                !userChoice.equalsIgnoreCase("B") &&
                !userChoice.equalsIgnoreCase("C") &&
                !userChoice.equalsIgnoreCase("D") &&
                !userChoice.equalsIgnoreCase("E") &&
                !userChoice.equalsIgnoreCase("X")) {
            userChoice = this.getUserInput("No such option, Reenter: ");
        }
        return userChoice;
    }

    private void displayStudentDetails(String filename) {
        String field, userDetail;
        String borderDecoration = "......................................";
        BufferedReader objReader;
        try {
            String line;
            objReader = new BufferedReader(new FileReader(filename));
            System.out.println("\nCustomer Details\n" + borderDecoration);
            while ((line = objReader.readLine()) != null) {
                String[] values = line.split(",");
                field = values[0];
                userDetail = values[1];
                System.out.printf("%-20s%-20s\n", field, userDetail);
            }
            System.out.println(borderDecoration + "\n");
            objReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find " + filename + ".");
        } catch (IOException ex) {
            System.out.println("Sorry, a serious issue has occurred.");
        }
    }

    private void displayFacultyChoice() {
        int i = this.getFacultyChoice() - 1;
        if (i < this.facultyName.length) {
            String facultyChoiceDisplay = "\nHere is your faculty contact: \n";
            facultyChoiceDisplay += "...............................\n";
            facultyChoiceDisplay += "Faculty: " + this.facultyName[i] + "\n";
            facultyChoiceDisplay += "Staff Name: " + this.facultyStaffName[i];
            facultyChoiceDisplay += "\nExtension Number: ";
            facultyChoiceDisplay += this.facultyExtension[i] + "\n";
            facultyChoiceDisplay += "...............................\n";
            System.out.println(facultyChoiceDisplay);
        }
    }

    private int getFacultyChoice() {
        int userChoice;
        int i = 0;
        int backVariable;
        String facultyMenu = "\nAcademic Faculties\n";
        facultyMenu += "==================================\n";

        while (i < this.facultyName.length) {
            facultyMenu += "[" + (i + 1) + "] " + this.facultyName[i] + "\n";
            i++;
        }
        backVariable = i + 1;
        facultyMenu += "[" + backVariable + "] " + "Back" + "\n";
        facultyMenu += "==================================\n";
        facultyMenu += "Enter your choice: ";
        userChoice = this.getInteger(facultyMenu);

        while ((userChoice > backVariable) || (userChoice < 1)) {
            userChoice = this.getInteger("No such option, Reenter: ");
        }
        return userChoice;
    }

    private void populateFacultyArrays(String filename) {
        int arraySize = this.countRows(filename);
        if (arraySize  > 0) {
            this.facultyName = new String[arraySize];
            this.facultyStaffName = new String[arraySize];
            this.facultyExtension = new int[arraySize];
            BufferedReader objReader;
            try {
                String line;
                objReader = new BufferedReader(new FileReader(filename));
                int i = 0;
                while (((line = objReader.readLine()) != null) &&
                        (i < this.facultyName.length)) {
                    String[] values = line.split(",");
                    this.facultyName[i] = values[0];
                    this.facultyStaffName[i] = values[1];
                    this.facultyExtension[i] = Integer.parseInt(values[2]);
                    i++;
                }
                objReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find " + filename + ".");
            } catch (IOException ex) {
                System.out.println("Sorry, a serious issue has occurred.");
            }
        } else {
            this.facultyName = new String[1];
            this.facultyStaffName = new String[1];
            this.facultyExtension = new int[1];
        }
    }

    private void displayAcademicHistory(String filename) {
        String studyPeriod, year, courseMark, courseCode, courseName,
                courseGrade;
        BufferedReader objReader;
        try {
            String line;
            objReader = new BufferedReader(new FileReader(filename));
            System.out.printf("%-14s %-6s %-13s %-55s %-14s %-13s\n",
                    "Study Period", "Year", "Course Code", "Course Name",
                    "Course Grade", "Course Mark");
            while ((line = objReader.readLine()) != null) {
                String[] values = line.split(",");
                studyPeriod = values[0];
                year = values[1];
                courseCode = values[2];
                courseName = values[3];
                courseGrade = values[4];
                courseMark = values[5];
                System.out.printf("%-14s %-6s %-13s %-55s %-14s %-13s\n",
                        studyPeriod, year, courseCode, courseName, courseGrade,
                        courseMark);
            }
            objReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find " + filename + ".");
        } catch (IOException ex) {
            System.out.println("Sorry, a serious issue has occurred.");
        }
    }

    private void appointmentsMenu(String filename) {
        String userChoice;
        do {
            userChoice = this.getAppointmentsChoice();
            if (userChoice.equalsIgnoreCase("A")) {
                this.displayAppointments();
            } else if (userChoice.equalsIgnoreCase("B")) {
                this.makeAppointment(filename);
            }
        } while (!userChoice.equalsIgnoreCase("X"));
    }

    private String getAppointmentsChoice() {
        String userChoice;
        String appointmentsMenu = "\nAppointments\n";
        appointmentsMenu += "=====================\n";
        appointmentsMenu += "[A] Check Appointments \n";
        appointmentsMenu += "[B] Make Appointment\n";
        appointmentsMenu += "[X] Back to Main Menu\n";
        appointmentsMenu += "=====================\n";
        appointmentsMenu += "Enter your choice: ";

        userChoice = this.getUserInput(appointmentsMenu);
        while ((!userChoice.equalsIgnoreCase("A") &&
                !userChoice.equalsIgnoreCase("B") &&
                !userChoice.equalsIgnoreCase("X"))) {
            userChoice = this.getUserInput("No such option, Reenter: ");
        }
        return userChoice;
    }

    private void populateAppointmentArrays(String filename) {
        int arraySize = this.countRows(filename);
        if (arraySize > 0) {
            this.appointmentDate = new String[arraySize];
            this.appointmentTime = new String[arraySize];
            this.appointmentDepartment = new String[arraySize];
            this.appointmentName = new String[arraySize];
            BufferedReader objReader;
            try {
                String line;
                objReader = new BufferedReader(new FileReader(filename));
                int i = 0;

                while (((line = objReader.readLine()) != null) &&
                        (i < this.appointmentDate.length)) {
                    String[] values = line.split(",");
                    this.appointmentDate[i] = values[0];
                    this.appointmentTime[i] = values[1];
                    this.appointmentDepartment[i] = values[2];
                    this.appointmentName[i] = values[3];
                    i++;
                }
                objReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find " + filename + ".");
            } catch (IOException ex) {
                System.out.println("Sorry, a serious issue has occurred.");
            }
        } else {
            this.appointmentDate = new String[1];
            this.appointmentTime = new String[1];
            this.appointmentDepartment = new String[1];
            this.appointmentName = new String[1];
        }
    }

    private void displayAppointments() {
        System.out.printf("\n%-20s%-20s%-20s%-20s\n", "Date", "Time",
                "Department", "Name");
        int i = 0;
        while (i < this.appointmentDate.length) {
            System.out.printf("%-19s%-20s%-20s%-20s\n",
                    this.appointmentDate[i], this.appointmentTime[i],
                    this.appointmentDepartment[i], this.appointmentName[i]);
            i++;
        }
    }

    private void makeAppointment(String filename) {
        String date, time, department, name;
        date = this.getUserInput("Enter the date: ");
        time = this.getUserInput("Enter the time: ");
        department = this.getUserInput("Enter the department: ");
        name = this.getUserInput("Enter the staff name: ");

        int arrayLength = 0;
        if (this.appointmentDate[0] != null){
            arrayLength++;
        }

        String[] tempDate = new String[this.appointmentDate.length +
                arrayLength];
        String[] tempTime = new String[this.appointmentTime.length +
                arrayLength];
        String[] tempDepartment = new String[this.appointmentDepartment.length +
                arrayLength];
        String[] tempName = new String[this.appointmentName.length +
                arrayLength];

        int i = 0;
        while (i < this.appointmentDate.length) {
            tempDate[i] = this.appointmentDate[i];
            tempTime[i] = this.appointmentTime[i];
            tempDepartment[i] = this.appointmentDepartment[i];
            tempName[i] = this.appointmentName[i];
            i++;
        }
        this.appointmentDate = tempDate;
        this.appointmentTime = tempTime;
        this.appointmentDepartment = tempDepartment;
        this.appointmentName = tempName;

        int j = 0;
        while (j < this.appointmentDate.length) {
            if (this.appointmentDate[j] == null) {
                this.appointmentDate[j] = date;
                this.appointmentTime[j] = time;
                this.appointmentDepartment[j] = department;
                this.appointmentName[j] = name;
            }
            j++;
        }
        saveAppointmentToFile(filename);
    }

    private void saveAppointmentToFile(String filename) {
        PrintWriter objWriter;
        try {
            objWriter = new PrintWriter(new FileWriter(filename));
            int i = 0;
            while (i < this.appointmentDate.length) {
                objWriter.print(this.appointmentDate[i] + ",");
                objWriter.print(this.appointmentTime[i] + ",");
                objWriter.print(this.appointmentDepartment[i] + ",");
                objWriter.print(this.appointmentName[i] + "\n");
                i++;
            }
            objWriter.close();
        } catch (IOException e) {
            System.out.println("Sorry, a serious issue has occurred.");
        }
    }

    private void customerServiceQAndA() {
        String userQuery;
        String queryOutput = "";
        do {
            userQuery = this.getUserInput("Enter the keyword: ");
            if (userQuery.length() > 20) {
                System.out.println("Too long. Enter keyword only.");
            } else if (userQuery.length() < 4) {
                System.out.println("Too short. Enter longer keyword.");
            }
        } while (userQuery.length() > 20 || userQuery.length() < 4);

        int i = 0;
        while (i < this.queryKeyword.length) {
            if ((userQuery.substring(0, 4)).equalsIgnoreCase(
                    this.queryKeyword[i].substring(0, 4))) {
                queryOutput = "\n" + this.queryQuestion[i] + "\n";
                queryOutput += "Department: " + this.queryDepartment[i] + "\n";
                queryOutput += "Extension: " + this.queryExtension[i] + "\n";
            } else if (queryOutput.equals("")) {
                queryOutput = "\nSorry there are no answers for your query.";
            }
            i++;
        }
        System.out.println(queryOutput);
    }

    private void populateQueryArrays(String filename) {
        int arraySize = this.countRows(filename);
        if (arraySize > 0) {
            this.queryQuestion = new String[arraySize];
            this.queryKeyword = new String[arraySize];
            this.queryDepartment = new String[arraySize];
            this.queryExtension = new int[arraySize];
            BufferedReader objReader;
            try {
                String line;
                objReader = new BufferedReader(new FileReader(filename));
                int i = 0;
                while (((line = objReader.readLine()) != null) &&
                        (i < this.queryQuestion.length)) {
                    String[] values = line.split(",");
                    this.queryQuestion[i] = values[0];
                    this.queryKeyword[i] = values[1];
                    this.queryDepartment[i] = values[2];
                    this.queryExtension[i] = Integer.parseInt(values[3]);
                    i++;
                }
                objReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find " + filename + ".");
            } catch (IOException ex) {
                System.out.println("Sorry, a serious issue has occurred.");
            }
        } else {
            this.queryQuestion = new String[1];
            this.queryKeyword = new String[1];
            this.queryDepartment = new String[1];
            this.queryExtension = new int[1];
        }
    }

    private String getUserInput(String prompt) {
        System.out.print(prompt);
        String userInput = this.keyboard.nextLine();
        while (userInput.equals("")) {
            System.out.print("Input cannot be empty: ");
            userInput = this.keyboard.nextLine();
        }
        return userInput;
    }

    private int getInteger(String prompt) {
        int value = 0;
        boolean valid = false;

        do {
            try {
                value = Integer.parseInt(getUserInput(prompt));
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Input must be an integer value.");
            }
        } while (!valid);
        return value;
    }

    private int countRows(String filename) {
        BufferedReader objReader;
        int count = 0;
        try {
            objReader = new BufferedReader(new FileReader(filename));
            while ((objReader.readLine()) != null) {
                count++;
            }
            objReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find " + filename + ".");
        } catch (IOException ex) {
            System.out.println("Sorry, a serious issue has occurred.");
        }
        return count;
    }

    public static void main(String[] args) {
        RMITConnect obj = new RMITConnect();
    }
}
