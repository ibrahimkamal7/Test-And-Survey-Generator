package com.company;

import java.io.*;
import java.util.*;


public class TF extends MultipleChoice implements Serializable {

    //for serialization
    private static final long serialVersionUID = 1L;

    //storing the choices for the question
    protected ArrayList<String> choices = new ArrayList<>();

    //object of input class for taking and validating user input
    public Input input = new Input();

    //output class object for displaying output
    public Output output = new Output();

    //setting the question prompt
    public void setQuestion(String question){
        this.question = question;
    }

    //getting the question prompt
    public String getQuestion(){
        return this.question;
    }

    //creating the question
    public void create() throws IOException {

        //asking the user for a prompt
        output.displayOutput("Enter the prompt for your True/False question: ");
        this.question = input.takeInput();

        //validating whether the entered prompt is empty or not
        while(input.isNull(this.question)){
            output.displayOutput("Question prompt cannot be empty");
            output.displayOutput("Enter the prompt for your True/False question: ");
            this.question = input.takeInput();
        }

        //adding true and false as the choices
        choices.add("True");
        choices.add("False");

    }

    //displaying the prompt and the choices
    public void display(){

        output.displayOutput("\n"+this.question+"\n");

        for(int i = 0;i<choices.size();i++)
            output.displayOutput((i+1)+" : " + choices.get(i));

    }

    //method for answering the question
    public List<String> take() throws IOException {

        this.display();
        String response = "";
        List<String> responses = new ArrayList<>();
        output.displayOutput("\nEnter response: ");
        response = input.takeInput();

        //validating whether the user has selected an option from the given choices
        while( !(this.choices.contains(response)) ){
            output.displayOutput("Select an option from the choices");
            output.displayOutput("\nEnter response: ");
            response = input.takeInput();
        }

        //adding the response to the list of responses
        responses.add(response);
        return responses;

    }

    //method for modifying the question
    public void modify() throws IOException {

        output.displayOutput("The current question prompt is:");
        output.displayOutput(this.question);
        output.displayOutput("Do you wish to modify this prompt?(yes/no)");
        String bool = input.takeInput();

        while( !(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no")) ){
            output.displayOutput("Enter a valid choice");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){
            output.displayOutput("Enter new prompt");
            String newQuestion = input.takeInput();

            //validating whether the user has entered an empty string for the question
            while(input.isNull(newQuestion)){
                output.displayOutput("Question prompt cannot be empty");
                output.displayOutput("Enter new prompt");
                newQuestion = input.takeInput();
            }

            this.setQuestion(newQuestion);
        }

    }

    public List<String> getCorrectAnswer() throws IOException {

        String response = "";
        List<String> responses = new ArrayList<>();
        output.displayOutput("\nEnter correct answer: ");
        response = input.takeInput();

        //validating whether the user has selected an option from the given choices
        while( !(this.choices.contains(response)) ){
            output.displayOutput("Select an option from the choices");
            output.displayOutput("\nEnter correct answer: ");
            response = input.takeInput();
        }

        //adding the response to the list of responses
        responses.add(response);
        return responses;
    }

    public String getQuestionType(){
        return "TF";
    }
}
