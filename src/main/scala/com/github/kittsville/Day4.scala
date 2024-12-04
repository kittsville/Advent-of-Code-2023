package com.github.kittsville

import scala.jdk.StreamConverters._

object Day4Solution {
  def points(scratchcards: String): Int =
    scratchcards.lines
      .toScala(LazyList)
      .foldLeft(0)((sum, line) => sum + scoreWins(singleGameWins(line)._2))

  def winningScratchcards(scratchcards: String): Int = {
    val wins = scratchcards.linesIterator.map(singleGameWins).toMap
    val accumulator = wins.keys.map(gameId => gameId -> 0).toMap

    wins.keys.toList.sorted
      .foldLeft(accumulator)((ac1, gameId) => {
        val scratchcardsWon = wins(gameId)

        val start = gameId + 1
        val end = gameId + scratchcardsWon

        val ac2 = ac1 ++ Map(gameId -> (ac1(gameId) + 1))
        val multiplier = ac2(gameId)
        val a3 = (start to end).foldLeft(ac2)((ac3, gId2) => ac3 ++ Map(gId2 -> (ac3(gId2) + multiplier)))

        a3
      })
      .values
      .foldLeft(0)(_ + _)
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
