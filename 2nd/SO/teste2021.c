#include <sys/types.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

void primeiraVersao(){
    mkfifo("tubo",0666);

    int fields[9];
    for(int i=0;i<9;i++){
        char nome_ficheiro[7];
        sprintf(nome_ficheiro, "zona_%d", i);
        fields[i] = open(nome_ficheiro,O_WRONLY|O_CREAT|O_TRUNC,0666);
    }

    
}