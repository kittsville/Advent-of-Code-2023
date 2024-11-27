package com.github.kittsville

class Day1Spec extends munit.FunSuite {
  test("parse digits from edges of input") {
    val input = """1derp2"""
    assertEquals(Day1Solution.calibrationSum(input), 12)
  }

  test("parse digits from non-edge of input") {
    val input = """a2derp3b"""
    assertEquals(Day1Solution.calibrationSum(input), 23)
  }

  test("parse digits from non-edge of input, ignoring irrelevant digits") {
    val input = """a36derp54b"""
    assertEquals(Day1Solution.calibrationSum(input), 34)
  }

  test("parse digits spelled with words") {
    val input = """afour36derp54fiveb"""
    assertEquals(Day1Solution.calibrationSum(input), 45)
  }

  test("sum multiple rows") {
    val input = """a16derp52b
                  |3bc4""".stripMargin
    assertEquals(Day1Solution.calibrationSum(input), 46)
  }
}
