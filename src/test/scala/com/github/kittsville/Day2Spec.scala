package com.github.kittsville

class Day2Spec extends munit.FunSuite {
  test("Identifies an invalid game with one draw") {
    assertEquals(Day2Solution.validGame("99 green"), false)
  }

  test("Identifies a valid game with one draw") {
    assertEquals(Day2Solution.validGame("13 green"), true)
  }

  test("Identifies an invalid game with multiple cube types in one draw") {
    assertEquals(Day2Solution.validGame("10 red, 99 green"), false)
  }

  test("Identifies a valid game with multiple cube types in one draw") {
    assertEquals(Day2Solution.validGame("10 red, 13 green"), true)
  }

  test(
    "Identifies an invalid game with multiple cube types in multiple draws"
  ) {
    val input = "10 red, 13 green; 5 blue, 99 green"
    assertEquals(Day2Solution.validGame(input), false)
  }

  test(
    "Identifies a valid game with multiple cube types in multiple draws"
  ) {
    val input = "10 red, 12 green; 14 blue, 13 green"
    assertEquals(Day2Solution.validGame(input), true)
  }

  test("sum IDs when no games are possible") {
    val input = """Game 1: 99 green"""
    assertEquals(Day2Solution.gamePossibleIds(input), 0)
  }

  test("count single IDs when game is possible") {
    val input = """Game 1: 10 red, 12 green; 14 blue, 13 green"""
    assertEquals(Day2Solution.gamePossibleIds(input), 1)
  }

  test("count multiple IDs when multiple games are possible") {
    val input = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                  |Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue""".stripMargin
    assertEquals(Day2Solution.gamePossibleIds(input), 3)
  }

  test("sum successfull games' IDs ignoring failed games") {
    val input = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                  |Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                  |Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                  |Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                  |Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".stripMargin
    assertEquals(Day2Solution.gamePossibleIds(input), 8)
  }
}
