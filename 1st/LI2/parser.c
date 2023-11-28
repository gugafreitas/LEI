/**
 * @file parser.c
 *
 * Module : Guião1 + Guião2
 * Description : Parser -> (Guião1 e Guião2) - Trabalho de LI2 2020/2021
 *
 * Este ficheiro tem como objetivo criar programas capazes de ler inputs e devolver outputs
 * adequados às operações implementadas no próprio programa e assim relacioná-las com a stack criada noutro ficheiro.
 *
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <math.h>
#include "stack.h"
#include "parser.h"

/**
 * \brief Função que define a operação da soma para gerar um output adequado ao input e à operação correspondente. No caso de uma string ou array, concatena.
 */
void mais (STACK *s) 
{
    DATA x = pop(s);
    DATA y = pop(s); 
    if (has_type(x,LONG) && has_type(y,LONG)) {push_LONG (s,x.LONG+y.LONG);}
    else if (has_type(x,DOUBLE) && has_type(y,LONG)) {push_DOUBLE (s,x.DOUBLE+y.LONG);}
    else if (has_type(x,LONG) && has_type(y,DOUBLE)) {push_DOUBLE (s,x.LONG+y.DOUBLE);}
    else if (has_type(x,DOUBLE) && has_type (y,DOUBLE)) {push_DOUBLE(s,x.DOUBLE+y.DOUBLE);}
    else {push(s,y);push(s,x);}
}

/**
 * \brief Função que define a operação da subtração para gerar um output adequado ao input e à operação correspondente.
 */
void menos (STACK *s)
{
    DATA x = pop(s);
    DATA y = pop(s);
    if (has_type(x,LONG) && has_type(y,LONG)) {push_LONG (s,y.LONG-x.LONG);}
    else if (has_type(x,DOUBLE) && has_type(y,LONG)) {push_DOUBLE (s,y.LONG-x.DOUBLE);}
    else if (has_type(x,LONG) && has_type(y,DOUBLE)) {push_DOUBLE (s,y.DOUBLE-x.LONG);}
    else {push_DOUBLE (s,y.DOUBLE-x.DOUBLE);}
}

/**
 * \brief Função que define a operação da divisão para gerar um output adequado ao input e à operação correspondente.
 */
void divide (STACK *s)
{
    DATA x = pop(s);
    DATA y = pop(s);
    if (has_type(x,LONG) && has_type(y,LONG)) {push_LONG (s,y.LONG/x.LONG);}
    else if (has_type(x,DOUBLE) && has_type(y,LONG)) {push_DOUBLE (s,y.LONG/x.DOUBLE);}
    else if (has_type(x,LONG) && has_type(y,DOUBLE)) {push_DOUBLE (s,y.DOUBLE/x.LONG);}
    else {push_DOUBLE (s,y.DOUBLE/x.DOUBLE);}
}

/**
 * \brief Função que define a operação da multiplicação para gerar um output adequado ao input e à operação correspondente. Se for um array ou uma string repete x vezes a string/array.
 */
void multiplica (STACK *s)
{
    DATA x = pop(s);
    DATA y = pop(s);
    if (has_type(x,LONG) && has_type(y,LONG)) {push_LONG (s,x.LONG*y.LONG);}
    else if (has_type(x,DOUBLE) && has_type(y,LONG)) {push_DOUBLE (s,x.DOUBLE*y.LONG);}
    else if (has_type(x,LONG) && has_type(y,DOUBLE)) {push_DOUBLE (s,x.LONG*y.DOUBLE);}
    else if (has_type(x,LONG) && has_type(y,STRING)) {for (;x.LONG!=0;x.LONG--) push_STRING(s,y.STRING);}
    else if (has_type(x,LONG) && has_type(y,ARRAY)) {for (;x.LONG!=0;x.LONG--) push_ARRAY(s,y.ARRAY);}
    else {push_DOUBLE (s,x.DOUBLE*y.DOUBLE);}
}

