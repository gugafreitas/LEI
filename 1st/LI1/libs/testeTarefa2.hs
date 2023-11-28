module Teste2 where
import Tarefa2



position1 = Pacman (PacState (0,(2,2),1,R,0,1) 0 Open Normal)





positiontest1 :: Play -> State -> State
positiontest1 (Move 0 L) (Pacman (PacState (0,(2,3),1,R,0,1) 0 Open Normal)) | play (Move 0 L) (Pacman (PacState (0,(2,3),1,R,0,1) 0 Open Normal)) == position1 = True
                                                                             | otherwise = False