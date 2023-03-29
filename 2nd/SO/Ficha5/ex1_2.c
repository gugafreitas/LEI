#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdio.h>

int main (void){
    int fifo = mkfifo(".",0666);
    if (fifo < 0){
        perror("Couldn't open fifo\n");
        return 1;
    }
    
    char buffer[1024] = { 0 };
    ssize_t _read = 0;
    while((_read = read(0,buffer,1024))>0){
        write(fifo,buffer,_read);
    }

    close(fifo);
}

