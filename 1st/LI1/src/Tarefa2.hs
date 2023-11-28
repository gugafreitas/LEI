module Tarefa2 where
import System.IO.Unsafe
import Types
import FileUtils
import Tarefa1

-- | Na Tarefa 2, foi nos pedido inicialmente formularmos a movimentação do Pacman e mais tarde, as movimentações também de Ghosts quando são controlados por jogadores reais.
-- | Em primeiro lugar, para a movimentação do Pacman foram definidas várias funções auxiliares que dividiam o labirinto em várias partes, faziam a verificação das peças existentes e reorganizavam
-- | listas de jogadores de forma a serem compatíveis com o resultado final. Com o auxilio destas funções, as funções mais crucias com por exemplo a função "direçao" ditavam as possibilidades e
-- | os limites da movimentação do Pacman.
-- | Em segundo lugar, foi preciso adaptar a estratégia de movimentação para ser possível a jogabilidade também com Ghosts. Para isso, foram necessárias alterações em algumas funções tais como 
-- | a função "playertostate" para ser compatível com a receção de um Ghost.
-- | Por fim, a Tarefa 2 apresenta um papel crucial na movimentação dos jogadores e por isso tem de estar incluida nas tarefas seguintes.




-- | Abreviaturas para a execução mais facil do jogo.
mz = generateMaze 21 10 31
ps = [pacman,ghost1]
l = 0
et = (State mez js 0)
mez = (generateMaze 30 20 2)

-- | Lista de jogadores presentes em jogo.

js = [ghost1,pacman,ghost2,ghost3,ghost4]

pacman = (Pacman (PacState (1,(8,45),1,R,10,2) 0 Open Normal))
ghost1 = (Ghost (GhoState (2,(10,0),1,R,1,1) Alive))
ghost2 = (Ghost (GhoState (3,(15,3),1,R,1,1) Alive))
ghost3 = (Ghost (GhoState (4,(9,5),1,R,1,1) Dead))
ghost4 = (Ghost (GhoState (5,(4,9),1,R,1,1) Dead))



-- | Muda a direção do jogador.
changeOrientation:: Orientation -> Player -> Player
changeOrientation a (Pacman ((PacState (i,(x,y),ve,o,p,v)c mo s))) = Pacman (PacState (i,(x,y),ve,a,p,v) c mo s)

-- | Identifica o comprimento do labirinto.
corridorsize :: Maze -> Int
corridorsize x = length (head x)


-- | Segunda versão da função halflab que identifica a linha do meio do labirinto.
halflabv2 :: Maze -> Int
halflabv2 l = div (length l) 2

-- | Atráves de umas determinadas coordenadas e de um labirinto, identifica o tipo de peça.
whichPiece:: Coords -> Maze -> Piece
whichPiece (x,y) m = head (drop y (head (drop x m)))

-- | Verfica se uma determinada peça é um fantasma.
isGhost :: Piece -> Bool
isGhost (PacPlayer (Ghost _)) = True
isGhost _ = False

-- | Verfica se uma determinada peça é o Pacman.
isPacman :: Piece -> Bool
isPacman (PacPlayer (Pacman _)) = True
isPacman _ = False

-- | Comportamento do jogador perante a movimentação para a esquerda em modo Normal (Pacman).
moveL :: State -> State
moveL (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Open Normal)] l)   | y == 0 && even (length m)  && x == (halflabv2 m)-1  = State m [Pacman (PacState (i, (x,(corridorsize m)-1),ve, o,p,v) c Closed Normal)] l
                                                                          | y == 0 && x == (halflabv2 m) = State m [Pacman (PacState (i, (x,(corridorsize m)-1),ve, o,p,v) c Closed Normal)] l
                                                                           | whichPiece (x,y-1) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Closed Normal)] l 
                                                                            | whichPiece (x,y-1) m == Empty = State m [Pacman (PacState (i, (x,y-1), ve, o,p,v) c Closed Normal)] l
                                                                             | whichPiece (x,y-1) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y-1), ve, o,p+1,v) c Closed Normal)] l 
                                                                              | whichPiece (x,y-1) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y-1), ve ,o,p+5,v) 10 Closed Mega)] l
                                                                               | isGhost (whichPiece (x,y-1) m) = if (v-1) /= 0 then State m [Pacman (PacState (i, (x,y-1), ve, o,p,(v-1)) c Closed Normal)] l else State m [Pacman (PacState (i, (x,y-1), ve, o,p,0) c Closed Dying)] l
                                                                                | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Closed Normal)] l

