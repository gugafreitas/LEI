#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{
    pid_t pid = fork();
    if(pid==0){
        //filho
        printf("Sou o filho\nO PID do meu pai é: %d\n", getppid());
    }else{
        //pai
        printf("Sou o pai\nO meu PID é: %d\nO PID do meu pai é: %d\nO PID do meu filho é: %d\n",getpid(),getppid(),pid);
    }
    return 0;
}
