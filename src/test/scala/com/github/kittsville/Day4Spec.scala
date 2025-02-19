package com.github.kittsville

class Day4Spec extends munit.FunSuite {
  test("Count points of a worthless scratchcard") {
    assertEquals(Day4Solution.points("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"), 0)
  }

  test("Count points of single matching number") {
    assertEquals(Day4Solution.points("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"), 1)
  }

  test("Count points of multiple matching numbers, doubling the score each time") {
    assertEquals(Day4Solution.points("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"), 8)
  }

  test("Count points of multiple cards") {
    val input = """Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                  |Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                  |Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                  |Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                  |Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                  |Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""".stripMargin
    assertEquals(Day4Solution.points(input), 13)
  }

  test("Count zero additionally won scratchcards") {
    assertEquals(Day4Solution.winningScratchcards("Card 1: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"), 1)
  }

  test("Count a single winning scratchcard") {
    val input = """Card 1: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                  |Card 2: 87 83 26 28 32 | 88 30 70 12 93 22 82 36""".stripMargin
    assertEquals(Day4Solution.winningScratchcards(input), 3)
  }

  test("Count multiple winning scratchcards") {
    val input = """Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                  |Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                  |Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                  |Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                  |Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                  |Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""".stripMargin
    assertEquals(Day4Solution.winningScratchcards(input), 30)
  }

  test("Count multiple winning scratchcards") {
    val input = """Card 1:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                  |Card 2: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                  |Card 3: 87 83 26 28 32 | 88 30 70 12 93 22 82 36""".stripMargin
    assertEquals(Day4Solution.winningScratchcards(input), 7)
  }

}
