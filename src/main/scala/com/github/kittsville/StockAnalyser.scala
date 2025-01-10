package com.github.kittsville

object StockAnalyserImpl {
  def getMaxProfit(prices: List[Int]): Int = {
    val initialProfit = prices match {
      case first :: second :: tail => Accumulator(first, second - first)
      case prices => throw new IllegalArgumentException("Price list too short to initialise profit calculator")
    }

    prices
      .foldLeft(initialProfit)((acc, currentPrice) => {
        val newLowest = if (currentPrice < acc.lowestPrice) currentPrice else acc.lowestPrice
        val potentialProfit = currentPrice - acc.lowestPrice
        val newMax = if (potentialProfit > acc.maxProfit) potentialProfit else acc.maxProfit

        Accumulator(newLowest, newMax)
      })
      .maxProfit
  }

  private case class Accumulator(lowestPrice: Int, maxProfit: Int)
}

trait StockAnalyser {
  def getMaxProfit(prices: List[Int]): Int
}