/**
 * \brief Função que define a operação lógica de E para gerar um output adequado ao input e à operação correspondente.
 */
void elogico (STACK *s)
{
    long y = pop_LONG(s);  
    long x = pop_LONG(s);  
    push_LONG (s, x & y);
}

/**
 * \brief Função que define a operação lógica de OU para gerar um output adequado ao input e à operação correspondente.
 */
void oulogico (STACK *s)
{
    long y = pop_LONG(s);  
    long x = pop_LONG(s);  
    push_LONG (s, x | y);  
}

/**
 * \brief Função que define a operação lógica de XOR para gerar um output adequado ao input e à operação correspondente.
 */
void xorlogico (STACK*s)
{
    long y = pop_LONG(s);  
    long x = pop_LONG(s);  
    push_LONG (s, x ^ y);
}

/**
 * \brief Função que define a operação lógica de negação para gerar um output adequado ao input e à operação correspondente. Se for um array, imprime o mesmo. Também coloca na stack todos os elementos do array.
 */
void negacaologica (STACK *s)
{
    DATA x = pop(s);
    if (has_type(x,LONG)) {push_LONG (s, ~ x.LONG);}
    else {push_STRING(s,x.STRING);}
}

/**
 * \brief Função auxiliar usada para o operador ')' no case de strings
 */
char *init (char *s) {
    int x =strlen(s)-1;
    s[x] = '\0';
    return s;
}

/**
 * \brief Função que define a operação de incrementação para gerar um output adequado ao input e à operação correspondente. Se for uma string retira o último elemento e coloca-o no fim como um char.
 */
void incrementa (STACK *s)
{
    DATA x = pop(s);
    if (has_type(x,LONG)) {push_LONG(s, x.LONG+1);}
    else if (has_type(x,DOUBLE)) {push_DOUBLE(s, x.DOUBLE+1);}
    else if (has_type(x,STRING)) {char aux =x.STRING[strlen(x.STRING)-1];push_STRING(s,init(x.STRING));push_CHAR(s,aux);}
    else {push_CHAR(s, x.CHAR+1);}
}


/**
 * \brief Função que define a operação de decrementação para gerar um output adequado ao input e à operação correspondente. Se for uma string retira o primeiro elemento e coloca-o no fim como um char.
 */
void decrementa (STACK *s)
{
    DATA x = pop(s);
    if (has_type(x,LONG)) {push_LONG(s, x.LONG-1);}
    else if (has_type(x,DOUBLE)) {push_DOUBLE(s, x.DOUBLE-1);}
    else if (has_type(x,STRING)) {push_STRING(s,++x.STRING);push_CHAR(s,x.STRING[0]);}
    else {push_CHAR(s,x.CHAR-1);}
}

/**
 * \brief Função que define a operação de módulo para gerar um output adequado ao input e à operação correspondente.
 */
void modulo (STACK *s)
{
     DATA x = pop(s);
     DATA y = pop(s);
     push_LONG (s,y.LONG%x.LONG);
}

/**
 * \brief Função que define a operação de elevação da base para gerar um output adequado ao input e à operação correspondente.
 */
void elevado (STACK *s)
{
   DATA x = pop(s);
   DATA y = pop(s);
   if (has_type(x,LONG) && has_type(y,LONG)) {push_LONG (s,pow(y.LONG,x.LONG));}
   else if (has_type(x,DOUBLE) && has_type(y,LONG)) {push_DOUBLE (s,pow(y.LONG,x.DOUBLE));}
   else if (has_type(x,CHAR) && has_type(y,LONG)) {push_DOUBLE (s,pow(y.LONG,x.CHAR));}
   else if (has_type(x,LONG) && has_type(y,CHAR)) {push_DOUBLE (s,pow(y.CHAR,x.LONG));}
   else if (has_type(x,CHAR) && has_type(y,CHAR)) {push_DOUBLE (s,pow(y.CHAR,x.CHAR));}
   else if (has_type(x,LONG) && has_type(y,DOUBLE)) {push_DOUBLE (s,pow(y.DOUBLE,x.LONG));}
   else {push_DOUBLE (s,pow(y.DOUBLE,x.DOUBLE));}
}

