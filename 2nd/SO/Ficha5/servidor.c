#include <sys/types.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

int main(int argc, char const *argv[])
{
    int fifo = mkfifo(".",0666);
    if (fifo < 0){
        perror("Couldn't open fifo\n");
        return 1;
    }

    int log = open("./log.txt", O_RDONLY | O_TRUNC | O_CREAT);

    if(fork() == 0) {
        fifo = open("./fifo2", O_RDONLY, 0666);
        for(ssize_t _read = 0; (_read = read(fifo, buffer, 1024)) > 0;) {
            write(log, buffer, _read);
        }
        close(fifo);
    }

    wait(NULL);

    close(log);
}


//MALLLL