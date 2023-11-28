module Tarefa3 where
import Types

-- Tarefa3
{- |

O objetivo desta tarefa é converter um labirinto válido numa sequência de instruções de modo a recriá-lo
num formato mais compacto para leitura.
A equipa decidiu percorrer o labirinto linha a linha para elaborar esta tarefa. Primeiramente, cada linha do
labirinto era tranformada numa instrução, assim foi, com esse objetivo que definimos a função compactCorredor.
Para tornar esta tarefa não só correta mas também óptima, adotamos uma estratégia mais compacta e económica
no número de instruções, tal como foi sugerido no guião da respetiva tarefa. Como tal, para evitar a ocorrência
de peças iguais no mesmo corredor, criamos a função element, para testar se existiam padrões iguais.
Em caso afirmativo, com a função sameCorridor, é possível indicar ao interpretador que aplique ao corredor x
o mesmo formato aplicado ao corredor y. Colocando uma instrução: Repeat y.
Por fim, a função final faz com que a função sameCorridor seja aplicada a todo o labirinto, corredor a
corredor.

-}

-- | Transforma cada peça de um corredor em instrução 
compactCorredor :: Corridor -> Instruction
compactCorredor [] = Instruct []
compactCorredor (x:xs) = Instruct (aux 1 x xs)
     where aux n l [] = [(n,l)]
           aux n l (h:ht)
                   | l == h = aux (n+1) h ht
                   | otherwise = (n,l) : aux 1 h ht

-- | Testa se as instruções são iguais. Se são iguais então dá verdade, de outra forma dá falso
element :: Instruction -> Instruction -> Bool
element _ _ = False
element (Instruct []) (Instruct []) = True
element (Instruct ((a,b):c)) (Instruct ((d,e):f)) = if ((a==d) && (b==e)) then element (Instruct c) (Instruct f) else False

-- | Usando a função element, quando as instruções são iguais, então repete. De outra forma cria uma nova instrução
sameCorridor :: Instruction -> Instructions -> Int -> Instructions
sameCorridor a [] b = []
sameCorridor a (x:xs) b
              | element a x = Repeat b: sameCorridor a xs b
              | otherwise = x: sameCorridor a xs b



sameCorridor' :: Instructions -> Int -> Instructions
sameCorridor' [] x = []
sameCorridor' (a:b) c = a : sameCorridor' y (c+1)
                          where y = sameCorridor a b c


-- | Função final na qual a função sameCorridor é aplicada a todos os corredores
compactMaze :: Maze -> Instructions
compactMaze m = sameCorridor' (map compactCorredor m) 0