moveL (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Closed Normal)] l) | y == 0 && even (length m)  && x == (halflabv2 m)-1  = State m [Pacman (PacState (i, (x,(corridorsize m)-1),ve, o,p,v) c Open Normal)] l
                                                                          | y == 0 && x == (halflabv2 m) = State m [Pacman (PacState (i, (x,(corridorsize m)-1),ve, o,p,v) c Open Normal)] l
                                                                           | whichPiece (x,y-1) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Open Normal)] l 
                                                                            | whichPiece (x,y-1) m == Empty = State m [Pacman (PacState (i, (x,y-1), ve, o,p,v) c Open Normal)] l
                                                                             | whichPiece (x,y-1) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y-1), ve, o,p+1,v) c Open Normal)] l 
                                                                              | whichPiece (x,y-1) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y-1), ve ,o,p+5,v) 10 Open Mega)] l
                                                                               | isGhost (whichPiece (x,y-1) m) = if (v-1) /= 0 then State m [Pacman (PacState (i, (x,y-1), ve, o,p,(v-1)) c Open Normal)] l else State m [Pacman (PacState (i, (x,y-1), ve, o,p,0) c Open Dying)] l
                                                                                | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Open Normal)] l

-- | Comportamento do jogador perante a movimentação para a direita em modo Normal (Pacman).                     
moveR :: State -> State
moveR (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Open Normal)] l)   | y == (corridorsize m)-1 && even (length m) && x == (halflabv2 m)-1 = State m [Pacman (PacState (i, (x,0),ve, o,p,v) c Closed Normal)] l
                                                                          | y == (corridorsize m)-1 && x == halflabv2 m = State m [Pacman (PacState (i, (x,0),ve, o,p,v) c Closed Normal)] l
                                                                           | whichPiece (x,y+1) m == Wall  = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Closed Normal)] l
                                                                            | whichPiece (x,y+1) m == Empty = State m [Pacman (PacState (i,(x,y+1), ve, o,p,v) c Closed Normal)] l
                                                                             | whichPiece (x,y+1) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y+1), ve, o,p+1,v) c Closed Normal)] l
                                                                              | whichPiece (x,y+1) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y+1), ve ,o,p+5,v) 10 Closed Mega)] l
                                                                               | isGhost (whichPiece (x,y+1) m) = if (v-1) /= 0 then State m [Pacman (PacState (i, (x,y+1), ve, o,p,(v-1)) c Closed Normal)] l else State m [Pacman (PacState (i, (x,(y+1)), ve, o,p,0) c Closed Dying)] l
                                                                                | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Closed Normal)] l

moveR (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Closed Normal)] l) | y == (corridorsize m)-1 && even (length m) && x == (halflabv2 m)-1 = State m [Pacman (PacState (i, (x,0),ve, o,p,v) c Open Normal)] l
                                                                          | y == (corridorsize m)-1 && x== halflabv2 m = State m [Pacman (PacState (i, (x,0),ve, o,p,v) c Open Normal)] l
                                                                           | whichPiece (x,y+1) m == Wall  = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Open Normal)] l
                                                                            | whichPiece (x,y+1) m == Empty = State m [Pacman (PacState (i,(x,y+1), ve, o,p,v) c Open Normal)] l
                                                                             | whichPiece (x,y+1) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y+1), ve, o,p+1,v) c Open Normal)] l
                                                                              | whichPiece (x,y+1) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y+1), ve ,o,p+5,v) 10 Open Mega)] l
                                                                               | isGhost (whichPiece (x,y+1) m) = if (v-1) /= 0 then State m [Pacman (PacState (i, (x,y+1), ve, o,p,(v-1)) c Open Normal)] l else State m [Pacman (PacState (i, (x,(y+1)), ve, o,p,0) c Open Dying)] l
                                                                                | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Open Normal)] l

-- | Comportamento do jogador perante a movimentação para a cima em modo Normal (Pacman).                            
moveU :: State -> State
moveU (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Open Normal)] l)   | whichPiece (x-1,y) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Closed Normal)] l
                                                                          | whichPiece (x-1,y) m == Empty = State m [Pacman (PacState (i, (x-1,y), ve, o,p,v) c Closed Normal)] l
                                                                           | whichPiece (x-1,y) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x-1,y), ve, o,p+1,v) c Closed Normal)] l
                                                                            | whichPiece (x-1,y) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x-1,y), ve ,o,p+5,v) 10 Closed Mega)] l
                                                                             | isGhost (whichPiece (x-1,y) m) = if (v-1) /= 0 then State m [Pacman (PacState (i, (x-1,y), ve, o,p,(v-1)) c Closed Normal)] l else State m [Pacman (PacState (i, (x-1,y), ve, o,p,0) c Closed Dying)] l
                                                                              | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Closed Normal)] l

