package com.github.kittsville

import scala.io.StdIn

object Main extends App {
  println("Puzzle input:")

  val input = multilineInput()
  val output = Day3Solution.summedGearRatios(input)

  println("Solution:")
  println(output)

  private def multilineInput(): String = {
    val input = StdIn.readLine()
    input match {
      case ""       => ""
      case nonEmpty => s"$input\n${multilineInput()}"
    }
  }
}
