package com.github.kittsville

import scala.collection.immutable.Range

object Day6Solution {
  def recordBeatersCount(raceTime: Int, recordDistance: Int): Int =
    new Range.Exclusive(1, raceTime, 1).count(time => (time * (raceTime - time) > recordDistance))

  def multipliedRecordBeaters(input: String): Any = {
    val lines = input.linesIterator.toList
    val times = parseInts(lines.head)
    val record = parseInts(lines(1))

    times.zip(record).map { case (time, record) => recordBeatersCount(time, record) }.reduce(_ * _)
  }

  private def parseInts(rawList: String): Seq[Int] = rawList
    .split(":")(1)
    .split(" ")
    .flatMap(_.trim() match {
      case "" => None
      case v  => Some(v.toInt)
    })
}

trait Day6 {
  def recordBeatersCount(raceTime: Int, recordDistance: Int): Int
  def multipliedRecordBeaters(input: String): Int
}