moveU (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Closed Normal)] l)  | whichPiece (x-1,y) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Open Normal)] l
                                                                           | whichPiece (x-1,y) m == Empty = State m [Pacman (PacState (i, (x-1,y), ve, o,p,v) c Open Normal)] l
                                                                            | whichPiece (x-1,y) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x-1,y), ve, o,p+1,v) c Open Normal)] l
                                                                             | whichPiece (x-1,y) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x-1,y), ve ,o,p+5,v) 10 Open Mega)] l
                                                                              | isGhost (whichPiece (x-1,y) m) = if (v-1) /= 0 then State m [Pacman (PacState (i, (x-1,y), ve, o,p,(v-1)) c Open Normal)] l else State m [Pacman (PacState (i, (x-1,y), ve, o,p,0) c Open Dying)] l
                                                                               | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Open Normal)] l

-- | Comportamento do jogador perante a movimentação para a baixo em modo Normal (Pacman).
moveD :: State -> State
moveD (State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Open Normal)] l) | whichPiece (x+1,y) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Closed Normal)] l
                                                                          | whichPiece (x+1,y) m == Empty = State m [Pacman (PacState (i, (x+1,y), ve, o,p,v) c Closed Normal)] l
                                                                           | whichPiece (x+1,y) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x+1,y), ve, o,p+1,v) c Closed Normal)] l
                                                                            | whichPiece (x+1,y) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x+1,y), ve ,o,p+5,v) 10 Closed Mega)] l
                                                                             | isGhost (whichPiece (x+1,y) m)= if (v-1) /= 0 then State m [Pacman (PacState (i, (x+1,y), ve, o,p,(v-1)) c Closed Normal)] l else State m [Pacman (PacState (i, (x+1,y), ve, o,p,0) c Closed Dying)] l
                                                                              | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Closed Normal)] l

moveD (State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Closed Normal)] l) | whichPiece (x+1,y) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Open Normal)] l
                                                                            | whichPiece (x+1,y) m == Empty = State m [Pacman (PacState (i, (x+1,y), ve, o,p,v) c Open Normal)] l
                                                                             | whichPiece (x+1,y) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x+1,y), ve, o,p+1,v) c Open Normal)] l
                                                                              | whichPiece (x+1,y) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x+1,y), ve ,o,p+5,v) 10 Open Mega)] l
                                                                               | isGhost (whichPiece (x+1,y) m)= if (v-1) /= 0 then State m [Pacman (PacState (i, (x+1,y), ve, o,p,(v-1)) c Open Normal)] l else State m [Pacman (PacState (i, (x+1,y), ve, o,p,0) c Open Dying)] l
                                                                                | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Open Normal)] l

-- | Comportamento do jogador perante a movimentação para a esquerda em modo Mega (Pacman).
moveLMega :: State -> State
moveLMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Open Mega)] l)  | y == 0 && even (length m)  && x == (halflabv2 m)-1 = State m [Pacman (PacState (i, (x,(corridorsize m)-1),ve, o,p,v) c Closed Mega)] l
                                                                           | y == 0 && x == (halflabv2 m) = State m [Pacman (PacState (i, (x,(corridorsize m)-1),ve, o,p,v) c Closed Mega)] l
                                                                             | whichPiece (x,y-1) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Closed Mega)] l
                                                                              | whichPiece (x,y-1) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y-1), ve, o, p+5, v) 10 Closed Mega)] l  
                                                                               | whichPiece (x,y-1) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y-1), ve, o,p+1,v) c Closed Mega)] l
                                                                                | whichPiece (x,y-1) m == Empty = State m [Pacman (PacState (i, (x,y-1), ve, o,p,v) c Closed Mega)] l
                                                                                 | isGhost (whichPiece  (x,y-1) m) = State m [Pacman (PacState (i, (x,y-1), ve, o,p+10,v) c Closed Mega)] l
                                                                                  | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Closed Mega)] l

moveLMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Closed Mega)] l)  | y == 0 && even (length m)  && x == (halflabv2 m)-1 = State m [Pacman (PacState (i, (x,(corridorsize m)-1),ve, o,p,v) c Open Mega)] l
                                                                             | y == 0 && x == (halflabv2 m) = State m [Pacman (PacState (i, (x,(corridorsize m)-1),ve, o,p,v) c Open Mega)] l
                                                                              | whichPiece (x,y-1) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Open Mega)] l
                                                                               | whichPiece (x,y-1) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y-1), ve, o, p+5, v) 10 Open Mega)] l  
                                                                                | whichPiece (x,y-1) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y-1), ve, o,p+1,v) c Open Mega)] l
                                                                                 | whichPiece (x,y-1) m == Empty = State m [Pacman (PacState (i, (x,y-1), ve, o,p,v) c Open Mega)] l
                                                                                  | isGhost (whichPiece (x,y-1) m) = State m [Pacman (PacState (i, (x,y-1), ve, o,p+10,v) c Open Mega)] l
                                                                                   | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Open Mega)] l


