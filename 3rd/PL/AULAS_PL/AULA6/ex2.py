import ply.lex as lex

tokens = (
    'APR',
    'FPR',
    'INT',
    'REAL',
    'PALAVRAS',
    'BOOLS'
)

t_APR = r'\['
t_FPR = r'\]'
t_VIRGULA = r','
t_INT = r'-?\d+'
t_REAL = r'-?\d+\.\d+'
t_PALAVRAS = r'\w+'
t_BOOLS = r'[F/f]alse|[T/t]rue'

# tratar a situação em que false e true é lido como palavra

t_ignore = ' \t\n'

def t_error(t):
    print(f"Carácter ilegal {t.value[0]}")
    t.lexer.skip(1)

lexer = lex.lex()

