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
        System.out.println(left);
        System.out.println(right);
        for (int i = 0; i < left.length; i++) {
            System.out.println("arr1");
            char[] arr1 = Arrays.copyOfRange(left, left.length - i - 1, left.length);
            System.out.println(arr1);
            System.out.println("arr2");
            char[] arr2 = Arrays.copyOfRange(right, 0, i + 1);

            System.out.println(arr2);
            if (Arrays.equals(arr1, arr2)) {
                System.out.println("Overlap");
                return i+1;
            }
        }
        System.out.println("Returns 0");
        return 0;
    }


    private static char[] concat(char[] first, char[] second) {
        char[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private String reassemble(){

        fragments.sort(Comparator.comparingInt(a->a.length));
        ArrayList<Node> stringNodes = new ArrayList<>();

        stringNodes.add(new Node(fragments.get(0), 0 , 0));
        fragments.remove(0);

        int i = 0;
        while (fragments.size() > 0) {
            int bestNodePostion = 0;
            int bestLeftOverlap = 0;
            int bestRightOverlap = 0;
            char[] toAdd = new char[0];
            int[] leftOverlaps = new int[0];
            int[] rightOverlaps = new int[0];
            int startPosition = 0;
            String add = "false";
            for (int j = 0; j < stringNodes.size(); j++){
                toAdd = fragments.get(i);
                Node baseNode = stringNodes.get(j);
                leftOverlaps = new int[stringNodes.size() + 1];
                rightOverlaps = new int[stringNodes.size() + 1];
                leftOverlaps[j] = getOverlap(toAdd, baseNode.value);
                rightOverlaps[j] = getOverlap(baseNode.value, toAdd);

                if (leftOverlaps[j] > baseNode.leftOverlap && leftOverlaps[j] > bestLeftOverlap){
                    bestNodePostion = j;
                    bestLeftOverlap = leftOverlaps[bestNodePostion];
                    add = "left";
                } else if (rightOverlaps[j]  > baseNode.rightOverlap && rightOverlaps[j]  >
                        bestRightOverlap) {
                    bestNodePostion = j+1;
                    bestRightOverlap = rightOverlaps[bestNodePostion] ;
                    add = "right";
                } else {
                    add = "false";
                }
            }

            switch (add) {
                case "left":
                    startPosition = leftOverlaps[bestNodePostion];
                case "right":
                    startPosition = rightOverlaps[bestNodePostion];
            }


            if (add != "false"){
                //System.out.println(Arrays.copyOfRange(toAdd, 0,toAdd
                        //.length - bestLeftOverlap));
                //System.out.println(bestNodePostion);
                //System.out.println(Arrays.toString(rightOverlaps));
                //System.out.println(Arrays.toString(leftOverlaps));
                stringNodes.add(bestNodePostion, new Node(Arrays.copyOfRange(toAdd,startPosition
                        ,toAdd.length),
                        leftOverlaps[bestNodePostion], rightOverlaps[bestNodePostion-1]));
                fragments.remove(i);
            } else {
                // Cant find an overlap so skip to next item
                i++;
            }

            if (i == fragments.size()){
                i = 0;
            }


            char[] base = new char[0];
            for (Node node : stringNodes){
                //System.out.println(node.value);
                base = concat(base, node.value);
            }
            System.out.println(base);
            System.out.println(toAdd);

        }

        return "";//new String(base);
    }


    class Node{
        int leftOverlap;
        int rightOverlap;
        char[] value;

        Node(char[] value, int leftOverlap, int rightOverlap){
            this.value = value;
            this.leftOverlap = leftOverlap;
            this.rightOverlap = rightOverlap;
        }
    }


    public String getReassembled(){
        return reassembled;
    }

}