/**
 * \brief Função que define a operação que roda a stack para gerar um output adequado ao input e à operação correspondente.
 */
void rodaStack (STACK *s) {
     DATA x = pop(s);
     DATA y = pop(s);
     DATA z = pop(s);
     push(s,y);push(s,x);push(s,z);
}

/**
 * \brief Função que define a operação que altera o topo da stack para gerar um output adequado ao input e à operação correspondente.
 */
void topoStack (STACK *s) {
     pop(s);
}

/**
 * \brief Função que define a operação que duplica um elemento para gerar um output adequado ao input e à operação correspondente.
 */
void duplica (STACK *s) {
     DATA x = pop(s);
     push(s,x);push(s,x);
}

/**
 * \brief Função que define a operação de troca de elementos para gerar um output adequado ao input e à operação correspondente.
 */
void troca (STACK *s) {
     DATA x = pop(s);
     DATA y = pop(s);
     push(s,x);push(s,y);
}

/**
 * \brief Função que define a operação que copia elementos para gerar um output adequado ao input e à operação correspondente.
 */
void copiaelemento (STACK *s) {
     int i;
     long X15 = pop_LONG(s); 
     for (i=0;i<=s->size;i++)
     {
          if (i==X15) push(s,s->stack[s->n_elems - X15 - 1]);
     }
}

/**
 * \brief Função que converte vários tipos de inputs para long.
 *
 * @returns x.type = LONG
 */
void paraLong (STACK *s) {
    DATA x = pop(s);
    char *nada;
    switch (x.type)
        {
        case LONG:
        push_LONG (s,x.LONG);
            break; 
        case CHAR: 
        push_LONG(s,(long)x.CHAR);
            break;
        case DOUBLE: 
        push_LONG(s,(long)x.DOUBLE);
            break;
        case STRING:
        push_LONG(s,strtol(x.STRING,&nada,10));
            break;
        default:
            break;
    }
    x.type = LONG;
}

/**
 * \brief Função que converte vários tipos de inputs para double.
 *
 * @returns x.type = DOUBLE
 */
void paraDouble (STACK *s) {
    DATA x = pop(s);
    char *nada;
    switch (x.type)
        {
        case DOUBLE:
        push_DOUBLE (s,x.DOUBLE);
            break; 
        case CHAR:
        push_DOUBLE(s,(double)((long)x.CHAR));
            break;
        case LONG: 
        push_DOUBLE(s,(double)x.LONG);
            break;
        case STRING:
        push_DOUBLE(s,strtod(x.STRING,&nada));
            break;
        default:
            break;
        }
        x.type = DOUBLE;
}

/**
 * \brief Função que converte vários tipos de inputs para char
 *
 * @returns x.type = CHAR
 */
void paraChar (STACK *s) {
     DATA X16 = pop(s);
     switch (X16.type)
          {
          case CHAR:
          push_CHAR(s,X16.CHAR);
               break; 
          case DOUBLE:
          push_CHAR(s,(char)X16.DOUBLE);
               break;
          case LONG:
          push_CHAR(s,(char)X16.LONG);
               break;
          default:
               break;
          }
          X16.type = CHAR;
}

/**
 * \brief Função que lê um input de uma string e devolve a mesma
 */
void leLinha (STACK *s) {
     char buffer [10240];
     assert(fgets(buffer,sizeof(buffer),stdin));
     push_STRING(s,strdup(buffer));
}

/**
 * \brief Função que converte as vars em forma de char para int
 * 
 * @param char a 
 * @return int 
 */
int charparaint (char a) {
    char alfabeto [26] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    int r=0;
    for (int i=0;i<=26;i++) {
        if (a==alfabeto[i]) return r;
        r++;
    }
return r;
}

/**
 * \brief Função que funciona quase como o OU lógico, porém de uma forma mais peculiar (com shortcut)  
 */
