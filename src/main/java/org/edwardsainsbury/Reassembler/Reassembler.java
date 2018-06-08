package org.edwardsainsbury.Reassembler;

import com.sun.tools.javac.util.ArrayUtils;

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
            char[] arr1 = Arrays.copyOfRange(left, left.length - i - 1, left.length);

            char[] arr2 = Arrays.copyOfRange(right, 0, i + 1);

            if (Arrays.equals(arr1, arr2) && arr1.length != left.length) {
                return i+1;
            } else if (Arrays.equals(arr1, arr2)){
                return -1;
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
        Collections.reverse(fragments);
        ArrayList<Node> stringNodes = new ArrayList<>();

        stringNodes.add(new Node(fragments.get(0), 0 , 0));
        fragments.remove(0);

        int i = 0;
        while (fragments.size() > 0) {
            int bestNodePostion = 0;
            int oldPosition = 0;
            int bestLeftOverlap = 0;
            int bestRightOverlap = 0;
            char[] toAdd = new char[0];
            int[] leftOverlaps = new int[stringNodes.size() + 1];
            int[] rightOverlaps = new int[stringNodes.size() + 1];

            String add = "false";
            for (int j = 0; j < stringNodes.size(); j++){
                toAdd = fragments.get(i);

                Node baseNode = stringNodes.get(j);
                int leftOverlap = getOverlap(toAdd, baseNode.value);
                int rightOverlap = getOverlap(baseNode.value, toAdd);
                if (leftOverlap == -1 || rightOverlap == -1) {
                    fragments.remove(i);
                    add = "remove";
                    break;
                }
                leftOverlaps[j] = leftOverlap;

                rightOverlaps[j] = rightOverlap;

                if (leftOverlaps[j] > baseNode.leftOverlap && leftOverlaps[j] > bestLeftOverlap){
                    bestNodePostion = j;
                    bestLeftOverlap = leftOverlaps[bestNodePostion];
                    add = "left";
                    oldPosition = j;
                } else if (rightOverlaps[j]  > baseNode.rightOverlap && rightOverlaps[j]  >
                        bestRightOverlap) {
                    bestNodePostion = j+1;
                    oldPosition = j;
                    bestRightOverlap = rightOverlaps[oldPosition] ;
                    add = "right";

                }
            }

            switch (add) {
                case "left":
                    stringNodes.get(bestNodePostion).leftOverlap = leftOverlaps[oldPosition];
                    stringNodes.add(bestNodePostion, new Node(Arrays.copyOfRange(toAdd, 0
                            , toAdd.length),
                            rightOverlaps[oldPosition], leftOverlaps[oldPosition]));
                    fragments.remove(i);
                    break;

                case "right":

                    stringNodes.get(bestNodePostion- 1).rightOverlap = rightOverlaps[oldPosition];
                    stringNodes.add(bestNodePostion, new Node(Arrays.copyOfRange(toAdd, 0
                            , toAdd.length), rightOverlaps[oldPosition], leftOverlaps[oldPosition]));
                    fragments.remove(i);
                    break;

                case "remove":
                    break;

                default:
                    i++;
            }

            if (i == fragments.size()){
                i = 0;
            }

        }
        char[] base = stringNodes.get(0).value;
        for (int y = 1; y < stringNodes.size(); y++){
            Node node = stringNodes.get(y);
            base = concat(base, Arrays.copyOfRange(node.value, node.leftOverlap, node.value.length));
        }
        System.out.println(base);

        return new String(base);
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
