#include <stdio.h>
#include <pthread.h>
int counter;
pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
pthread_t tid[2];
void* threadFunc(void* param) {
pthread_mutex_lock(&lock); /* Khóa tài nguyên để tránh xung đột */
for (int i = 0; i < 20000; i++) {
counter++;
}
pthread_mutex_unlock(&lock); /* Mở khóa tài nguyên để các luồng khác sử dụng 
*/
pthread_exit(NULL);
}
int main() {
counter = 0;
int ret = 0;
for (int i = 0;i< 2;i++) {
ret = pthread_create(&(tid[i]), NULL, threadFunc, NULL);
if (ret != 0)
{
printf("Thread [%d] created error\n", i);
}
}
pthread_join(tid[0], NULL);
pthread_join(tid[1], NULL);
printf("Counter = %d\n", counter);
return 0;
}
