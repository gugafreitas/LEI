#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{
    pid_t pid;
    if ((pid = fork())==0){
        //processo filho
        printf("Filho\n");
        _exit(0);
    } else{
        //processo pai
        printf("Filho: %d\n", pid);
        printf("Pai: %d\n", getpid());
    }
    return 0;
}


