package com.github.kittsville

object Day9Solution {
  def nextInSequence(sequence: List[Int]): Int = {
    val differences = sequence.zip(sequence.tail).map { case (first, second) => second - first }

    if (differences.forall(_ == 0)) sequence.last
    else sequence.last + nextInSequence(differences)
  }

  def sumOfNextInSequences(raw: String): Int = {
    raw.linesIterator
      .flatMap {
        case ""        => None
        case rawDigits => Some(rawDigits.split(" ").map(_.toInt).toList)
      }
      .map(nextInSequence)
      .reduce(_ + _)
  }
}

trait Day9 {
  def nextInSequence(sequence: List[Int]): Int
  def sumOfNextInSequences(raw: String): Int
}
