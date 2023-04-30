import ply.lex as lex

tokens = (
    'CATEGORIA',
    'DOISPONTOS',
    'TRACO',
    'INT',
    'PRODUTO',
    'FLOAT',
    'PONTOVIRG',
    'SEP'
)

t_SEP = r'::'
t_DOISPONTOS = r':'
t_PONTOVIRG = r';'
t_TRACO = r'-'

def t_CATEGORIA(t):
    r'\b[A-Z]+\b'
    return t

def t_FLOAT(t):
    r'\d+\.\d+'
    t.value = float(t.value)
    return t

def t_INT(t):
    r'\d+'
    t.value = int(t.value)
    return t

def t_PRODUTO(t):
    r'\w+(\s+\w+)*'
    return t

t_ignore = ' \t\n'

def t_error(t):
    print(f"CarÃ¡cter ilegal {t.value[0]}")
    t.lexer.skip(1)

lexer = lex.lex()

def main():
    with open("lista_compras.txt") as f:
        content = f.read()

    lexer.input(content)

    while tok := lexer.token():
        print(tok)