#include <sys/types.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h> /* O_RDONLY, O_WRONLY, O_CREAT, O_* */


// int open(const char *path, int oflag [, mode]);
// ssize_t read(int fildes, void *buf, size_t nbyte);
// ssize_t write(int fildes, const void *buf, size_t nbyte);
// off_t lseek(int fd, off_t offset, int whence);
// int close(int fildes);

int main(int argc, char const *argv[])
{
    unsigned char buffer[4096];

    if(!argv[1] || !argv[2]){
        perror("Invalid files\n");
        return -1;
    }

    int file1 = open(argv[1], O_RDONLY, 0660);
    if (file1 < 0){
        perror("Invalid file\n");
        return -1;
    }


    int file2 = open(argv[2], O_CREAT | O_TRUNC | O_WRONLY, 0660);
    if(file2 < 0){
        perror("Invalid file\n");
        return -1;
    }

    for(ssize_t i = read(file1,buffer,4096); i>0; i = read(file1, buffer, 4096)){
        write(file1,buffer,i);
    }
    
    return 0;
}
