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
}

trait Day2 {
  def gamePossibleIds(games: String): Int
  def validGame(gameResult: String): Boolean
}

case class Cube(name: String, available: Int)
