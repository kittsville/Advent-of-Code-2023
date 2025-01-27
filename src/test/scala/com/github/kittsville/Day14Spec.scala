package com.github.kittsville

class Day14Spec extends munit.FunSuite {
  test("Calculates single rock load with no rolling") {
    val platform =
      """#.
        |O#""".stripMargin

    assertEquals(Day14Solution.rolledLoad(platform), 1)
  }

  test("Calculates rock load from multiple rocks with no rolling") {
    val platform =
      """#.O
        |O##""".stripMargin

    assertEquals(Day14Solution.rolledLoad(platform), 3)
  }

  test("Calculates the rock load from a single rolled rock") {
    val platform =
      """#..
        |.#.
        |O##""".stripMargin

    assertEquals(Day14Solution.rolledLoad(platform), 2)
  }

  test("Calculates the rock load when a rock rolls to the edge") {
    val platform =
      """...
        |.#.
        |O##""".stripMargin

    assertEquals(Day14Solution.rolledLoad(platform), 3)
  }

  test("Calculates the rock load when multiple rocks pile against each other") {
    val platform =
      """...
        |O#.
        |O##""".stripMargin

    assertEquals(Day14Solution.rolledLoad(platform), 5)
  }

  test("Calculates the rock load from a variety of rocks") {
    val platform =
      """O....#....
        |O.OO#....#
        |.....##...
        |OO.#O....O
        |.O.....O#.
        |O.#..O.#.#
        |..O..#O..O
        |.......O..
        |#....###..
        |#OO..#....""".stripMargin

    assertEquals(Day14Solution.rolledLoad(platform), 136)
  }
}