-- | Comportamento do jogador perante a movimentação para a direita em modo Mega (Pacman).
moveRMega :: State -> State
moveRMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Open Mega)] l)  | y == (corridorsize m)-1 && even (length m) && x == halflabv2 m-1 = State m [Pacman (PacState (i, (x,0),ve, o,p,v) c Closed Mega)] l
                                                                            | y == (corridorsize m)-1 && x == halflabv2 m = State m [Pacman (PacState (i, (x,0),ve, o,p,v) c Closed Mega)] l
                                                                             | whichPiece (x,y+1) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y+1), ve, o, p+5, v) c Closed Mega)] l  
                                                                               | whichPiece (x,y+1) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y+1), ve, o, p+1,v) c Closed Mega)] l
                                                                                | whichPiece (x,y+1) m == Empty = State m [Pacman (PacState (i, (x,y+1), ve, o,p,v) c Closed Mega)] l
                                                                                 | isGhost (whichPiece (x,y+1) m) = State m [Pacman (PacState (i, (x,y+1), ve, o,p+10,v) c Closed Mega)] l
                                                                                  | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) (c) Closed Mega)] l

moveRMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Closed Mega)] l) | y == (corridorsize m)-1 && even (length m) && x == halflabv2 m-1 = State m [Pacman (PacState (i, (x,0),ve, o,p,v) c Open Mega)] l
                                                                            | y == (corridorsize m)-1 && x == halflabv2 m = State m [Pacman (PacState (i, (x,0),ve, o,p,v) c Open Mega)] l
                                                                              | whichPiece (x,y+1) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y+1), ve, o, p+5, v) c Open Mega)] l  
                                                                               | whichPiece (x,y+1) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x,y+1), ve, o, p+1,v) c Open Mega)] l
                                                                                | whichPiece (x,y+1) m == Empty = State m [Pacman (PacState (i, (x,y+1), ve, o,p,v) c Open Mega)] l
                                                                                 | isGhost (whichPiece (x,y+1) m) = State m [Pacman (PacState (i, (x,y+1), ve, o,p+10,v) c Open Mega)] l
                                                                                  | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) (c) Open Mega)] l

-- | Comportamento do jogador perante a movimentação para a cima em modo Mega (Pacman).
moveUMega :: State -> State
moveUMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Open Mega)] l) | whichPiece (x-1,y) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Closed Mega)] l
                                                                          | whichPiece (x-1,y) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x-1,y), ve, o, p+5, v) 10 Closed Mega)] l  
                                                                           | whichPiece (x-1,y) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x-1,y), ve, o,p+1,v) c Closed Mega)] l
                                                                            | whichPiece (x-1,y) m == Empty = State m [Pacman (PacState (i, (x-1,y), ve, o,p,v) c Closed Mega)] l
                                                                             | isGhost (whichPiece (x-1,y) m) = State m [Pacman (PacState (i, (x-1,y), ve, o,p+10,v) c Closed Mega)] l
                                                                              | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) (c-1) Closed Mega)] l 

moveUMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Closed Mega)] l) | whichPiece (x-1,y) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Open Mega)] l
                                                                            | whichPiece (x-1,y) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x-1,y), ve, o, p+5, v) 10 Open Mega)] l  
                                                                             | whichPiece (x-1,y) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x-1,y), ve, o,p+1,v) c Open Mega)] l
                                                                              | whichPiece (x-1,y) m == Empty = State m [Pacman (PacState (i, (x-1,y), ve, o,p,v) c Open Mega)] l
                                                                               | isGhost (whichPiece (x-1,y) m) = State m [Pacman (PacState (i, (x-1,y), ve, o,p+10,v) c Open Mega)] l
                                                                                | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) (c-1) Open Mega)] l

-- | Comportamento do jogador perante a movimentação para a baixo em modo Mega (Pacman).
moveDMega :: State -> State
moveDMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Open Mega)] l) | whichPiece (x+1,y) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Closed Mega)] l
                                                                          | whichPiece (x+1,y) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x+1,y), ve, o, p+5, v) 10 Closed Mega)] l  
                                                                           | whichPiece (x+1,y) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x+1,y), ve, o,p+1,v) c Closed Mega)] l
                                                                            | whichPiece (x+1,y) m == Empty = State m [Pacman (PacState (i, (x+1,y), ve, o,p,v) c Closed Mega)] l
                                                                             | isGhost (whichPiece (x+1,y) m) = State m [Pacman (PacState (i, (x+1,y), ve, o,p+10,v) c Closed Mega)] l
                                                                              | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Closed Mega)] l

moveDMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c Closed Mega)] l) | whichPiece (x+1,y) m == Wall = State m [Pacman (PacState (i, (x,y), ve, o,p,v) c Open Mega)] l
                                                                            | whichPiece (x+1,y) m == Food Big = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x+1,y), ve, o, p+5, v) 10 Open Mega)] l  
                                                                             | whichPiece (x+1,y) m == Food Little = State (replaceElemInMaze (x,y) (Empty) m) [Pacman (PacState (i, (x+1,y), ve, o,p+1,v) c Open Mega)] l
                                                                              | whichPiece (x+1,y) m == Empty = State m [Pacman (PacState (i, (x+1,y), ve, o,p,v) c Open Mega)] l
                                                                               | isGhost (whichPiece (x+1,y) m) = State m [Pacman (PacState (i, (x+1,y), ve, o,p+10,v) c Open Mega)] l
                                                                                | otherwise = State m [Pacman (PacState (i, (x,y),ve, o,p,v) c Open Mega)] l

-- | Comportamento do jogador perante a movimentação para a esquerda em modo Alive (Ghost).
moveLG :: State -> State
moveLG (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)   | y == 0 && even (length m)  && x == (halflabv2 m)-1  = State m [Ghost (GhoState (i, (x,(corridorsize m)-1),ve,o,p,v) s)] l
                                                               | y == 0 && x == (halflabv2 m) = State m [Ghost (GhoState (i, (x,(corridorsize m)-1),ve, o,p,v) s)] l
                                                                 | whichPiece (x,y-1) m == Wall = State m [Ghost (GhoState (i, (x,y), ve, o,p,v) s)] l 
                                                                   | whichPiece (x,y-1) m == Empty = State m [Ghost (GhoState (i, (x,y-1), ve, o,p,v) s)] l
                                                                     | whichPiece (x,y-1) m == Food Little = State m [Ghost (GhoState (i, (x,y-1), ve, o,p,v) s)] l 
                                                                       | whichPiece (x,y-1) m == Food Big = State m [Ghost (GhoState (i, (x,y-1), ve ,o,p,v) s)] l
                                                                         | isPacman (whichPiece (x,y-1) m) = if s == Dead then ghostToHouse (State m [Ghost (GhoState (i, (x,y), ve, o,p,v) s)] l) else State m [Ghost (GhoState (i, (x,y-1), ve, o,p,v) s)] l
                                                                           | otherwise = State m [Ghost (GhoState (i, (x,y),ve, o,p,v) s)] l

-- | Comportamento do jogador perante a movimentação para a direita em modo Alive (Ghost).
moveRG :: State -> State
moveRG (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)   | y == (corridorsize m)-1 && even (length m) && x == halflabv2 m-1 = State m [Ghost (GhoState (i, (x,0),ve, o,p,v) s)] l
                                                                | y == (corridorsize m)-1 && x == halflabv2 m = State m [Ghost (GhoState (i, (x,0),ve, o,p,v) s)] l
                                                                 | whichPiece (x,y+1) m == Wall = State m [Ghost (GhoState (i, (x,y), ve, o,p,v) s)] l 
                                                                   | whichPiece (x,y+1) m == Empty = State m [Ghost (GhoState (i, (x,y+1), ve, o,p,v) s)] l
                                                                     | whichPiece (x,y+1) m == Food Little = State m [Ghost (GhoState (i, (x,y+1), ve, o,p,v) s)] l 
                                                                       | whichPiece (x,y+1) m == Food Big = State m [Ghost (GhoState (i, (x,y+1), ve ,o,p,v) s)] l
                                                                         | isPacman (whichPiece (x,y+1) m) = if s == Dead then ghostToHouse (State m [Ghost (GhoState (i, (x,y), ve, o,p,v) s)] l) else State m [Ghost (GhoState (i, (x,y+1), ve, o,p,v) s)] l
                                                                           | otherwise = State m [Ghost (GhoState (i, (x,y),ve, o,p,v) s)] l

