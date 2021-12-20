package com.company;

import java.io.*;
import java.util.*;

public class Matching extends Question implements Serializable {

    //for serialization
    private static final long serialVersionUID = 1L;

    //maximum number of rows
    public int numRows = 0;

    //storing right side data
    public ArrayList<String> rightSideData = new ArrayList<String>();

    //storing left side data
    public ArrayList<String> leftSideData = new ArrayList<String>();

    //input class object for taking and validating user input
    public Input input = new Input();

    //output class object for displaying output
    public Output output = new Output();
    
    //getting the question
    public String getQuestion(){
        return this.question;
    }

    //setting the question
    public void setQuestion(String question){
        this.question=question;
    }

    //getting number of rows
    public void setNumRows(int numRows){
        this.numRows=numRows;
    }

    //setting number of rows
    public int getNumRows(){
        return this.numRows;
    }

    //getting right side data
    public ArrayList<String> getRightSideData() {
        return this.rightSideData;
    }

    //setting right side data
    public ArrayList<String> getLeftSideData() {
        return this.rightSideData;
    }

    //creating the question and asking for necessary information and validating them
    public void create() throws IOException {

        output.displayOutput("Enter the prompt for your matching question: ");
        this.question = input.takeInput();

        while(input.isNull(this.question)) {
            output.displayOutput("Question prompt cannot be empty");
            output.displayOutput("Enter the prompt for your matching question: ");
            this.question = input.takeInput();
        }

        output.displayOutput("Enter number of rows: ");
        String numC = input.takeInput();

        while( !(input.isInteger(numC)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput("Enter number choices: ");
            numC = input.takeInput();
        }

        this.numRows = Integer.valueOf(numC);

        while(this.numRows<=0){
            output.displayOutput("Enter a value greater than 0.");
            output.displayOutput("Enter number of rows: ");
            numC = input.takeInput();

            while( !(input.isInteger(numC)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter number choices: ");
                numC = input.takeInput();
            }

            this.numRows = Integer.valueOf(numC);
        }

        output.displayOutput("Enter Left Side Data: ");

        for(int i = 1; i <= this.numRows ; i++){

            output.displayOutput("Enter left data field number " + i +" :");
            String data = input.takeInput();

            while(input.isNull(data)) {
                output.displayOutput("Data cannot be empty");
                output.displayOutput("Enter left data field number " + i +" :");
                data = input.takeInput();
            }

            leftSideData.add(data);
        }

        output.displayOutput("Enter Right Side Data: ");

        for(int i = 1; i <= this.numRows; i++){

            output.displayOutput("Enter right data field number " + i +" :");
            String data = input.takeInput();

            while(input.isNull(data)) {
                output.displayOutput("Data cannot be empty");
                output.displayOutput("Enter right data field number " + i +" :");
                data = input.takeInput();
            }

            rightSideData.add(data);
        }

    }

    //displaying the question and necessary information
    public void display(){

        output.displayOutput("\n" + this.question + "\n");

        for (int i = 0; i < this.numRows; i++) {
            output.displayOutput(this.leftSideData.get(i) + "\t" + this.rightSideData.get(i));
        }

    }

    //method to answer the question
    public List<String> take() throws IOException {

        this.display();
        String response = "";
        List<String> responses = new ArrayList<>();

        for(int i = 0 ; i < this.leftSideData.size() ; i++){
            output.displayOutput("\nEnter response for " + this.leftSideData.get(i)+ " :");
            response = input.takeInput();

            while( !(this.rightSideData.contains(response))){
                output.displayOutput("Select an option from the given choices");
                output.displayOutput("\nEnter response for " + this.leftSideData.get(i)+ " :");
                response = input.takeInput();
            }
            responses.add(response);
        }

        return responses;
    }

    //method to modify the question
    public void modify() throws IOException {

        output.displayOutput("The current question prompt is:");
        output.displayOutput(this.question);

        //modifying prompt
        output.displayOutput("Do you wish to modify this prompt?(yes/no)");
        String bool = input.takeInput();

        while( !(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no")) ){
            output.displayOutput("Enter a valid choice");
            output.displayOutput("Do you wish to modify this prompt?(yes/no)");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")) {
            output.displayOutput("Enter new prompt");
            String newQuestion = input.takeInput();

            while(input.isNull(newQuestion)){
                output.displayOutput("Question propmt cannot be empty");
                output.displayOutput("Enter new prompt");
                newQuestion = input.takeInput();
            }

            this.setQuestion(newQuestion);
        }

        //modifying right side data
        output.displayOutput("Do you wish to modify any right side choices?(yes/no)");
        bool = input.takeInput();

        while( !(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no")) ){
            output.displayOutput("Enter a valid choice");
            output.displayOutput("Do you wish to modify any right side choices?(yes/no)");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){
            output.displayOutput("\nThe current choices are:\n");

            for(int i = 0;i<this.rightSideData.size();i++)
                output.displayOutput(this.rightSideData.get(i));

            output.displayOutput("");
            output.displayOutput("Enter the choice you wish to modify");
            String choice = input.takeInput();

            while( !(this.rightSideData.contains(choice))){
                output.displayOutput("Enter a choice from the given options");
                output.displayOutput("Enter the choice you wish to modify");
                choice = input.takeInput();
            }

            int i = this.rightSideData.indexOf(choice);
            output.displayOutput("Enter the new choice");
            String nchoice = input.takeInput();

            while(input.isNull(nchoice)){
                output.displayOutput("Data fields cannot be empty");
                output.displayOutput("Enter the new choice");
                nchoice = input.takeInput();
            }

            this.rightSideData.set(i,nchoice);
        }

        //modifying left side data
        output.displayOutput("Do you wish to modify any left side choices?(yes/no)");
        bool = input.takeInput();

        while( !(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no")) ){
            output.displayOutput("Enter a valid choice");
            output.displayOutput("Do you wish to modify any left side choices?(yes/no)");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){
            output.displayOutput("\nThe current choices are:\n");

            for(int i = 0; i < this.leftSideData.size() ; i++)
                output.displayOutput(this.leftSideData.get(i));

            output.displayOutput("");
            output.displayOutput("Enter the choice you wish to modify");
            String choice = input.takeInput();

            while( !(this.leftSideData.contains(choice))){
                output.displayOutput("Enter a choice from the given options");
                output.displayOutput("Enter the choice you wish to modify");
                choice = input.takeInput();
            }

            int i = this.leftSideData.indexOf(choice);
            output.displayOutput("Enter the new choice");
            String nchoice = input.takeInput();

            while(input.isNull(nchoice)){
                output.displayOutput("Data fields cannot be empty");
                output.displayOutput("Enter the new choice");
                nchoice = input.takeInput();
            }

            this.leftSideData.set(i,nchoice);
        }
    }

    public List<String> getCorrectAnswer() throws IOException {

        String response = "";
        List<String> responses = new ArrayList<>();

        for(int i = 0 ; i < this.leftSideData.size() ; i++){
            output.displayOutput("\nEnter correct response for " + this.leftSideData.get(i)+ " :");
            response = input.takeInput();

            while( !(this.rightSideData.contains(response))){
                output.displayOutput("Select an option from the given choices");
                output.displayOutput("\nEnter correct response for " + this.leftSideData.get(i)+ " :");
                response = input.takeInput();
            }
            responses.add(response);
        }

        return responses;
    }

    public String getQuestionType(){
        return "Matching";
    }
}



