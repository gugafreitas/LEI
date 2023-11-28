
{-|
Module      : Tarefa1
Description : Módulo haskell relativo à Tarefa1.
Copyright   : José Carlos Gonçalves Braz <a96168@alunos.uminho.pt>
              Gonçalo Lobo Freitas <a96146alunos.uminho.pt>


O objetivo desta tarefa é implementar um mecanismo de geração de labirintos. Os inputs serão o seu comprimento, largura e seed (mecanismo usado de forma a haver aleatoriadade na estrutura do labirinto.)

-}

module Tarefa1 where
import System.Random
import Types
import Data.List


{- 

== Um exemplo de um labirinto:

@

sampleMaze :: Maze


sampleMaze = [
                [Wall, Wall, Wall, Wall, Wall, Wall, Wall, Wall],
                [Empty, Food Little, Food Little, Food Big, Food Little, Food Big, Food Little, Empty],
                [Wall, Wall, Wall, Wall, Wall, Wall, Wall, Wall]
            ]

@

-}



-- | Given a seed returns a list of n integer randomly generated
geraAleatorios :: Int -> Int -> [Int]
geraAleatorios n seed = let gen = mkStdGen seed -- creates a random generator
                        in take n $ randomRs (0,99) gen -- takes the first n elements from an infinite series of random numbers between 0-9


-- | Através de uma seed, apresenta um número aleatório.
nrAleatorio :: Int -> Int
nrAleatorio seed = head $ geraAleatorios 1 seed


-- | Converte uma lista numa lista de listas de tamanho n.
subLista :: Int -> [a] -> [[a]]
subLista _ [] = []
subLista n l = take n l: subLista n (drop n l)


-- | Apresenta peça consoante o número apresentado
convertePeca :: Int -> Piece
convertePeca  x
    | x == 3 = Food Big
    | x >= 0 && x<70 = Food Little
    | x >= 70 && x <= 99 = Wall

-- | Converte uma lista de inteiros num corredor
converteCorredor:: [Int] -> Corridor  
converteCorredor []=[]
converteCorredor (h:t)= convertePeca h : converteCorredor t 

-- | Converte listas de inteiros num Labirinto com paredes em volta
converteMaze :: [[Int]]-> Maze
converteMaze []= []
converteMaze (h:t)= ([Wall]++ converteCorredor h ++[Wall]): converteMaze t

-- | Função auxiliar para execução do labririnto
printCorridor:: Corridor ->String
printCorridor []= "\n"
printCorridor (h:t)= show h ++ printCorridor t


-- | Adiciona paredes ao labirinto-
paredes :: Int-> Corridor 
paredes 0 = []
paredes l = Wall : paredes (l-1)

-- | Define o meio do labirinto
halflab :: Maze -> Int
halflab l = div (length l) 2

-- | Seleciona uma determinada area-
insidelab :: Maze -> Maze
insidelab l | even (length l) = take 2 $ drop ((halflab l)-1) l
            | otherwise = take 1 $ drop (halflab l) l
-- | Adiciona os tuneis
addtunels :: Maze -> Maze
addtunels l | even (length l) = (take ((halflab l)-1) l) ++ (auxaddtunels (insidelab l)) ++ (drop ((halflab l) +1) l)
            | otherwise = (take (halflab l) l) ++ (auxaddtunels (insidelab l)) ++ (drop ((halflab l) +1) l)
-- | Função auxiliar para adicionar os túneis
auxaddtunels :: Maze -> Maze
auxaddtunels [] = []
auxaddtunels (x:xs)= ([Empty]++(init (tail x))++ [Empty]): auxaddtunels xs

-- | A partir dos inputs (3 números inteiros), apresenta um maze com as respetivas restrições impostas por outras funções nela contidas.
bobinsidelab :: Int -> Int -> Int -> Maze 
bobinsidelab l h seed = converteMaze $ subLista l $ geraAleatorios (l*h) seed

-- | Constroi a casa dos fantasmas. 
bobcasafantasmas :: Int -> Maze
bobcasafantasmas l | odd l = [take 11 (repeat Empty)] ++[[Empty] ++ take 3 (repeat Wall) ++ take 3 (repeat Empty) ++ take 3 (repeat Wall)++[Empty]]++[[Empty]++[Wall]++(take 7 (repeat Empty))++[Wall]++ [Empty]]++[[Empty]++ take 9 (repeat Wall)++[Empty]]++[(take 11 (repeat Empty))]
                   | otherwise = [take 10 (repeat Empty)]++[[Empty] ++ take 3 (repeat Wall) ++ take 2 (repeat Empty) ++ take 3 (repeat Wall)++[Empty]]++[[Empty]++[Wall]++ (take 6 (repeat Empty))++[Wall]++ [Empty]]++[[Empty]++ take 8 (repeat Wall)++[Empty]]++[(take 10 (repeat Empty))]

