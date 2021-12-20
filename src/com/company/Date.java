package com.company;

import java.io.*;

import java.util.*;

public class Date extends Question implements Serializable {

    //for serialization
    private static final long serialVersionUID = 1L;

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
        this.question = question;
    }

    //creating the question
    public void create() throws IOException {

        output.displayOutput("Enter the prompt for your date type question: ");
        this.question = input.takeInput();

        while( input.isNull(this.question) ){
            output.displayOutput("Question cannot be empty");
            output.displayOutput("Enter the prompt for your date type question: ");
            this.question = input.takeInput();
        }

    }

    //displaying the question
    public void display(){
        output.displayOutput("\n" + this.question + "\n");
    }

    //validating whether the entered date format is correct or not
    public boolean validDate(String s){
        int count = 0;

        for(char ch : s.toCharArray()){
            if(ch == '/')
                count+=1;
        }

        if(count == 2){

            String[] date = s.split("/",3);

            if(input.isInteger(date[0]) && input.isInteger(date[1]) && input.isInteger(date[2]))
                if(date[0].length() == 4 && date[1].length() == 2 && date[2].length() == 2)
                    return validateDays(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));

        }

        return false;
    }

    //validating date
    public boolean validateDays(int year, int month, int days){

        if(month <= 0 || month >12)
            return false;

        if(days <= 0 || days > 31)
            return false;

        //I have taken this line to check for leap year from stack over flow
        //Link: https://stackoverflow.com/questions/1021324/java-code-for-calculating-leap-year
        if(java.time.Year.of(year).isLeap() && month == 2 && days <= 29)
            return true;

        int numMonth[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int numDays[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        for(int i = 0; i < numMonth.length; i++){

            if(month == numMonth[i] && days <= numDays[i])
                return true;

        }

        return false;
    }

    //method to answer the question
    public List<String> take() throws IOException {

        this.display();
        String response = "";
        List<String> responses = new ArrayList<>();
        output.displayOutput("\nEnter response in yyyy/mm/dd format: ");
        response = input.takeInput();

        while( !(validDate(response)) ){
            output.displayOutput("Enter a valid date");
            output.displayOutput("\nEnter response in yyyy/mm/dd format: ");
            response = input.takeInput();
        }

        responses.add(response);
        return responses;

    }

    //method to modify the question
    public void modify() throws IOException {

        output.displayOutput("The current question prompt is: ");
        output.displayOutput(this.question);

        //modifying the question prompt
        output.displayOutput("Do you wish to modify this prompt?(yes/no) ");
        String bool = input.takeInput();

        while( !(bool.equalsIgnoreCase("yes") || bool.equalsIgnoreCase("no")) ){
            output.displayOutput("Enter a valid choice: ");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){
            output.displayOutput("Enter new prompt: ");
            String newQuestion = input.takeInput();

            while(input.isNull(newQuestion)){
                output.displayOutput("Question cannot be empty");
                output.displayOutput("Enter new prompt: ");
                newQuestion = input.takeInput();
            }

            this.setQuestion(newQuestion);
        }
    }

    public List<String> getCorrectAnswer() throws IOException {

        String response = "";
        List<String> responses = new ArrayList<>();
        output.displayOutput("\nEnter correct response in yyyy/mm/dd format: ");
        response = input.takeInput();

        while( !(validDate(response)) ){
            output.displayOutput("Enter a valid date");
            output.displayOutput("\nEnter correct response in yyyy/mm/dd format: ");
            response = input.takeInput();
        }

        responses.add(response);
        return responses;

    }

    public String getQuestionType(){
        return "Date";
    }
}
