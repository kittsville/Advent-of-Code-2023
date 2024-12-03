package com.github.kittsville

import scala.jdk.StreamConverters._

object Day4Solution {
  def points(scratchcards: String): Int =
    scratchcards.lines
      .toScala(LazyList)
      .foldLeft(0)((sum, line) => sum + scoreWins(singleGameWins(line)._2))

  def winningScratchcards(scratchcards: String): Int = {
    println(scratchcards)
    val wins = scratchcards.linesIterator.map(singleGameWins).toMap

    wins.keys
      .map(checkGameId => countScratchcardWins(wins, checkGameId))
      .foldLeft(0)(_ + _)
  }

  private def countScratchcardWins(wins: Map[Int, Int], checkGameId: Int): Int = {
    val singleGameWins = wins(checkGameId)

    if (singleGameWins == 0) {
      1
    } else {
      val start = checkGameId + 1
      val end = checkGameId + singleGameWins

      val res = 1 + (start to end).map(newGameId => countScratchcardWins(wins, newGameId)).foldLeft(0)(_ + _)
      res
    }
  }

  private def singleGameWins(rawGame: String): (Int, Int) = rawGame.halve(':') match {
    case (meta, results) =>
      (
        meta.stripPrefix("Card").trim().toInt,
        results.trim().halve('|') match {
          case (rawChosenNumbers, rawWinningNumbers) =>
            parseNumbers(rawChosenNumbers).count(parseNumbers(rawWinningNumbers).toSet.contains)
        }
      )
  }

  private def scoreWins(wins: Int): Int =
    wins match {
      case 0 => 0
      case w => Math.pow(2, w - 1).toInt
    }

  private def parseNumbers(text: String): List[Int] = text.split(" ").flatMap(_.toIntOption).toList

  private case class UnableToHalveText(text: String, divider: Char)
      extends IllegalArgumentException(s"Unable to halve input $text using divider $divider")

  private implicit class StringWithHalving(text: String) {
    def halve(divider: Char): (String, String) =
      text.split(divider).toList match {
        case left :: right :: Nil => (left, right)
        case _                    => throw UnableToHalveText(text, divider)
      }
  }
}

trait Day4 {
  def points(scratchcards: String): Int
  def winningScratchcards(scratchcards: String): Int
}
