package org.edwardsainsbury.reassembler;

import org.edwardsainsbury.Reassembler.Reassembler;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReassemblerTest {

    @Test
    public void getReassembled() {
        /*
        String result = new Reassembler("m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, " +
                "adipisci velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem ipsum qu;iquam" +
                " quaerat vol;psum quia dolor sit amet, consectetur, a;ia dolor sit amet, conse;squam est, qui " +
                "do;Neque porro quisquam est, qu;aerat voluptatem.;m eius modi tem;Neque porro qui;, sed quia non " +
                "numquam ei;lorem ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut labore et" +
                " dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us modi tempora incid;Neque porro" +
                " quisquam est, qui dolorem i;uam eius modi tem;pora inc;am al").getReassembled();
        String expectedResult = "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, " +
                "adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam " +
                "quaerat voluptatem.";
        assertEquals(result, expectedResult);

        result = new Reassembler("O draconia;conian devil! Oh la;h lame sa;saint! ").getReassembled();
        expectedResult = "O draconian devil! Oh lame saint! ";
        assertEquals(result, expectedResult);
        */

        String result = new Reassembler("Hello w;world.").getReassembled();
        String expectedResult = "Hello world.";
        assertEquals(expectedResult, result);
        /*
        String result = new Reassembler("").getReassembled();
        String expectedResult = "";
        assertEquals(result, expectedResult);

        result = new Reassembler("").getReassembled();
        expectedResult = "";
        assertEquals(result, expectedResult);
        */
    }
}