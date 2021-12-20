package com.company;

import java.io.*;
import java.util.*;

public class Input implements Serializable{

    private static final long serialVersionUID = 1L;

    //asking the user for the input
    public String takeInput() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        return s;

    }

    //checking whether the input is an empty string  or not
    public boolean isNull(String s){
        return s.length() == 0;
    }


    //checking whether the entered string is integer or not
    public boolean isInteger(String s){

        if(s.length() >= 1){
            for(int i = 0; i< s.length(); i++){
                if(!(Character.isDigit(s.charAt(i))))
                    return false;
            }
        }

        if(s.length() == 0 )
            return false;

        return true;

    }

}
