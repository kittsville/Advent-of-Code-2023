package com.github.kittsville

import scala.io.StdIn

object Main extends App {
  println("Puzzle input:")

  val input = multilineInput(limit = 2)
  println("Input captured, processing...")
  val output = Day6Solution.singleLargeRecordBeater(input)

  println("Solution:")
  println(output)

  private def multilineInput(limit: Int = 1, count: Int = 0): String = {
    val input = StdIn.readLine()
    input match {
      case "" if count < limit => s"\n\n${multilineInput(limit, count + 1)}"
      case ""                  => ""
      case nonEmpty            => s"$input\n${multilineInput(limit, 0)}"
    }
  }
}
