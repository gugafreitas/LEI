/**
 * @file stack.c
 * 
 * Module : Guião1 + Guião2
 * Description : Stack -> (Guião1 e Guião2) - Trabalho de LI2 2020/2021
 *
 * Neste ficheiro é dada a criação e iniciação da manobragem da Stack para que depois se possa então,
 * manipular os dados através das operações feitas na mesma.
 *
 */

#include <stdio.h>
#include <stdlib.h>

#include "stack.h"
#include "parser.h"

int has_type(DATA elem, int mask) {
  return (elem.type & mask) != 0;
}

/**
 * \brief Esta função está responsável pela criação de uma stack vazia
 *
 * @returns s , Stack
 */

STACK *create_stack() {
  STACK *s = (STACK *) calloc(1, sizeof(STACK));
  s->size = 100;
  s->stack = (DATA *) calloc(s->size, sizeof(DATA));
  return s;
}

/**
 * \brief Esta função adiciona elementos ao topo da stack (push).
 *
 * @param s e elem , Stack e elemento que sofre o push (que será adicionado ao topo da stack).
 */

void push(STACK *s, DATA elem) {
  if(s->size == s->n_elems) {
    s->size += 100;
    s->stack = (DATA *) realloc(s->stack, s->size * sizeof(DATA));
  }
  s->stack[s->n_elems] = elem;
  s->n_elems++;
}

/**
 * \brief Esta função remove o elemento que está no topo da stack (pop).
 *
 * @param s
 *
 * @returns s->stack[s->n_elems] , Stack sem o elemento do topo e com o pointer atualizado.
 */

DATA pop(STACK *s) {
  s->n_elems--;
  return s->stack[s->n_elems];
}

/**
 * \brief Esta função aponta o topo da stack
 *
 * @param s
 *
 * @returns s->stack[s->n_elems] , Elemento apontado pelo pointer
 */

DATA top(STACK *s) {
  return s->stack[s->n_elems - 1];
}

/**
 * \brief Esta função verifica se a stack está, ou não, vazia
 *
 * @param s
 *
 * @returns s->n_elems == 0
 */

int is_empty(STACK *s) {
  return s->n_elems == 0;
}

/**
 * \brief Esta função está encarregue de imprimir a stack
 *
 * @param s
 */

void print_stack(STACK *s) {
  for(int K = 0; K < s->n_elems; K++) {
    DATA elem = s->stack[K];
    TYPE type = elem.type;
    switch(type) {
      case LONG:
        printf("%ld", elem.LONG); break;
      case DOUBLE:
        printf("%g", elem.DOUBLE); break;
      case CHAR:
        printf("%c", elem.CHAR); break;
      case STRING:
        printf("%s", elem.STRING); break;
      case ARRAY:
        print_stack(elem.ARRAY); break;
    }
  }
  printf("\n");
}

/**
 * \brief Sequência de macros para as operações a que a stack se pode sujeitar
 */
#define STACK_OPERATION(_type, _name)         \
  void push_##_name(STACK *s, _type val) {    \
    DATA elem;                                \
    elem.type = _name;                        \
    elem._name = val;                         \
    push(s, elem);                            \
  }                                           \
  _type pop_##_name(STACK *s) {               \
    DATA elem = pop(s);                       \
    assert(elem.type == _name);               \
    return elem._name;                        \
  }

STACK_OPERATION(STACK *, ARRAY)
STACK_OPERATION(long, LONG)
STACK_OPERATION(double, DOUBLE)
STACK_OPERATION(char, CHAR)
STACK_OPERATION(char *, STRING)
