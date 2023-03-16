#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <fcntl.h>

int main(int argc, char const *argv[])
{
    for(int i=0;i<argc;i++){
        int pid = fork();
        if (pid==0){
            //filho
            execlp(argv[i],argv[i],NULL);
            _exit(-1);
        }
    }

    for(int i=1;i<argc;i++){
        int status;
        wait(&status);
        if(WEXITSTATUS(status)){ //SE PROCESSO FILHO EXECUTOU CORRETAMENTE
            printf("%d Exited %d\n", i, WEXITSTATUS(status));
        }
        else{
            printf("Correu mal %d\n", i);
        }
    }
    return 0;
}
