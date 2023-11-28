module Tarefa4 where
import Types
import Tarefa2

defaultDelayTime = 250 -- ms

-- TAREFA 4

{--

Introdução

Nesta tarefa, que inicializa a 2ª fase do projeto e que tem como objetivo aproximar o pacman o mais
possível daquilo que acontece no jogo original, tínhamos que reagir à passagem do tempo.
Como sabemos, à medida que o jogo/tempo avança todos os jogadores se movimentam e, consequentemente, é 
produzido um novo estado no qual cada jogador e o mapa são atualizados.

Objetivo

O objetivo final desta tarefa é definir a função passTime :: Int -> State -> State (integrará a nextFrame no
Main) que, para um dado step e estado, altera o estado do jogo em uma iteração. Para isso teremos de dar um
step (iteração) e um state, para devolver o novo state.
Se o tempo for maior ou igual a 250 ms todos os jogadores no mapa têm de jogar e, consequentemente,
o seu estado ser atualizado, caso contrário é retornado o mesmo estado, sem qualquer alteração.

Para definirmos a função jogaTodos, tivemos especial atenção às jogadas efetuadas pelo pacman no modo mega
e pelos fantasmas.


Conclusão

Cumprimos a tarefa 4 com alguma dificuldade pois inicilmente ignoramos a possibilidade do pacman se movimentar
em modo mega e também a movimentação do fantasma mas tais situações foram resolvidas com sucesso.

--}




-- |Função que dá a passagem do tempo: se delta (tempo decorrido desde a última jogada) é menor quer 250 ms
passTime :: Int -> State -> State --ok
passTime x s = if x< defaultDelayTime then s else jogaTodos (player s) s x

-- | Tipo de jogador
player :: State -> [Player]
player (State m [x] l) = [x] 




-- |Função que é aplicada a cada 250 ms e faz com que todos os jogadores se movimentem, atualizando o seu estado depois de cada jogada.
jogaTodos :: [Player] -> State -> Int -> State
jogaTodos (x:xs) s i 
 | isPacmanMega (x) && mod i 2 == 0 = jogaTodos xs (velocidade (getVelocidade (x)) (x) s) (i+1)
 | isGhostv2 (x) && mod i 2 == 0 = jogaTodos xs (velocidade (getVelocidade (x)) (x) s) (i+1)
 | otherwise = jogaTodos xs (play (getNextMove (x)) s) (i+1) 


-- | Jogada a aplicar
getNextMove :: Player -> Play
getNextMove (Pacman (PacState (x,y,z,t,h,l) q c d )) = Move x t
getNextMove (Ghost (GhoState (x,y,z,t,h,l) q ))      = Move x t

-- |Diz se o jogador em causa é ou não um fantasma
isGhostv2 :: Player -> Bool
isGhostv2 (Ghost (GhoState (i,(x,y),ve,o,p,v) s)) = True
isGhostv2 _ = False


-- |Diz se o jogador em causa é um pacman no modo mega
isPacmanMega :: Player-> Bool
isPacmanMega (Pacman (PacState (x,y,z,t,h,l) q c Mega)) = True
isPacmanMega _ = False

-- |Velocidade do jogador
getVelocidade :: Player-> Double
getVelocidade (Pacman (PacState (x,y,z,t,h,l) q c d )) = z
getVelocidade (Ghost (GhoState (x,y,z,t,h,l) q )) = z

-- | iterações do jogador
velocidade :: Double -> Player -> State -> State
velocidade v player s = if v>1 then velocidade (v-1) player (play (getNextMove (player)) s) else (play (getNextMove (player)) s)

