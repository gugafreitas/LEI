-- | A Tarefa 6 consiste na criação de um "bot" que simule a jogabilidade de um jogador normal com jogadas pré-definidas em código. 
-- | Para isso, começamos por criar um mecanismo de intruções para a jogabilidade do Pacman no labirinto através da função "awayfromG" que define várias situações possíveis que o Pacman possa encontrar
-- | no labirinto, em particular, alguns casos em que um Ghost se encontra perto do Pacman, este localiza a "Food Big" mais próxima e tenta dirigir se para lá (mecanismo criado com as funções "isFoodBig"
-- | e "closerFood").
-- | Por fim, a tarefa exigiu funcionalidades idênticas à Tarefa 5, onde foram necessárias definir várias funcionalidas de comportamentos do Pacman suficiente para uma jogabilidade básica mas um pouco mais
-- | escassas para uma jogabilidade avançada.

module Tarefa6 where
import Tarefa2
import Types
import Tarefa5

-- | Verifica se uma peça é uma "Food Big" e aplica recursividade.
isFoodBig :: Maze -> Coords -> [Coords]
isFoodBig [] (a,b) = []
isFoodBig ([]:xs) (a,b) = isFoodBig xs (a+1,0)
isFoodBig ((y:ys):xs) (a,b) | y == Food Big = (a,b) : isFoodBig (ys:xs) (a,b)
                            | otherwise = isFoodBig (ys:xs) (a,b+1)

-- | Determina a distância mais curta entre duas coordenas.
closerFood :: [Coords] -> Coords -> Coords
closerFood [] (x,y) = (0,0)
closerFood [(x1,y1),(x2,y2)] (a,b) | (sqrt ((fromIntegral (a-x1)^2)+(fromIntegral (b-y1)^2))) < (sqrt ((fromIntegral (a-x2)^2)+(fromIntegral (b-y2)^2))) = (x1,y1)
                                   | otherwise = (x2,y2)
closerFood ((x1,y1):(x2,y2):(x3,y3):ys) (a,b) | (sqrt ((fromIntegral (a-x1)^2)+(fromIntegral (b-y1)^2))) < (sqrt ((fromIntegral (a-x2)^2)+(fromIntegral (b-y2)^2))) = closerFood ((x1,y1):(x3,y3):ys) (a,b)
                                              | otherwise = closerFood ((x2,y2):(x3,y3):ys) (a,b)



