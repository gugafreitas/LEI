/**
 * @file stack.h
 *
 * Module : Guião1 + Guião2
 * Description : Stack -> (Guião1 e Guião2) - Trabalho de LI2 2020/2021
 *
 * A Stack é uma estrutura específica de manipulação de dados linear na ordem **Last In First Out**, sendo nessa ordem que são realizadas as operações e é dado um empilhamento de dados.
 * Este ficheiro tem então como objetivo, a implementação de uma Stack.
 * Agora no Guião 2, aprende a trabalhar com novos e diferentes tipos de dados.
 */

#ifndef ___STACK_H___
#define ___STACK_H___

#include <assert.h>

typedef enum {LONG = 1, DOUBLE = 2, CHAR = 4, STRING = 8, ARRAY=10} TYPE;

#define INTEGER  (LONG | CHAR)
#define NUMBER   (INTEGER | DOUBLE)

struct stack;
typedef struct stack STACK;

typedef struct data {
  TYPE type;
  long LONG;
  double DOUBLE;
  char CHAR;
  char *STRING;
  STACK *ARRAY;
} DATA;

/**
 * \brief Representação da estrutura da stack
 *
 * É aqui definido o array que vai conter os elementos da Stack,
 * a capacidade total da Stack e também o apontador da Stack, pela ordem de menção.
 *
 */

typedef struct stack {
  DATA *stack;
  int size;
  int n_elems;
} STACK;


int has_type(DATA elem, int mask); 
STACK *create_stack();
void push(STACK *s, DATA elem);
DATA pop(STACK *s);
DATA top(STACK *s);
int is_empty(STACK *s);
void print_stack(STACK *s);

#define STACK_OPERATION_PROTO(_type, _name)   \
  void push_##_name(STACK *s, _type val);     \
  _type pop_##_name(STACK *s);

STACK_OPERATION_PROTO(STACK *, ARRAY)
STACK_OPERATION_PROTO(long, LONG)
STACK_OPERATION_PROTO(double, DOUBLE)
STACK_OPERATION_PROTO(char, CHAR)
STACK_OPERATION_PROTO(char *, STRING)

#endif