-- | Comportamento do jogador perante a movimentação para a cima em modo Alive (Ghost).
moveUG :: State -> State
moveUG (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)   | whichPiece (x-1,y) m == Wall = State m [Ghost (GhoState (i, (x,y), ve, o,p,v) s)] l 
                                                               | whichPiece (x-1,y) m == Empty = State m [Ghost (GhoState (i, (x-1,y), ve, o,p,v) s)] l
                                                                 | whichPiece (x-1,y) m == Food Little = State m [Ghost (GhoState (i, (x-1,y), ve, o,p,v) s)] l 
                                                                   | whichPiece (x-1,y) m == Food Big = State m [Ghost (GhoState (i, (x-1,y), ve ,o,p,v) s)] l
                                                                     | isPacman (whichPiece (x-1,y) m) = if s == Dead then ghostToHouse (State m [Ghost (GhoState (i, (x,y), ve, o,p,v) s)] l) else State m [Ghost (GhoState (i, (x-1,y), ve, o,p,v) s)] l
                                                                       | otherwise = State m [Ghost (GhoState (i, (x,y),ve, o,p,v) s)] l

-- | Comportamento do jogador perante a movimentação para baixo em modo Alive (Ghost).
moveDG :: State -> State
moveDG (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)   | whichPiece (x+1,y) m == Wall = State m [Ghost (GhoState (i, (x,y), ve, o,p,v) s)] l 
                                                               | whichPiece (x+1,y) m == Empty = State m [Ghost (GhoState (i, (x+1,y), ve, o,p,v) s)] l
                                                                 | whichPiece (x+1,y) m == Food Little = State m [Ghost (GhoState (i, (x+1,y), ve, o,p,v) s)] l 
                                                                   | whichPiece (x+1,y) m == Food Big = State m [Ghost (GhoState (i, (x+1,y), ve ,o,p,v) s)] l
                                                                     | isPacman (whichPiece (x+1,y) m) = if s == Dead then ghostToHouse (State m [Ghost (GhoState (i, (x,y), ve, o,p,v) s)] l) else State m [Ghost (GhoState (i, (x+1,y), ve, o,p,v) s)] l
                                                                       | otherwise = State m [Ghost (GhoState (i, (x,y),ve, o,p,v) s)] l

-- | Comportamento do jogador perante os diferentes casos de mudanças de direção (estado Normal e Mega).
direcao :: Play -> State -> State
direcao (Move id d) (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c mo Normal)] l)     | d == R && o == R = moveR (State m [Pacman (PacState (i,(x,y),ve,o,p,v)c mo Normal)] l)
                                                                                          | d == R && o /= R = (State m [Pacman (PacState (i,(x,y),ve,R,p,v)c mo Normal)] l)
                                                                                           | d == L && o == L = moveL (State m [Pacman (PacState (i,(x,y),ve,o,p,v)c mo Normal)] l)
                                                                                            | d == L && o /= L = (State m [Pacman (PacState (i,(x,y),ve,L,p,v)c mo Normal)] l)
                                                                                             | d == U && o == U = moveU (State m [Pacman (PacState (i,(x,y),ve,o,p,v)c mo Normal)] l)
                                                                                              | d == U && o /= U = (State m [Pacman (PacState (i,(x,y),ve,U,p,v)c mo Normal)] l)
                                                                                               | d == D && o == D = moveD (State m [Pacman (PacState (i,(x,y),ve,o,p,v)c mo Normal)] l)
                                                                                                | otherwise = (State m [Pacman (PacState (i,(x,y),ve,D,p,v)c mo Normal)] l)

direcao (Move id d) (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c mo Mega)] l)       | d == R && o == R =  (moveRMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v)c mo Mega)] l))
                                                                                         | d == R && o /= R = (State m [Pacman (PacState (i,(x,y),ve,R,p,v)c mo Mega)] l)
                                                                                          | d == L && o == L = (moveLMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v)c mo Mega)] l))
                                                                                           | d == L && o /= L = (State m [Pacman (PacState (i,(x,y),ve,L,p,v)c mo Mega)] l)
                                                                                            | d == U && o == U = (moveUMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v)c mo Mega)] l))
                                                                                             | d == U && o /= U = (State m [Pacman (PacState (i,(x,y),ve,U,p,v)c mo Mega)] l)
                                                                                              | d == D && o == D = (moveDMega (State m [Pacman (PacState (i,(x,y),ve,o,p,v)c mo Mega)] l))
                                                                                               | otherwise = (State m [Pacman (PacState (i,(x,y),ve,D,p,v)c mo Mega)] l)

direcao (Move id d) (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)   | d == R && o == R = moveRG (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)
                                                                              | d == R && o /= R = (State m [Ghost (GhoState (i,(x,y),ve,R,p,v) s)] l)
                                                                                | d == L && o == L = moveLG (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)
                                                                                  | d == L && o /= L = (State m [Ghost (GhoState (i,(x,y),ve,L,p,v) s)] l)
                                                                                    | d == U && o == U = moveUG (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)
                                                                                      | d == U && o /= U = (State m [Ghost (GhoState (i,(x,y),ve,U,p,v) s)] l)
                                                                                        | d == D && o == D = moveDG (State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l)
                                                                                          | otherwise = (State m [Ghost (GhoState (i,(x,y),ve,D,p,v) s)] l)




