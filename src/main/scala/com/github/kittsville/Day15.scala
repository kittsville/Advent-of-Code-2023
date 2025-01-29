package com.github.kittsville

object Day15Solution extends Day15 {
  def hash(step: String): Int = step.toCharArray().foldLeft(0) { case (acc, char) => ((acc + char.toInt) * 17) % 256 }

  def sumStepHashes(sequence: String): Int = sequence.split("\n").head.split(",").foldLeft(0)(_ + hash(_))
}

trait Day15 {
  def hash(step: String): Int
  def sumStepHashes(sequence: String): Int
}
