package com.github.kittsville

import scala.jdk.StreamConverters._

object Day1Solution extends Day1 {
  def calibrationSum(document: String): Int = document.lines
    .toScala(LazyList)
    .foldLeft(0)((sum, line) => sum + parseLine(line))

  private def firstDigit(line: String): String =
    letterMappings
      .flatMap(mapping =>
        Seq(
          line.indexOf(mapping.letter) -> mapping.digit,
          line.indexOf(mapping.digit) -> mapping.digit
        )
      )
      .filterNot { case (pos, _) => pos == -1 }
      .minBy { case (pos, _) => pos }
      ._2

  private def lastDigit(line: String): String =
    letterMappings
      .flatMap(mapping =>
        Seq(
          line.lastIndexOf(mapping.letter) -> mapping.digit,
          line.lastIndexOf(mapping.digit) -> mapping.digit
        )
      )
      .filterNot { case (pos, _) => pos == -1 }
      .maxBy { case (pos, _) => pos }
      ._2

  private def parseLine(line: String): Int =
    (s"${firstDigit(line)}${lastDigit(line)}").toInt

  private val letterMappings = Seq(
    LetterMapping("one", "1"),
    LetterMapping("two", "2"),
    LetterMapping("three", "3"),
    LetterMapping("four", "4"),
    LetterMapping("five", "5"),
    LetterMapping("six", "6"),
    LetterMapping("seven", "7"),
    LetterMapping("eight", "8"),
    LetterMapping("nine", "9")
  )
}

trait Day1 {
  def calibrationSum(document: String): Int
}

case class LetterMapping(letter: String, digit: String)
