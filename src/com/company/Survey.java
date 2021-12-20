package com.company;

import java.io.*;
import java.util.*;

public class Survey implements Serializable {

    //for serialization
    private static final long serialVersionUID = 1L;

    //question class object
    public Question questions;

    //for storing list of questions
    protected List<Question> questionList = new ArrayList<>();;

    //input class object for taking and validating user input
    public Input input = new Input();

    //output class object for displaying output
    public Output output = new Output();
    
    //menu message for populating the survey
    public final String menuMessage1 = "\nMenu For Populating The Survey: \n\n" +
            "1) Add a new T/F question\n" +
            "2) Add a new multiple-choice question\n" +
            "3) Add a new short answer question\n" +
            "4) Add a new essay question\n" +
            "5) Add a new date question\n" +
            "6) Add a new matching question\n" +
            "7) Return to previous menu\n";

    //storing user responses
    public Response resp = new Response();

    //hash map which stores the name of all the loaded surveys as a key their object as the value
    public HashMap<String,Survey> loadedSurveys = new HashMap<>();

    //returns the list of all the surveys present
    public ArrayList<String> surveyList(){

        ArrayList<String> surveys = new ArrayList<>();
        File dir = new File("./");
        File a[] = dir.listFiles();

        for (File file : a) {

            if (file.getName().endsWith(".survey")) {
                surveys.add(file.getName());
            }

        }

        return surveys;
    }

    //returns the list of all the responses present
    public ArrayList<String> responseList(){

        ArrayList<String> surveys = new ArrayList<>();
        File dir = new File("./");
        File a[] = dir.listFiles();

        for (File file : a) {

            if (file.getName().endsWith(".res")) {
                surveys.add(file.getName());
            }

        }

        return surveys;
    }

    //getting the number for the last response for a particular survey to generate a name for a new response
    public int getMax(ArrayList<Integer> array){
        int max = array.get(0);
        for (Integer integer : array) {
            if (integer > max)
                max = integer;
        }
        return max;
    }

    public static HashMap<List<String>,Integer> countFrequencies(List<List<String>> l){
        HashMap<List<String>,Integer> countFrequency = new HashMap<>();

        for (List<String> strings : l) {
            if (countFrequency.containsKey(strings))
                countFrequency.put(strings, countFrequency.get(strings) + 1);
            else
                countFrequency.put(strings, 1);
        }

        return countFrequency;
    }

    //creating the survey
    public void createSurvey() throws IOException, ClassNotFoundException {

        output.displayOutput(menuMessage1);
        output.displayOutput("Enter your choice:");
        String n = input.takeInput();

        //validating whether user has entered an integer or not
        while( !(input.isInteger(n)) ){
            output.displayOutput("Enter an integer");
            output.displayOutput(menuMessage1);
            output.displayOutput("Enter your choice:");
            n = input.takeInput();
        }

        int choice = Integer.valueOf(n);

        //validating whether user's input was within the specified limit or not
        while(choice > 7 || choice < 1){
            output.displayOutput("\nEnter a valid choice!!\n");
            output.displayOutput(menuMessage1);
            output.displayOutput("\nEnter your choice:\n");
            n = input.takeInput();

            while( !(input.isInteger(n)) ){
                output.displayOutput("Enter an integer");
                output.displayOutput(menuMessage1);
                output.displayOutput("Enter your choice:");
                n = input.takeInput();
            }

            choice = Integer.valueOf(n);
        }

        //taking action based on user's input
        while(choice < 7 && choice >= 1 ){

            if(choice == 1){
                questions = new TF();
                questions.create();
            }

            else if(choice == 2){
                questions = new MultipleChoice();
                questions.create();
            }

            else if(choice == 3){
                questions = new ShortAnswer();
                questions.create();
            }

            else if(choice == 4){
                questions = new Essay();
                questions.create();
            }

            else if(choice == 5){
                questions = new Date();
                questions.create();
            }

            else if(choice == 6){
                questions=new Matching();
                questions.create();
            }

            questionList.add(questions);
            createSurvey();
            output.displayOutput("Enter your choice:\n");
            choice = Integer.valueOf(input.takeInput());
        }

        Main m = new Main();
        m.surveyMenu();
    }

