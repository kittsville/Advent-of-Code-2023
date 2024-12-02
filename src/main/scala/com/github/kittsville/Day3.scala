package com.github.kittsville

import scala.util.matching.Regex

object Day3Solution extends Day3 {
  private def partFinder(schematic: String, partFilter: Char => Boolean): Set[Position] =
    schematic.linesIterator.zipWithIndex.flatMap {
      case (line, yPos) => {
        line.zipWithIndex.flatMap {
          case (char, xPos) if !char.isDigit && char != '.' => Some(Position(x = xPos, y = yPos))
          case _                                            => None
        }.toSet
      }
    }.toSet

  def parsePartPositions(schematic: String): Set[Position] = partFinder(schematic, _ != '.')

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
    val maxX = regexMatch.end // .end is already +1 further than last digit's position
    val minY = yPos - 1
    val maxY = yPos + 1

    if (
      partPositions
        .exists(position => position.x >= minX && position.x <= maxX && position.y >= minY && position.y <= maxY)
    ) Some(regexMatch.group(0).toString.toInt)
    else None
  }

  private def adjacentGears(yPos: Int, partPositions: Set[Position])(regexMatch: Regex.Match): Set[(Position, Int)] = {
    val minX = regexMatch.start - 1
    val maxX = regexMatch.end // .end is already +1 further than last digit's position
    val minY = yPos - 1
    val maxY = yPos + 1

    partPositions
      .filter(position => position.x >= minX && position.x <= maxX && position.y >= minY && position.y <= maxY)
      .map(position => (position, regexMatch.group(0).toInt))
  }

  def summedPartNumbers(schematic: String): Int = partNumbers(schematic).fold(0)(_ + _)

  def summedGearRatios(schematic: String): Int = {
    val gearPositions = partFinder(schematic, _ == '*')
    val gearMappings = gearPositions.map(_ -> Set.empty[Int]).toMap

    val numberToGearMapping = schematic.linesIterator.zipWithIndex.flatMap {
      case (line, yPos) => {
        partNumberParser
          .findAllMatchIn(line)
          .flatMap(adjacentGears(yPos, gearPositions))
      }
    }.toList

    numberToGearMapping
      .groupBy(_._1)
      .view
      .mapValues(_.map(_._2))
      .collect { case (pos, number1 :: number2 :: Nil) =>
        number1 * number2
      }
      .fold(0)(_ + _)
  }

  private val partNumberParser = """([0-9]+)""".r
}

trait Day3 {
  def summedPartNumbers(schematic: String): Int
  def summedGearRatios(schematic: String): Int
}

case class Position(x: Int, y: Int)
