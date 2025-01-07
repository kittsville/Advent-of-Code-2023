package com.github.kittsville

class Day7Part2Spec extends munit.FunSuite {
  test("Returns score 0 when all card labels are distinct") {
    assertEquals(Day7Solution.jokeredHandType("23456"), 0)
  }

  test("Returns score 1 when all card labels are distinct, with a joker") {
    assertEquals(Day7Solution.jokeredHandType("2345J"), 1)
  }

  test("Returns score 3 when all card labels are distinct, with two jokers") {
    assertEquals(Day7Solution.jokeredHandType("234JJ"), 3)
  }

  test("Returns score 1 when the cards contains a pair") {
    assertEquals(Day7Solution.jokeredHandType("A23A4"), 1)
  }

  test("Returns score 3 when the cards contains a pair and a joker") {
    assertEquals(Day7Solution.jokeredHandType("A23AJ"), 3)
  }

  test("Returns score 2 when the cards contains two pairs") {
    assertEquals(Day7Solution.jokeredHandType("23432"), 2)
  }

  test("Returns score 4 when the cards contains two pairs and a joker") {
    assertEquals(Day7Solution.jokeredHandType("23J32"), 4)
  }

  test(
    "Returns score 3 when 3 cards have the same label, and the remaining 2 cards are each different from any other card in the hand"
  ) {
    assertEquals(Day7Solution.jokeredHandType("TTT98"), 3)
  }

  test(
    "Returns score 4 when 3 cards have the same label, and the remaining 2 cards share a different label"
  ) {
    assertEquals(Day7Solution.jokeredHandType("23332"), 4)
  }

  test(
    "Returns score 5 when 4 cards have the same label"
  ) {
    assertEquals(Day7Solution.jokeredHandType("AA8AA"), 5)
  }

  test(
    "Returns score 6 when all 5 cards have the same label"
  ) {
    assertEquals(Day7Solution.jokeredHandType("AAAAA"), 6)
  }

  test(
    "Returns score 6 when all 5 cards are jokers"
  ) {
    assertEquals(Day7Solution.jokeredHandType("JJJJJ"), 6)
  }

  test("Can compare two Hands by type") {
    assertEquals(PlayedHand.compareJokered(PlayedHand("AAAAA 2"), PlayedHand("AA8AA 3")), false)

    assertEquals(PlayedHand.compareJokered(PlayedHand("AAAAA 2"), PlayedHand("AA8AA 2")), false)
    assertEquals(PlayedHand.compareJokered(PlayedHand("23332 2"), PlayedHand("AA8AA 2")), true)
  }

  test("Can compare two hands by each card's strength") {
    assertEquals(PlayedHand.compareJokered(PlayedHand("33332 2"), PlayedHand("2AAAA 2")), false)
    assertEquals(PlayedHand.compareJokered(PlayedHand("77788 2"), PlayedHand("77888 2")), true)
  }

  test("Can compare two hands by each card's strength, ranking jokers the lowest") {
    assertEquals(PlayedHand.compareJokered(PlayedHand("JJJJ2 2"), PlayedHand("2JJJJ 2")), true)
    assertEquals(PlayedHand.compareJokered(PlayedHand("JJ888 2"), PlayedHand("JJJ88 2")), false)
  }

  test("Fail if two hands are exactly equal") {
    interceptMessage[java.lang.IllegalArgumentException]("Exhausted cards from compared hands and all were equal") {
      PlayedHand.compareJokered(PlayedHand("33332 2"), PlayedHand("33332 2"))
    }
  }

  test("Calculate the total winnings for a list of hands") {
    val input = """32T3K 765
                  |T55J5 684
                  |KK677 28
                  |KTJJT 220
                  |QQQJA 483""".stripMargin
    assertEquals(Day7Solution.totalWinningsJokered(input), 5905)
  }
}
