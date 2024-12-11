package com.github.kittsville

import scala.collection.immutable.NumericRange

object Day6Solution {
  def recordBeatersCount(raceTime: Long, recordDistance: Long): Long =
    Range.Long(1, raceTime, 1).count(time => (time * (raceTime - time) > recordDistance))

  def multipliedRecordBeaters(input: String): Long = {
    val lines = input.linesIterator.toList
    val times = parseInts(lines.head)
    val record = parseInts(lines(1))

    times.zip(record).map { case (time, record) => recordBeatersCount(time, record) }.reduce(_ * _)
  }

  def singleLargeRecordBeater(input: String): Long = {
    val lines = input.linesIterator.toList
    val time = parseLargeInt(lines.head)
    val record = parseLargeInt(lines(1))

    recordBeatersCount(time, record)
  }

  private def parseInts(rawList: String): Seq[Long] = rawList
    .split(":")(1)
    .split(" ")
    .flatMap(_.trim() match {
      case "" => None
      case v  => Some(v.toLong)
    })

  private def parseLargeInt(rawList: String): Long = rawList
    .split(":")(1)
    .split(" ")
    .flatMap(_.trim() match {
      case "" => None
      case v  => Some(v)
    })
    .mkString
    .toLong
}

trait Day6 {
  def recordBeatersCount(raceTime: Long, recordDistance: Long): Long
  def multipliedRecordBeaters(input: String): Long
  def singleLargeRecordBeater(input: String): Long
}
