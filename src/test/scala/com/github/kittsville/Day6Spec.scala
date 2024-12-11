package com.github.kittsville

class Day6Spec extends munit.FunSuite {
  test("Count the number of record beating charge times") {
    assertEquals(Day6Solution.recordBeatersCount(7, 9), 4)
    assertEquals(Day6Solution.recordBeatersCount(15, 40), 8)
    assertEquals(Day6Solution.recordBeatersCount(30, 200), 9)
  }

  test("Parse input and multiple results") {
    val input = """Time:      7  15   30
                  |Distance:  9  40  200""".stripMargin
    assertEquals(Day6Solution.multipliedRecordBeaters(input), 288)
  }
}
