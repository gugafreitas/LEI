#include <sys/types.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h> /* O_RDONLY, O_WRONLY, O_CREAT, O_* */

int main(int argc, char const *argv[])
{
    unsigned char buf[4096];
    for(ssize_t i=read(0,buf,4096);i>0;i=read(0,buf,4096)){
        write(1,buf,i);
    }
    return 0;
}
