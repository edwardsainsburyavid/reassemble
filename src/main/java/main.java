import org.edwardsainsbury.Reassembler.Reassembler;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
            String fragmentProblem;
            while ((fragmentProblem = in.readLine()) != null) {
                System.out.println(fragmentProblem);
                Reassembler reassembler = new Reassembler(fragmentProblem);
                System.out.println(reassembler.getReassembled());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