-- | Casos possíveis para a movimentação do bot em todo o labirinto (modo Normal).
awayfromG :: State -> Coords -> Play
awayfromG ((State m [Pacman (PacState (i,(x,y),ve,o,p,v) c mo Normal)] l)) (a,b)   | isGhost (whichPiece (x,y+3) m) && whichPiece (x,y-1) m /= Wall = Move i L
                                                                                   | isGhost (whichPiece (x,y+3) m) && whichPiece (x,y-1) m == Wall = if distance (Pacman (PacState (i,(x+1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x-1,y),ve,o,p,v) c mo Normal)) (a,b) then Move i U
                                                                                                                                                      else Move i D
                                                                                   | isGhost (whichPiece (x,y+3) m) && whichPiece (x,y-1) m == Wall && whichPiece (x+1,y) m == Wall = Move i D
                                                                                   | isGhost (whichPiece (x,y+3) m) && whichPiece (x,y-1) m == Wall && whichPiece (x+1,y) m == Wall && whichPiece (x+1,y) m == Wall = Move i R
                                                                                   | isGhost (whichPiece (x,y-3) m) && whichPiece (x,y+1) m /= Wall = Move i R
                                                                                   | isGhost (whichPiece (x,y-3) m) && whichPiece (x,y+1) m == Wall = if distance (Pacman (PacState (i,(x+1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x-1,y),ve,o,p,v) c mo Normal)) (a,b) then Move i U
                                                                                                                                                      else Move i D
                                                                                   | isGhost (whichPiece (x,y-3) m) && whichPiece (x,y+1) m == Wall = Move i R
                                                                                   | isGhost (whichPiece (x,y-3) m) && whichPiece (x,y-1) m == Wall && whichPiece (x+1,y) m == Wall = Move i D
                                                                                   | isGhost (whichPiece (x,y-3) m) && whichPiece (x,y-1) m == Wall && whichPiece (x+1,y) m == Wall && whichPiece (x+1,y) m == Wall = Move i L
                                                                                   | isGhost (whichPiece (x+3,y) m) && whichPiece (x-1,y) m /= Wall = Move i L
                                                                                   | isGhost (whichPiece (x+3,y) m) && whichPiece (x-1,y) m == Wall = if distance (Pacman (PacState (i,(x,y+1),ve,o,p,v) c mo Normal))  (a,b) > distance (Pacman (PacState (i,(x,y-1),ve,o,p,v) c mo Normal)) (a,b) then Move i L
                                                                                                                                                      else Move i R
                                                                                   | isGhost (whichPiece (x+3,y) m) && whichPiece (x-1,y) m == Wall && whichPiece (x+1,y) m == Wall = Move i D
                                                                                   | isGhost (whichPiece (x+3,y) m) && whichPiece (x-1,y) m == Wall && whichPiece (x+1,y) m == Wall && whichPiece (x+1,y) m == Wall = Move i R
                                                                                   | isGhost (whichPiece (x-3,y) m) && whichPiece (x+1,y) m /= Wall = Move i L
                                                                                   | isGhost (whichPiece (x-3,y) m) && whichPiece (x+1,y) m == Wall = if distance (Pacman (PacState (i,(x,y+1),ve,o,p,v) c mo Normal))  (a,b) > distance (Pacman (PacState (i,(x,y-1),ve,o,p,v) c mo Normal)) (a,b) then Move i L
                                                                                                                                                      else Move i R
                                                                                   | isGhost (whichPiece (x-3,y) m) && whichPiece (x+1,y) m == Wall && whichPiece (x+1,y) m == Wall = Move i D
                                                                                   | isGhost (whichPiece (x-3,y) m) && whichPiece (x+1,y) m == Wall && whichPiece (x+1,y) m == Wall && whichPiece (x+1,y) m == Wall = Move i R
                                                                                   | whichPiece (x+1,y) m == Food Little && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Empty = (Move i D)
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Food Little && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Empty = (Move i U)
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Food Little && whichPiece (x,y-1) m == Empty = (Move i R)
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Food Little = (Move i L)
                                                                                   | whichPiece (x+1,y) m == Food Little && whichPiece (x-1,y) m == Food Little && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Empty = if distance (Pacman (PacState (i,(x+1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x-1,y),ve,o,p,v) c mo Normal)) (a,b) then Move i U
                                                                                                                                                                                                                                    else Move i D
                                                                                   | whichPiece (x+1,y) m == Food Little && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Food Little && whichPiece (x,y-1) m == Empty = if distance (Pacman (PacState (i,(x+1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x,y+1),ve,o,p,v) c mo Normal)) (a,b) then Move i R
                                                                                                                                                                                                                                    else Move i D
                                                                                   | whichPiece (x+1,y) m == Food Little && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Food Little = if distance (Pacman (PacState (i,(x+1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x,y-1),ve,o,p,v) c mo Normal)) (a,b) then Move i L
                                                                                                                                                                                                                                    else Move i D
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Food Little && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Empty = (Move i U)                                                                                                                                                 
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Food Little && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Food Little = if distance (Pacman (PacState (i,(x-1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x,y-1),ve,o,p,v) c mo Normal)) (a,b) then Move i L
                                                                                                                                                                                                                                    else Move i U
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Food Little && whichPiece (x,y+1) m == Food Little && whichPiece (x,y-1) m == Empty = if distance (Pacman (PacState (i,(x-1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x,y+1),ve,o,p,v) c mo Normal)) (a,b) then Move i R
                                                                                                                                                                                                                                    else Move i U
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Food Little && whichPiece (x,y-1) m == Empty = (Move i R)
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Food Little && whichPiece (x,y-1) m == Food Little = if distance (Pacman (PacState (i,(x,y+1),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x,y-1),ve,o,p,v) c mo Normal)) (a,b) then Move i L
                                                                                                                                                                                                                                    else Move i R
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Food Little = (Move i L)
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Empty = if distance (Pacman (PacState (i,(x+1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x-1,y),ve,o,p,v) c mo Normal)) (a,b) then Move i U
                                                                                                                                                                                                                        else Move i D 
                                                                                   | whichPiece (x+1,y) m == Food Big && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Empty = (Move i D)
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Food Big && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Empty = (Move i U)
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Food Big && whichPiece (x,y-1) m == Empty = (Move i R)
                                                                                   | whichPiece (x+1,y) m == Empty && whichPiece (x-1,y) m == Empty && whichPiece (x,y+1) m == Empty && whichPiece (x,y-1) m == Food Big = (Move i L)
                                                                                   | whichPiece (x+1,y) m == Food Big && whichPiece (x-1,y) m == Food Little && whichPiece (x,y+1) m == Food Little && whichPiece (x,y-1) m == Food Little = (Move i D)
                                                                                   | whichPiece (x+1,y) m == Food Little && whichPiece (x-1,y) m == Food Big && whichPiece (x,y+1) m == Food Little && whichPiece (x,y-1) m == Food Little = (Move i U)
                                                                                   | whichPiece (x+1,y) m == Food Little && whichPiece (x-1,y) m == Food Little && whichPiece (x,y+1) m == Food Big && whichPiece (x,y-1) m == Food Little = (Move i R)
                                                                                   | whichPiece (x+1,y) m == Food Little && whichPiece (x-1,y) m == Food Little && whichPiece (x,y+1) m == Food Little && whichPiece (x,y-1) m == Food Big = (Move i L)
                                                                                   | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 == 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else if x < (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i D)
                                                                                                                                                                                               else if x == (halflabv2 m) && whichPiece (x,y+1) m /= Wall then (Move i R)
                                                                                                                                                                                               else Move i L      
                                                                                   | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 /= 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else (Move i L)
                                                                                   | y <= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 == 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else if x < (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i D)
                                                                                                                                                                                               else if x == (halflabv2 m) && whichPiece (x,y+1) m /= Wall then (Move i R)
                                                                                                                                                                                               else (Move i L)
                                                                                   | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 /= 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                                            else (Move i D)                                                                                                                     
 
                                                                                   
