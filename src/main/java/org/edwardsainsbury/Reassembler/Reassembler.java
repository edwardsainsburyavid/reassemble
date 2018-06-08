package org.edwardsainsbury.Reassembler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
            char[] arr1 = Arrays.copyOfRange(left, left.length - i - 1, left.length);
            char[] arr2 = Arrays.copyOfRange(right, 0, i + 1);

            if (Arrays.equals(arr1, arr2) && arr1.length != left.length) {
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
        // Sort fragments to add larger first
        fragments.sort(Comparator.comparingInt(a -> -a.length));

        char[] base = fragments.get(0);
        fragments.remove(0);
        int noAdd = 0;

        // Loop while there are remaining fragemnts
        while (noAdd != fragments.size()) {
            int bestOverlap = 0;
            int bestOverlapFragmentIndex = 0;
            String bestOverlapDirection = "false";

            for (int i = 0; i < fragments.size(); i++) {
                char[] test = fragments.get(i);
                int leftOverlap = getOverlap(test, base);
                int rightOverlap = getOverlap(base, test);
                if (leftOverlap > bestOverlap && leftOverlap > rightOverlap) {
                    bestOverlap = leftOverlap;
                    bestOverlapFragmentIndex = i;
                    bestOverlapDirection = "left";
                } else if (rightOverlap > bestOverlap) {
                    bestOverlap = rightOverlap;
                    bestOverlapFragmentIndex = i;
                    bestOverlapDirection = "right";
                }
            }

            if (bestOverlap > 0) {
                char[] toAdd = fragments.get(bestOverlapFragmentIndex);
                if (bestOverlapDirection.equals("right")) {
                    base = concat(base, Arrays.copyOfRange(toAdd, bestOverlap, toAdd.length));
                } else {
                    base = concat(toAdd, Arrays.copyOfRange(base, bestOverlap, base.length));
                }
                fragments.remove(bestOverlapFragmentIndex);
            } else {
                noAdd++;
            }
        }

        return new String(base);

    }

    public String getReassembled(){
        return reassembled;
    }

}
