import java.io.File;
import java.io.FileWriter;
import static java.lang.System.*;
public class GpioLed {
    
 /* Hàm main để khởi chạy chương trình */
public static void main(String[] args) {
int value, gpioNo;
if (args.length != 2) {
System.out.println("error");
exit(1);
}
try {
gpioNo = Integer.parseInt(args[0]);
value = Integer.parseInt(args[1]);
if (gpioNo < 0 || gpioNo > 2 || value < 0 || value > 1){
System.out.println("Invalid Argument Value");
exit(1);
}
/* Khởi tạo một đối tượng GPIO và truyền vào các tham số
dòng lệnh từ bàn phím */
Gpio gpio = new Gpio(gpioNo,"out", value);
GpioControl gpioControl = new GpioControl(gpio);
gpioControl.writeValue(value);
} catch (NumberFormatException ex) {
ex.printStackTrace();
}
}
}
class Gpio {
private int gpioNo;
private String direction;
private int value;
public void setGpioNo(int gpioNo) { this.gpioNo = gpioNo; } 
public int getGpioNo() { return this.gpioNo; } 
public void setDirection(String direction) { this.direction = direction; } 
public String getDirection() { return this.direction; } 
public void setValue(int value) { this.value = value; } 
public int getValue() { return this.value; }
public Gpio(int gpioNo, String direction, int value) {
this.gpioNo = gpioNo;
this.direction = direction;
this.value = value;

}
}
class GpioControl {
private Gpio gpio;
public GpioControl(Gpio gpio) {
this.gpio = gpio;
}
/* Phương thức hỗ trợ ghi giá trị 0/1 vào chân GPIO */
public void writeValue(int value) {
try {
File file = new File("/sys/class/gpio/gpio" + 
this.gpio.getGpioNo() + "/value");
FileWriter fw = new FileWriter(file);
fw.write(Integer.toString(value));
fw.close();
} catch (Exception e) {
e.printStackTrace();
}
}
}