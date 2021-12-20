package com.company;

import java.io.*;
import java.util.*;

public class ShortAnswer extends Essay implements Serializable {

    //for serialization
    private static final long serialVersionUID = 1L;

    //maximum length of responses
    public int maxLength = 0;

    //maximum number of allowed responses
    public int maxResponses = 0;

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

    //setting the maximum length
    public void setMaxLength(int maxLength){
        this.maxLength=maxLength;
    }

    //getting the maximum length
    public int getMaxLength(){
        return this.maxLength;
    }

    //setting the maximum number of allowed responses
    public void setMaxResponses(int maxResponses) {
        this.maxResponses = maxResponses;
    }

    //creating the question and asking for necessary information and validating them
    public void create() throws IOException {

        output.displayOutput("Enter the prompt for your short answer type question: ");
        this.question = input.takeInput();

        while(input.isNull(this.question)){
            output.displayOutput("Question cannot be empty");
            output.displayOutput("Enter the prompt for your short answer type question: ");
            this.question = input.takeInput();
        }

        output.displayOutput("Enter the maximum allowed length for each paragraph: ");
        String n = input.takeInput();

        while( !(input.isInteger(n)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput("Enter the maximum allowed length for each paragraph: ");
            n = input.takeInput();
        }

        this.maxLength = Integer.valueOf(n);

        while(this.maxLength <= 0){
            output.displayOutput("Enter an integer greater than 0: ");
            output.displayOutput("Enter the maximum number of characters allowed per paragraph: ");
            n = input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter the maximum allowed length for each paragraph: ");
                n = input.takeInput();
            }

            this.maxLength = Integer.valueOf(n);
        }

        output.displayOutput("Enter the maximum number of responses: ");
        n = input.takeInput();

        while( !(input.isInteger(n)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput("Enter the maximum number of responses: ");
            n = input.takeInput();
        }

        this.maxResponses = Integer.valueOf(n);

        while(this.maxResponses <= 0){
            output.displayOutput("Enter an integer greater than 0: ");
            output.displayOutput("Enter the maximum number of responses: ");
            n = input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter the maximum number of responses: ");
                n = input.takeInput();
            }

            this.maxResponses = Integer.valueOf(n);
        }

    }


    //displaying the question with the necessary information
    public void display(){

        output.displayOutput("\n" + this.question + "\n");
        output.displayOutput("Word Limit: " + this.getMaxLength() + " characters per response" + "\n");
        output.displayOutput("The maximum number of allowed responses are: " + this.maxResponses);

    }

    //method for answering the question
    public List<String> take() throws IOException {

        this.display();
        String response = "";
        List<String> responses = new ArrayList<>();
        output.displayOutput("\nEnter number of paragraphs you wish to write\n");
        String n = input.takeInput();

        while( !(input.isInteger(n))){
            output.displayOutput("Enter an integer");
            output.displayOutput("\nEnter number of paragraphs you wish to write\n");
            n = input.takeInput();
        }
        int num = Integer.valueOf(n);

        while(num > this.maxResponses || num <= 0){
            output.displayOutput("Enter a valid number!!");
            output.displayOutput("\nEnter number of paragraphs you wish to write:\n");
            n = input.takeInput();

            while( !(input.isInteger(n))){
                output.displayOutput("Enter an integer");
                output.displayOutput("\nEnter number of paragraphs you wish to write\n");
                n = input.takeInput();
            }

            num = Integer.valueOf(n);
        }

        for(int i = 1 ; i <= num ; i++){
            output.displayOutput("\nEnter response "+i+" ;");
            response = input.takeInput();

            while(input.isNull(response)){
                output.displayOutput("Response cannot be empty");
                output.displayOutput("\nEnter response "+i+" ;");
                response=input.takeInput();
            }

            while(response.length()>maxLength){
                output.displayOutput("Enter response within the specified limit");
                output.displayOutput("\nEnter response "+i+" ;");
                response=input.takeInput();
            }

            //adding the response to the list of responses if it is valid
            responses.add(response);
        }

        return responses;
    }

