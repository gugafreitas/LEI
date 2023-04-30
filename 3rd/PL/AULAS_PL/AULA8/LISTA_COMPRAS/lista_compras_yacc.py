import ply.yacc as yacc

from lista_compras_lex import tokens

def p_lista(p):
    'lista : categorias'
    p[0] = p[1]

def p_categorias(p):
    '''categorias : categorias categoria
                  | categoria'''
    if len(p) == 2:
        p[0] = p[1]
    else:
        p[0] = p[1]
        p[0].update(p[2])

def p_categoria(p):
    'categoria : CATEGORIA DOISPONTOS produtos'
    p[0] = { p[1] : p[3] }

def p_produtos(p):
    '''produtos : produtos produto
                | produto'''
    if len(p) == 2:
        p[0] = [p[1]]
    else:
        p[0] = p[1] + [p[2]]

def p_produto(p):
    'produto : TRACO INT SEP PRODUTO SEP FLOAT SEP INT PONTOVIRG'
    p[0] = (p[4], p[6] * p[8])

def p_error(p):
    print("Erro sintÃ¡tico!")

parser = yacc.yacc()

with open("lista_compras.txt") as f:
    content = f.read()

print(parser.parse(content))