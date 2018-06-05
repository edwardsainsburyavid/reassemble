package org.edwardsainsbury.Reassembler;

import java.util.ArrayList;
import java.util.Arrays;

public class Reassembler {
    public char[][] fragments;
    private String reassembled;

    public Reassembler(String inputString){
        String[] fragmentArray = inputString.split(";");
        char[][] fragments = new char[fragmentArray.length][];
        for (int x = 0; x < fragmentArray.length; x++){
            fragments[x] = fragmentArray[x].toCharArray();
        }
        this.fragments = fragments;
        this.reassembled = reassemble();
    }

    private String reassemble(){

        Arrays.sort(fragments);
        for (fragment : char[][] fragments){

        }

        return ""; //String.join("", fragments);
    }

    public String getReassembled(){
        return reassembled;
    }
}
