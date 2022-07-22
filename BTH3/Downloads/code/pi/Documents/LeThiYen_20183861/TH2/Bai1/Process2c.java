public class Process2c {
public static void main(String argv[])
{
String args[] = {"./process1"};
try {
Process p = Runtime.getRuntime().exec("./process1");
System.out.println("Run successfull!");
} catch (Exception e) {
System.err.println("Some error occured : " + e.toString());
}
}
}
