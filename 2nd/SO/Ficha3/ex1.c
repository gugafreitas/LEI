#include <unistd.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{
    int res = execl("/bin/ls", "ls", "-l", NULL);

    printf("Terminou!!! %d\n", res);
    return 0;
}
