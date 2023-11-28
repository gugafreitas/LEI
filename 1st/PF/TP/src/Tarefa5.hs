module Tarefa5 where
import Tarefa2
import Types
import Tarefa1

--TAREFA5

-- | Relativamente a esta tarefa, o objetivo seria realizar as movimentações dos fantasmas no labirinto, partindo de dois cenários. 
-- | Em primeiro lugar, a movimentação dos fantasmas vivos é dada pela função "chasemode" que através de várias funções auxiliares, em particular, a função "moveGhost" que aborda uma grande escala de situaçõe que o fantasma se pode encontrar e sua melhor solução para uma perseguição mais eficaz ao Pacman.
-- | Em segundo lugar, a movimentação dos fantasmas mortos suportada pela função "scattermode" constituida por várias funções nomeadamente a "deadG" que dita o modo de fuga do fantasma em circulo para melhor fuga do Pacman em modo Mega.
-- | Por fim, a Tarefa está desenvolvida para um jogabilidade com menos dificuldade para o Pacman visto que o comportamento dos fantasmas não apresenta um críterio tão exigente.

-- | Função final (pelos 2 modos do fantasma, )
ghostPlay :: State -> [Play]
ghostPlay (State m [] l) = []
ghostPlay (State m (x:xs) l) | isPacmanv2 x = ghostPlay (State m xs l)
                             | otherwise = if getGhostMode ((State m [x] l)) == Alive then chasemode (State m [x] l) (getPlayerID x) : ghostPlay (State m xs l)
                                           else scatterMode (State m [x] l) (getPlayerID x) : ghostPlay (State m xs l)

-- | Demonstra o estado de um fantasma na lista de jogadores (Alive ou Dead).
getGhostMode :: State -> GhostMode 
getGhostMode (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l) = s

-- | Verifica se um jogador é fantasma.
isGhostv2 :: Player -> Bool
isGhostv2 (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) = True

-- | Coloca um dado jogador em primeiro lugar da lista de jogadores do State.
firstG :: Player -> State -> State
firstG x (State m (a:as) l) = (State m (x:a:as) l)

-- | Organiza a lista de jogadores de forma a o Pacman ser o último ele
lastPac :: State -> State 
lastPac (State m (x:xs) l) | isGhostv2 x = (State m (xs ++ [x]) l)
                           | otherwise = firstG x (lastPac (State m xs l))

--(DEAD)
-- | Aborda os diferentes casos para a movimentação de um fantasma morto.
deadG :: State -> Coords -> Play
deadG (State m [Ghost (GhoState (id,(x,y),ve,o,p,v) Dead )] l) (a,b) | whichPiece (x+1,y) m /= Wall && o == D = (Move id D)
                                                                      | whichPiece (x+1,y) m == Wall && o == D = (Move id L)
                                                                       | whichPiece (x-1,y) m /= Wall && o == U = (Move id U)
                                                                        | whichPiece (x-1,y) m == Wall && o == U = (Move id R)
                                                                         | whichPiece (x,y+1) m /= Wall && o == R = (Move id R)
                                                                          | whichPiece (x,y+1) m == Wall && o == R = (Move id D)
                                                                           | whichPiece (x,y-1) m /= Wall && o == L = (Move id L)
                                                                            | otherwise = (Move id U)

-- | Verifica se um jogador é o Pacman.
isPacmanv2 :: Player -> Bool
isPacmanv2 (Pacman _) = True 
isPacmanv2 _ = False

-- | Obtem as coordenadas do Pacman, dada uma lista de jogadores. (auxiliar)
pac :: [Player] -> Coords
pac [] = (0,0)
pac (x:xs) | isPacmanv2 x = getPlayerCoords x
           | otherwise = pac xs

-- | Obtem as coordenadas do Pacman, dada uma lista de jogadores.
getPacCoords :: State -> Coords
getPacCoords (State m p l) = pac p

-- | Através da verificação do id do jogador, aplica as instruções da função que formúla as movimentações de fantasmas mortos.
rearmode :: State -> Int -> Play
rearmode (State m (x:xs) l) id | getPlayerID x == id = deadG (State m [x] l) (getPacCoords (State m (x:xs) l))
                               | otherwise = rearmode (State m (xs) l) id

-- | Adiciona um novo jogador a lista de jogadores.
newin :: Player -> State -> State
newin y (State m [] l) = (State m [y] l )
newin y (State m (x:xs) l) = (State m (y:x:xs) l)

-- | Reorganiza a lista de jogadores de forma ao Pacman estar na última posição.
priority :: State -> State
priority (State m [] l) = State m [] l 
priority (State m (x:xs) l) | isPacmanv2 x = (State m (xs ++ [x]) l)
                            | otherwise = newin x (priority (State m (xs) l))

-- | Função final para fantasmas mortos.
scatterMode :: State -> Int -> Play
scatterMode x1 x2 = rearmode (priority x1) x2


