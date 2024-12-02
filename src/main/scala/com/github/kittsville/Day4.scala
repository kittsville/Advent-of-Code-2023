package com.github.kittsville

import scala.jdk.StreamConverters._

object Day4Solution {
  def points(scratchcards: String): Int =
    scratchcards.lines
      .toScala(LazyList)
      .foldLeft(0)((sum, line) =>
        sum + (line.halve(':')._2.trim().halve('|') match {
          case (rawChosenNumbers, rawWinningNumbers) =>
            parseNumbers(rawChosenNumbers).foldLeft(0)(countIfWinningNumber(parseNumbers(rawWinningNumbers).toSet))
        })
      )

  private def countIfWinningNumber(winningNumbers: Set[Int])(total: Int, chosenNumber: Int): Int = {
    (total, winningNumbers.contains(chosenNumber)) match {
      case (0, true)  => 1
      case (t, true)  => t * 2
      case (t, false) => t
    }
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
}
