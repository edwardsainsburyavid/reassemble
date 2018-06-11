package org.edwardsainsbury.Reassembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

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
     * @return result - char[] Concatenated result
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
    public void addFragment(String fragment) {

        fragments.add(fragment.toCharArray());
        reassembled = setReassembled();
        System.out.println(reassembled);
    }

    @Override
    public void removeFragment(String fragment) {
        char[] toRemove = fragment.toCharArray();
        boolean foundFlag = false;
        int fragmentIndex = 0;

        for (int i = 0; i < fragments.size(); i++) {
            if (Arrays.equals(fragments.get(i), toRemove)) {
                foundFlag = true;
                fragmentIndex = i;
                break;
            }
        }

        if (foundFlag) {
            fragments.remove(fragmentIndex);
        }

        reassembled = setReassembled();
        System.out.println(reassembled);
    }


    /**
     * getOverlaps:
     * Method that finds left and right hand overlaps between a base fragment and a test fragment.
     * For speed strings are only considered to overlap if the test string does not exist completely
     * within the base string. This give a maximum time complexity of (shortest_length * 2).
     * If the strings overlap on the left side of the base string, the overlap is returned negated.
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
            int shortest = Math.min(base.length, fragment.length);
            for (int i = 0; i < shortest; i++) {
                char[] arr1 = Arrays.copyOfRange(fragment, fragment.length - i - 1,
                        fragment.length);
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
     * setReassembled:
     * Iteratively combines sentance fragments into one main base string by checking for commonality.
     * @return Returns a fully recombined string.
     */
    private String setReassembled() {
        ArrayList<char[]> fragmentsCopy = new ArrayList<>();

        fragmentsCopy.addAll(fragments);

        char[] base = fragmentsCopy.get(0);
        fragmentsCopy.remove(0);

        for (int j = 0; j < fragments.size(); j++) {
            int bestOverlap = 0;
            int bestOverlapFragmentIndex = 0;
            boolean outerOverlap = false;
            boolean rightOverlap = false;

            for (int i = 0; i < fragmentsCopy.size(); i++) {

                char[] test = fragmentsCopy.get(i);
                int newBestOverlap;
                if (bestOverlap > test.length) {
                    break;
                }

                if (new String(base).contains(new String(test))){
                    newBestOverlap = test.length;
                    if (newBestOverlap > bestOverlap) {
                        outerOverlap = false;
                    }
                } else {
                    newBestOverlap = getOverlaps(test, base);
                    if (Math.abs(newBestOverlap) > bestOverlap) {
                        outerOverlap = true;

                        if (newBestOverlap > 0) {
                            rightOverlap = true;
                        }
                    }
                }

                if (Math.abs(newBestOverlap) > bestOverlap) {
                    bestOverlapFragmentIndex = i;
                    bestOverlap = Math.abs(newBestOverlap);
                }

            }

            if (bestOverlap > 0) {
                char[] toAdd = fragmentsCopy.get(bestOverlapFragmentIndex);
                if (outerOverlap){
                    if (rightOverlap) {
                        base = concat(base, Arrays.copyOfRange(toAdd, bestOverlap, toAdd.length));
                    } else{
                        base = concat(toAdd, Arrays.copyOfRange(base, bestOverlap, base.length));
                    }
                }
                fragmentsCopy.remove(bestOverlapFragmentIndex);
            }
        }
        return new String(base);
    }
}
