#include<stdio.h>// standard input / output functions
#include <unistd.h>
#include <time.h>
int main(int argc, char** argv)
{
    printf("Ma tien trinh exec() dang chay : %d\n",(int)getpid());
    char *args[] = {"./process1", NULL};
    printf("Chay tien trinh moi voi ham exec()\n");
    execvp(args[0], args);
    return(0);
}