void etraco (STACK *s) {
    DATA x=pop(s);
    DATA y=pop(s);
    if (y.LONG!=0) push(s,y);
    else push (s,x);
}

/**
 * \brief Função que funciona quase como o E lógico, porém de uma forma mais peculiar (com shortcut)  
 */
void ecomercial (STACK *s) {
    DATA x=pop(s);
    DATA y=pop(s);
    if (x.LONG==0 || y.LONG==0) push_LONG(s,0);
    else if (x.LONG>y.LONG) push(s,x);
    else push(s,y);
}
/**
 * \brief Função que dá push do menor dos valores inscritos na operação  
 */
void emenor (STACK *s) {
    DATA x=pop(s);
    DATA y=pop(s);
    if (has_type(x,LONG)&&has_type(y,LONG)) {if (x.LONG<y.LONG) push_LONG(s,x.LONG); else push_LONG(s,y.LONG);}
    else if (has_type(x,LONG)&&has_type(y,DOUBLE)) {if (x.LONG<y.DOUBLE) push_LONG(s,x.LONG); else push_DOUBLE(s,y.DOUBLE);}
    else if (has_type(x,DOUBLE)&&has_type(y,LONG)) {if (x.DOUBLE<y.LONG) push_DOUBLE(s,x.DOUBLE); else push_LONG(s,y.LONG);}
    else if (has_type(x,STRING)&&has_type(y,STRING)) {char *s1 = x.STRING; char *s2 = y.STRING;if(strlen(s1)<strlen(s2)) push_STRING(s,y.STRING);else push_STRING(s,x.STRING);}
    else {if (x.DOUBLE<y.DOUBLE) push_DOUBLE(s,x.DOUBLE); else push_DOUBLE(s,y.DOUBLE);}
}

/**
 * \brief Função que dá push do maior dos valores inscritos na operação
 */
void emaior (STACK *s) {
    DATA x=pop(s);
    DATA y=pop(s);
    if (has_type(x,LONG)&&has_type(y,LONG)) {if (x.LONG>y.LONG) push_LONG(s,x.LONG); else push_LONG(s,y.LONG);}
    else if (has_type(x,LONG)&&has_type(y,DOUBLE)) {if (x.LONG>y.DOUBLE) push_LONG(s,x.LONG); else push_DOUBLE(s,y.DOUBLE);}
    else if (has_type(x,DOUBLE)&&has_type(y,LONG)) {if (x.DOUBLE>y.LONG) push_DOUBLE(s,x.DOUBLE); else push_LONG(s,y.LONG);}
    else if (has_type(x,STRING)&&has_type(y,STRING)) {char *s1 = x.STRING; char *s2 = y.STRING;if(strlen(s1)>strlen(s2)) push_STRING(s,x.STRING);else push_STRING(s,y.STRING);}
    else {if (x.DOUBLE>y.DOUBLE) push_DOUBLE(s,x.DOUBLE); else push_DOUBLE(s,y.DOUBLE);}
}

/**
 * \brief Função que verifica vericidade de uma possivel igualdade e que identifica a posição de um valor por índice
 */
void igual (STACK *s) {
    DATA x=pop(s);
    DATA y=pop(s);
    if (has_type(x,LONG)&&has_type(y,LONG)) { if(x.LONG==y.LONG) push_LONG (s,1);else push_LONG (s,0);}
    else if (has_type(x,DOUBLE)&&has_type(y,DOUBLE)) { if(x.DOUBLE==y.DOUBLE) push_LONG (s,1);else push_LONG (s,0);}
    else if (has_type(x,LONG)&&has_type(y,DOUBLE)) { if(x.LONG==y.DOUBLE) push_LONG (s,1);else push_LONG (s,0);}        
    else if (has_type(x,DOUBLE)&&has_type(y,LONG)) { if(x.DOUBLE==y.LONG) push_LONG (s,1);else push_LONG (s,0);}
    else if (has_type(x,LONG)&&has_type(y,ARRAY)){push_CHAR(s,y.STRING[x.LONG]);}
    else if (has_type(x,STRING)&&has_type(y,STRING)) {char *s1 = x.STRING; char *s2 = y.STRING; int x=0; if(strcmp(s1,s2)==0){x=1;} push_LONG(s,x);}
    else if (has_type(x,LONG)&&has_type(y,ARRAY)) {push_CHAR(s,y.STRING[x.LONG]);}
}

