package com.company;

import java.io.*;

public class Output implements Serializable {

    //for serialization
    private static final long serialVersionUID = 1L;

    //printing the string passed
    public void displayOutput(String s){
        System.out.println(s);
    }
}
