package com.github.kittsville

class Day3Spec extends munit.FunSuite {
  test("Parse a schematic with no parts") {
    assertEquals(Day3Solution.parsePartPositions("467..114.."), Set.empty[Position])
  }

  test("Parse a schematic with parts") {
    assertEquals(Day3Solution.parsePartPositions("...$.*...."), Set(Position(3, 0), Position(5, 0)))
  }

  test("Parse a multi-line schematic with parts") {
    val input = """......#...
                  |617*......""".stripMargin
    assertEquals(Day3Solution.parsePartPositions(input), Set(Position(6, 0), Position(3, 1)))
  }

  test("Identify part numbers in a schematic with none") {
    assertEquals(Day3Solution.partNumbers("......#..."), List.empty[Int])
  }

  test("Identify part numbers in a schematic with adjacent parts on the same line") {
    assertEquals(Day3Solution.partNumbers("617*......"), List(617))
  }

  test("Identify part numbers in a schematic with adjacent parts on different lines") {
    val input = """467..114..
                  |...*......
                  |..35..633.
                  |......#...
                  |617*......
                  |.....+.58.
                  |..592.....
                  |......755.
                  |...$.*....
                  |.664.598..""".stripMargin
    assertEquals(Day3Solution.partNumbers(input), List(467, 35, 633, 617, 592, 755, 664, 598))
  }

  test("Calculate the sum of part numbers") {
    val input = """467..114..
                  |...*......
                  |..35..633.
                  |......#...
                  |617*......
                  |.....+.58.
                  |..592.....
                  |......755.
                  |...$.*....
                  |.664.598..""".stripMargin
    assertEquals(Day3Solution.summedPartNumbers(input), 4361)
  }

  test("Can handle duplicate part numbers") {
    val input = """1..
                  |*..
                  |1.. """.stripMargin
    assertEquals(Day3Solution.summedPartNumbers(input), 2)
  }
}
