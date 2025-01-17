package com.github.kittsville

class Day10Spec extends munit.FunSuite {
  test("Converts a string representation of a maze to a matrix") {
    val maze = """S-7
                 |L-J""".stripMargin

    val expected = Vector(
      Vector('S', '-', '7'),
      Vector('L', '-', 'J')
    )

    assertEquals(Day10Solution.parseMaze(maze), expected)
  }

  test("Identifies an out-of-bounds position is not connected to another position") {
    val maze = Day10Solution.parseMaze("""F-7
                                         |L-|""".stripMargin)

    assertEquals(Day10Solution.positionsConnect(maze, Position(2, 1))(Position(2, 2)), false)
  }

  test("Identifies a connection between two positions with linking chars along the X axis") {
    val maze = Day10Solution.parseMaze("""F-7
                                         |L-|""".stripMargin)

    assertEquals(Day10Solution.positionsConnect(maze, Position(0, 1))(Position(1, 1)), true)
  }

  test("Identifies a connection between two positions with linking chars along the Y axis") {
    val maze = Day10Solution.parseMaze("""F-7
                                         |L-|""".stripMargin)

    assertEquals(Day10Solution.positionsConnect(maze, Position(0, 0))(Position(0, 1)), true)
  }

  test("Identifies there's no connection between two positions with non-linking chars along the Y axis") {
    val maze = Day10Solution.parseMaze("""F-7
                                         |L-|""".stripMargin)

    assertEquals(Day10Solution.positionsConnect(maze, Position(1, 0))(Position(1, 1)), false)
  }

  test("Identifies there's no connection between two positions with non-linking chars along the X axis") {
    val maze = Day10Solution.parseMaze("""F-7
                                         |L-|""".stripMargin)

    assertEquals(Day10Solution.positionsConnect(maze, Position(1, 1))(Position(2, 1)), false)
  }

  test("Find the longest distance from the start point") {
    val maze = """.....
                 |.S-7.
                 |.|.|.
                 |.L-J.
                 |.....""".stripMargin

    assertEquals(Day10Solution.furthestDistance(maze), 4)
  }
}