-- | Apresenta os diferentes cenários perante a mudança de estado de um jogador, quer seja Pacman ou Ghost.
ghostbehavior :: [Player] -> [Player]
ghostbehavior [] = []
ghostbehavior [Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)] = [Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)]
ghostbehavior (Pacman (PacState (i,(x,y),ve,o,p,v) c mo s):xs) = (Pacman (PacState (i,(x,y),ve,o,p,v) c mo s):(ghostbehavior xs))
ghostbehavior [Ghost (GhoState (i,(x,y),ve,o,p,v) Alive)] | o == R = [Ghost (GhoState (i,(x,y),(ve/2),L,p,v) Dead)]
                                                          | o == L = [Ghost (GhoState (i,(x,y),(ve/2),R,p,v) Dead)]
                                                          | o == U = [Ghost (GhoState (i,(x,y),(ve/2),D,p,v) Dead)]
                                                          | o == D = [Ghost (GhoState (i,(x,y),(ve/2),U,p,v) Dead)]

ghostbehavior [Ghost (GhoState (i,(x,y),ve,o,p,v) Dead)] =  [Ghost (GhoState (i,(x,y),(ve),o,p,v) Dead)]
ghostbehavior (Ghost (GhoState (i,(x,y),ve,o,p,v) Alive):xs) | o == R = (Ghost (GhoState (i,(x,y),ve/2,L,p,v) Dead):(ghostbehavior xs)) 
                                                             | o == L = (Ghost (GhoState (i,(x,y),ve/2,R,p,v) Dead):(ghostbehavior xs))
                                                             | o == U = (Ghost (GhoState (i,(x,y),ve/2,D,p,v) Dead):(ghostbehavior xs))
                                                             | o == D = (Ghost (GhoState (i,(x,y),ve/2,U,p,v) Dead):(ghostbehavior xs))
ghostbehavior (Ghost (GhoState (i,(x,y),ve,o,p,v) Dead):xs) = (Ghost (GhoState (i,(x,y),ve,o,p,v) Dead):(ghostbehavior xs))

-- | Perante a interação de um Pacman em estado Mega e um Fantasma em estado Dead, retorna o fantasma para o interior da casa dos fantasmas em estado Alive.
ghostToHouse :: State -> State
ghostToHouse (State m [] l) = (State m [] l)
ghostToHouse (State m (h:(Ghost (GhoState (i,(x,y),ve,o,p,v) l)):t) lv) | odd (length m) == True = (State m (h:(Ghost (GhoState (i,(halflabv2 m, div (corridorsize m) 2),ve,o,p,v) Alive)):t) lv)
                                                                        | otherwise = (State m (h:(Ghost (GhoState (i,((halflabv2 m)-1,div (corridorsize m) 2),ve,o,p,v) Alive)):t) lv)

-- | Verifica se 2 jogadores colidem.
solve :: State -> State
solve (State m [] l) = (State m [] l)
solve (State m [x] l) = (State m [x] l)
solve (State m (h:x:t) l) | getPlayerCoords h == getPlayerCoords x = ghostToHouse (State m (h:x:t) l)
                          | otherwise = colidestate x (solve (State m (h:t) l))



-- | Faz com que um jogador adote um estado usado por outros jogadores.
colidestate :: Player -> State -> State
colidestate y (State m [] l) = (State m [y] l)
colidestate y (State m (x:xs) l) = (State m (x:y:xs) l)

-- | Função auxiliar para a função clearpac.
death :: Player -> Player -> Player
death (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) (Ghost (GhoState (i2,(x2,y2),ve2,o2,p2,v2) s2)) = (Ghost (GhoState (i2,(x2,y2),ve2,o2,p2,v2) s2))
death (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) (Pacman (PacState (i2,(x2,y2),ve2,o2,p2,v2) c mo Normal)) | (x,y) == (x2,y2) = if v2>1 then (Pacman (PacState (i2,(x2,y2),ve2,o2,p2,v2-1) c mo Normal))
                                                                                                                             else (Pacman (PacState (i2,(x2,y2),ve2,o2,p2,v2-1) c mo Dying))
                                                                                                        | otherwise = (Pacman (PacState (i2,(x2,y2),ve2,o2,p2,v2) c mo Normal))
death (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) (Pacman (PacState (i2,(x2,y2),ve2,o2,p2,v2) c mo s2)) = (Pacman (PacState (i2,(x2,y2),ve2,o2,p2,v2) c mo s2))


