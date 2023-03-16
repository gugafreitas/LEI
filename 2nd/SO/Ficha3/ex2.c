#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <fcntl.h>

int main(int argc, char const *argv[])
{
    int pid = fork();
    if(pid==0){
        sleep(3);
        int res = execl("/bin/ls", "ls", "-l", NULL);
        printf("Correu mal! %d\n", res);
        _exit(1);
    }
    else{
        //codigo pai
        int status;
        wait(&status); //força execução sequencial
        printf("Terminei. %d\n", WEXITSTATUS(status));
    }
    return 0;
}
