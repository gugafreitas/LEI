#include <sys/types.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

int main(void){
    int fifo = open("./fifo", O_WRONLY);
    if (fifo < 0){
        perror("Couldn't open fifo\n");
    }
}