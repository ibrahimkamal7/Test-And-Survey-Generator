package com.company;

import java.util.*;
import java.io.*;

public class Response implements Serializable {

    //storing lists of responses
    List<List<String>> responses = new ArrayList<>();

    //for serialization
    private static final long serialVersionUID = 1L;
    
    //adding a response
    public void addResponse(List<String> a)  {
        this.responses.add(a);
    }

    //getting the list of responses
    public List<List<String>> getResponse(){
        return this.responses;
    }

}
