package org.edwardsainsbury.Reassembler;

import java.util.*;

public class Reassembler {
    private ArrayList<char[]> fragments;
    private String reassembled;

    public Reassembler(String inputString){
        String[] fragmentArray = inputString.split(";");
        ArrayList<char[]> fragments = new ArrayList<>();

        for (String fragment : fragmentArray){
            fragments.add(fragment.toCharArray());
        }
        this.fragments = fragments;
        this.reassembled = reassemble();
    }

    private int getOverlap(char left[], char right[] ) {
        for (int i = 0; i < left.length; i++) {
            char[] arr1 = Arrays.copyOfRange(left, left.length - 1 - i, left.length);
            char[] arr2 = Arrays.copyOfRange(right, 0, i + 1);
            if (Arrays.equals(arr1, arr2)) {
                System.out.println("Overlap");
                return i+1;
            }
        }
        return 0;
    }


    private static char[] concat(char[] first, char[] second) {
        char[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private String reassemble(){
        fragments.sort(Comparator.comparingInt(a->a.length));
        char[] base = fragments.get(0);
        fragments.remove(0);
        int i = 0;
        while (fragments.size() > 0) {
            char[] toAdd = fragments.get(i);
            int leftOverlap = getOverlap(toAdd, base);
            int rightOverlap = getOverlap(base, toAdd);
            if (leftOverlap > rightOverlap){
                base = concat(Arrays.copyOfRange(toAdd, 0,toAdd.length - leftOverlap), base);
                fragments.remove(i);
            } else if (leftOverlap > 0 || rightOverlap > 0) {
                base = concat(Arrays.copyOfRange(base, 0,base.length - rightOverlap), toAdd);
                fragments.remove(i);
            } else {
                // Cant find an overlap so skip to next item
                i++;
            }

            if (i == fragments.size()){
                i = 0;
            }
            System.out.println(toAdd);
            System.out.println(base);

        }

        return new String(base);
    }


    public String getReassembled(){
        return reassembled;
    }

}