-- | Coloca a casa dos fantasmas na posiçao correta (labirinto par).
putcasaeven :: Maze -> Maze -> Maze
putcasaeven [] [] = []
putcasaeven l [] = []
putcasaeven [] l = []
putcasaeven (x:xs) (y:ys)= ((take (div ((length x)-10) 2) x)++ y ++ (drop((div((length x)-10) 2) +10) x)) : putcasaeven xs ys


-- | Coloca a casa dos fantasmas na posiçao correta (labirinto impar).
putcasaodd :: Maze -> Maze -> Maze
putcasaodd [] [] = []
putcasaodd l [] = []
putcasaodd [] l = []
putcasaodd (x:xs) (y:ys)= ((take (div ((length x)-11) 2) x)++ y ++ (drop((div((length x)-11) 2)+11) x)) : putcasaodd xs ys

{-|
== Preview de como deverá ser a casa dos fantasmas.


@
casaFantasmas :: Int -> Maze
casaFantasmas x 
   |even x = [[Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao],
              [Chao,Parede, Parede, Parede, Chao, Chao, Parede, Parede, Parede,Chao], 
              [Chao,Parede, Chao, Chao, Chao, Chao, Chao, Chao, Parede,Chao],
              [Chao,Parede, Parede, Parede, Parede, Parede, Parede, Parede, Parede,Chao],
              [Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao]]
  |otherwise =  [[Chao,Chao,Chao,Chao,Chao,Chao,Chao,Chao,Chao,Chao,Chao],
                 [Chao,Parede, Parede, Parede, Chao, Chao, Chao, Parede, Parede, Parede,Chao],
                 [Chao,Parede, Chao, Chao, Chao, Chao, Chao, Chao, Chao, Parede,Chao],
                 [Chao,Parede, Parede, Parede, Parede, Parede, Parede, Parede, Parede, Parede,Chao],
                 [Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao, Chao,Chao]]
@

-}
-- | Construçao final da casa dos fantasmas.
casaF :: Maze -> Maze
casaF l | even (length l) && odd (length(head l)) = (take (a-3) l) ++ (putcasaodd (putcasa l) (bobcasafantasmas (length (head l)))) ++ (drop(a+2)l)
        | odd (length l) && odd (length(head l)) =  (take (a-2) l) ++ (putcasaodd (putcasa l) (bobcasafantasmas (length (head l)))) ++ (drop(a+3)l)
        | odd (length l) && even (length(head l)) = (take (a-2) l) ++ (putcasaeven (putcasa l) (bobcasafantasmas (length (head l)))) ++ (drop(a+3)l)
        | otherwise = (take (a-3) l) ++ (putcasaeven  (putcasa l) (bobcasafantasmas (length (head l)))) ++ (drop(a+2)l)   
         where
              a = halflab l
              putcasa l | even (length l)= take 5 (drop((halflab l)-3) l)
                        | otherwise = take 5 (drop ((halflab l)-2) l)

-- | Labirinto final (com casa dos fantasmas e tuneis).
fim :: Int -> Int -> Int -> IO ()
fim x y s | x >= 15 && y >= 10 = putStr $ printMaze $ addtunels $ casaF $ [paredes x] ++ (bobinsidelab (x-2) (y-2) s) ++ [paredes x]
          | x >= 15 && y < 10 = putStr $ printMaze $ addtunels $ casaF $ [paredes x] ++ (bobinsidelab (x-2) (8) s) ++ [paredes x]
          | x <  15 && y >= 10 = putStr $ printMaze $ addtunels $ casaF $ [paredes 15] ++ (bobinsidelab (15-2) (y-2) s) ++ [paredes 15]
          | otherwise = putStr $ printMaze $ addtunels $ casaF $ [paredes 15] ++ (bobinsidelab (13) (8) s) ++ [paredes 15]

-- | Labirinto final em forma de lista.
generateMaze :: Int -> Int -> Int -> Maze
generateMaze x y s | x >= 15 && y >= 10 = addtunels $ casaF $ [paredes x] ++ (bobinsidelab (x-2) (y-2) s) ++ [paredes x]
                   | x >= 15 && y < 10 = addtunels $ casaF $ [paredes x] ++ (bobinsidelab (x-2) (8) s) ++ [paredes x]
                   | x <  15 && y >= 10 = addtunels $ casaF $ [paredes 15]++ (bobinsidelab (15-2) (y-2) s) ++ [paredes 15]
                   | otherwise =  addtunels $ casaF $ [paredes 15] ++ (bobinsidelab (13) (8) s) ++ [paredes 15]