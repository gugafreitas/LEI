import Text.XHtml (emphasize)
data BTree a = Empty | Node a (BTree a) (BTree a) deriving Show

altura :: BTree a -> Int
altura Empty = 0
altura (Node _ l r) = 1 + max (altura l) (altura r)

contaNodos :: BTree a -> Int
contaNodos Empty = 0
contaNodos (Node _ l r) = 1 + contaNodos l + contaNodos r

folhas :: BTree a -> Int
folhas Empty = 0
folhas (Node _ Empty Empty) = 1
folhas (Node _ l r) = folhas l + folhas r

prune :: Int -> BTree a -> BTree a
prune _ Empty = Empty
prune 0 _ = Empty
prune x (Node e l r) = Node e (prune (x-1) l) (prune (x-1) r)

path :: [Bool] -> BTree a -> [a]
path _ Empty = []
path [] (Node e l r) = [e]
path (h:hs) (Node e l r) = e : path hs (if h then r else l)

mirror :: BTree a -> BTree a
mirror Empty = Empty
mirror (Node e l r) = Node e (mirror r) (mirror l)

minimo :: Ord a => BTree a -> a 
minimo (Node e Empty Empty) = e 
minimo (Node e l r) = minimo l

semMinimo :: Ord a => BTree a -> BTree a
semMinimo (Node e Empty r) = r
semMinimo (Node e l r) = Node e (semMinimo l) r

minSmin :: Ord a => BTree a -> (a,BTree a)
minSmin (Node e Empty r) = (e,r)
minSmin (Node e l r) = (minimo l,Node e (semMinimo l) r)


-- Nesta função, depois de remover o elemento, temos de formar uma nova árvore, 
-- pois não podemos ter um nodo vazio. Para isso, removemos o menor elemento do ramo 
-- da direita e colocamos esse elemento onde estava o elemento removido. Desta forma, 
-- a árvore mantém a sua ordem, já que todos os elementos à esquerda continuam a ser
-- mais pequenos e todos os elementos à direita continuam a ser maiores do que o elemento no nodo.
remove :: Ord a => a -> BTree a -> BTree a
remove a Empty = Empty
remove a (Node e l r)
    | a < e = Node e (remove a l) r
    | a > e = Node e l (remove a r)
    | otherwise = Node (minimo (Node e l r)) (semMinimo (Node e l r)) r


type Aluno = (Numero,Nome,Regime,Classificacao)
type Numero = Int
type Nome = String
data Regime = ORD | TE | MEL deriving Show
data Classificacao = Aprov Int | Rep | Faltou deriving Show
type Turma = BTree Aluno -- ´arvore bin´aria de procura (ordenada por n´umero)

inscNum :: Numero -> Turma -> Bool
inscNum _ Empty = False
inscNum n (Node (num,_,_,_) l r) = n == num || inscNum n (if n<num then l else r)


inscNome :: Nome -> Turma -> Bool
inscNome _ Empty = False
inscNome n (Node (_,nome,_,_) l r) = n == nome || inscNome n l || inscNome n r 

trabEst :: Turma -> [(Numero,Nome)]
trabEst Empty = []
trabEst (Node (num,nom,TE,_) l r) = trabEst l ++ [(num,nom)] ++ trabEst r
trabEst (Node _ l r) = trabEst l ++ trabEst r

nota :: Numero -> Turma -> Maybe Classificacao
nota n (Node (num,_,_,class) l r)
    | n == num = Just class
    | n < num = nota n l
    | otherwise nota n r

-- percFaltas :: Turma -> Float
-- percFaltas (Node (num,_,_,class) l r)
--     | class == "FALTOU" = 

