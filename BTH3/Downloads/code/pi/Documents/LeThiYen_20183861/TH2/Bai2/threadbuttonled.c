/**********************************************************
* Chuong trinh:
* main: Thuc hien dieu khien led chay duoi
* thread: Thuc hien doc trang thai (polling) nut bam de thay doi toc do led
***********************************************************/
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <stdlib.h>
#include <sys/time.h>
#define MAX 64
//Định nghĩa các giá trị chân board
#define K1 2
#define K2 3
#define LED1 4
#define LED2 5
#define LED3 6
#define LED4 7
#define ON 1
#define OFF 0
static int t = 500;
int GPIO_in[2] = {K1, K2};
int GPIO_out[4] = {LED1, LED2, LED3, LED4};
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
//Ham delay
void sleepms(int ms);
//Hàm khởi tạo các chân input cho nút bấm và output cho đèn
int initGPIO(int pin_in[], int pin_out[]);
//Hàm xử lý trạng thái đèn
int digitalWrite(int GPIO_pin, int value);
//Hàm polling đọc giá trị nút bấm
void* btn_polling(void* param);
int main() {
    initGPIO(GPIO_in, GPIO_out);
    int K1_pin = K1;
    int K2_pin = K2;
    //Tạo 2 luồng con cho mỗi nút bấm
    pthread_t K1_thread_id;
    pthread_t K2_thread_id;
    K1_thread_id = pthread_create(&K1_thread_id, NULL, btn_polling, &K1_pin);
    K2_thread_id = pthread_create(&K2_thread_id, NULL, btn_polling, &K2_pin);
    //Luồng master chạy riêng điều khiển chạy Led
    int led_no = 0;
    while(1) {
        digitalWrite(GPIO_out[led_no], ON);
        sleepms(t);
        digitalWrite(GPIO_out[led_no], OFF);
        led_no++;
        if(led_no == 4) {
            led_no = 0;
        }
    }
    pthread_join(K1_thread_id, NULL);
    pthread_join(K2_thread_id, NULL);
    return 0;
}
void sleepms(int ms) {
    usleep(1000 * ms);
    return;
}
    //Hàm khởi tạo các chân input cho nút bấm và output cho đèn
int initGPIO(int pin_in[], int pin_out[]) {
    int n_pin_in = 2;
    int n_pin_out = 4;
    //Tạo con trỏ file để xử lý các sysfs tương ứng với các chân
    FILE *fp = NULL;
    char setValue[4], GPIO_direction[MAX];
    //Đặt các chân làm input dành cho nút bấm thông qua file direction
    for(int i = 0; i < n_pin_in;i++) {
        sprintf(GPIO_direction, "/sys/class/gpio/gpio%d/direction", 
        pin_in[i]); 
        if((fp = fopen(GPIO_direction, "wb")) == NULL) {
            printf("Cannot open direction of gpio %d", pin_in[i]);
            return 1;
        }
        strcpy(setValue, "in");
        fwrite(&setValue, sizeof(char), 3, fp);
        fclose(fp);
    } 
    //Đặt các chân làm output dành cho các Led thông qua file direction
    for(int i = 0; i < n_pin_out;i++) {
        sprintf(GPIO_direction, "/sys/class/gpio/gpio%d/direction", 
        pin_out[i]); 
        if((fp = fopen(GPIO_direction, "wb")) == NULL) {
            printf("Cannot open direction of gpio %d", pin_out[i]);
            return 1;
        }
        strcpy(setValue, "out"); 
        fwrite(&setValue, sizeof(char), 3, fp);
        fclose(fp);
    }
    return 0;
}
//Hàm xử lý trạng thái đèn
int digitalWrite(int GPIO_pin, int value) {
FILE *fp = NULL;
char setValue[4], GPIO_value[MAX];
sprintf(GPIO_value, "/sys/class/gpio/gpio%d/value", GPIO_pin);
//Mở file value của các chân Led và truyền vào giá trị mức cao hoặc thấp
if((fp = fopen(GPIO_value, "wb")) == NULL) {
printf("Cannot open value of gpio %d", GPIO_pin);
return 1;
}
sprintf(setValue, "%d", value);
fwrite(&setValue, sizeof(char), 1, fp);
fclose(fp);
return 0;
}
//Hàm polling đọc giá trị nút bấm
void* btn_polling(void* param) {
int* K_pin = (int*) param;
printf("GPIO of K1 = %d\n", *K_pin);
FILE *K_file = NULL;
char setValue[4], K_value[MAX];
sprintf(K_value, "/sys/class/gpio/gpio%d/value", *K_pin);
int K_old = 0;
//Dùng polling để đọc giá trị input từ file value của chân tương ứng 
while(1) {
if((K_file = fopen(K_value, "rb")) == NULL) {
printf("Cannot open value of gpio %d\n", *K_pin);
exit(1);
}
//Đọc giá trị và chuyển về trạng thái để so sánh với các trạng thái trước
fread(&setValue, sizeof(char), 1, K_file);
int K_cur = (int)setValue[0] - 48;
if(K_old != K_cur) {
K_old = K_cur;
//Dùng khóa mutex để khóa giá trị t cho riêng luồng này
pthread_mutex_lock(&lock);
if(*K_pin == K1) {
t = t + 100;
printf("Increase t = %d\n", t);
} else {
t = t - 100;
if(t < 100) {
t = 100;
}
printf("Decrease t = %d\n", t);
}
sleepms(100);
pthread_mutex_unlock(&lock);
}
fclose(K_file);
}
return NULL;
}