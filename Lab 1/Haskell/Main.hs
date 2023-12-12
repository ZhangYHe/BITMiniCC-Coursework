module Main where

-- import Data.Time.LocalTime   -- 来自 time 库

-- main :: IO ()
-- main = do
--   now <- getZonedTime
--   print now

import Text.Printf
import System.CPUTime

matrixGenerator n =
  let rowIndex n k = [x | x <- [1,2..n]]
  in [rowIndex n k | k <- [1,2..n]]

matMul x y =
    let
        col m = [x|x:xs <- m]
        rights m = [xs|x:xs <- m,length(xs) > 0 ]
        rowMulMat r []  = []
        rowMulMat r m   = sum(zipWith (*) r (col m)):(rowMulMat r (rights m))
    in case x of
        [r]     -> [rowMulMat r y]
        (r:rs)  -> (rowMulMat r y):(matMul rs y)

main = do
  let matrix_one = matrixGenerator 10
  let matrix_two = matrixGenerator 10

  startTime <- getCPUTime
  let product = matMul matrix_one matrix_two
  print product
  endTime <- getCPUTime

  let totalTime = (fromIntegral(endTime - startTime))/ (10 ^ 6)
  printf "time: %f ms\n" (totalTime :: Double)


