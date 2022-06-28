#include<stdio.h>// standard input / output functions
#include <unistd.h>
#include <time.h>
int main(int argc, char** argv) {
    printf("Ma tien trinh goi ham system(): %d\n",(int)getpid());
    printf("Chay tien trinh moi voi ham system()\n");
    system("./process1");
    return(0);
}