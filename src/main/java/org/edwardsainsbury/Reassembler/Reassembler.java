package org.edwardsainsbury.Reassembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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

    private int getOverlap(char left[], char right[] ) {
        System.out.println(Arrays.toString(left));
        System.out.println(Arrays.toString(right));
        for (int i = 0; i < left.length; i++) {
            System.out.println(Arrays.copyOfRange(left, left.length - 1 - i, left.length));
            System.out.println(Arrays.copyOfRange(right, 0, i + 1));
            System.out.println(Arrays.copyOfRange(left, left.length - 1 - i, left.length) !=
                    Arrays.copyOfRange(right, 0, i + 1));
            char[] arr1 = Arrays.copyOfRange(left, left.length - 1 - i, left.length);
            char[] arr2 = Arrays.copyOfRange(right, 0, i + 1);
            if (Arrays.equals(arr1, arr2)) {
                return i;
            }
        }
        return 0;
    }


    private String reassemble(){

        Arrays.sort(fragments, Comparator.comparingInt(a->a.length));

        for (int i = 0; i < fragments.length; i++) {
            for (int j = i + 1; j < fragments.length; j++) {
                int overlap = getOverlap(fragments[i], fragments[j]);
                System.out.println(overlap);
                overlap = getOverlap(fragments[j], fragments[i]);
                System.out.println(overlap);
            }
        }
        return ""; //String.join("", fragments);
    }

    public String getReassembled(){
        return reassembled;
    }

}