/**
 * \brief Função que verifica a veracidade da relação menor entre elementos, caso verdadeiro retorna 1, se não 0. Também vai buscar X elementos ou caracteres do inicio.
 */
void menor (STACK *s) {
    DATA x=pop(s);
    DATA y=pop(s);
    switch (y.type)
    {
    case LONG : switch (x.type)
                {
                    case LONG : {if(x.LONG<y.LONG) push_LONG (s,0);else push_LONG (s,1);};break;
                    case DOUBLE : {if(x.LONG<y.DOUBLE) push_LONG (s,0);else push_LONG (s,1);};break;
                    case CHAR : {double d1=x.LONG; double d2=y.CHAR; if (d1<d2) push_LONG (s,0);else push_LONG (s,1);}break;  
                    default: break;      
                }break;
    case DOUBLE : switch (x.type)
                {
                    case LONG : {if(x.DOUBLE<y.LONG) push_LONG (s,0);else push_LONG (s,1);}break;
                    case DOUBLE : {if(x.DOUBLE<y.DOUBLE) push_LONG (s,0);else push_LONG (s,1);}; break;
                    case CHAR : {double d1=x.DOUBLE; double d2=y.CHAR; if (d1<d2) push_LONG (s,0);else push_LONG (s,1);}break;   
                    default: break;
                }break;
    case CHAR : switch (x.type)
                {
                    case LONG : {if(x.CHAR<y.LONG) push_LONG (s,0);else push_LONG (s,1);}break;
                    case DOUBLE : {if(x.CHAR<y.DOUBLE) push_LONG (s,0);else push_LONG (s,1);}; break;
                    case CHAR : {double d1=x.CHAR; double d2=y.CHAR; if (d1<d2) push_LONG (s,0);else push_LONG (s,1);}break;
                    default: break;   
                }break;
    case STRING : switch (x.type)
                {
                    case LONG : {char *aux = malloc(sizeof(char));
                    for (int i=0;i < x.LONG; i++) 
                    {
                        aux[i] = y.STRING[i]; 
                    }
                    push_STRING (s,aux);
                    }break;
                    case STRING : {char *s1 = x.STRING; char *s2 = y.STRING; int x=1; if(strlen(s1)<=strlen(s2))x=0; push_LONG(s,x);}
                    default: break;
                }break;
    case ARRAY : switch (x.type)
                {
                    case LONG : {char *aux = malloc(sizeof(char));
                    for (int i=0;i < x.LONG; i++) 
                    {
                        aux[i] = y.STRING[i]; 
                    }
                    push_STRING (s,aux);
                    }break;
                    case ARRAY : {char *s1 = x.STRING; char *s2 = y.STRING; int x=1; if(strlen(s1)>strlen(s2))x=0; push_LONG(s,x);}
                    default: break;
                }break;
    default:
        break;
    }
}

/**
 * \brief Função que verifica a veracidade da relação maior entre elementos, caso verdadeiro retorna 1, se não 0.Também vai buscar X elementos ou caracteres do fim.
 */
