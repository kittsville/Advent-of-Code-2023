package com.github.kittsville

class Day9Spec extends munit.FunSuite {
  test("Can identify the next digit in a sequence that doesn't change") {
    assertEquals(Day9Solution.nextInSequence(List(9, 9, 9)), 9)
  }

  test("Can identify the next digit in a simple incrementing sequence") {
    assertEquals(Day9Solution.nextInSequence(List(1, 2, 3)), 4)
  }

  test("Can identify the next digit in a sequence incrementing its rate of change") {
    assertEquals(Day9Solution.nextInSequence(List(1, 2, 4, 7)), 11)
  }

  test("Can identify the next digit in a sequence with multiple levels of rate of change") {
    assertEquals(Day9Solution.nextInSequence(List(10, 13, 16, 21, 30, 45)), 68)
  }

  test("Parses a list of sequences then identifies and sums the next number in each sequence") {
    val input = """0 3 6 9 12 15
                  |1 3 6 10 15 21
                  |10 13 16 21 30 45
                  |
                  |
                  |""".stripMargin

    assertEquals(Day9Solution.sumOfNextInSequences(input), 114)
  }
}
