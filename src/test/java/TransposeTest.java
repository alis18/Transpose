import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TransposeTest {

    @Test
    void transpose() {
        List<List<String>> first = List.of(List.of("A", "B", "C"), List.of("D", "E"), List.of("M", "D", "G", "K"));
        var a = Transpose.transpose(first,5, false, false);
        List<List<String>> second = List.of(List.of("A    ", "D    ", "M    "), List.of("B    ", "E    ", "D    "),
                List.of("C    ", "G    "), List.of("K    "));
        assertEquals(a, second);
    }

    @Test
    void getText() {
        List<List<String>> a = null;
        try(FileWriter fw = new FileWriter("test")){
            fw.write("Just for some testing\n For my function");
            fw.close();
            a = Transpose.getText(new Scanner(new File("test")));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        List<List<String>> expect = List.of(List.of("Just", "for", "some", "testing"), List.of("For", "my", "function"));
        assertEquals(expect, a);
    }

    @Test
    void print() {
        List<List<String>> first = List.of(List.of("A", "B", "C"), List.of("D", "E"), List.of("M", "D", "G", "K"));
        String res = Transpose.print(first);
        String exp = "A B C\nD E\nM D G K\n";
        assertEquals(exp, res);
    }
}