#include <sys/types.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

int main(int argc, char const *argv[])
{
    int saida = open("saida.txt",O_CREAT | O_TRUNC | O_WRONLY);
    if(saida<0){
        write(2,"Erro na saida\n",21);
    }

    int erros = open("erros.txt",O_CREAT | O_TRUNC | O_WRONLY);
    if(erros<0){
        write(2,"Erro no open erros\n",21);
    }

    int passwd = open("/etc/passwd",O_RDONLY);
    if(passwd<0){
        write(2,"Erro no open passwd\n",21);
    }

    int backup = dup(1);

    dup2(passwd,0);
    dup2(saida,1);
    dup2(erros,2);

    close(passwd);
    close(saida);
    close(erros);

    execlp("wc","wc",NULL);

    return 0;
}