-- | Retira o Pacman de uma lista (função auxiliar).
clearpac :: Player -> State -> State
clearpac (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) (State m [] l) = (State m [] l)
clearpac (Ghost (GhoState (i,(x2,y),ve,o,p,v) s)) (State m (x:xs) l) = (State m ((death (Ghost (GhoState (i,(x2,y),ve,o,p,v) s)) x): remove (clearpac (Ghost (GhoState (i,(x2,y),ve,o,p,v) s)) (State m xs l)))l)



-- | Perante o modo em que o Pacman (Normal ou Mega), o Fantasma adota diferentes comportamentos.
playertostate :: State  -> State -> State
playertostate (State m2 [(Pacman (PacState (i,(x,y),ve,o,p,v) c mo s))] l2) (State m [] l)= State m [Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)] l
playertostate (State m2 [(Pacman (PacState (i,(x,y),ve,o,p,v) c mo s))] l2) (State m (x2:xs) l) | getPacmanMode (Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)) == Mega = if getPlayerID (Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)) < getPlayerID x2 then solve (State m2 ((Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)):(ghostbehavior(x2:xs))) l) else (State m2 (x2:(changestate (playertostate (State m2 [(Pacman (PacState (i,(x,y),ve,o,p,v) c mo s))] l2) (State m xs l))):xs) l)
                                                                                                | otherwise = if getPlayerID (Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)) < getPlayerID x2 then (State m2 ((Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)):x2:xs) l) else (State m2 (x2:(changestate (playertostate (State m2 [(Pacman (PacState (i,(x,y),ve,o,p,v) c mo s))] l2) (State m xs l))):xs) l)
playertostate (State m2 ([Ghost (GhoState (i,(x,y),ve,o,p,v) s)]) l2) (State m [] l) = State m [Ghost (GhoState (i,(x,y),ve,o,p,v) s)] l
playertostate (State m2 ([Ghost (GhoState (i,(x,y),ve,o,p,v) s)]) l2) (State m (x2:xs) l) = if getPlayerID (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) < getPlayerID x2 then clearpac (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) (State m2 ((Ghost (GhoState (i,(x,y),ve,o,p,v) s)):x2:xs) l) else clearpac (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) (State m2 ((x2:(changestate (playertostate (State m2 ([Ghost (GhoState (i,(x,y),ve,o,p,v) s)]) l2) (State m xs l))):xs))l)



-- | Revela o state de um jogador (função auxiliar).
changestate :: State -> Player
changestate (State m (Pacman (PacState (i,(x,y),ve,o,p,v) c mo s):xs) l) = Pacman (PacState (i,(x,y),ve,o,p,v) c mo s)
changestate (State m (Ghost (GhoState (i,(x,y),ve,o,p,v) s):xs) l) = Ghost (GhoState (i,(x,y),ve,o,p,v) s)


-- | Verifica jogador (função auxiliar).
addstate:: Play -> State -> State
addstate (Move id d ) (State m [] l)= State m [] l
addstate (Move id d ) (State m (x:xs) l) | (getPlayerID x) == id = (State m [x] l)
                                         | otherwise = addstate (Move id d) (State m xs l)


-- | Identifica todos os jogadores diferentes do primeiramente mencionado (função auxiliar).
forceplayer :: Player -> State -> State
forceplayer x (State m (x2:xs) l) = (State m (filter (/= (x)) (x2:xs)) l)


-- | Identifica os jogadores com estados diferentes do primeiramente mencionado.
forcedplay :: Play -> State -> State
forcedplay (Move id d) (State m [] l) = State m [] l
forcedplay (Move id d) (State m (x:xs) l) = forceplayer (changestate (addstate (Move id d) (State m (x:xs) l))) (State m (x:xs) l)


-- | Através de um estado, retira um jogador.
remove :: State -> [Player]
remove (State m (x:xs) l) = x : remove (State m xs l)


-- | Coloca os jogadores "trabalhados" anteriormente no labirinto.
transformstate :: State -> State
transformstate (State m (x:xs) l) = (State m [x] l)



-- | Permite aos jogadores realizar movimentos com todas as exceções colocadas nas funções anteriores (contidas na função).
movimento :: Play -> State -> State
movimento (Move id d) (State m [] l)= State m [] l
movimento (Move id d) (State m (x:xs)l) | id == getPlayerID x = direcao (Move id d) (transformstate (State m (x:xs) l))
                                        | otherwise = movimento (Move id d) (State m xs l)




-- | Função final que apresenta o resultado pretendido.
play :: Play -> State -> State
play (Move id d) (State m [] l) = State m [] l
play (Move id d) (State m (x:xs) l) = playertostate ((movimento (Move id d) (State m (x:xs) l))) (forcedplay (Move id d) (State m (x:xs) l))