-- | Casos possíveis para a movimentação do bot em todo o labirinto (modo Mega).
closetoG :: State -> Coords -> Play
closetoG (State m [Pacman (PacState (i,(x,y),ve,o,p,v) c mo Mega)] l) (a,b)        | y < b && whichPiece (x,y+1) m /= Wall = Move i R
                                                                                   | y < b && whichPiece (x,y+1) m == Wall = if distance (Pacman (PacState (i,(x+1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x-1,y),ve,o,p,v) c mo Normal)) (a,b) then Move i U
                                                                                                                           else Move i D
                                                                                   | y < b && whichPiece (x,y+1) m == Wall && whichPiece (x+1,y) m == Wall = Move i U
                                                                                   | y < b && whichPiece (x,y+1) m == Wall && whichPiece (x-1,y) m == Wall = Move i D
                                                                                   | y < b && whichPiece (x,y+1) m == Wall && whichPiece (x-1,y) m == Wall && whichPiece (x+1,y) m == Wall = Move i L                                
                                                                                   | y < b && whichPiece (x,y-1) m /= Wall = Move i L
                                                                                   | y < b && whichPiece (x,y-1) m == Wall = if distance (Pacman (PacState (i,(x+1,y),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x-1,y),ve,o,p,v) c mo Normal)) (a,b) then Move i U
                                                                                                                             else Move i D
                                                                                   | y < b && whichPiece (x,y-1) m == Wall && whichPiece (x+1,y) m == Wall = Move i U
                                                                                   | y < b && whichPiece (x,y-1) m == Wall && whichPiece (x-1,y) m == Wall = Move i D
                                                                                   | y < b && whichPiece (x,y-1) m == Wall && whichPiece (x-1,y) m == Wall && whichPiece (x+1,y) m == Wall = Move i R
                                                                                   | y == b && x < a && whichPiece (x+1,y) m /= Wall = Move i D
                                                                                   | y == b && x < a && whichPiece (x+1,y) m == Wall = if distance (Pacman (PacState (i,(x,y+1),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x,y-1),ve,o,p,v) c mo Normal)) (a,b) then Move i L
                                                                                                                                      else Move i R
                                                                                   | y == b && x < a && whichPiece (x+1,y) m == Wall && whichPiece (x,y+1) m == Wall = Move i L
                                                                                   | y == b && x < a && whichPiece (x+1,y) m == Wall && whichPiece (x,y-1) m == Wall = Move i R
                                                                                   | y == b && x < a && whichPiece (x+1,y) m == Wall && whichPiece (x,y-1) m == Wall && whichPiece (x,y-1) m == Wall = Move i U
                                                                                   | y == b && x > a && whichPiece (x-1,y) m /= Wall = Move i U
                                                                                   | y == b && x > a && whichPiece (x-1,y) m == Wall = if distance (Pacman (PacState (i,(x,y+1),ve,o,p,v) c mo Normal)) (a,b) > distance (Pacman (PacState (i,(x,y-1),ve,o,p,v) c mo Normal)) (a,b) then Move i L
                                                                                                                                     else Move i R
                                                                                   | y == b && x > a && whichPiece (x-1,y) m == Wall && whichPiece (x,y+1) m == Wall = Move i L
                                                                                   | y == b && x > a && whichPiece (x-1,y) m == Wall && whichPiece (x,y+1) m == Wall && whichPiece (x,y-1) m == Wall = Move i D
                                                                                   | otherwise = Move i U
                                                                                   | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 == 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else if x < (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i D)
                                                                                                                                                                                               else if x == (halflabv2 m) && whichPiece (x,y+1) m /= Wall then (Move i R)
                                                                                                                                                                                               else Move i L      
                                                                                   | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 /= 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else (Move i L)
                                                                                   | y <= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 == 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i U)
                                                                                                                                                                                               else if x < (halflabv2 m) && whichPiece (x+1,y) m /= Wall then (Move i D)
                                                                                                                                                                                               else if x == (halflabv2 m) && whichPiece (x,y+1) m /= Wall then (Move i R)
                                                                                                                                                                                               else (Move i L)
                                                                                   | y >= ((corridorsize m) - (div (corridorsize m) 4)) && (mod (corridorsize m) 2 /= 0 && b <= (div (corridorsize m) 4)) = if x > (halflabv2 m) && whichPiece (x-1,y) m /= Wall then (Move i U)
                                                                                                                                                                                                            else (Move i D)


-- | Verifica numa lista de jogadores de um State se é Ghost e caso se confirma, retira as suas coordenadas.
isGhostv3 :: State -> [Coords]
isGhostv3 (State m [] l) = []
isGhostv3 (State m (x:xs) l) | isGhostv2 x = getPlayerCoords x : isGhostv3 (State m xs l)
                             | otherwise = isGhostv3 (State m xs l)



-- / Função final que aplica a movimentação do bot.
bot :: Int-> State -> Maybe Play
bot id (State m [] l) = Nothing
bot id (State m (x:xs) l) | isPacmanv2 x && getPacmanMode x == Mega && getPlayerID x == id = Just (closetoG (State m [x] l) (closerFood (isGhostv3 (State m (x:xs) l)) (getPlayerCoords x)))
                          | isPacmanv2 x && getPacmanMode x == Normal && getPlayerID x == id = Just (awayfromG (State m [x] l) (closerFood (isFoodBig m (0,0)) (getPlayerCoords x)))
                          | otherwise = bot id (State m xs l)