    //displaying the survey if any of the surveys are loaded
    public void displaySurvey() throws IOException {

        //checking whether any of the available surveys are loaded or not
        if(loadedSurveys.size() == 0) {
            output.displayOutput("\nNo surveys are loaded\n");
            return;
        }

        output.displayOutput("\nLoaded Surveys\n");

        for(String surveyLoaded: loadedSurveys.keySet())
            output.displayOutput(surveyLoaded);

        //asking the user to select from the list of loaded surveys
        output.displayOutput("\nEnter your choice\n");
        String surveyName = input.takeInput();

        //validating whether the survey is loaded or not
        while(!(loadedSurveys.containsKey(surveyName))){
            output.displayOutput("\nEnter a valid survey name\n");
            output.displayOutput("\nLoaded Surveys\n");
            for(String surveyLoaded: loadedSurveys.keySet())
                output.displayOutput(surveyLoaded);
            output.displayOutput("\nEnter your choice: \n");
            surveyName=input.takeInput();
        }

        //displaying the survey
        Survey s = loadedSurveys.get(surveyName);

        for(int i=0;i<s.questionList.size();i++){
            output.displayOutput("\nQuestion "+(i+1)+" :");
            s.questionList.get(i).display();
            output.displayOutput("");
        }

    }

    //saving the survey
    public void saveSurvey() throws IOException {

        //asking the user for the name of the survey
        output.displayOutput("\nEnter a name for the survey\n");
        String surveySaveName = input.takeInput();

        //validating whether user has entered a valid name or not
        while(input.isNull(surveySaveName)){
            output.displayOutput("Survey name cannot be empty");
            output.displayOutput("\nEnter a name for the survey\n");
            surveySaveName = input.takeInput();
        }

        //saving the survey using serialization
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./"+surveySaveName + ".survey"));
        oos.writeObject(this);
        oos.close();
        output.displayOutput("\n" + surveySaveName+".survey"+" is saved successfully");
    }

    //loading a survey
    public void loadSurvey() throws IOException, ClassNotFoundException {

        //displaying the list of loaded surveys
        ArrayList<String> surveys=surveyList();

        if(surveys.size() == 0){
            output.displayOutput("No surveys are present");
            return;
        }

        output.displayOutput("\nAvailable Surveys:\n");

        for (String survey : surveys) {
            output.displayOutput(survey);
        }

        //asking the user to enter the name of the survey they wish to load
        output.displayOutput("\nEnter the survey name:\n");
        String surveyName = input.takeInput();

        //validating whether the user has entered a valid name or not
        while(!(surveys.indexOf(surveyName)>=0)){
            output.displayOutput("\nEnter a valid survey name!!\n");

            output.displayOutput("\nAvailable Surveys:\n");
            for(int i = 0 ; i < surveys.size() ; i++){
                output.displayOutput(surveys.get(i));
            }

            output.displayOutput("\nEnter the survey name:\n");
            surveyName = input.takeInput();
        }

        //loading the survey using deserialization
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./"+surveyName));
        Survey s = (Survey)ois.readObject();
        ois.close();
        output.displayOutput("\n"+surveyName+" is loaded.");
        loadedSurveys.put(surveyName,s);
    }

