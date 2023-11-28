module Main where

import Data.Time.Clock.POSIX
import Control.Monad.IO.Class
import UI.NCurses
import Types
import FileUtils
import Tarefa1
import Tarefa2
import Tarefa3
import Tarefa4
import Tarefa5
import Tarefa6


data Manager = Manager 
    {   
        state   :: State
    ,    pid    :: Int
    ,    step   :: Int
    ,    before :: Integer
    ,    delta  :: Integer
    ,    delay  :: Integer
    } deriving Show

isPac :: Player -> Bool
isPac (Pacman _) = True
isPac _ = False

isPacV2 :: Piece -> Bool
isPacV2 (PacPlayer (Pacman _)) = True
isPacV2 _ = False

getPac :: [Player] -> Coords
getPac [] = (0,0)
getPac (x:xs) | isPac x == True = getPlayerCoords x
              | otherwise = getPac xs

getPacmanCoords :: State -> Coords
getPacmanCoords (State maze x l) = getPac x

loadManager :: Manager
loadManager = (Manager et 0 0 0 0 defaultDelayTime )

changePacOr :: Int -> Player -> Player
changePacOr 1 (Pacman (PacState (id,(a,b),s,o,p,v) t m e)) = (Pacman (PacState (id,(a,b),s,R,p,v) t m e))
changePacOr 2 (Pacman (PacState (id,(a,b),s,o,p,v) t m e)) = (Pacman (PacState (id,(a,b),s,L,p,v) t m e))
changePacOr 3 (Pacman (PacState (id,(a,b),s,o,p,v) t m e)) = (Pacman (PacState (id,(a,b),s,U,p,v) t m e))
changePacOr 4 (Pacman (PacState (id,(a,b),s,o,p,v) t m e)) = (Pacman (PacState (id,(a,b),s,D,p,v) t m e))

changeStateOr :: Int -> [Player] -> [Player]
changeStateOr n [] = []
changeStateOr n (x:xs) | isPac x = (changePacOr n x) : changeStateOr n xs
                       | otherwise = x : changeStateOr n xs

updateControlledPlayer :: Key -> Manager -> Manager
updateControlledPlayer KeyUpArrow (Manager (State maze (x:xs) l) pid step before delta delay) = (Manager (State maze (changeStateOr 3 (x:xs)) l) pid step before delta delay)
updateControlledPlayer KeyDownArrow (Manager (State maze (x:xs) l) pid step before delta delay) = (Manager (State maze (changeStateOr 4 (x:xs)) l) pid step before delta delay)
updateControlledPlayer KeyRightArrow (Manager (State maze (x:xs) l) pid step before delta delay) = (Manager (State maze (changeStateOr 1 (x:xs)) l) pid step before delta delay)
updateControlledPlayer KeyLeftArrow (Manager (State maze (x:xs) l) pid step before delta delay) = (Manager (State maze (changeStateOr 2 (x:xs)) l) pid step before delta delay)

updateScreen :: Window  -> ColorID -> Manager -> Curses ()
updateScreen w a man =
                  do
                    updateWindow w $ do
                      clear
                      setColor a
                      moveCursor 0 0 
                      drawString $ show (state man)
                    render
     
currentTime :: IO Integer
currentTime = fmap ( round . (* 1000) ) getPOSIXTime

updateTime :: Integer -> Manager -> Manager
updateTime now (Manager state pid step before delta delay) = (Manager state pid step before (now - before) delay)

resetTimer :: Integer -> Manager -> Manager
resetTimer now (Manager state pid step before delta delay) = (Manager state pid 0 0 0 delay)

nextFrame :: Integer -> Manager -> Manager
nextFrame now (Manager state pid step before delta delay) = (Manager (passTime step (state)) pid (step + 1) now 0 delay)

loop :: Window -> Manager -> Curses ()
loop w man@(Manager s pid step bf delt del ) = do 
  color_schema <- newColorID ColorBlue ColorWhite  10
  now <- liftIO  currentTime
  updateScreen w  color_schema man
  if ( delt > del )
    then loop w $ nextFrame now man
    else do
          ev <- getEvent w $ Just 0
          case ev of
                Nothing -> loop w (updateTime now man)
                Just (EventSpecialKey arrow ) -> loop w $ updateControlledPlayer arrow (updateTime now man)
                Just ev' ->
                  if (ev' == EventCharacter 'q')
                    then return ()  
                    else loop w (updateTime now man)

main :: IO ()
main =
  runCurses $ do
    setEcho False
    setCursorMode CursorInvisible
    w <- defaultWindow
    loop w loadManager

