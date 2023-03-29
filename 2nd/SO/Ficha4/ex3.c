#include <unistd.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{
    int p[2];

    pipe(p);

    int res = fork();
    if(res==0){
        //filho
        close(p[0]);

        int i = 888;

        printf("Sou o filho e vou escrever\n");
        sleep(10);
        write(p[1],&i,sizeof(int));
        printf("Já escrevi\n");


        close(p[1]);
        _exit(0);
    }
    else{
        //pai
        close(p[1]);

        int recebido;

        read(p[0],&recebido,sizeof(int));
        printf("Sou o pai e recebi %d\n",recebido);

        close(p[0]);
        wait(NULL);
    }
    return 0;
}
