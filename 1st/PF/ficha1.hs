import Prelude
import Distribution.Simple.Utils (xargs)
import Trace.Hpc.Mix (BoxLabel)
import Data.Char

--ex1a
perimetro :: Float -> Float
perimetro x = 2*pi*x

--ex1b
dist :: (Double,Double) -> (Double,Double) -> Double
dist (x1,y1) (x2,y2) = sqrt ((x2-x1)^2 + (y2-y1)^2)

--ex1c
--primUlt :: [Int] -> (Int,Int)
--primUlt h:hs =

--ex1d
multiplo :: Int -> Int -> Bool
multiplo x y = div x y  > 0

--ex1e
truncaImpar :: [Int] -> [Int]
truncaImpar l = if odd(length l) then tail l else l

--ex1f
max2 :: Int -> Int -> Int
max2 x y = if x>y then x else y

--ex1g
max3 :: Int -> Int -> Int -> Int
max3 x y z = (max2(max2 x y)z)

--ex2a
nRaizes :: (Double,Double,Double) -> Int
nRaizes (a,b,c) 
    | (b ^ 2 - 4 * a * c) > 0 = 2
    | (b ^ 2 - 4 * a * c) < 0 = 0
    | otherwise = 1

--ex2b
raizes :: (Double,Double,Double) -> [Double]
raizes (a,b,c) 
    | n==2 = [(-b+sqrt(b^2-4*a*c))/2*a,(-b-sqrt(b^2-4*a*c))/2*a]
    | n==1 = [-b/2*a]
    | n==0 = []
    where n=nRaizes(a,b,c)

--ex3a
type Hora = (Int,Int)

horaValida :: Hora -> Bool
horaValida (x,y) = (x>=0 && x<=23) && (y>=0 && x<=59)

--ex3b
horaDepois :: Hora -> Hora -> Bool
horaDepois (h1,m1) (h2,m2) = h1>h2 || (h1==h2 && m1>m2)

--ex3c
converteHorasMinutos :: Hora -> Int
converteHorasMinutos (h,m) = h*60 + m

--ex3d
converteMinutosHoras :: Int -> Hora
converteMinutosHoras x = divMod x 60

--ex3e
diferencaHoras :: Hora -> Hora -> Int
diferencaHoras (h1,m1) (h2,m2) = abs h * 60 + abs m1-m2
    where h=h1-h2 

--ex3f
adicionarMinutos :: Hora -> Int -> Hora
adicionarMinutos (h,m) n = (h+hr,mn)
    where (hr,mn) = converteMinutosHoras (m+n)


data Semaforo = Verde | Amarelo | Vermelho deriving (Show,Eq)

--ex5a
next :: Semaforo -> Semaforo
next x 
    | x==Verde = Amarelo
    | x==Vermelho = Verde
    | otherwise=Vermelho

--ex5b
stop :: Semaforo -> Bool 
stop x = x==Vermelho

--ex5c
safe :: Semaforo -> Semaforo -> Bool
safe x y  = (x==Vermelho && y==Verde) || (x==Verde && y==Vermelho) || (x==Vermelho && y==Vermelho)

--ex8a
--isLower' :: Char -> Bool
--isLower' x = ord(x) > 96 && ord(x) < 123
