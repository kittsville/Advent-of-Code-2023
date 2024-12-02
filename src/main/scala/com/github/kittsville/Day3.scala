package com.github.kittsville

import scala.util.matching.Regex

object Day3Solution extends Day3 {
  def parsePartPositions(schematic: String): Set[Position] =
    schematic.linesIterator.zipWithIndex.flatMap {
      case (line, yPos) => {
        line.zipWithIndex.flatMap {
          case (char, xPos) if !char.isDigit && char != '.' => Some(Position(x = xPos, y = yPos))
          case _                                            => None
        }.toSet
      }
    }.toSet

  def partNumbers(schematic: String): List[Int] = {
    val partPositions = parsePartPositions(schematic)

    schematic.linesIterator.zipWithIndex.flatMap {
      case (line, yPos) => {
        partNumberParser
          .findAllMatchIn(line)
          .flatMap(adjacentPartNumber(yPos, partPositions))
      }
    }.toList
  }

  private def adjacentPartNumber(yPos: Int, partPositions: Set[Position])(regexMatch: Regex.Match): Option[Int] = {
    val minX = regexMatch.start - 1
    val maxX = regexMatch.end // Regex end is
    val minY = yPos - 1
    val maxY = yPos + 1

    if (
      partPositions
        .exists(position => position.x >= minX && position.x <= maxX && position.y >= minY && position.y <= maxY)
    ) Some(regexMatch.group(0).toString.toInt)
    else None
  }

  def summedPartNumbers(schematic: String): Int = partNumbers(schematic).fold(0)(_ + _)

  private val partNumberParser = """([0-9]+)""".r
}

trait Day3 {
  def summedPartNumbers(schematic: String): Int
}

case class Position(x: Int, y: Int)