-- | Calcula a distancia entre as coordenadas de um fantasma e umas determinadas coordenadas do mapa (futuras coordenadas do Pacman).
distance :: Player -> Coords  -> Float 
distance (Ghost (GhoState (id,(x,y),ve,o,p,v) s)) (x1,y1) = (sqrt ((fromIntegral (x-x1)^2)+(fromIntegral (y-y1)^2)))

-- | Analisa todos os cenários de movimentação do fantasma e, tendo em conta os vários obstáculos, toma a melhor decisão para uma chegada mais rápida ao Pacman.
moveGhost :: State -> Coords -> Play
moveGhost (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) Alive)] l) (a,b) | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 == 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else if x < (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i D)
                                                                                                                                                                                               else if x == (halflabv2 m) && whichPiece (x,y+1) m /= Wall then (Move i R)
                                                                                                                                                                                               else (Move i L)
                                                                        | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 /= 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else (Move i L)
                                                                        | y <= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 == 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else if x < (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i D)
                                                                                                                                                                                               else if x == (halflabv2 m) && whichPiece (x,y+1) m /= Wall then (Move i R)
                                                                                                                                                                                               else (Move i L)
                                                                        | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 /= 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else (Move i D)
                                                                        | y < b && whichPiece (x,y+1) m /= Wall = (Move i R)
                                                                        | y < b && whichPiece (x,y+1) m == Wall = if distance (Ghost (GhoState (i,(x+1,y),ve,o,p,v) Alive)) (a,b) > distance (Ghost (GhoState (i,(x-1,y),ve,o,p,v) Alive)) (a,b) then (Move i U)
                                                                                                                    else (Move i D)
                                                                        | y < b && whichPiece (x,y+1) m == Wall && whichPiece (x+1,y) m == Wall = (Move i U)
                                                                        | y < b && whichPiece (x,y+1) m == Wall && whichPiece (x-1,y) m == Wall = (Move i D)
                                                                        | y < b && whichPiece (x,y+1) m == Wall && whichPiece (x-1,y) m == Wall && whichPiece (x+1,y) m == Wall = (Move i L)                                
                                                                        | y < b && whichPiece (x,y-1) m /= Wall = (Move i L)
                                                                        | y < b && whichPiece (x,y-1) m == Wall = if distance (Ghost (GhoState (i,(x+1,y),ve,o,p,v) Alive)) (a,b) > distance (Ghost (GhoState (i,(x-1,y),ve,o,p,v) Alive)) (a,b) then (Move i U)
                                                                                                                    else (Move i D)
                                                                        | y < b && whichPiece (x,y-1) m == Wall && whichPiece (x+1,y) m == Wall = (Move i U)
                                                                        | y < b && whichPiece (x,y-1) m == Wall && whichPiece (x-1,y) m == Wall = (Move i D)
                                                                        | y < b && whichPiece (x,y-1) m == Wall && whichPiece (x-1,y) m == Wall && whichPiece (x+1,y) m == Wall = (Move i R)
                                                                        | y == b && x < a && whichPiece (x+1,y) m /= Wall = (Move i D)
                                                                        | y == b && x < a && whichPiece (x+1,y) m == Wall = if distance (Ghost (GhoState (i,(x,y+1),ve,o,p,v) Alive)) (a,b) > distance (Ghost (GhoState (i,(x,y-1),ve,o,p,v) Alive)) (a,b) then (Move i L)
                                                                                                                            else (Move i R)
                                                                        | y == b && x < a && whichPiece (x+1,y) m == Wall && whichPiece (x,y+1) m == Wall = (Move i L)
                                                                        | y == b && x < a && whichPiece (x+1,y) m == Wall && whichPiece (x,y-1) m == Wall = (Move i R)
                                                                        | y == b && x < a && whichPiece (x+1,y) m == Wall && whichPiece (x,y-1) m == Wall && whichPiece (x,y-1) m == Wall = (Move i U)
                                                                        | y == b && x > a && whichPiece (x-1,y) m /= Wall = (Move i U)
                                                                        | y == b && x > a && whichPiece (x-1,y) m == Wall = if distance (Ghost (GhoState (i,(x,y+1),ve,o,p,v) Alive)) (a,b) > distance (Ghost (GhoState (i,(x,y-1),ve,o,p,v) Alive)) (a,b) then (Move i L)
                                                                                                                            else (Move i R)
                                                                        | y == b && x > a && whichPiece (x-1,y) m == Wall && whichPiece (x,y+1) m == Wall = (Move i L)
                                                                        | y == b && x > a && whichPiece (x-1,y) m == Wall && whichPiece (x,y+1) m == Wall && whichPiece (x,y-1) m == Wall = (Move i D)
                                                                        | otherwise = (Move i U)

-- | Função final para fantasmas vivos.
chasemode :: State -> Int -> Play
chasemode (State m (Ghost (GhoState (i,(x,y),ve,o,p,v) Alive):xs) l) id | id == i = moveGhost (State m [(Ghost (GhoState (i,(x,y),ve,o,p,v) Alive))] l) (getPacCoords (State m (Ghost (GhoState (i,(x,y),ve,o,p,v) Alive):xs) l))
                                                                        | otherwise = chasemode (State m xs l) (id)


                                              





