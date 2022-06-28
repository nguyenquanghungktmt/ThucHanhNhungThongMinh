import java.io.*;
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
            if (gpioNo < 0 || gpioNo > 3 || value < 0 || value > 1){
                System.out.println("Invalid Argument Value");
                exit(1);
            }

            System.out.println(gpioNo);
            System.out.println(value);
            Gpio gpio = new Gpio(gpioNo,"out", value);
            
            GpioControl gpioControl = new GpioControl(gpio);
            gpioControl.export(gpio.getGpioNo());
            gpioControl.direction(gpio.getDirection());
            gpioControl.writeValue(gpio.getValue());
            gpioControl.unexport(gpio.getGpioNo());

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}

class Gpio {
    private int gpioNo;
    private int value;
    private String direction;

    public Gpio(int gpioNo, String direction, int value) {
        this.gpioNo = gpioNo;
        this.direction = direction;
        this.value = value;
    }

    public int getGpioNo(){
        return this.gpioNo;
    }

    public String getDirection(){
        return this.direction;
    }

    public int getValue(){
        return this.value;
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
            File file = new File("/sys/class/gpio/gpio" + this.gpio.getGpioNo() + "/value");
            FileWriter fw = new FileWriter(file);
            fw.write(Integer.toString(value));
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void direction(String value) {
        try {
            System.out.println("direction success value " + value);
            File file = new File("/sys/class/gpio/gpio" + this.gpio.getGpioNo() + "/direction");
            FileWriter fw = new FileWriter(file);
            fw.write(value);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void export(int gpioNo) {
        try {
            System.out.println("export success pin " + gpioNo);
            File file = new File("/sys/class/gpio/export");
            FileWriter fw = new FileWriter(file);
            fw.write(Integer.toString(gpioNo));
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void unexport(int gpioNo) {
        try {
            System.out.println("unexport success pin " + gpioNo);
            File file = new File("/sys/class/gpio/unexport");
            FileWriter fw = new FileWriter(file);
            fw.write(Integer.toString(gpioNo));
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}