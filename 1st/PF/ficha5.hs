import Data.List 

any' :: (a -> Bool) -> [a] -> Bool
any' f [] = False
any' f (h:t) = f h || any' f t

zipWith' :: (a->b->c) -> [a] -> [b] -> [c]
zipWith' f [] [] = []
zipWith' f (a:as) (b:bs) = f a b : zipWith' f as bs

takeWhile' :: (a->Bool) -> [a] -> [a]
takeWhile' f [] = []
takeWhile' f (h:t)
    | f h = h : takeWhile' f t
    | otherwise = []

dropWhile' :: (a->Bool) -> [a] -> [a]
dropWhile' f [] = []
dropWhile' f (h:t)
    | f h = dropWhile' f t
    | otherwise = h:t

-- deleteBy' :: (a -> a -> Bool) -> a -> [a] -> [a]
-- deleteBy' f 