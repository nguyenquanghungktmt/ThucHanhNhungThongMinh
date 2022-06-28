/* Nhấp nháy led */
int gpio_flash(unsigned int gpio[], int loop)
{
    unsigned char value = 0;
    while (loop--) {
        for (int i = 0; i < 6; i++){
            gpio_value(gpio[i], value);
        }
        value = ~value;
        sleep(1);
    }
}

/* Led chạy đối xứng từ ngoài vào trong */
int gpio_spot_bumber(unsigned int gpio[], int loop) {
    unsigned char value = 0;
    while (loop--) {
        for (int i = 0; i < 3; i++){
            gpio_value(gpio[i], 1);
            gpio_value(gpio[5 - i], 1);
            if (i == 0) {
                gpio_value(gpio[2], 0);
                gpio_value(gpio[3], 0);
            } else {
                gpio_value(gpio[i - 1], 0);
                gpio_value(gpio[6 - i], 0);
            }
            sleep(1);
        }
    }
}

/* Led đuổi từ trái sang phải */
int gpio_running(unsigned int gpio[], int loop) {
    unsigned char value = 0;
    while (loop--) {
        for (int i = 0; i < 6; i++) {
            gpio_value(gpio[i], 1);
            if (i == 0) {
                gpio_value(gpio[5], 0);
            } else {
                gpio_value(gpio[i - 1], 0);
            }
            sleep(1);
        }
    }
}

/*************************************************
Chương trình chính để test các hàm này
*************************************************/
#if 1
int main(int argc, char *argv[])
{
    int loop = 5;
    unsigned int gpio[6] = {2, 3, 17, 22, 27, 23}; /* Khai báo các số hiệu chân sử dụng */
    for (int i = 0; i < 6; i++){
        gpio_export(gpio[i]);
        gpio_dir_out(gpio[i]);
    }

    gpio_spot_bumber(gpio, loop);
    for (int i = 0; i < 6; i++)
    {
        gpio_unexport(gpio[i]);
    }
}
#endif