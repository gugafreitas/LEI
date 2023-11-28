module Teste where
import Tarefa3
import Types

aMaze1 = [Instruct [(15,Wall)],Instruct [(1,Wall),(12,Food Little),(2,Wall)], Instruct [(1,Wall),(1,Food Little),(11,Empty),(2,Wall)], Instruct [(2,Wall),(1,Empty),(3,Wall),(3,Empty),(3,Wall),(1,Empty),(1,Food Little),(1,Wall)],
          Instruct [(1,Empty),(1,Food Little),(1,Empty),(1,Wall),(7,Empty),(1,Wall),(1,Empty),(1,Food Little),(1,Empty)], Instruct [(1,Empty),(1,Wall),(1,Empty),(9,Wall),(1,Empty),(1,Food Little),(1,Empty)], Repeat 2, Instruct [(1,Wall),(2,Food Little),(1,Wall),(4,Food Little),(1,Wall),(2,Food Little),(4,Wall)],
          Instruct [(1,Wall),(1,Food Big),(2,Food Little),(2,Wall),(2,Food Little),(1,Wall),(3,Food Little),(1,o),(1,Food Little),(1,Wall)], Repeat 0]


aMaze2 = [Instruct [(15,Wall)],Instruct [(1,Wall),(2,Food Little),(2,Wall),(7,Food Little),(1,Wall),(1,Food Little),(1,Wall)], Instruct [(1,Wall),(1,Food Little),(11,Empty),(1,Food Little),(1,Wall)], Instruct [(1,Wall),(1,Food Little),(1,Empty),(3,Wall),(3,Empty),(3,Wall),(1,Empty),(1,Food Little),(1,Wall)],
          Instruct [(1,Empty),(1,Wall),(1,Empty),(1,Wall),(7,Empty),(1,Wall),(1,Empty),(1,Food Little),(1,Empty)], Instruct [(1,Empty),(1,Food Little),(1,Empty),(9,Wall),(1,Empty),(1,Food Little),(1,Empty)], Instruct [(2,Wall),(11,Empty),(2,Wall)], Instruct [(1,Wall),(4,Food Little),(1,Wall),(1,Food Little),(2,Wall),(3,Food Little),(3,Wall)],
          Repeat 1,Repeat 0]


aMaze3 = [Instruct [(15,Wall)],Instruct [(1,Wall),(3,Food Little),(1,Wall),(7,Food Little),(1,Wall),(1,Food Little),(1,Wall)],Instruct [(1,Wall),(1,Food Little),(11,Empty),(1,Food Little),(1,Wall)],Instruct [(1,Wall),(1,Food Little),(1,Empty),(3,Wall),(3,Empty),(3,Wall),(1,Empty),(1,Food Little),(1,Wall)],
          Instruct [(1,Empty),(1,Food Little),(1,Empty),(1,Wall),(7,Empty),(1,Wall),(1,Empty),(1,Food Little),(1,Empty)],Repeat 4,Instruct [(1,Wall),(1,Food Little),(11,Empty),(2,Wall)],Instruct [(2,Wall),(3,Food Little),(1,Wall),(2,Food Little),(1,Wall),(2,Food Little),(1,Wall),(2,Food Little),(1,Wall)],
          Repeat 7,Repeat 0]



testecompactMaze1 :: Instructions -> Bool
testecompactMaze1 (4,4,1) = if (compactMaze 4 4 1) == aMaze1 then True else False


testecompactMaze2 :: (Int,Int,Int) -> Bool
testecompactMaze2 (5,6,9) = if (compactMaze 5 6 9) == aMaze2 then True else False


testecompactMaze3 :: (Int,Int,Int) -> Bool
testecompactMaze3 (7,5,2) = if (compactMaze 7 5 2) == aMaze3 then True else False

