module Teste1 where
import Tarefa1
import Types



aMaze1 = [[Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall],[Wall,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Wall,Wall],[Wall,Food Little,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Wall,Wall],[Wall,Wall,Empty,Wall,Wall,Wall,Empty,Empty,Empty,Wall,Wall,Wall,Empty,Food Little,Wall],
                     [Empty,Food Little,Empty,Wall,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Wall,Empty,Food Little,Empty],[Empty,Wall,Empty,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Empty,Food Little,Empty],[Wall,Food Little,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Food Little,Wall],[Wall,Food Little,Food Little,Wall,Food Little,Food Little,Food Little,Food Little,Wall,Food Little,Food Little,Wall,Wall,Wall,Wall],
                     [Wall,Food Big,Food Little,Food Little,Wall,Wall,Food Little,Food Little,Wall,Food Little,Food Little,Food Little,Food Big,Food Little,Wall],[Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall]]

aMaze2 = [[Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall],[Wall,Food Little,Food Little,Wall,Wall,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Wall,Food Little,Wall],[Wall,Food Little,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Food Little,Wall],[Wall,Food Little,Empty,Wall,Wall,Wall,Empty,Empty,Empty,Wall,Wall,Wall,Empty,Food Little,Wall],
                     [Empty,Wall,Empty,Wall,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Wall,Empty,Food Little,Empty],[Empty,Food Little,Empty,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Empty,Food Little,Empty],[Wall,Wall,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Wall,Wall],[Wall,Food Little,Food Little,Food Little,Food Little,Wall,Food Little,Wall,Wall,Food Little,Food Little,Food Little,Wall,Wall,Wall],
                     [Wall,Food Little,Food Little,Food Little,Food Little,Food Little,Wall,Food Little,Food Little,Wall,Food Little,Food Little,Food Little,Wall,Wall],[Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall]]


aMaze3 = [[Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall],[Wall,Food Little,Food Little,Food Little,Wall,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Food Little,Wall,Food Little,Wall],[Wall,Food Little,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Food Little,Wall],[Wall,Food Little,Empty,Wall,Wall,Wall,Empty,Empty,Empty,Wall,Wall,Wall,Empty,Food Little,Wall],
                     [Empty,Food Little,Empty,Wall,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Wall,Empty,Food Little,Empty],[Empty,Food Little,Empty,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Empty,Food Little,Empty],[Wall,Food Little,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Empty,Wall,Wall],[Wall,Wall,Food Little,Food Little,Food Little,Wall,Food Little,Food Little,Wall,Food Little,Food Little,Wall,Food Little,Food Little,Wall],
                     [Wall,Wall,Food Little,Food Little,Food Little,Food Little,Wall,Wall,Food Little,Food Little,Food Little,Food Little,Wall,Food Little,Wall],[Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall,Wall]]






testesGenerateMaze1 :: (Int,Int,Int) -> Bool
testesGenerateMaze1 (4,4,1)  | (generateMaze 4 4 1) == aMaze1 = True
                             | otherwise = False

testesGenerateMaze2 :: (Int,Int,Int) -> Bool
testesGenerateMaze2 (5,6,9) | (generateMaze 5 6 9) == aMaze2 = True
                            | otherwise = False


testesGenerateMaze3 :: (Int,Int,Int) -> Bool
testesGenerateMaze3 (7,5,2) | (generateMaze 7 5 2) == aMaze3 = True
                            | otherwise = False