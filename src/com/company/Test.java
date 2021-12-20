package com.company;
import java.io.*;
import java.util.*;

public class Test extends Survey implements Serializable {

    //storing correct answers for the questions
    public List<List<String>> correctAnswers = new ArrayList<>();

    //for serialization
    private static final long serialVersionUID = 1L;

    //input class object for validating user input
    public Input input = new Input();

    //output class object for displaying output
    public Output output = new Output();
    
    //storing user responses
    public Response resp = new Response();

    //hash map which stores the name of all the loaded tests as a key their object as the value
    public HashMap<String,Test> loadedTests = new HashMap<>();

    //question class object
    public Question questions;

    //for storing list of questions
    protected List<Question> questionList = new ArrayList<>();

    //menu message for populating the test
    public final String menuMessage1 = "\nMenu For Populating The Test: \n\n" +
            "1) Add a new T/F question\n" +
            "2) Add a new multiple-choice question\n" +
            "3) Add a new short answer question\n" +
            "4) Add a new essay question\n" +
            "5) Add a new date question\n" +
            "6) Add a new matching question\n" +
            "7) Return to previous menu\n";

    //returns the list of all the test present
    public ArrayList<String> testList(){

        ArrayList<String> tests = new ArrayList<>();
        File dir = new File("./");
        File a[] = dir.listFiles();

        for (File file : a) {

            if (file.getName().endsWith(".test"))
                tests.add(file.getName());
        }

        return tests;
    }

    //returns the list of all the responses present
    public ArrayList<String> responseList(){

        ArrayList<String> tests = new ArrayList<>();
        File dir = new File("./");
        File a[] = dir.listFiles();

        for (File file : a) {
            if (file.getName().endsWith(".res"))
                tests.add(file.getName());
        }

        return tests;
    }

    //getting the number for the last response for a particular test to generate a name for a new response
    public int getMax(ArrayList<Integer> array){
        int max = array.get(0);
        for (Integer integer : array) {
            if (integer > max)
                max = integer;
        }
        return max;
    }

    public static HashMap<List<String>,Integer> countFrequencies(List<List<String>> l){
        HashMap<List<String> , Integer> countFrequency = new HashMap<>();

        for (List<String> strings : l) {
            if (countFrequency.containsKey(strings))
                countFrequency.put(strings, countFrequency.get(strings) + 1);
            else
                countFrequency.put(strings, 1);
        }

        return countFrequency;
    }

    //creating the test
    public void createTest() throws IOException, ClassNotFoundException {

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
                this.correctAnswers.add(questions.getCorrectAnswer());
            }

            else if(choice == 2){
                questions = new MultipleChoice();
                questions.create();
                this.correctAnswers.add(questions.getCorrectAnswer());
            }

            else if(choice == 3){
                questions = new ShortAnswer();
                questions.create();
                this.correctAnswers.add(questions.getCorrectAnswer());
            }

            else if(choice == 4){
                questions = new Essay();
                questions.create();
                this.correctAnswers.add(questions.getCorrectAnswer());
            }

            else if(choice == 5){
                questions = new Date();
                questions.create();
                this.correctAnswers.add(questions.getCorrectAnswer());
            }

            else if(choice == 6){
                questions = new Matching();
                questions.create();
                this.correctAnswers.add(questions.getCorrectAnswer());
            }

