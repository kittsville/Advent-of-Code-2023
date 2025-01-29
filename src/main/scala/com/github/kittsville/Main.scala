package com.github.kittsville

import scala.io.StdIn
import scala.io.Source

object Main extends App {
  println("Puzzle input:")

  val input = multilineInput(limit = 2).stripLineEnd
  println("Input captured, processing...")
  val output = Day14Solution.rolledLoad(input)

  println("Solution:")
  println(output)

  private def multilineInput(limit: Int = 1, count: Int = 0): String = {
    val input = StdIn.readLine()
    input match {
      case "file"              => fileInput()
      case "" if count < limit => s"\n\n${multilineInput(limit, count + 1)}"
      case ""                  => ""
      case nonEmpty            => s"$input\n${multilineInput(limit, 0)}"
    }
  }

  private def fileInput(): String = {
    println("Enter file path:")
    val path = StdIn.readLine()
    val bufferedSource = Source.fromFile(path)
    val input = bufferedSource.getLines().mkString("\n")
    bufferedSource.close
    input
  }
}
