package com.github.kittsville

class Day7Spec extends munit.FunSuite {
  test("Returns the lowest score when all card labels are distinct") {
    assertEquals(Day7Solution.handType("23456"), 0)
  }

  test("Returns score 1 when the cards contains a pair") {
    assertEquals(Day7Solution.handType("A23A4"), 1)
  }

  test("Returns score 2 when the cards contains two pairs") {
    assertEquals(Day7Solution.handType("23432"), 2)
  }

  test(
    "Returns score 3 when 3 cards have the same label, and the remaining 2 cards are each different from any other card in the hand"
  ) {
    assertEquals(Day7Solution.handType("TTT98"), 3)
  }

  test(
    "Returns score 4 when 3 cards have the same label, and the remaining 2 cards share a different label"
  ) {
    assertEquals(Day7Solution.handType("23332"), 4)
  }

  test(
    "Returns score 5 when 4 cards have the same label"
  ) {
    assertEquals(Day7Solution.handType("AA8AA"), 5)
  }

  test(
    "Returns score 6 when all 5 cards have the same label"
  ) {
    assertEquals(Day7Solution.handType("AAAAA"), 6)
  }

  test("Can compare two Hands by type") {
    assertEquals(Hand("AAAAA").compare(Hand("AA8AA")), 1)
    assertEquals(Hand("23332").compare(Hand("AA8AA")), -1)
  }

  test("Can compare two hands by each card's strength") {
    assertEquals(Hand("33332").compare(Hand("2AAAA")), 1)
    assertEquals(Hand("77788").compare(Hand("77888")), -1)
  }

  test("Fail if two hands are exactly equal") {
    interceptMessage[java.lang.IllegalArgumentException]("Exhausted cards from compared hands and all were equal") {
      Hand("33332").compare(Hand("33332"))
    }
  }

  test("Calculate the total winnings for a list of hands") {
    val input = """32T3K 765
                  |T55J5 684
                  |KK677 28
                  |KTJJT 220
                  |QQQJA 483""".stripMargin
    assertEquals(Day7Solution.totalWinnings(input), 6440)
  }
}
