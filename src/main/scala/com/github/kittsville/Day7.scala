package com.github.kittsville

import java.util.InputMismatchException
import scala.annotation.tailrec

object Day7Solution extends Day7 {
  def handType(raw: String): Int =
    Hand(raw).typeScore

  def jokeredHandType(raw: String): Int =
    Hand(raw).jokeredTypeScore

  def totalWinnings(raw: String): Int = totalWinningsImpl(PlayedHand.compareUnjokered)(raw)
  def totalWinningsJokered(raw: String): Int = totalWinningsImpl(PlayedHand.compareJokered)(raw)

  def totalWinningsImpl(sorter: (PlayedHand, PlayedHand) => Boolean)(raw: String): Int = {
    raw.strip.linesIterator.map(PlayedHand.apply).toSeq.sortWith(sorter).zipWithIndex.foldLeft(0) {
      case (score, (currentPlay, index)) => {
        score + currentPlay.bid * (index + 1)
      }
    }
  }

}

trait Day7 {
  def handType(raw: String): Int
  def totalWinnings(raw: String): Int
  def totalWinningsJokered(raw: String): Int
}

case class Hand(cards: String) {
  private val letterGroups = cards.toCharArray().groupBy(identity).map { case (k, v) => k -> v.length }

  val typeScore: Int = Hand.scoreLetterGroups(letterGroups)

  val jokeredTypeScore: Int = {
    val jokerCount = letterGroups.getOrElse('J', 0)

    if (jokerCount == 0 || jokerCount == cards.length) {
      typeScore
    } else {
      val (mostFrequentChar, mostFrequentCount) =
        letterGroups.filterNot { case (char, count) => char == 'J' }.maxBy { case (_, count) => count }

      val jokeredLetterGroups = letterGroups.flatMap {
        case ('J', _)                                  => None
        case (char, count) if char == mostFrequentChar => Some(char -> (count + jokerCount))
        case (char, count)                             => Some(char -> count)
      }

      Hand.scoreLetterGroups(jokeredLetterGroups)
    }
  }
}

object Hand {
  def scoreLetterGroups(letterGroups: Map[Char, Int]): Int = {
    lazy val threeOfAKind = letterGroups.values.exists(_ == 3)
    lazy val pairsCount = letterGroups.values.count(_ == 2)

    if (letterGroups.values.exists(_ == 5)) 6
    else if (letterGroups.values.exists(_ == 4)) 5
    else if (pairsCount == 1 & threeOfAKind) 4
    else if (threeOfAKind & letterGroups.values.count(_ == 1) == 2) 3
    else pairsCount
  }

  @tailrec
  def compareHandsByCard(labelScore: Map[Char, Int])(cardPairs: Seq[(Char, Char)]): Boolean = {
    val currentPair = cardPairs.headOption.getOrElse(
      throw new IllegalArgumentException("Exhausted cards from compared hands and all were equal")
    )

    val leftScore = labelScore(currentPair._1)
    val rightScore = labelScore(currentPair._2)

    if (leftScore > rightScore) false
    else if (leftScore < rightScore) true
    else compareHandsByCard(labelScore)(cardPairs.tail)
  }

  val unjokeredLabelScore = List('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A').zipWithIndex.toMap
  val jokeredLabelScore = List('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A').zipWithIndex.toMap
}

case class PlayedHand(hand: Hand, bid: Int)

object PlayedHand {
  def apply(raw: String): PlayedHand = raw.split(" ").toList match {
    case rawHand :: rawBid :: Nil => PlayedHand(Hand(rawHand), rawBid.toInt)
    case derp                     => throw new IllegalArgumentException(s"Bad PlayedHand: '$raw'")
  }

  def compareUnjokered(left: PlayedHand, right: PlayedHand): Boolean = {
    if (left.hand.typeScore > right.hand.typeScore) false
    else if (left.hand.typeScore < right.hand.typeScore) true
    else {
      Hand.compareHandsByCard(Hand.unjokeredLabelScore)(
        left.hand.cards.toCharArray().zip(right.hand.cards.toCharArray())
      )
    }
  }

  def compareJokered(left: PlayedHand, right: PlayedHand): Boolean = {
    if (left.hand.jokeredTypeScore > right.hand.jokeredTypeScore) false
    else if (left.hand.jokeredTypeScore < right.hand.jokeredTypeScore) true
    else {
      Hand.compareHandsByCard(Hand.jokeredLabelScore)(
        left.hand.cards.toCharArray().zip(right.hand.cards.toCharArray())
      )
    }
  }
}
