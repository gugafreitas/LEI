#include <sys/types.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

int main (void){
    int fifo = mkfifo(".",0666);
    if (fifo < 0){
        perror("Couldn't open fifo\n");
        return 1;
    }
    
    char buffer[1024];
    ssize_t _read = 0;
    while((_read = read(fifo,buffer,1024))>0){
        write(1,buffer,_read);
    }

    close(fifo);
}