    //modifying the survey
    public void modifySurvey() throws IOException {

        //displaying a message if no surveys are loaded
        if(loadedSurveys.size() == 0) {
            output.displayOutput("\nNo surveys are loaded\n");
            return;
        }

        //displaying list of loaded surveys
        output.displayOutput("\nLoaded Surveys\n");

        for(String surveyLoaded: loadedSurveys.keySet())
            output.displayOutput(surveyLoaded);

        //asking the user for their choice
        output.displayOutput("\nEnter your choice\n");
        String surveyName = input.takeInput();

        //validating user input
        while(!(loadedSurveys.containsKey(surveyName))){
            output.displayOutput("\nEnter a valid survey name!!\n");
            output.displayOutput("\nLoaded Surveys\n");
            for(String surveyLoaded: loadedSurveys.keySet())
                output.displayOutput(surveyLoaded);
            output.displayOutput("\nEnter your choice\n");
            surveyName = input.takeInput();
        }

        //displaying the survey
        Survey s = loadedSurveys.get(surveyName);
        output.displayOutput("\nHere is the  list of questions in the survey.\n");

        for(int i = 0 ; i < s.questionList.size() ; i++){
            output.displayOutput("\nQuestion "+(i+1)+" :");
            s.questionList.get(i).display();
            output.displayOutput("");
        }

        //asking whether they wish to modify it or not
        output.displayOutput("\nDo you wish to modify the survey?(yes/no)\n");
        String bool = input.takeInput();

        while(!(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no"))){
            output.displayOutput("\nDo you wish to modify the survey?(yes/no)\n");
            output.displayOutput("\nEnter a valid choice\n");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){

            /*displaying the question prompt and asking them whether they want to modify or not and giving the users an
            option to modify questions, choices, maximum responses, maximum length*/
            while(bool.equalsIgnoreCase("yes")){
                output.displayOutput("\nEnter the question no. you wish to change\n");
                String num = input.takeInput();

                while(!input.isInteger(num)){
                    output.displayOutput("Enter an integer");
                    output.displayOutput("\nEnter the question no. you wish to change\n");
                    num = input.takeInput();
                }
                int n = Integer.valueOf(num);

                while(n < 1 || n > s.questionList.size()){
                    output.displayOutput("\nEnter a valid choice\n");
                    output.displayOutput("\nEnter the question no. you wish to change\n");
                    String num1 = input.takeInput();

                    while(!input.isInteger(num1)){
                        output.displayOutput("\nEnter an integer\n");
                        output.displayOutput("\nEnter the question no. you wish to change\n");
                        num1 = input.takeInput();
                    }
                    n = Integer.valueOf(num1);
                }

                s.questionList.get(n-1).modify();
                output.displayOutput("\nDo you wish to continue modifying the survey?(yes/no)\n");
                bool = input.takeInput();
            }

            s.saveSurvey();
        }
    }

    //taking the survey
    public void takeSurvey() throws IOException, ClassNotFoundException {

        resp = new Response();

        //displaying a message if no surveys are loaded
        if(loadedSurveys.size() == 0) {
            output.displayOutput("\nNo surveys are loaded!!\n");
            return;
        }

        //displaying a list of loaded surveys
        output.displayOutput("\nLoaded Surveys\n");

        for(String surveyLoaded: loadedSurveys.keySet())
            output.displayOutput(surveyLoaded);

        //asking the user which survey they want to take
        output.displayOutput("\nEnter your choice\n");
        String surveyName = input.takeInput();

        //validating whether user has entered a valid name or not
        while(!(loadedSurveys.containsKey(surveyName))){
            output.displayOutput("\nEnter a valid survey name\n");
            output.displayOutput("\nLoaded Surveys\n");
            for(String surveyLoaded : loadedSurveys.keySet())
                output.displayOutput(surveyLoaded);
            output.displayOutput("\nEnter your choice\n");
            surveyName = input.takeInput();
        }

        //displaying the questions and asking for response
        Survey s = loadedSurveys.get(surveyName);

        for(int i = 0; i < s.questionList.size() ; i++){
            output.displayOutput("\nQuestion "+(i+1)+" :");
            List<String> response = s.questionList.get(i).take();
            resp.addResponse(response);
        }

        //creating a name for the response on the basis of survey name and the available responses for that particular survey
        ArrayList<String> list = surveyList();
        int i = surveyName.indexOf(".survey");
        String sName = surveyName.substring(0, i);
        ArrayList<String> respList = responseList();
        int count = 0;
        ArrayList<Integer> array = new ArrayList<>();
        for (String value : respList) {

            if (value.startsWith(sName)) {
                int index = value.indexOf(".res");
                int index1 = value.indexOf("Response");
                String numR = value.substring(index1 + 8, index);
                count = Integer.valueOf(numR) + 1;
                array.add(count);
            }

        }

        count = array.size() > 0 ? getMax(array) : 0;
        String responseName = surveyName.substring(0, i) + "Response" + count + ".res";

        //saving the survey using serialization
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./"+responseName));
        oos.writeObject(resp);
        oos.close();
        output.displayOutput("\n"+responseName+" is saved successfully");

    }

    //tabulating the survey
    public void tabulateSurvey() throws IOException, ClassNotFoundException {

        List<List<String>> TFResponse = new ArrayList<>();
        List<List<String>> MultipleChoiceResponse = new ArrayList<>();
        List<List<String>> ShortAnswerResponse = new ArrayList<>();
        List<List<String>> EssayResponse = new ArrayList<>();
        List<List<String>> MatchingResponse = new ArrayList<>();
        List<List<String>> DateResponse = new ArrayList<>();

        List<Response> responseObjects = new ArrayList<>();

        ///displaying a message if no surveys are loaded
        if (loadedSurveys.size() == 0) {
            output.displayOutput("\nNo surveys are loaded!!\n");
            return;
        }

        //displaying a list of loaded surveys
        output.displayOutput("\nLoaded Surveys\n");

        for (String surveyLoaded : loadedSurveys.keySet())
            output.displayOutput(surveyLoaded);

        //asking the user which survey they want to tabulate
        output.displayOutput("\nEnter your choice\n");
        String surveyName = input.takeInput();

        //validating whether user has entered a valid name or not
        while (!(loadedSurveys.containsKey(surveyName))) {
            output.displayOutput("\nEnter a valid survey name\n");
            output.displayOutput("\nLoaded surveys\n");
            for (String surveyLoaded : loadedSurveys.keySet())
                output.displayOutput(surveyLoaded);
            output.displayOutput("\nEnter your choice\n");
            surveyName = input.takeInput();
        }

        Survey s = loadedSurveys.get(surveyName);

        ArrayList<String> allResponses = responseList();
        ArrayList<String> particularSurveyResponses = new ArrayList<>();
        String sName = surveyName.substring(0, surveyName.indexOf('.'));

        for (String allRespons : allResponses) {
            if (allRespons.startsWith(sName))
                particularSurveyResponses.add(allRespons);
        }

        if (particularSurveyResponses.size() == 0)
            output.displayOutput("\nNo responses for this survey");

        else {

            for (String particularSurveyRespons : particularSurveyResponses) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./" + particularSurveyRespons));
                Response r = (Response) ois.readObject();
                responseObjects.add(r);
                ois.close();
            }

            for (Response responseObject : responseObjects) {
                List<List<String>> userResponse = responseObject.getResponse();

                for (int j = 0; j < userResponse.size(); j++) {

                    if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("TF"))
                        TFResponse.add(userResponse.get(j));
                    if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("MultipleChoice"))
                        MultipleChoiceResponse.add(userResponse.get(j));
                    if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("Matching"))
                        MatchingResponse.add(userResponse.get(j));
                    if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("Date"))
                        DateResponse.add(userResponse.get(j));
                    if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("Essay"))
                        EssayResponse.add(userResponse.get(j));
                    if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("ShortAnswer"))
                        ShortAnswerResponse.add(userResponse.get(j));
                }

            }

            for (int j = 0; j < s.questionList.size(); j++) {

                if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("TF")) {
                    s.questionList.get(j).display();
                    output.displayOutput("\nTabulation for question " + (j + 1) + ": \n");
                    HashMap<List<String>, Integer> count = countFrequencies(TFResponse);
                    for (List<String> keys : count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }

                if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("MultipleChoice")) {
                    s.questionList.get(j).display();
                    output.displayOutput("\nTabulation for question " + (j + 1) + ": \n");
                    HashMap<List<String>, Integer> count = countFrequencies(MultipleChoiceResponse);
                    for (List<String> keys : count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }

                if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("Matching")) {
                    s.questionList.get(j).display();
                    output.displayOutput("\nTabulation for question " + (j + 1) + ": \n");
                    HashMap<List<String>, Integer> count = countFrequencies(MatchingResponse);
                    for (List<String> keys : count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }

                if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("Date")) {
                    s.questionList.get(j).display();
                    output.displayOutput("\nTabulation for question " + (j + 1) + ": \n");
                    HashMap<List<String>, Integer> count = countFrequencies(DateResponse);
                    for (List<String> keys : count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }

                if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("Essay")) {
                    s.questionList.get(j).display();
                    output.displayOutput("\nTabulation for question " + (j + 1) + ": \n");
                    HashMap<List<String>, Integer> count = countFrequencies(EssayResponse);
                    for (List<String> keys : count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }

                if (s.questionList.get(j).getQuestionType().equalsIgnoreCase("ShortAnswer")) {
                    s.questionList.get(j).display();
                    output.displayOutput("\nTabulation for question " + (j + 1) + ": \n");
                    HashMap<List<String>, Integer> count = countFrequencies(ShortAnswerResponse);
                    for (List<String> keys : count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }
            }
        }
    }

}