void maior (STACK *s) {
    DATA x=pop(s);
    DATA y=pop(s);
    switch (y.type)
    {
    case LONG : switch (x.type)
                {
                    case LONG : {if(x.LONG>y.LONG) push_LONG (s,0);else push_LONG (s,1);};break;
                    case DOUBLE : {if(x.LONG>y.DOUBLE) push_LONG (s,0);else push_LONG (s,1);};break;
                    case CHAR : {double d1=x.LONG; double d2=y.CHAR; if (d1>d2) push_LONG (s,0);else push_LONG (s,1);}break;  
                    default: break;      
                }break;
    case DOUBLE : switch (x.type)
                {
                    case LONG : {if(x.DOUBLE>y.LONG) push_LONG (s,0);else push_LONG (s,1);}break;
                    case DOUBLE : {if(x.DOUBLE>y.DOUBLE) push_LONG (s,0);else push_LONG (s,1);}; break;
                    case CHAR : {double d1=x.DOUBLE; double d2=y.CHAR; if (d1>d2) push_LONG (s,0);else push_LONG (s,1);}break;   
                    default: break;
                }break;
    case CHAR : switch (x.type)
                {
                    case LONG : {if(x.CHAR>y.LONG) push_LONG (s,0);else push_LONG (s,1);}break;
                    case DOUBLE : {if(x.CHAR>y.DOUBLE) push_LONG (s,0);else push_LONG (s,1);}; break;
                    case CHAR : {double d1=x.CHAR; double d2=y.CHAR; if (d1>d2) push_LONG (s,0);else push_LONG (s,1);}break;
                    default: break;   
                }break;
    case STRING : switch (x.type)
                {
                    case LONG : {char *aux = malloc(sizeof(char));
                    for (int i=0;i < x.LONG; i++) 
                    {
                        aux[i] = y.STRING[i]; 
                    }
                    push_STRING (s,aux);
                    }break;
                    case STRING : {char *s1 = x.STRING; char *s2 = y.STRING; int x=1; if(strlen(s1)<strlen(s2))x=0; push_LONG(s,x);}
                    default: break;
                }break;
    case ARRAY : switch (x.type)
                {
                    case LONG : {
                    char *aux = (char *)malloc(x.LONG*sizeof(char));
                    int len = strlen(y.STRING);
                    for (int i=len-x.LONG,j=0;i < len+x.LONG; i++,j++) {
                    aux[j] = y.STRING[i]; 
                    }
                    push_STRING (s,aux);
                    } break;
                    case ARRAY : {char *s1 = x.STRING; char *s2 = y.STRING; int x=1; if(strlen(s1)<=strlen(s2))x=0; push_LONG(s,x);}
                    default: break;
                }break;
    default:
        break;
    }
}

/**
 * \brief Função que efetua a negação de elementos de acordo com o type dos mesmos, se este for um array dá push do mesmo 
 */
void nao (STACK *s) {
    DATA x=pop(s);
    if (has_type (x,CHAR)) { if ((x.CHAR) == 0)  push_LONG (s,1); else push_LONG (s,0);}
    else if (has_type (x,DOUBLE)) { if ((x.DOUBLE) == 0)  push_LONG (s,1); else push_LONG (s,0);}
    else { if ((x.LONG) == 0)  push_LONG (s,1); else push_LONG (s,0);}
}

/**
 * \brief Função que faz a operação If (topo da stack) Then Else
 */
void interrogacao (STACK *s) {
    DATA x=pop(s);
    DATA y=pop(s);
    DATA z=pop(s);
    if (z.LONG==0) push(s,x);
    else push(s,y);
}

/**
 * \brief Função que imprime os elementos após operações efetuadas e de acordo com o type do mesmo
 */
void imprime (STACK *s) {
    DATA x = top(s);
    if (has_type(x,LONG)) push_LONG(s,x.LONG);
    else if (has_type(x,CHAR)) push_CHAR(s,x.CHAR);
    else if (has_type(x,DOUBLE)) push_DOUBLE(s,x.DOUBLE);
    else (push_LONG(s,x.LONG));
}

/**
 * \brief cria ou identifica tipo long
 * 
 * @param long x 
 * @return y 
 */
DATA makeLong (long x) {
    DATA y;
    y.LONG = x;
    y.type = LONG;

    return y;
}

/**
 * \brief cria ou identifica tipo char
 * 
 * @param char x 
 * @return y 
 */
