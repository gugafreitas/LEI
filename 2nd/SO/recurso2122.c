#include <sys/types.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>


int comando(const char* filtro, const char* entrada, const char* saida){
    int saida = open(saida,O_CREAT|O_TRUNC|O_WRONLY);
    if (saida<0){
        write(1,"Erro1\n");
    }

    int entrada = open(entrada,O_CREAT|O_TRUNC|O_WRONLY);
    if (entrada<0){
        write(0,"Erro0\n");
    }

    int err_pipe[2];
    pipe(err_pipe);

    if (fork()==0){
        dup2(entrada,0);
        dup2(saida,1);

        dup2(err_pipe[1],2);
        close(err_pipe[1]);
        close(err_pipe[2]);

        close(saida);
        close(entrada);

        execlp(filtro,filtro,NULL); //executa o programa filtro sem argumentos
    }

    close(err_pipe[1]);

    wait(NULL);

    char buffer[1];
    int result = read(err_pipe[0],buffer,1);

    close(err_pipe[0]);

    close(entrada);
    close(saida);

    return result > 0;
}

int main(int argc, char const *argv[])
{
    
    return 0;
}
