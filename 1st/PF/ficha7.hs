import Data.ByteString (isPrefixOf)
data ExpInt = Const Int | Simetrico ExpInt | Mais ExpInt ExpInt | Menos ExpInt ExpInt | Mult ExpInt ExpInt

calcula :: ExpInt -> Int
calcula (Const num) = num
calcula (Simetrico x) = (- calcula x)
calcula (Mais x y) = calcula x + calcula y
calcula (Menos x y) = calcula x - calcula y
calcula (Mult x y) = calcula x * calcula y

data RTree a = R a [RTree a]

soma :: Num a => RTree a -> a
soma (R x []) = x
soma (R x l) = x + sum(map soma l)

altura :: RTree a -> Int
altura (R x []) = 1
altura (R x l) = 1 + maximum (map altura l)


isPrefixOf' :: Eq a => [a] -> [a] -> Bool
isPrefixOf' [] _ = True
isPrefixOf' _ [] = False
isPrefixOf' (h:hs) (x:xs)
    | h == x = isPrefixOf' hs xs
    | otherwise = False