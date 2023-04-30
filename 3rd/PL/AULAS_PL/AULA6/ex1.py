import ply.lex as lex
 
tokens = (
    'PALAVRA',
    'VIRGULA',
    'PONTOE',
    'PONTOI',
    'PONTOF',
    'RETS'
)

t_PALAVRA = r'\w+'
t_VIRGULA = r','
t_PONTOE = r'\!'
t_PONTOI = r'\?'
t_PONTOF = r'\.'
t_RETS = r'\.{3,}'

t_ignore = ' \t\n'

def t_error(t):
    print(f"Carácter ilegal {t.value[0]}")
    t.lexer.skip(1)

lexer = lex.lex()

data = '''3 + 4 * 10 + -20 *2'''

lexer.input(data)

while tok := lexer.token():
    print(tok)

