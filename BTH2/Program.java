import java.io.*;
import java.io.FileWriter;
import java.lang.*;
import static java.lang.System.*;

public class Program {
    public static void main(String argv[]) {
        String args[] = {"./test", null};
        try {
            Process p = Runtime.getRuntime().exec("./test");
        } catch (Exception e) {
            System.err.println("Some error occured : " + e.toString());
        }
    }
}