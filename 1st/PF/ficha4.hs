--Tupling (calcular vários resultados numa só travessia da lista)
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Redundant bracket" #-}
import Data.Char

digitAlpha :: String -> (String,String)
digitAlpha "" = ("","")
digitAlpha (h:t)
    | isAlpha h = (  digits, h:letters)
    | isDigit h = (h:digits,   letters)
    | otherwise = (  digits,   letters)
    where (digits,letters) = digitAlpha t

nzp :: [Int] -> (Int,Int,Int)
nzp [] = (0,0,0)
nzp (h:hs)
    | h <0 = (menos+1,zero,mais)
    | h==0 = (menos,zero+1,mais)
    | otherwise = (menos,zero,mais+1)
    where (menos,zero,mais) = nzp hs

divMod :: Integral a => a -> a -> (a, a)
divMod x y
    | x-y<0 = (0,x)
    | otherwise = (q+1,r)
    where (q,r) = Prelude.divMod (x-y) y

fromDigits :: [Int] -> Int
fromDigits [] = 0
fromDigits (h:t) = h*10^(length t) + fromDigits t

