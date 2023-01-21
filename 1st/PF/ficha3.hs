import Prelude
import Distribution.Simple.Utils (xargs)
import Trace.Hpc.Mix (BoxLabel)
import Data.Char
import System.Posix (DL(Null))

--3
-- data Contacto = Casa Integer | Trab Integer | Tlm Integer | Email String deriving Show

-- type Nome = String
-- type Agenda = [(Nome, [Contacto])]

-- acrescEmail :: Nome -> String -> Agenda -> Agenda
-- acrescEmail nome email [] = [(nome, [email])]
-- acrescEmail nome email ((n,cs):t)
--     | nome == n = (n,email : cs) : t
--     | otherwise = (n,cs) : acrescEmail nome email t

-- verEmails :: Nome -> Agenda -> Maybe [String]
-- verEmails x [] = Nothing
-- verEmails nome ((n,cs):t)
--     | nome == n = Just (soEmails cs)
--     | otherwise = verEmails nome t

-- soEmails :: [Contacto] -> [String]
-- soEmails [] = []
-- soEmails (Email e : t) = e : soEmails t
-- soEmails (_:t) = soEmails t


-- -- consTelefs :: [Contacto] -> [Integer] 
-- -- consTelefs [] = []
-- -- consTelefs (Email _:xs) = consTelefs xs
-- -- consTelefs (x:xs) = x : consTelefs xs 

-- casa :: Nome -> Agenda -> Maybe Integer 
-- casa _ [] = Nothing
-- casa nome ((x:xs):t)
--     | nome == x = numCasa (xs:t)
--     | otherwise = casa nome t

--4
type Dia = Int
type Mes = Int
type Ano = Int
type Nome = String
data Data = D Dia Mes Ano deriving Show

type TabDN = [(Nome,Data)]

procura :: Nome -> TabDN -> Maybe Data
procura _ [] = Nothing
procura nome ((x,xs):h)
    | nome == x = Just xs
    | otherwise = procura nome h

idade :: Data -> Nome -> TabDN -> Maybe Int
idade _ _ [] = Nothing
idade (D d m a) nome ((x,D d1 d2 d3):h)
    | nome == x = Just (calculaIdade (D d m a) (D d1 d2 d3))
    | otherwise = idade (D d m a) nome h


calculaIdade :: Data -> Data -> Int
calculaIdade (D d m a) (D d1 d2 d3) = a-d3

anterior :: Data -> Data -> Bool
anterior (D d m a) (D d1 m2 a3)
    | (a3==a && m2==m) = if d1>d then True else False
    | (a3==a && m2/=m) = if m2>m then True else False 
    | a<a3 = False 
    | otherwise = True

--5
data Movimento = Credito Float | Debito Float deriving Show
data Extracto = Ext Float [(Data, String, Movimento)] deriving Show

getValor :: Movimento -> Float
getValor (Credito x) = x
getValor (Debito x) = x

extValor :: Extracto -> Float -> [Movimento]
extValor (Ext si ((_,_,m):m1)) valor = if getValor m > valor then m:extValor (Ext si m1) valor else extValor (Ext si m1) valor


-- retorna informa¸c˜ao relativa apenas aos movimentos cuja descri¸c˜ao esteja inclu´ıda
-- na lista fornecida no segundo parˆametro.
-- filtro :: Extracto -> [String] -> [(Data,Movimento)] 
-- filtro (Ext _ []) _ = []
-- filtro (Ext si ((d,s,m):e)) (h:hs)
--     | s `elem` h = (d,m):filtro (Ext si e) hs
--     | otherwise = filtro (Ext si e) hs