{-# LANGUAGE BlockArguments #-}
import Data.Time.Format.ISO8601 (yearFormat)
import GHC.Bits (Bits(xor))
import System.Posix (DL(Null), raiseSignal, c_dlclose)
import Prelude
import GHC.Generics (prec)

--1
enumFromTo' :: Int -> Int -> [Int]
enumFromTo' x y = if x>y then [] else x:enumFromTo' (x+1) y

--2 DIFICL
enumFromThenTo' :: Int -> Int -> Int -> [Int]
enumFromThenTo' x y z 
    | x > z && y >= x || x < z && y < x = []
    | otherwise = x:enumFromThenTo' y (2*y - x) z

--3 DIFICL
maismais :: [a] -> [a] -> [a]
maismais [] l = l
maismais l [] = l
maismais (h:t) l = h : maismais t l

--4
exclam :: [a] -> Int -> a
exclam (h:hs) x
    | x==0 = h
    | otherwise = exclam hs (x-1)

--5
reverse' :: [a] -> [a]
reverse' [] = []
reverse' (x:xs) = reverse' xs ++ [x]

--6
take' :: Int -> [a] -> [a]
take' _ [] = []
take' 0 l = []
take' x (h:hs) = h : take' (x-1) hs

--7
drop' :: Int -> [a] -> [a]
drop' _ [] = []
drop' 0 l = l
drop' x (h:hs) = drop' (x-1) hs

--8
zip' :: [a] -> [b] -> [(a,b)]
zip' _ [] = []
zip' [] _ = []
zip' (x:xs) (h:hs) = (x,h) : zip' xs hs

--9
replicate' :: Int -> a -> [a]
replicate' 0 _ = []
replicate' n x
    | n < 0 = []
    | otherwise = x: replicate' (n-1) x

--10
intersperse' :: a -> [a] -> [a]
intersperse' _ [] = []
intersperse' x [y] = [y]
intersperse' x (h:hs) = [h,x] ++ intersperse' x hs

--11
group' :: Eq a => [a] -> [[a]]
group' [] = []
group' [x] = [[x]]
group' (h:t) 
    | elem h (head r) = (h:(head r)):tail r
    | otherwise = [h] : r
    where r = group' t

--12   DIFICIL
concat' :: [[a]] -> [a]
concat' [] = []
concat' (l:ls) = l ++ concat' ls

--13  DIFICIL
inits :: [a] -> [[a]]
inits [] = [[]]
inits l = inits (init l) ++ [l]

--14   DIFICIL
tails :: [a] -> [[a]]
tails [] = [[]]
tails l = l : tails (tail l)

--15  DIFICIL
heads :: [[a]] -> [a]
heads [] = []
heads ([]:t) = heads t
heads (l:ls) = (head l) : heads ls

--16  DIFICIL
total :: [[a]] -> Int
total [] = 0
total ([]:t) = total t
total (h:t) = length h + total t

--17  DIFICIL
fun :: [(a,b,c)] -> [(a,c)]
fun [] = []
fun ((a,b,c):t) = (a,c) : fun t

--18
cola :: [(String,b,c)] -> String
cola [] = ""
cola ((a,b,c):d) = a ++ cola d

--19
idade :: Int -> Int -> [(String,Int)] -> [String] 
idade _ _ [] = []
idade a b ((c,d):t) = if (a-d)>=26 then c:idade a b t else idade a b t

--20    ERRADA
powerEnumFrom :: Int -> Int -> [Int]
powerEnumFrom n 0 = [1]
powerEnumFrom n m = n^0 : powerEnumFrom (n+1) m

-- --21
-- isPrime :: Int -> Bool
-- isPrime x = if 2 <= m <= sqrt x && mod x m == 0 then True else False 

-- isPrime x = if sqrt x - 2 > 0 && mod x (sqrt x)-2 == 0 then True else is prime 

--22  DIFICIL
isPrefixOf' :: Eq a => [a] -> [a] -> Bool
isPrefixOf' _ [] = False
isPrefixOf' [] _ = True
isPrefixOf' (h:t) (h':t') = h==h' && isPrefixOf' t t'

--23  DIFICIL
-- isSuffixOf' :: Eq a => [a] -> [a] -> Bool
-- isSuffixOf' _ [] = False
-- isSuffixOf' [] _ = True
-- isSuffixOf' 

--24   REVER
isSubsequenceOf' :: Eq a => [a] -> [a] -> Bool
isSubsequenceOf' _ [] = False
isSubsequenceOf' [] _ = True
isSubsequenceOf' (h:t) (h':t') = h == h' && isSubsequenceOf' t t' || isSubsequenceOf' (h:t) t'

--25   REVER FUNÇÂO MAP
elemIndices' :: Eq a => a -> [a] -> [Int]
elemIndices' _ [] = []
elemIndices' x (h:t) 
    | x==h = 0: map(+1) (elemIndices' x t)
    | otherwise = map(+1) (elemIndices' x t)

--26
nub' :: Eq a => [a] -> [a]
nub' [] = []
nub' (h:t) = if h `elem` t then nub' t else h:nub' t

--27
delete' :: Eq a => a -> [a] -> [a]
delete' _ [] = []
delete' x (h:t) = if x==h then t else h:delete' x t

--28
remove :: Eq a => [a] -> [a] -> [a]
remove l [] = l
remove [] _ = []
remove l (h:t) = remove (delete' h l) t

--29
union' :: Eq a => [a] -> [a] -> [a]
union' l [] = l
union' l (h:t)
    | h `elem` l = union' l t
    | otherwise = union' (l++[h]) t

--30
intersecte :: Eq a => [a] -> [a] -> [a]
intersecte [] _ = []
intersecte l [] = []
intersecte (h:t) l
    | h `elem` l = h:intersecte t l
    | otherwise = intersecte t l

--31
insert' :: Ord a => a -> [a] -> [a]
insert' x [] = [x]
insert' x (h:t)
    | x<=h = x:h:t
    | otherwise = h:insert' x t

--32
unwords' :: [String] -> String
unwords' [] = ""
unwords' (h:t) = h ++ (if null t then "" else " ") ++ unwords' t

--33
unlines' :: [String] -> String
unlines' (h:t) = h ++ "\n" ++ unlines' t

--34
-- pMaior :: Ord a => [a] -> Int
-- pMaior [_] = 0
-- pMaior (h:hs)
--     | h>=()

--35
-- lookup' :: Eq a => a -> [(a,b)] -> Maybe b

--36
preCrescente :: Ord a => [a] -> [a]
preCrescente [] = []
preCrescente [x] = [x]
preCrescente (h:s:hs)
    | s >= h = h:preCrescente (s:hs)
    | otherwise = [h]

--37
iSort :: Ord a => [a] -> [a]
iSort [] = []
iSort [x] = [x]
iSort (h:s:hs) = if h>s then iSort(insert' h (s:hs)) else h:iSort (s:hs)

--38
menor :: String -> String -> Bool
menor _ "" = False
menor "" _ = True
menor (h:t) (h':t')
    | h<h' = True
    | h==h' = menor t t'
    | otherwise = False

--39
elemMSet :: Eq a => a -> [(a,Int)] -> Bool
elemMSet _ [] = False
elemMSet x ((a,b):c)
    | x == a = True
    | otherwise = elemMSet x c

--40
converteMSet :: [(a,Int)] -> [a]
converteMSet [] = []
converteMSet ((x,1):xs) = x:converteMSet xs
converteMSet ((x,n):xs) = x:converteMSet ((x,n-1):xs)

--41
insereMSet :: Eq a => a -> [(a,Int)] -> [(a,Int)]
insereMSet x [] = [(x,1)]
insereMSet x ((a,b):c)
    | x==a = ((a,b+1):c)
    | otherwise = [(a,b)]++insereMSet x c

--42
removeMSet :: Eq a => a -> [(a,Int)] -> [(a,Int)]
removeMSet _ [] = []
removeMSet x ((a,b):c)
    | x==a = c 
    | otherwise = [(a,b)] ++ removeMSet x c

--43
constroiMSet :: Ord a => [a] -> [(a,Int)]
constroiMSet [] = []
constroiMSet (h:hs) = insereMSet h (constroiMSet hs)