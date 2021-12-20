package com.company;

import java.io.*;
import java.util.*;

public class MultipleChoice extends Question implements Serializable {

    //for serialization
    private static final long serialVersionUID = 1L;

    //list of choices
    protected ArrayList<String> choices = new ArrayList<String>();

    //number of available choices
    public int numChoices = 0;

    //maximum number of responses allowed
    public int maxChoice = 0;

    //input class object for taking and validating user input
    public Input input = new Input();

    //output class object for displaying output
    public Output output = new Output();
    
    //getting the question prompt
    public String getQuestion(){
        return this.question;
    }

    //setting the question prompt
    public void setQuestion(String question){
        this.question = question;
    }

    //setting maximum number of allowed responses
    public void setMaxChoice(int maxChoice){
        this.maxChoice = maxChoice;
    }

    //setting number of available choices
    public void setNumChoices(int numChoices){
        this.numChoices = numChoices;
    }

    //getting maximum number of available choices
    public int getNumChoices(){
        return this.numChoices;
    }

    //creating the question
    public void create() throws IOException {

        //asking the user for all the necessary information and validating the input
        output.displayOutput("Enter the prompt for your multiple choice question: ");
        this.question = input.takeInput();

        while(input.isNull(question)){
            output.displayOutput("Question prompt cannot be empty");
            output.displayOutput("Enter the prompt for your multiple choice question: ");
            this.question = input.takeInput();
        }

        output.displayOutput("Enter number choices: ");
        String numC = input.takeInput();

        while( !(input.isInteger(numC)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput("Enter number choices: ");
            numC = input.takeInput();
        }

        this.numChoices = Integer.valueOf(numC);

        while(this.numChoices <= 0){
            output.displayOutput("Enter an integer greater than 0: ");
            output.displayOutput("Enter number choices: ");
            numC = input.takeInput();

            while( !(input.isInteger(numC)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter number choices: ");
                numC = input.takeInput();
            }

            this.numChoices = Integer.valueOf(numC);
        }

        output.displayOutput("Enter the maximum number of responses a user can enter: ");
        numC = input.takeInput();

        while( !(input.isInteger(numC)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput("Enter the maximum number of responses a user can enter: ");
            numC = input.takeInput();
        }

        this.maxChoice = Integer.valueOf(numC);

        while(this.maxChoice <= 0 || this.maxChoice > this.numChoices){
            output.displayOutput("Enter an integer greater than 0 and less than "+this.numChoices+": ");
            output.displayOutput("Enter the maximum number of responses a user can enter: ");
            numC = input.takeInput();

            while( !(input.isInteger(numC)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter the maximum number of responses a user can enter: ");
                numC = input.takeInput();
            }

            this.maxChoice = Integer.valueOf(numC);
        }

        //adding the choices to list of choices
        for(int i = 1 ; i <= this.numChoices ; i++){
            output.displayOutput("Enter choice number "+i+":");
            String choice = input.takeInput();
            while(input.isNull(choice)){
                output.displayOutput("Choice cannot be empty");
                output.displayOutput("Enter choice number "+i+":");
                choice = input.takeInput();
            }
            choices.add(choice);
        }

    }

    //displaying the question and the choices
    public void display(){

        output.displayOutput("\n"+this.question+"\n");

        for(int i = 0 ; i < this.choices.size() ; i++)
            output.displayOutput((i+1)+" : " + this.choices.get(i));

        output.displayOutput("\nThe maximum number of allowed respone(s) is/are: "+this.maxChoice);

    }

    //method for answering the question
    public List<String> take() throws IOException {

        this.display();
        String response = "";
        List<String> responses = new ArrayList<>();

        //asking for the number of responses user wish to give and validating user input
        output.displayOutput("\nEnter number of responses:\n");
        String n = input.takeInput();

        while( !(input.isInteger(n)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput("\nEnter number of responses:\n");
            n = input.takeInput();
        }

        int num = Integer.valueOf(n);

        while(num > this.numChoices || num > this.maxChoice || num <= 0){
            output.displayOutput("\nInvalid input!!\n");
            output.displayOutput("Enter number of responses:\n");
            n = input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("\nEnter number of responses:\n");
                n = input.takeInput();
            }
            num = Integer.valueOf(n);
        }

        //asking the user for their reponse and validating the response
        for(int i = 1 ; i <= num ; i++){
            output.displayOutput("\nEnter response "+i+" :");
            response = input.takeInput();
            while( !(this.choices.contains(response)) ){
                output.displayOutput("Select an option from the choices");
                output.displayOutput("\nEnter response "+i+" :");
                response = input.takeInput();
            }
            responses.add(response);
        }

        return responses;

    }

    //method for modifying the question
    public void modify() throws IOException {

        output.displayOutput("The current question prompt is:");
        output.displayOutput(this.question);

        //modifying question prompt
        output.displayOutput("Do you wish to modify this prompt?(yes/no)");
        String bool = input.takeInput();

        while( !(bool.equalsIgnoreCase("yes") || bool.equalsIgnoreCase("no")) ){
            output.displayOutput("Enter a valid choice:");
            output.displayOutput("Do you wish to modify this prompt?(yes/no)");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){
            output.displayOutput("Enter new prompt:");
            String newQuestion = input.takeInput();
            while(input.isNull(newQuestion)){
                output.displayOutput("Question prompt cannot be empty");
                output.displayOutput("Enter new prompt:");
                newQuestion = input.takeInput();
            }
            this.setQuestion(newQuestion);
        }

        //modifying the choices
        output.displayOutput("Do you wish to modify any choices?(yes/no)");
        bool = input.takeInput();

        while(!(bool.equalsIgnoreCase("yes") || bool.equalsIgnoreCase("no"))){
            output.displayOutput("Enter a valid choice:");
            output.displayOutput("Do you wish to modify any choices?(yes/no)");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){
            output.displayOutput("\nThe current choices are:\n");

            for(int i = 0 ; i < this.choices.size() ; i++)
                output.displayOutput(this.choices.get(i));

            output.displayOutput("");
            output.displayOutput("Enter the choice you wish to modify:");
            String choice = input.takeInput();

            while(! (this.choices.contains(choice))){
                output.displayOutput("Enter an option from the choices");
                output.displayOutput("Enter the choice you wish to modify:");
                choice = input.takeInput();
            }

            int i = this.choices.indexOf(choice);
            output.displayOutput("Enter the new choice:");
            String nchoice = input.takeInput();
            while(input.isNull(nchoice)){
                output.displayOutput("Choice cannot be empty");
                output.displayOutput("Enter the new choice:");
                nchoice = input.takeInput();
            }
            this.choices.set( i , nchoice);
        }

        //modifying the maximum number of allowed responses
        output.displayOutput("Do you wish to modify the maximum number of allowed choices?(yes/no)");
        bool = input.takeInput();

        while(!(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no"))){
            output.displayOutput("Enter a valid choice: ");
            output.displayOutput("Do you wish to modify the maximum number of allowed choices?(yes/no)");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")) {
            output.displayOutput("\nThe maximum number of allowed responses is currently set to: " + this.maxChoice);
            output.displayOutput("Enter the new value for the maximum number of allowed responses: ");
            String n = input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter the new value for the maximum number of allowed responses: ");
                n = input.takeInput();
            }
            int newMaxChoice = Integer.valueOf(n);

            while(newMaxChoice <=0 || newMaxChoice >this.numChoices){
                output.displayOutput("Enter a valid number");
                output.displayOutput("Enter the new value for the maximum number of allowed responses: ");
                n = input.takeInput();

                while( !(input.isInteger(n)) ){
                    output.displayOutput("Enter an integer");
                    output.displayOutput("Enter the new value for the maximum number of allowed responses: ");
                    n = input.takeInput();
                }
                newMaxChoice = Integer.valueOf(n);
            }

            this.setMaxChoice(newMaxChoice);
        }
    }

    public List<String> getCorrectAnswer() throws IOException {

        String response = "";
        List<String> responses = new ArrayList<>();

        //asking for the number of responses user wish to give and validating user input
        output.displayOutput("\nEnter number of correct responses:\n");
        String n = input.takeInput();

        while (!(input.isInteger(n))) {
            output.displayOutput("Enter an integer");
            output.displayOutput("\nEnter number of correct responses:\n");
            n = input.takeInput();
        }

        int num = Integer.valueOf(n);

        while (num > this.numChoices || num > this.maxChoice || num <= 0) {
            output.displayOutput("\nInvalid input!!\n");
            output.displayOutput("Enter number of correct responses:\n");
            n = input.takeInput();

            while (!(input.isInteger(n))) {
                output.displayOutput("Enter an integer");
                output.displayOutput("\nEnter number of correct responses:\n");
                n = input.takeInput();
            }
            num = Integer.valueOf(n);
        }

        //asking the user for their reponse and validating the response
        for (int i = 1; i <= num; i++) {
            output.displayOutput("\nEnter correct response " + i + " :");
            response = input.takeInput();
            while (!(this.choices.contains(response))) {
                output.displayOutput("Select an option from the choices");
                output.displayOutput("\nEnter correct response " + i + " :");
                response = input.takeInput();
            }
            responses.add(response);
        }

        return responses;

    }

    public String getQuestionType(){
        return "MultipleChoice";
    }

}