#include <unistd.h>
#include <stdio.h>


void pai_to_filho(){
    int p[2];

    pipe(p);

    int res = fork();
    if(res==0){
        //filho
        close(p[1]);

        int recebido;

        read(p[0],&recebido,sizeof(int));
        printf("Sou o filho e recebi %d\n", recebido);

        close(p[0]);
        _exit(0);
    }
    else{
        //pai
        close(p[0]);

        int i=23;
        printf("Sou o pai e vou escrever\n");
        sleep(10);
        write(p[1],&i,sizeof(int));
        printf("Já escrevi\n");
        close(p[1]);
    }
}

int main(int argc, char const *argv[]){
    pai_to_filho();

    return 0;
}