            questionList.add(questions);
            createTest();
            output.displayOutput("Enter your choice:\n");
            choice = Integer.valueOf(input.takeInput());
        }

        Main.testMenu();
    }

    //displaying the test if any of them are loaded
    public void displayTest() throws IOException {

        //checking whether any of the available test are loaded or not
        if(loadedTests.size() == 0) {
            output.displayOutput("\nNo tests are loaded\n");
            return;
        }

        output.displayOutput("\nLoaded Tests\n");

        for(String testLoaded: loadedTests.keySet())
            output.displayOutput(testLoaded);

        //asking the user to select from the list of loaded test
        output.displayOutput("\nEnter your choice\n");
        String testName = input.takeInput();

        //validating whether the test is loaded or not
        while(!(loadedTests.containsKey(testName))){
            output.displayOutput("\nEnter a valid test name\n");
            for(String testLoaded: loadedTests.keySet())
                output.displayOutput(testLoaded);
            output.displayOutput("\nEnter your choice\n");
            testName = input.takeInput();
        }

        //displaying the test
        Test t = loadedTests.get(testName);

        for(int i = 0; i < t.questionList.size(); i++){
            output.displayOutput("\nQuestion "+(i+1)+" :");
            t.questionList.get(i).display();
            output.displayOutput("");
        }

    }

    //displaying the test with correct answers if any of them are loaded
    public void displayWithAnswers() throws IOException {


        //checking whether any of the available test are loaded or not
        if(loadedTests.size() == 0) {
            output.displayOutput("\nNo tests are loaded\n");
            return;
        }

        output.displayOutput("\nLoaded Tests\n");

        for(String testLoaded: loadedTests.keySet())
            output.displayOutput(testLoaded);

        //asking the user to select from the list of loaded test
        output.displayOutput("\nEnter your choice\n");
        String testName = input.takeInput();

        //validating whether the test is loaded or not
        while(!(loadedTests.containsKey(testName))){
            output.displayOutput("\nEnter a valid test name\n");
            for(String testLoaded: loadedTests.keySet())
                output.displayOutput(testLoaded);
            output.displayOutput("\nEnter your choice\n");
            testName=input.takeInput();
        }

        //displaying the test
        Test t = loadedTests.get(testName);
        for(int i = 0; i < t.questionList.size(); i++){
            output.displayOutput("\nQuestion "+(i+1)+" :");
            t.questionList.get(i).display();
            output.displayOutput("\nCorrect Response: \n");
            System.out.println(t.correctAnswers.get(i));
            output.displayOutput("");
        }

    }


    //saving the test
    public void saveTest() throws IOException {

        //asking the user for the name of the test
        output.displayOutput("\nEnter a name for the test\n");
        String testSaveName = input.takeInput();

        //validating whether user has entered a valid name or not
        while(input.isNull(testSaveName)){
            output.displayOutput("test name cannot be empty");
            output.displayOutput("\nEnter a name for the test\n");
            testSaveName = input.takeInput();
        }

        //saving the test using serialization
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./"+testSaveName + ".test"));
        oos.writeObject(this);
        oos.close();
        testSaveName = testSaveName + ".test";
        output.displayOutput("\n" + testSaveName+" is saved successfully");
    }

    //loading a test
    public void loadTest() throws IOException, ClassNotFoundException {

        //displaying the list of loaded test
        ArrayList<String> tests = testList();

        if(tests.size() == 0){
            output.displayOutput("No tests are present");
            return;
        }

        output.displayOutput("\nAvailable tests:\n");

        for (String test : tests) {
            output.displayOutput(test);
        }

        //asking the user to enter the name of the test they wish to load
        output.displayOutput("\nEnter the test name:\n");
        String testName = input.takeInput();

        //validating whether the user has entered a valid name or not
        while(!(tests.indexOf(testName)>=0)){
            output.displayOutput("\nEnter a valid test name!!\n");

            for (String test : tests) {
                output.displayOutput(test);
            }

            output.displayOutput("\nEnter the test name:\n");
            testName = input.takeInput();
        }

        //loading the test using deserialization
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./"+testName));
        Test t = (Test)ois.readObject();
        ois.close();
        output.displayOutput("\n" + testName + " is loaded.");
        loadedTests.put(testName, t);
    }

    //modifying the test
    public void modifyTest() throws IOException {

        //displaying a message if no tests are loaded
        if(loadedTests.size() == 0) {
            output.displayOutput("\nNo tests are loaded\n");
            return;
        }

        //displaying list of loaded tests
        output.displayOutput("\nLoaded Tests\n");

        for(String testLoaded: loadedTests.keySet())
            output.displayOutput(testLoaded);

        //asking the user for their choice
        output.displayOutput("\nEnter your choice\n");
        String testName = input.takeInput();

        //validating user input
        while(!(loadedTests.containsKey(testName))){
            output.displayOutput("\nEnter a valid test name!!\n");
            for(String testLoaded: loadedTests.keySet())
                output.displayOutput(testLoaded);
            output.displayOutput("\nEnter the test name:\n");
            testName = input.takeInput();
        }

        //displaying the test
        Test t = loadedTests.get(testName);
        output.displayOutput("\nHere is the  list of questions in the test.\n");

        for(int i = 0 ; i < t.questionList.size() ; i++){
            output.displayOutput("\nQuestion "+(i+1)+" :");
            t.questionList.get(i).display();
            output.displayOutput("");
        }

        //asking whether they wish to modify it or not
        output.displayOutput("\nDo you wish to modify the test?(yes/no)\n");
        String bool = input.takeInput();

        while(!(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no"))){
            output.displayOutput("\nDo you wish to modify the test?(yes/no)\n");
            output.displayOutput("\nEnter a valid choice\n");
            bool = input.takeInput();
        }

        if(bool.equalsIgnoreCase("yes")){

            //displaying the question prompt and asking them whether they want to modify or not and giving the users an
            //option to modify questions, choices, maximum responses, maximum length
            while(bool.equalsIgnoreCase("yes")){
                output.displayOutput("\nEnter the question no. you wish to change\n");
                String qNum = input.takeInput();
                while(!input.isInteger(qNum)){
                    output.displayOutput("Enter an integer");
                    output.displayOutput("\nEnter the question no. you wish to change\n");
                    qNum = input.takeInput();
                }
                int n = Integer.valueOf(qNum);

                while(n < 1 || n > t.questionList.size()){
                    output.displayOutput("\nEnter a valid choice\n");
                    output.displayOutput("\nEnter the question no. you wish to change\n");
                    qNum = input.takeInput();
                    while(!input.isInteger(qNum)){
                        output.displayOutput("Enter an integer");
                        output.displayOutput("\nEnter the question no. you wish to change\n");
                        qNum = input.takeInput();
                    }
                    n = Integer.valueOf(qNum);
                }

                t.questionList.get(n - 1).modify();

                output.displayOutput("\nThe correct answer for this question is set to " + t.correctAnswers.get(n - 1) + "\n");
                output.displayOutput("Do you wish to modify it?(yes/no)");
                bool = input.takeInput();

                while( !(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no")) ){
                    output.displayOutput("\nEnter a valid choice\n");
                    output.displayOutput("The correct answer for this question is set to " + t.correctAnswers.get(n - 1) + "\n");
                    output.displayOutput("Do you wish to modify it?(yes/no)");
                    bool = input.takeInput();
                }

                if(bool.equalsIgnoreCase("yes")){
                    t.correctAnswers.set(n - 1, t.questionList.get(n-1).getCorrectAnswer());
                }

                output.displayOutput("\nDo you wish to continue modifying the test?(yes/no)\n");
                bool = input.takeInput();
                while( !(bool.equalsIgnoreCase("yes")|| bool.equalsIgnoreCase("no")) ){
                    output.displayOutput("\nDo you wish to continue modifying the test?(yes/no)\n");
                    output.displayOutput("\nEnter a valid choice\n");
                    bool = input.takeInput();
                }
            }

            t.saveTest();
        }
    }

    //taking the test
    public void takeTest() throws IOException, ClassNotFoundException {

        resp = new Response();

        //displaying a message if no tests are loaded
        if(loadedTests.size() == 0) {
            output.displayOutput("\nNo tests are loaded!!\n");
            return;
        }

        //displaying a list of loaded tests
        output.displayOutput("\nLoaded Tests\n");

        for(String testLoaded: loadedTests.keySet())
            output.displayOutput(testLoaded);

        //asking the user which test they want to take
        output.displayOutput("\nEnter your choice\n");
        String testName = input.takeInput();

        //validating whether user has entered a valid name or not
        while(!(loadedTests.containsKey(testName))){
            output.displayOutput("\nEnter a valid test name\n");
            output.displayOutput("Here are you choices\n");
            for(String testLoaded : loadedTests.keySet())
                output.displayOutput(testLoaded);
            output.displayOutput("\nEnter your choice\n");
            testName = input.takeInput();
        }

        //displaying the questions and asking for response
        Test t = loadedTests.get(testName);

        for(int i = 0; i < t.questionList.size() ; i++){
            output.displayOutput("\nQuestion "+(i+1)+" :");
            List<String> response = t.questionList.get(i).take();
            resp.addResponse(response);
        }

        //creating a name for the response on the basis of test name and the available responses for that particular test
        int i = testName.indexOf(".test");
        String sName = testName.substring(0, i);
        ArrayList<String> respList = responseList();

        int count = 0;
        ArrayList<Integer> array = new ArrayList<>();

        for (String s : respList) {

            if (s.startsWith(sName)) {
                int index = s.indexOf(".res");
                int index1 = s.indexOf("Response");
                String numR = s.substring(index1 + 8, index);
                count = Integer.valueOf(numR) + 1;
                array.add(count);
            }

        }

        count = array.size() > 0 ? getMax(array) : 0;
        String responseName = testName.substring(0, i) + "Response" + count + ".res";

        //saving the test using serialization
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./"+responseName));
        oos.writeObject(resp);
        oos.close();
        output.displayOutput("\n" + responseName+" is saved successfully");

    }

    //method for tabulating the test
    public void tabulateTest() throws IOException, ClassNotFoundException {

        List<List<String>> TFResponse = new ArrayList<>();
        List<List<String>> MultipleChoiceResponse = new ArrayList<>();
        List<List<String>> ShortAnswerResponse = new ArrayList<>();
        List<List<String>> EssayResponse = new ArrayList<>();
        List<List<String>> MatchingResponse = new ArrayList<>();
        List<List<String>> DateResponse = new ArrayList<>();

        List<Response> responseObjects = new ArrayList<>();

        ///displaying a message if no tests are loaded
        if(loadedTests.size() == 0) {
            output.displayOutput("\nNo tests are loaded!!\n");
            return;
        }

        //displaying a list of loaded tests
        output.displayOutput("\nLoaded Tests\n");

        for(String testLoaded: loadedTests.keySet())
            output.displayOutput(testLoaded);

        //asking the user which test they want to tabulate
        output.displayOutput("\nEnter your choice\n");
        String testName = input.takeInput();

        //validating whether user has entered a valid name or not
        while( !(loadedTests.containsKey(testName)) ){
            output.displayOutput("\nEnter a valid test name\n");
            output.displayOutput("\nLoaded tests\n");

            for(String testLoaded : loadedTests.keySet())
                output.displayOutput(testLoaded);

            output.displayOutput("\nEnter your choice\n");
            testName = input.takeInput();
        }

        Test t = loadedTests.get(testName);

        ArrayList<String> allResponses = responseList();
        ArrayList<String> particularTestResponses = new ArrayList<>();
        String tName = testName.substring(0, testName.indexOf('.'));

        for (String allRespons : allResponses) {
            if (allRespons.startsWith(tName))
                particularTestResponses.add(allRespons);
        }

        if(particularTestResponses.size() == 0){
            output.displayOutput("\nNo responses for this test");
        }

        else {

            for (String particularTestRespons : particularTestResponses) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./" + particularTestRespons));
                Response r = (Response) ois.readObject();
                responseObjects.add(r);
                ois.close();
            }

            for (Response responseObject : responseObjects) {
                List<List<String>> userResponse = responseObject.getResponse();

                for (int j = 0; j < userResponse.size(); j++) {

                    if (t.questionList.get(j).getQuestionType().equalsIgnoreCase("TF"))
                        TFResponse.add(userResponse.get(j));
                    if (t.questionList.get(j).getQuestionType().equalsIgnoreCase("MultipleChoice"))
                        MultipleChoiceResponse.add(userResponse.get(j));
                    if (t.questionList.get(j).getQuestionType().equalsIgnoreCase("Matching"))
                        MatchingResponse.add(userResponse.get(j));
                    if (t.questionList.get(j).getQuestionType().equalsIgnoreCase("Date"))
                        DateResponse.add(userResponse.get(j));
                    if (t.questionList.get(j).getQuestionType().equalsIgnoreCase("Essay"))
                        EssayResponse.add(userResponse.get(j));
                    if (t.questionList.get(j).getQuestionType().equalsIgnoreCase("ShortAnswer"))
                        ShortAnswerResponse.add(userResponse.get(j));
                }

            }

            for(int j = 0; j < t.questionList.size(); j++){
                if(t.questionList.get(j).getQuestionType().equalsIgnoreCase("TF")){
                    output.displayOutput("\nQuestion " + (j + 1) + ":");
                    t.questionList.get(j).display();
                    output.displayOutput("\nTabulation for Question " + (j + 1) +": \n");
                    HashMap<List<String>,Integer> count = countFrequencies(TFResponse);
                    for(List<String> keys: count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }

                if(t.questionList.get(j).getQuestionType().equalsIgnoreCase("MultipleChoice")){
                    output.displayOutput("\nQuestion " + (j + 1 ) + ":");
                    t.questionList.get(j).display();
                    output.displayOutput("\nTabulation for Question " + (j + 1) + ": \n");
                    HashMap<List<String>,Integer> count = countFrequencies(MultipleChoiceResponse);
                    for(List<String> keys: count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }

                if(t.questionList.get(j).getQuestionType().equalsIgnoreCase("Matching")){
                    output.displayOutput("\nQuestion " + (j + 1) + ":");
                    t.questionList.get(j).display();
                    output.displayOutput("\nTabulation for Question " + (j + 1) + ": \n");
                    HashMap<List<String>,Integer> count = countFrequencies(MatchingResponse);
                    for(List<String> keys: count.keySet())
                        output.displayOutput(keys + ": "+count.get(keys));
                }

                if(t.questionList.get(j).getQuestionType().equalsIgnoreCase("Date")){
                    output.displayOutput("\nQuestion " + (j + 1) +  ":");
                    t.questionList.get(j).display();
                    output.displayOutput("\nTabulation for Question " + ( j + 1) + ": \n");
                    HashMap<List<String>,Integer> count = countFrequencies(DateResponse);
                    for(List<String> keys: count.keySet())
                        output.displayOutput(keys + ": "+count.get(keys));
                }

                if(t.questionList.get(j).getQuestionType().equalsIgnoreCase("Essay")){
                    output.displayOutput("\nQuestion " + (j + 1) + ":");
                    t.questionList.get(j).display();
                    output.displayOutput("\nTabulation for Question " + (j + 1) + ": \n");
                    HashMap<List<String>,Integer> count = countFrequencies(EssayResponse);
                    for(List<String> keys: count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }

                if(t.questionList.get(j).getQuestionType().equalsIgnoreCase("ShortAnswer")){
                    output.displayOutput("\nQuestion "+(j+1)+":");
                    t.questionList.get(j).display();
                    output.displayOutput("\nTabulation for Question "+(j+1)+": \n");
                    HashMap<List<String>,Integer> count = countFrequencies(ShortAnswerResponse);
                    for(List<String> keys: count.keySet())
                        output.displayOutput(keys + ": " + count.get(keys));
                }
            }
        }

    }

    //method for grading the test
    public void gradeTest() throws IOException, ClassNotFoundException {

        ///displaying a message if no tests are loaded
        if(loadedTests.size() == 0) {
            output.displayOutput("\nNo tests are loaded!!\n");
            return;
        }

        //displaying a list of loaded tests
        output.displayOutput("\nLoaded Tests\n");

        for(String testLoaded: loadedTests.keySet())
            output.displayOutput(testLoaded);

        //asking the user which test they want to take
        output.displayOutput("\nEnter your choice\n");
        String testName = input.takeInput();

        //validating whether user has entered a valid name or not
        while(!(loadedTests.containsKey(testName))){
            output.displayOutput("\nEnter a valid test name\n");
            output.displayOutput("\nLoaded tests\n");
            for(String testLoaded : loadedTests.keySet())
                output.displayOutput(testLoaded);
            output.displayOutput("\nEnter your choice\n");
            testName = input.takeInput();
        }

        Test t = loadedTests.get(testName);

        ArrayList<String> allResponses = responseList();
        ArrayList<String> particularTestResponses = new ArrayList<>();
        String tName = testName.substring(0, testName.indexOf('.'));

        for (String allRespons : allResponses) {
            if (allRespons.startsWith(tName))
                particularTestResponses.add(allRespons);
        }

        if(particularTestResponses.size() == 0)
            output.displayOutput("\nNo responses for this test");

        else {
            output.displayOutput("\nAvailable responses for the test\n");

            for (String particularTestRespons : particularTestResponses) output.displayOutput(particularTestRespons);

            output.displayOutput("\nEnter the response name you wish to grade:\n");
            String responseName = input.takeInput();

            //validating whether the user has entered a valid name or not
            while(!(particularTestResponses.indexOf(responseName)>=0)){
                output.displayOutput("\nEnter a valid response name!!\n");
                output.displayOutput("\nAvailable responses for the test\n");

                for (String particularTestRespons : particularTestResponses)
                    output.displayOutput(particularTestRespons);

                output.displayOutput("\nEnter the response name:\n");
                responseName = input.takeInput();
            }

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./"+responseName));
            Response r = (Response)ois.readObject();
            ois.close();

            List<List<String>> userResponses = r.getResponse();

            int score = 0;
            int numQuestionsNotIncluded = 0;

            for(int i = 0 ; i < t.questionList.size(); i++){
                if(t.questionList.get(i).getQuestionType().equalsIgnoreCase("Essay") || t.questionList.get(i).getQuestionType().equalsIgnoreCase("ShortAnswer"))
                    numQuestionsNotIncluded += 1;
            }

            for(int i = 0; i < userResponses.size(); i++ ){
                if( userResponses.get(i).equals(t.correctAnswers.get(i)) && !(t.questionList.get(i).getQuestionType().equalsIgnoreCase("Essay") || t.questionList.get(i).getQuestionType().equalsIgnoreCase("ShortAnswer")) )
                    score += 1;

            }

            int totalQuestions = userResponses.size() - numQuestionsNotIncluded;
            output.displayOutput("There are a total of " + userResponses.size() + " questions out of which " + numQuestionsNotIncluded + " questions cannot be graded at this time\n");
            output.displayOutput("Your score: ");
            double percentage = (score * 1.0) / totalQuestions * 100;
            output.displayOutput(score + " / " + totalQuestions + " = " + percentage);
        }
    }

}
