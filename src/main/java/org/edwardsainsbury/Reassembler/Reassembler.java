package org.edwardsainsbury.Reassembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Reassembler extends AbstractReassembler {
    private String reassembled;
    private ArrayList<char[]> fragments;

    public Reassembler(String inputString){
        String[] fragmentArray = inputString.split(";");
        ArrayList<char[]> fragments = new ArrayList<>();

        for (String fragment : fragmentArray){
            fragments.add(fragment.toCharArray());
        }
        this.fragments = fragments;
        this.reassembled = setReassembled();
    }

    public static void main(String[] args) {

        try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
            String fragmentProblem;
            while ((fragmentProblem = in.readLine()) != null) {
                Reassembler reassembler = new Reassembler(fragmentProblem);
                System.out.println(reassembler.getReassembled());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Please provide path to file");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * concat:
     * Utility method to concatenate a second array to the first
     *
     * @param first  - char[] Char array to be appended to
     * @param second - char[] Char array to append first array
     * @return result - char[] Concatented result
     */
    private static char[] concat(char[] first, char[] second) {
        char[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    @Override
    public String getReassembled() {
        return reassembled;
    }

    @Override
    public void addFragment() {

    }

    @Override
    public void removeFragment() {

    }


    /**
     * getOverlaps:
     * Method that finds left and right hand out overlaps between a base fragment and a test fragment. Inner overlaps.
     * where the whole test fragment is a substring of the base fragment, are ignored. This limits interations to
     * a maximum of (shortest length * 2)
     *
     * @param testFragment - char[] to compare either side of base char[]
     * @param baseFragment - char[] to be used as a base for comparison
     * @return overlaps - int array containing the left and right hand overlaps
     */
    private int getOverlaps(char testFragment[], char baseFragment[]) {
        int[] overlaps = new int[2];
        char[][] fragments = {testFragment, baseFragment};
        for (int j = 0; j < 2; j++) {
            char[] fragment = fragments[j];
            char[] base = fragments[(j + 1) % 2];
            int longest = Math.min(base.length, fragment.length);
            for (int i = 0; i < longest; i++) {
                char[] arr1 = Arrays.copyOfRange(fragment, fragment.length - i - 1, fragment.length);
                char[] arr2 = Arrays.copyOfRange(base, 0, i + 1);
                if (Arrays.equals(arr1, arr2) && arr1.length != fragment.length) {
                    overlaps[j] = i + 1;
                    break;
                }
            }
        }
        if (overlaps[0] > overlaps[1]) {
            return overlaps[0] * -1;
        }

        return overlaps[1];
    }

    /**
     * @return
     */
    private String setReassembled() {
        /* Sort fragments to add largest first */
        fragments.sort(Comparator.comparingInt(a -> -a.length));

        char[] base = fragments.get(0);
        fragments.remove(0);
        int potentialSubstrings = 0;

        /* Loop while until potentialSubstrings == remaining fragments */
        while (potentialSubstrings != fragments.size()) {
            int bestOverlap = 0;
            int bestOverlapFragmentIndex = 0;
            String bestOverlapDirection = "left";

            for (int i = 0; i < fragments.size(); i++) {
                char[] test = fragments.get(i);

                if (bestOverlap > test.length) {
                    break;
                }

                int overlap = getOverlaps(test, base);
                int newBestOverlap = Math.abs(overlap);

                if (newBestOverlap > bestOverlap && overlap > 0) {
                    bestOverlapDirection = "right";
                }

                if (newBestOverlap > bestOverlap) {
                    bestOverlapFragmentIndex = i;
                    bestOverlap = newBestOverlap;
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
                /*Fragment has no outer overlap so mark as potential substring of larger fragment */
                potentialSubstrings++;
            }
        }

        return new String(base);

    }
}