    //method for modifying the question
    public void modify() throws IOException {

        output.displayOutput("The current question prompt is:");
        output.displayOutput(this.question);

        //modifying the prompt
        output.displayOutput("Do you wish to modify this prompt?(yes/no)");
        String bool = input.takeInput();

        while( !(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no")) ){
            output.displayOutput("Enter a valid choice");
            output.displayOutput("Do you wish to modify this prompt?(yes/no)");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){
            output.displayOutput("Enter new prompt");
            String newQuestion = input.takeInput();

            while(input.isNull(newQuestion)){
                output.displayOutput("Question cannot be empty");
                output.displayOutput("Enter new prompt");
                newQuestion = input.takeInput();
            }

            this.setQuestion(newQuestion);
        }

        //modifying the maximum length
        output.displayOutput("Do you wish to modify the maximum allowed length per paragraph?(yes/no)");
        bool = input.takeInput();

        while( !(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no")) ){
            output.displayOutput("Enter a valid choice");
            output.displayOutput("Do you wish to modify the maximum allowed length per paragraph?(yes/no)");
            bool=input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){
            output.displayOutput("The current character limit per paragraph is: "+this.maxLength);
            output.displayOutput("Enter the new length");
            String n=input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter the new length");
                n = input.takeInput();
            }

            int newLength = Integer.valueOf(n);

            while( newLength <= 0 ){
                output.displayOutput("Length cannot be 0");
                output.displayOutput("Enter the new length");
                n = input.takeInput();

                while( !(input.isInteger(n)) ){
                    output.displayOutput("Enter an integer");
                    output.displayOutput("Enter the new length");
                    n = input.takeInput();
                }

                newLength=Integer.valueOf(n);
            }
            this.setMaxLength(newLength);
        }

        //modifying the the maximum number of allowed responses
        output.displayOutput("Do you wish to modify the maximum number of allowed responses?(yes/no)");
        bool = input.takeInput();

        while ( !(bool.equalsIgnoreCase("yes") || bool.equalsIgnoreCase("no")) ) {
            output.displayOutput("Enter a valid choice");
            output.displayOutput("Do you wish to modify the maximum number of allowed responses?(yes/no)");
            bool = input.takeInput();
        }

        if (bool.equalsIgnoreCase("yes")) {
            output.displayOutput("\nThe maximum number of allowed responses is currently set to: " + this.maxResponses);
            output.displayOutput("Enter the new maximum number of allowed responses:");
            String n = input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter the new maximum number of allowed responses:");
                n = input.takeInput();
            }

            int newMaxResponses = Integer.valueOf(n);

            while(newMaxResponses <= 0){
                output.displayOutput("Enter an integer greater than 0");
                output.displayOutput("Enter an integer");
                output.displayOutput("Enter the new maximum number of allowed responses:");
                n = input.takeInput();

                while( !(input.isInteger(n)) ){
                    output.displayOutput("Enter an integer");
                    output.displayOutput("Enter the new maximum number of allowed responses:");
                    n = input.takeInput();
                }

                newMaxResponses=Integer.valueOf(n);
            }

            newMaxResponses = Integer.valueOf(n);
            this.setMaxResponses(newMaxResponses);

        }

    }

    public List<String> getCorrectAnswer() throws IOException {

        String response = "";
        List<String> responses = new ArrayList<>();
        output.displayOutput("\nEnter correct number of paragraphs\n");
        String n = input.takeInput();

        while( !(input.isInteger(n))){
            output.displayOutput("Enter an integer");
            output.displayOutput("\nEnter correct number of paragraphs\n");
            n = input.takeInput();
        }
        int num = Integer.valueOf(n);

        while(num > this.maxResponses || num <= 0){
            output.displayOutput("Enter a valid number!!");
            output.displayOutput("\nEnter correct number of paragraphs:\n");
            n = input.takeInput();

            while( !(input.isInteger(n))){
                output.displayOutput("Enter an integer");
                output.displayOutput("\nEnter correct number of paragraphs\n");
                n = input.takeInput();
            }

            num = Integer.valueOf(n);
        }

        for(int i = 1 ; i <= num ; i++){
            output.displayOutput("\nEnter correct response "+i+" ;");
            response = input.takeInput();

            while(input.isNull(response)){
                output.displayOutput("Response cannot be empty");
                output.displayOutput("\nEnter correct response "+i+" ;");
                response = input.takeInput();
            }

            while(response.length()>maxLength){
                output.displayOutput("Enter correct response within the specified limit");
                output.displayOutput("\nEnter correct response "+i+" ;");
                response = input.takeInput();
            }

            responses.add(response);
        }

        return responses;
    }

    public String getQuestionType(){
        return "ShortAnswer";
    }
}
