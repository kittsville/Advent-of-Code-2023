package com.github.kittsville

object Day9Solution extends Day9 {
  def nextInSequence(sequence: List[Int]): Int = {
    val differences = sequence.zip(sequence.tail).map { case (first, second) => second - first }

    if (differences.forall(_ == 0)) sequence.last
    else sequence.last + nextInSequence(differences)
  }

  def previousInSequence(sequence: List[Int]): Int = {
    val differences = sequence.zip(sequence.tail).map { case (first, second) => second - first }

    if (differences.forall(_ == 0)) sequence.head
    else sequence.head - previousInSequence(differences)
  }

  def sumOfNextInSequences(raw: String): Int =
    parseProcessAndReduce(raw, nextInSequence)

  def sumOfPreviousInSequences(raw: String): Int =
    parseProcessAndReduce(raw, previousInSequence)

  private def parseProcessAndReduce(raw: String, process: List[Int] => Int): Int = {
    raw.linesIterator
      .flatMap {
        case ""        => None
        case rawDigits => Some(rawDigits.split(" ").map(_.toInt).toList)
      }
      .map(process)
      .reduce(_ + _)
  }
}

trait Day9 {
  def nextInSequence(sequence: List[Int]): Int
  def previousInSequence(sequence: List[Int]): Int
  def sumOfNextInSequences(raw: String): Int
  def sumOfPreviousInSequences(raw: String): Int
}
