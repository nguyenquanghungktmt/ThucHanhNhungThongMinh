#include <signal.h>
#include <inttypes.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <errno.h>
int main(int argc, char** argv)
{
    int sig = 10;
    pid_t pid = 6891;
    intmax_t xmax;
    char *tmp;
    errno=0;
    if(argc==3)
    {
        sig = atoi(argv[1]);
        xmax = strtoimax(argv[2], &tmp, 10);
        if(errno != 0 || tmp == argv[2] || *tmp != '\0' || xmax != (pid_t)xmax)
        {
            fprintf(stderr, "Bad PID!\n");
        } 
    else 
    {
        pid = (pid_t)xmax;
    }
    printf("\nkill %d %d\n",(int)pid,sig);
    kill(pid, sig);
    }
    return 0;
}
