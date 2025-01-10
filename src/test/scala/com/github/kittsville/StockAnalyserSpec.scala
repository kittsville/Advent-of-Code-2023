package com.github.kittsville

class StockAnalyserSpec extends munit.FunSuite {
  test("Errors on too few price values") {
    interceptMessage[java.lang.IllegalArgumentException]("Price list too short to initialise profit calculator") {
      StockAnalyserImpl.getMaxProfit(List(1))
    }
  }

  test("Returns the max profit from a basic price increase") {
    assertEquals(StockAnalyserImpl.getMaxProfit(List(1, 2)), 1)
  }

  test("Returns the max profit from a fluxtuating price") {
    assertEquals(StockAnalyserImpl.getMaxProfit(List(10, 7, 5, 8, 11, 9)), 6)
  }

  test("Returns the max profit when a new lowest price becomes available") {
    assertEquals(StockAnalyserImpl.getMaxProfit(List(10, 7, 5, 8, 11, 9, 4, 11)), 7)
  }

  test("Does not discard more valueable profits from a new but unusable lowest prices") {
    assertEquals(StockAnalyserImpl.getMaxProfit(List(10, 7, 5, 8, 11, 9, 4, 7)), 6)
  }
}
