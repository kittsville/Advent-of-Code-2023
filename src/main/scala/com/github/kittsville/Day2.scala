package com.github.kittsville

import scala.jdk.StreamConverters._

object Day2Solution extends Day2 {
  private val gameParser = """^Game\s(\d+)\:\s(.*)$""".r

  def gamePossibleIds(games: String): Int = games.lines
    .toScala(LazyList)
    .foldLeft(0)((sum, line) =>
      line match {
        case gameParser(id, gameResult) if validGame(gameResult) =>
          sum + id.toInt
        case _ => sum
      }
    )

  def validGame(gameResult: String): Boolean =
    gameResult.split("; ").forall(validDrawResult)

  private def validDrawResult(drawResult: String): Boolean =
    drawResult.split(", ").forall(validCubeResult)

  private def validCubeResult(cubeResult: String): Boolean =
    cubeResult.split(' ').toList match {
      case found :: "red" :: Nil if found.toInt <= maxRed     => true
      case found :: "green" :: Nil if found.toInt <= maxGreen => true
      case found :: "blue" :: Nil if found.toInt <= maxBlue   => true
      case bad                                                => false
    }

  private val maxRed = 12
  private val maxGreen = 13
  private val maxBlue = 14

  def summedPowerOfGameCubes(games: String): Int = games.lines
    .toScala(LazyList)
    .foldLeft(0)((sum, line) => {
      val mcc = minimumCubesForGame(line)
      println(mcc)
      val power = mcc.red * mcc.green * mcc.blue

      sum + power
    })

  def minimumCubesForGame(game: String): MinCubeCount = game match {
    case gameParser(_, gameResult) => gameResult.split("; ").map(parseCubeResult).reduce((a, b) => a.combine(b))
    case _                         => throw BadGameInput(game)
  }

  def parseCubeResult(drawResult: String): MinCubeCount =
    drawResult.split(", ").map(parseDrawResult).reduce((a, b) => a.combine(b))

  private def parseDrawResult(cubeResult: String): MinCubeCount =
    cubeResult.split(' ').toList match {
      case found :: "red" :: Nil   => MinCubeCount(red = found.toInt)
      case found :: "green" :: Nil => MinCubeCount(green = found.toInt)
      case found :: "blue" :: Nil  => MinCubeCount(blue = found.toInt)
      case _                       => throw BadGameInput(cubeResult)
    }
}

trait Day2 {
  def gamePossibleIds(games: String): Int
  def validGame(gameResult: String): Boolean
}

case class Cube(name: String, available: Int)

case class BadGameInput(input: String) extends IllegalArgumentException(s"Bad game input: $input")

case class MinCubeCount(red: Int = 0, green: Int = 0, blue: Int = 0) {
  def combine(that: MinCubeCount): MinCubeCount = {
    MinCubeCount(
      red = Math.max(this.red, that.red),
      green = Math.max(this.green, that.green),
      blue = Math.max(this.blue, that.blue)
    )
  }
}