DATA makeChar (char x) {
    DATA y;
    y.CHAR = x;
    y.type = CHAR;

    return y;
}

/**
 * \brief cria ou identifica tipo double
 * 
 * @param double x 
 * @return y 
 */
DATA makeDouble (double x) {
    DATA y;
    y.DOUBLE = x;
    y.type = DOUBLE;

    return y;
}

/**
 * \brief identifica o token pretendido
 * 
 * @return token
 */
char *get_token(char *line, char **rest) {
    char *token = malloc (10240);
    int i;

    i=0;
    while (line[i] != ' ' && line[i] != '\0' && line[i] != '\n') {
        token[i] = line[i];
        i++;
    }
    token[i] = '\0';

    *rest = NULL;

    if (line[i] != '\0') {
        *rest = line + i + 1;
    }
    return token;
}

/**
 * \brief Recebe uma linha e um inteiro que começa com '[', e retorna o array correspondete ao que está dentro do mesmo, atualizando também o rest
 * 
 */
char *get_delimited_array(char *line, char **rest){
    int i=0, j=2;

    char *array = calloc(10000,sizeof(char));

    while (line[j] != ']'){ 
        array[i] = line[j];
        i++,j++;}

    array[i-1] = '\0';

    *rest = NULL;

    if (line[j]!='\0') *rest = line + j + 2;
    
 int aux,aux1;
    for (aux=0;array[aux]!='\0';aux++)
    if (array[aux]==' ') {
        for (aux1=aux+1;array[aux1]!='\0';aux1++){
        array[aux1-1]=array[aux1];
    }
    array[aux1-1] = '\0';
    }

    return array;
}

/**
 * \brief Recebe uma linha e um inteiro que começa com '\', e retorna o string correspondete ao que está dentro do mesmo, atualizando também o rest
 * 
 */
char *get_delimited_string(char *line, char **rest){
    int i = 0, j=1;

    char *string = calloc(10000,sizeof(char));

    while (line[j]!='\"'){
        string[i] = line[j];
        i++,j++;}

    string[i] = '\0';

    *rest = NULL;

    if (line[j]!='\0') *rest = line + j + 2;
    
    int aux,aux1;
    for (aux=0;string[aux]!='\0';aux++)
    if (string[aux]==' ') {
        for (aux1=aux+1;string[aux1]!='\0';aux1++){
        string[aux1-1]=string[aux1];
    }
    string[aux1] = '\0';
    }
    return string;
}


/**
 * \brief função que identifica tamanho ou range de strings ou arrays
 * 
 * @param s
 */
void virgula (STACK *s) {
    DATA x = pop(s);
    if (has_type(x,LONG)) {for (int i=0;i<x.LONG;i++) {push_LONG(s,i);}}
    if (has_type(x,ARRAY)) {
        int i, count = 0;
        for (i=0;x.STRING[i] != '\0';i++) {count++;} push_LONG (s,count);}
}

/**
 * \brief função que adiciona e identifica presença de caracteres numa ou para strings
 * 
 * @return string
 */
char * addCharToStr (char *str, char c)  {
    size_t len = strlen(str);
    char *str2 = malloc(len + 1 + 1 );
    strcpy(str2, str);
    str2[len] = c;
    str2[len + 1] = '\0';

    return str2;
}

/**
 * \brief função que identifica espaços e divide uma string por caracteres
 *
 * @return array de chars
 */
void splitStrByChar (STACK *s, char c) {
    STACK *p = create_stack();
    DATA x = pop(s);
    if (has_type(x,STRING)) {
        char *inputString = x.STRING;
        char *resultingStr = "";
        char inputChar = ' ';
        int len = strlen(inputString);
        for(int i = 0; i < len; i++) {
            inputChar = inputString[i];
            if(inputChar != c) {
                resultingStr = addCharToStr(resultingStr, inputChar);
                if(i+1 == len) {
                    push_STRING(p, resultingStr);
                }        
            }
            else if (strlen(resultingStr) > 0) {
                push_STRING(p, resultingStr);
                resultingStr = "";
            } 
        }        
    }
    push_ARRAY(s,p);
}
  
