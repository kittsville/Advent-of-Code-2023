package com.github.kittsville

class Day7Part1Spec extends munit.FunSuite {
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
    assertEquals(PlayedHand.compareUnjokered(PlayedHand("AAAAA 2"), PlayedHand("AA8AA 3")), false)

    assertEquals(PlayedHand.compareUnjokered(PlayedHand("AAAAA 2"), PlayedHand("AA8AA 2")), false)
    assertEquals(PlayedHand.compareUnjokered(PlayedHand("23332 2"), PlayedHand("AA8AA 2")), true)
  }

  test("Can compare two hands by each card's strength") {
    assertEquals(PlayedHand.compareUnjokered(PlayedHand("33332 2"), PlayedHand("2AAAA 2")), false)
    assertEquals(PlayedHand.compareUnjokered(PlayedHand("77788 2"), PlayedHand("77888 2")), true)
  }

  test("Fail if two hands are exactly equal") {
    interceptMessage[java.lang.IllegalArgumentException]("Exhausted cards from compared hands and all were equal") {
      PlayedHand.compareUnjokered(PlayedHand("33332 2"), PlayedHand("33332 2"))
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
