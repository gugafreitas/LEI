#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <fcntl.h>
#include <string.h>

#define MAX_ARGS 10

int mySystem(char *command)
{
    char *token, *args[MAX_ARGS];
    int i=0;
    int status;

    for(token = strtok(command," "); token != NULL; token = strtok(NULL, " ")){
        args[i++] = token;
    }

    args[i] = NULL;

    //----------------------------------------

    int pid = fork();
    if(pid==0){
        //filho
        execvp(args[0],args);
        _exit(-1);
    } else{
        wait(&status);
        return (WEXITSTATUS(status));
    }
}


int main(int argc, char const *argv[])
{
    char comando1[] = "ls -l -a";
    char comando2[] = "sleep 10";
    char comando3[] = "ps";
    int ret;

    printf("a executar mySystem para %s\n", comando1);
    ret = mySystem(comando1);
    printf("ret: %d\n", ret);


    printf("a executar mySystem para %s\n", comando2);
    ret = mySystem(comando2);


    printf("a executar mySystem para %s\n", comando3);
    mySystem(comando3);

    return 0;
}
