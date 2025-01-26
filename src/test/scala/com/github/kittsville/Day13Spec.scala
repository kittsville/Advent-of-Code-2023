package com.github.kittsville

class Day13Spec extends munit.FunSuite {
  private val verticalReflectionPoint =
    """#.##..##.
      |..#.##.#.
      |##......#
      |##......#
      |..#.##.#.
      |..##..##.
      |#.#.##.#.""".stripMargin

  private val horizontalReflectionPoint =
    """#...##..#
      |#....#..#
      |..##..###
      |#####.##.
      |#####.##.
      |..##..###
      |#....#..#""".stripMargin

  test("Doesn't find a horizontal reflection point if there isn't one") {
    val rocks = """#..#
                  |.#..""".stripMargin
    assertEquals(Day13Solution.getHorizontalReflectionPoint(rocks), None)
  }

  test("Doesn't find a horizontal reflection point if there there's an incomplete one") {
    assertEquals(Day13Solution.getHorizontalReflectionPoint(verticalReflectionPoint), None)
  }

  test("Doesn't find a vertical reflection point if there isn't one") {
    assertEquals(Day13Solution.getVerticalReflectionPoint(horizontalReflectionPoint), None)
  }

  test("Finds the horizontal reflection point") {
    assertEquals(Day13Solution.getHorizontalReflectionPoint(horizontalReflectionPoint), Some(4))
  }

  test("Finds a horizontal reflection point at the edge of the pattern") {
    val pattern = """#...#.##.
                    |#...#.##.
                    |..#.#....
                    |...#.....
                    |.##.##..#""".stripMargin

    assertEquals(Day13Solution.getHorizontalReflectionPoint(pattern), Some(1))
  }

  test("Finds the vertical reflection point") {
    assertEquals(Day13Solution.getVerticalReflectionPoint(verticalReflectionPoint), Some(5))
  }

  test("Sumarises locations of reflection points") {
    val input = s"$verticalReflectionPoint\n\n$horizontalReflectionPoint\n\n\n"
    assertEquals(Day13Solution.getNotesSummary(input), 405)
  }
}
