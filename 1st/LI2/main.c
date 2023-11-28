/**
* @file  Ficheiro que contém a função principal do programa
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <math.h>
#include "parser.h"

/**
 * \brief Esta é a função principal do programa
 * 
 * @returns O valor '0'
 */


int main()
{
    char line[1000];

    assert(fgets(line, 1000, stdin) != NULL);
    assert(line[strlen(line) - 1] == '\n');

    STACK *p = create_stack();
    eval(line,p);
    print_stack (p);
    free (p);

return 0;
}
