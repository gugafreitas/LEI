

def minimax(estado, profundidade, jogador):
    if jogador == BOT:
        best = [-1, -1, infinito]
    else:
        best=[-1, -1, infinito]

    if profundidade == 0 or (verifica_vitoria(estado, HUMANO) or verifica_vitoria(estado, BOT)):
        score = avaliar(estado)
        return [-1, -1, score]

    for cell in celulas_vazias(estado):
        x, y = cell[0], cell[1]
        estado[x][y] = jogador
        score=minimax(estado, profundidade-1, jogador)
        estado[x][y] = 0
        score[0], score[1] = x, y

        if jogador == BOT:
            if score[2] > best[2]:
                best = score

        else:
            if score[2] < best[2]:
                best = score

    return best

