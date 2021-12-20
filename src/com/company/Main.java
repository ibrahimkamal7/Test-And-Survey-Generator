package com.company;

import java.io.*;

public class Main implements Serializable {

    //initial menu message
    public final static String initialMenuMessage = "\nAvailable Choices For You: \n" +
            "1) Survey\n" +
            "2) Test\n";

    public final static String surveyMenuMessage = "\nAvailable Choices For You: \n" +
            "1) Create a new Survey\n" +
            "2) Display an existing Survey\n" +
            "3) Load an existing Survey\n" +
            "4) Save the current Survey\n" +
            "5) Take the current Survey\n" +
            "6) Modifying the current Survey\n" +
            "7) Tabulate a survey\n" +
            "8) Return to previous menu\n";

    public final static String testMenuMessage = "\nAvailable Choices For You: \n" +
            "1) Create a new Test\n" +
            "2) Display an existing Test without correct answers\n" +
            "3) Display an existing Test with correct answers\n" +
            "4) Load an existing Test\n" +
            "5) Save the current Test\n" +
            "6) Take the current Test\n" +
            "7) Modify the current Test\n" +
            "8) Tabulate a Test\n" +
            "9) Grade a Test\n" +
            "10) Return to the previous menu\n";

    //for serialization
    private static final long serialVersionUID = 1L;

    //survey class object
    private static Survey survey = new Survey();

    //test class object
    private static Test test = new Test();

    //input class object for validating user input
    private static Input input = new Input();

    //output class object for displaying output
    public static Output output = new Output();

    //main method for displaying the menu
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        initialMenu();
    }

    public static void initialMenu() throws IOException, ClassNotFoundException {

        System.out.print(initialMenuMessage);
        output.displayOutput("\nEnter your choice:");

        String n = input.takeInput();

        while( !(input.isInteger(n)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput(initialMenuMessage);
            output.displayOutput("Enter your choice: ");
            n = input.takeInput();;
        }

        int userChoice = Integer.valueOf((n));

        while(userChoice > 2 || userChoice < 1 ){

            output.displayOutput("\nEnter a valid choice\n");
            output.displayOutput(initialMenuMessage);
            output.displayOutput("Enter your choice:");

            n = input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput(initialMenuMessage);
                output.displayOutput("Enter your choice: ");
                n = input.takeInput();;
            }

            userChoice = Integer.valueOf((n));

        }

        if(userChoice == 1)
            surveyMenu();

        else
            testMenu();
    }

    //method for displaying the menu and taking action upon the user's choice
    public static void surveyMenu() throws IOException, ClassNotFoundException {
        output.displayOutput(surveyMenuMessage);
        output.displayOutput("Enter your choice: ");
        String n = input.takeInput();

        //checking whether the user has entered an integer or not
        while( !(input.isInteger(n)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput(surveyMenuMessage);
            output.displayOutput("Enter your choice: ");
            n = input.takeInput();
        }

        int choice = Integer.valueOf(n);

        //checking whether the user's choice is within the limit or not
        while(choice > 8 || choice < 1){
            output.displayOutput("\nNot a valid choice.Try Again.\n");
            output.displayOutput(surveyMenuMessage);
            output.displayOutput("Enter your choice: ");

            n = input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput(surveyMenuMessage);
                output.displayOutput("Enter your choice: ");
                n = input.takeInput();
            }

            choice = Integer.valueOf(n);

        }

        //taking action based on the user's choice
        while(choice <= 8 && choice >= 1) {

            if (choice == 1) {
                survey = new Survey();
                survey.createSurvey();
            }

            else if (choice == 2)
                survey.displaySurvey();

            else if (choice == 3)
                survey.loadSurvey();

            else if (choice == 4)
                survey.saveSurvey();

            else if (choice == 5)
                survey.takeSurvey();

            else if (choice == 6)
                survey.modifySurvey();

            else if (choice == 7)
                survey.tabulateSurvey();

            else if (choice == 8)
                initialMenu();

            surveyMenu();
            output.displayOutput("Enter your choice: ");

            n = input.takeInput();

            while ( !(input.isInteger(n)) ) {
                output.displayOutput("Enter an integer");
                output.displayOutput(surveyMenuMessage);
                output.displayOutput("Enter your choice: ");
                n = input.takeInput();
            }

            choice = Integer.valueOf(n);
        }
    }

    public static void testMenu() throws IOException, ClassNotFoundException {
        output.displayOutput(testMenuMessage);
        output.displayOutput("Enter your choice: ");
        String n = input.takeInput();

        //checking whether the user has enterd an integer or not
        while ( !(input.isInteger(n)) ) {
            output.displayOutput("Enter an integer");
            output.displayOutput(testMenuMessage);
            output.displayOutput("Enter your choice: ");
            n = input.takeInput();
        }

        int choice = Integer.valueOf(n);

        //checking whether the user's choice is within the limit or not
        while (choice > 10 || choice < 1) {
            output.displayOutput("\nNot a valid choice.Try Again.\n");
            output.displayOutput(testMenuMessage);
            output.displayOutput("Enter your choice: ");

            n = input.takeInput();

            while ( !(input.isInteger(n)) ) {
                output.displayOutput("Enter an integer");
                output.displayOutput(testMenuMessage);
                output.displayOutput("Enter your choice: ");
                n = input.takeInput();
            }

            choice = Integer.valueOf(n);

        }

        //taking action based on the user's choice
        while (choice <= 10 && choice >= 1) {

            if (choice == 1) {
                test = new Test();
                test.createTest();
            }

            else if (choice == 2)
                test.displayTest();

            else if (choice == 3)
                test.displayWithAnswers();

            else if (choice == 4)
                test.loadTest();

            else if (choice == 5)
                test.saveTest();

            else if (choice == 6)
                test.takeTest();

            else if (choice == 7)
                test.modifyTest();

            else if (choice == 8)
                test.tabulateTest();

            else if (choice == 9)
               test.gradeTest();

            else if (choice == 10)
                initialMenu();

           testMenu();
            output.displayOutput("Enter your choice: ");

            n = input.takeInput();

            while ( !(input.isInteger(n)) ) {
                output.displayOutput("Enter an integer");
                output.displayOutput(testMenuMessage);
                output.displayOutput("Enter your choice: ");
                n = input.takeInput();
            }

            choice = Integer.valueOf(n);
        }
    }
}