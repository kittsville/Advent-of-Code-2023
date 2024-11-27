package com.github.kittsville

import scala.jdk.StreamConverters._

object Day1Solution extends Day1 {
  def calibrationSum(document: String): Int = document.lines.toScala(LazyList).foldLeft(0)((sum, line) => sum + parseLine(line))

  private def parseLine(document: String): Int = {
    val digits = document.filter(_.isDigit)

    println(digits)
    
    (s"${digits.head}${digits.last}").toInt
  }
}

trait Day1 {
  def calibrationSum(document: String): Int
}
