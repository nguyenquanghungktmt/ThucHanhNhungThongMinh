#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>

#define GPIO_DIR_IN 0
#define GPIO_DIR_OUT 1


/*************************************************
Hàm đăng ký (export) chân gpio muốn sử dụng ra không gian người dùng
*************************************************/
int gpio_export(unsigned gpio)
{
    int fd, len;
    char buf[11];
    fd = open("/sys/class/gpio/export", O_WRONLY);
    if (fd < 0) {
        perror("gpio/export");
        return fd;
    }
    len = snprintf(buf, sizeof(buf), "%d", gpio);
    write(fd, buf, len); //Ghi số hiệu (ID) pin muốn sử dụng vào file /sys/class/gpio/export khi đăng ký sử dụng
    close(fd);
    return 0;
}

/*************************************************
Hàm giải phóng (unexport) chân gpio khi không còn sử dụng
*************************************************/
int gpio_unexport(unsigned gpio)
{
    int fd, len;
    char buf[11];
    fd = open("/sys/class/gpio/unexport", O_WRONLY);
    if (fd < 0) {
        perror("gpio/export");
        return fd;
    }
    len = snprintf(buf, sizeof(buf), "%d", gpio);
    write(fd, buf, len); //Ghi số hiệu (ID) pin muốn sử dụng vào file /sys/class/gpio/unexport khi giải phóng
    close(fd);
    return 0;
}

/*************************************************
Hàm cấu hình chân gpio là in hay out (dir)
*************************************************/
int gpio_dir(unsigned gpio, unsigned dir)
{
    int fd, len;
    char buf[60];
    len = snprintf(buf, sizeof(buf), "/sys/class/gpio/gpio%d/direction", gpio);
    fd = open(buf, O_WRONLY);
    if (fd < 0) {
    perror("gpio/direction");
    return fd;
    }
    //Cấu hình pin là input/output bằng cách ghi giá trị (ASCII) in, out vào file /sys/class/gpio/gpio[ID]/diriection
    if (dir == GPIO_DIR_OUT)
        write(fd, "out", 4);
    else
        write(fd, "in", 3);
    close(fd);
    return 0;
}

/*************************************************
Hàm thiết lập chân gpio out
*************************************************/
int gpio_dir_out(unsigned gpio)
{
    return gpio_dir(gpio, GPIO_DIR_OUT); //trường hợp là output
}

/*************************************************
Hàm thiết lập chân gpio in
*************************************************/
int gpio_dir_in(unsigned gpio)
{
    return gpio_dir(gpio, GPIO_DIR_IN); //trường hợp là input
}

/*************************************************
Hàm ghi ra trị ra chân gpio out (tương ứng xuất 0 , 1)
*************************************************/
int gpio_value(unsigned gpio, unsigned value) {
    int fd, len;
    char buf[60];
    len = snprintf(buf, sizeof(buf), "/sys/class/gpio/gpio%d/value", gpio);
    fd = open(buf, O_WRONLY);
        if (fd < 0) {
        perror("gpio/value");
        return fd;
    }
    //Xuất giá trị 1, 0 bằng cách ghi ra file value tương ứng với pin đã cấu hình
    if (value)
        write(fd, "1", 2);
    else
        write(fd, "0", 2);

    close(fd);
    return 0;
}

/*************************************************
Chương trình chính để test các hàm này
*************************************************/
#if 0
int main(int argc, char *argv[]) {
    int i = 20;
    int pin_no = 2; //Sử dụng chân GPIO2
    gpio_export(pin_no);
    gpio_dir_out(pin_no);
    while (i--) {
        gpio_value(pin_no, i & 1); //toggle on GPIO2
        sleep(1);
    }
    gpio_unexport(pin_no);
}
#endif