/**
 * Esta função vai identificar todos os nossos tokens e realizar as operações correspondentes aos mesmos.
 * Esta dá ainda print da stack final, stack esta que foi atualizada conforme as operações feitas nela.
 */
STACK *eval(char *line, STACK *s){
    char **rest = malloc (sizeof (char*));
    DATA *letras[26];
    for (int i=0;i<=26;i++) letras[i]=(DATA *)malloc(sizeof(DATA));
    for (int i=0;i<6;i++) *letras[i] = makeLong(10+i);
    *letras['S'-'A'] = makeChar(' ');
    *letras['N'-'A'] = makeChar('\n');
    for (int i=23;i<27;i++) {*letras[i] = makeLong(i-23);}
    for (char *token = get_token(line,rest);*rest!=NULL;token = get_token(line,rest)) {
        char *sobra;
        char *sobrad;
        long vi = strtol(token, &sobra, 10);
        double vd = strtod(token, &sobrad);
        if(strlen(sobra) == 0) push_LONG(s, vi);
        else if(strlen(sobrad) == 0) push_DOUBLE(s,vd);
        else
          {
            switch (token[0])
        {
        case '[': {char *ar=get_delimited_array(line,rest);push_STRING(s,ar);};break;
        case '\"': {char *str=get_delimited_string(line,rest);push_STRING(s,str);};break;
        case '+': mais(s); break;
        case '-': menos(s); break;
        case '/': divide(s); break;
        case '*': multiplica(s); break;
        case '&': elogico(s);break;
        case '|': oulogico(s);break;
        case '^': xorlogico(s);break;
        case '~': negacaologica(s);break;
        case ')': incrementa(s);break;
        case '(': decrementa(s);break;
        case '%': modulo(s);break;
        case '#': elevado(s);break; 
        case '@': rodaStack(s);break;
        case ';': topoStack(s);break;     
        case '_': duplica(s);break;
        case '\\': troca(s);break;
        case '$': copiaelemento(s);break;
        case 'i': paraLong(s);break;
        case 'f': paraDouble(s);break;
        case 'c': paraChar(s);break;
        case 'l': leLinha(s);break;
        case 'e':{switch (token[1])
        {
        case '>':emaior(s);break;
        case '<':emenor(s);break;
        case '|':etraco(s);break;
        case '&':ecomercial(s);break;  
        default:break;
        }
        }break;
        case '=': igual(s); break;
        case '<': menor(s);break;
        case '>': maior(s);break;
        case '!': nao (s);break;
        case '?': interrogacao(s);break;
        case 'A': push(s,*letras[0]);break;
        case 'B': push(s,*letras[1]);break;
        case 'C': push(s,*letras[2]);break;
        case 'D': push(s,*letras[3]);break;
        case 'E': push(s,*letras[4]);break;
        case 'F': push(s,*letras[5]);break;
        case 'G': imprime(s);break;
        case 'H': imprime(s);break;
        case 'I': imprime(s);break;
        case 'J': imprime(s);break;
        case 'K': imprime(s);break;
        case 'L': imprime(s);break;
        case 'M': imprime(s);break;
        case 'N': push(s,*letras[13]);break;
        case 'O': imprime(s);break;
        case 'P': imprime(s);break;
        case 'Q': imprime(s);break;
        case 'R': imprime(s);break;
        case 'S': push(s,*letras[18]);break;
        case 'T': imprime(s);break;
        case 'U': imprime(s);break;
        case 'V': imprime(s);break;
        case 'W': imprime(s);break;
        case 'X': push(s,*letras[23]);break;
        case 'Y': push(s,*letras[24]);break;
        case 'Z': push(s,*letras[25]);break;
        case ':': *letras[charparaint(token[1])] = top(s);break;
        case ',': virgula(s);
        }
    }
    line = *rest;
    }
    return s;
}
