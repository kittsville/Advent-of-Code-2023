package com.github.kittsville

class Day11Spec extends munit.FunSuite {
  val exampleUniverse = """...#......
                            |.......#..
                            |#.........
                            |..........
                            |......#...
                            |.#........
                            |.........#
                            |..........
                            |.......#..
                            |#...#.....""".stripMargin

  test("Converts a string representation of a galaxy to a list of galaxy coordinates") {
    val expectedPositions = Set(
      Position(3, 0),
      Position(7, 1),
      Position(0, 2),
      Position(6, 4),
      Position(1, 5),
      Position(9, 6),
      Position(7, 8),
      Position(0, 9),
      Position(4, 9)
    )

    assertEquals(Day11Solution.parseUniverse(exampleUniverse), expectedPositions)
  }

  test("Expands a basic universe where there is no effect") {
    assertEquals(Day11Solution.expandUniverse(Set(Position(0, 0)), 2), Set(Position(0, 0)))
  }

  test("Expands a basic universe where 1 galaxy is moved") {
    assertEquals(Day11Solution.expandUniverse(Set(Position(1, 1)), 2), Set(Position(2, 2)))
  }

  test("Expands a basic universe where 1 galaxy is moved by a factor of 3") {
    assertEquals(Day11Solution.expandUniverse(Set(Position(2, 2)), 3), Set(Position(6, 6)))
  }

  test("Expands a complex universe by a 10x factor, where multiple galaxies are moved") {
    assertEquals(
      Day11Solution.sumDistancesBetweenPairs(
        Day11Solution.expandUniverse(Day11Solution.parseUniverse(exampleUniverse), 10)
      ),
      1030L
    )
  }

  test("Expands a complex universe where multiple galaxies are moved") {
    val universe = Set(
      Position(0, 0),
      Position(2, 2),
      Position(2, 5),
      Position(5, 2)
    )

    val expectedExpandedUniverse = Set(
      Position(0, 0),
      Position(3, 3),
      Position(3, 8),
      Position(8, 3)
    )
    assertEquals(Day11Solution.expandUniverse(universe, 2), expectedExpandedUniverse)
  }

  test("Sums the total distances between galaxies in a basic universe") {

    val universe = Set(
      Position(1, 1),
      Position(4, 4)
    )

    assertEquals(Day11Solution.sumDistancesBetweenPairs(universe), 6L)
  }

  test("Sums the total distances between galaxies when they exist on the same Y axis") {

    val universe = Set(
      Position(1, 2),
      Position(1, 5)
    )

    assertEquals(Day11Solution.sumDistancesBetweenPairs(universe), 3L)
  }

  test("Sums the total distances between galaxies when they exist on the same X axis") {

    val universe = Set(
      Position(2, 1),
      Position(5, 1)
    )

    assertEquals(Day11Solution.sumDistancesBetweenPairs(universe), 3L)
  }

  test("Sums the total distances between multiple galaxies") {

    val universe = Set(
      Position(1, 1),
      Position(1, 5),
      Position(5, 1)
    )

    assertEquals(Day11Solution.sumDistancesBetweenPairs(universe), 16L)
  }

  test("Sums the total distances between galaxies in a complex universe") {
    val rawUniverse = """....#........
                        |.........#...
                        |#............
                        |.............
                        |.............
                        |........#....
                        |.#...........
                        |............#
                        |.............
                        |.............
                        |.........#...
                        |#....#.......""".stripMargin
    val universe = Day11Solution.parseUniverse(rawUniverse)

    assertEquals(Day11Solution.sumDistancesBetweenPairs(universe), 374L)
  }
}
