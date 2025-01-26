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

  test("Two pattern rows of different length are not equal") {
    assertEquals(Day13Solution.equal("##..#######..", "...#....."), Day13.NotEqual)
  }

  test("Two pattern rows of entirely different content are not equal") {
    assertEquals(Day13Solution.equal("##..#######..", "#.#.##.#....."), Day13.NotEqual)
  }

  test("Two pattern rows of identical content are equal") {
    assertEquals(Day13Solution.equal("##..#######..", "##..#######.."), Day13.Equal)
  }

  test("Two pattern rows differing by one character are smudged") {
    assertEquals(Day13Solution.equal("##..#######..", "##..###.###.."), Day13.Smudged)
  }

  test("Finds the smudged horizontal reflection point") {
    assertEquals(Day13Solution.getSmudgedHorizontalReflectionPoint(verticalReflectionPoint), Some(3))
  }

  test("Finds the smudged vertical reflection point") {
    val smugedVertical =
      """#..#..##.
        |..#.##.#.
        |##......#
        |##......#
        |..#.##.#.
        |..##..##.
        |#.#.##.#.""".stripMargin
    assertEquals(Day13Solution.getSmudgedVerticalReflectionPoint(smugedVertical), Some(5))
  }

  test("Doesn't find a smuged horizontal reflection point if the reflection is un-smudged") {
    val horizontalWithNoSmudge =
      """#...##..#
        |#...##..#
        |..##..###
        |#####.##.
        |#####....
        |..##..###
        |#....#..#""".stripMargin

    assertEquals(Day13Solution.getSmudgedHorizontalReflectionPoint(horizontalWithNoSmudge), None)
  }

  test("Doesn't find a smuged vertical reflection point if the reflection is un-smudged") {
    assertEquals(Day13Solution.getSmudgedVerticalReflectionPoint(verticalReflectionPoint), None)
  }
}
