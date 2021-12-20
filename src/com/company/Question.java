package com.company;

import java.io.*;
import java.util.*;

public abstract class Question  implements Serializable {

    //for serialization
    private static final long serialVersionUID = 1L;

    //storing question prompt
    String question = "";

    //abstract methods for each question types
    public abstract void display();
    public abstract void create() throws IOException;
    public abstract List<String> take() throws IOException;
    public abstract void modify() throws IOException;
    public abstract String getQuestionType();
    public abstract List<String>  getCorrectAnswer() throws IOException;
}
