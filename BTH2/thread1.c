#include <stdio.h>
#include <pthread.h>

//Hàm xử lý của luồng thực hiện liên tục in chữ x
void* printx(void* unused)
{
    while (1) {
        fputc('x', stdout);
    }
    return NULL;
}

int main(int argc, char** argv) {
    pthread_t thread_id;
    //Tạo ra một luồng mới với hàm xử lý luồng là printx
    pthread_create(&thread_id, NULL, &printx, NULL);
    //Chương trình chính liên tục in chữ o
    
    while (1) {
        fputc('o', stdout);
    }
    return 0;
}