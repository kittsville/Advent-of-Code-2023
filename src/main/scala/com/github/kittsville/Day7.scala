package com.github.kittsville

import java.util.InputMismatchException
import scala.annotation.tailrec

object Day7Solution {
  def handType(raw: String): Int =
    Hand(raw).typeScore

  def totalWinnings(raw: String): Int = {
    raw.strip.linesIterator.map(PlayedHand.apply).toSeq.sortBy(_.hand).zipWithIndex.foldLeft(0) {
      case (score, (currentPlay, index)) => {
        score + currentPlay.bid * (index + 1)
      }
    }
  }

}

trait Day7 {
  def handType(raw: String): Int
  def totalWinnings(raw: String): Int
}

case class Hand(cards: String) extends Ordered[Hand] {
  val typeScore: Int = {
    val letterGroups = cards.toCharArray().groupBy(identity).map { case (k, v) => k -> v.length }

    lazy val threeOfAKind = letterGroups.values.exists(_ == 3)
    lazy val pairsCount = letterGroups.values.count(_ == 2)

    if (letterGroups.values.exists(_ == 5)) 6
    else if (letterGroups.values.exists(_ == 4)) 5
    else if (pairsCount == 1 & threeOfAKind) 4
    else if (threeOfAKind & letterGroups.values.count(_ == 1) == 2) 3
    else pairsCount
  }

  override def compare(that: Hand): Int = {
    if (this.typeScore > that.typeScore) 1
    else if (this.typeScore < that.typeScore) -1
    else {
      Hand.compareHandsByCard(this.cards.toCharArray().zip(that.cards.toCharArray()))
    }
  }
}

object Hand {
  @tailrec
  def compareHandsByCard(cardPairs: Seq[(Char, Char)]): Int = {
    val currentPair = cardPairs.headOption.getOrElse(
      throw new IllegalArgumentException("Exhausted cards from compared hands and all were equal")
    )

    val leftScore = labelScore(currentPair._1)
    val rightScore = labelScore(currentPair._2)

    if (leftScore > rightScore) 1
    else if (leftScore < rightScore) -1
    else compareHandsByCard(cardPairs.tail)
  }

  val labelScore = Map(
    'A' -> 12,
    'K' -> 11,
    'Q' -> 10,
    'J' -> 9,
    'T' -> 8,
    '9' -> 7,
    '8' -> 6,
    '7' -> 5,
    '6' -> 4,
    '5' -> 3,
    '4' -> 2,
    '3' -> 1,
    '2' -> 0
  )
}

case class PlayedHand(hand: Hand, bid: Int)

object PlayedHand {
  def apply(raw: String): PlayedHand = raw.split(" ").toList match {
    case rawHand :: rawBid :: Nil => PlayedHand(Hand(rawHand), rawBid.toInt)
    case derp                     => throw new IllegalArgumentException(s"Bad PlayedHand: '$raw'")
  }
}
