package com.github.kittsville

import cats.syntax.all._

object Day13Solution extends Day13 {
  def getHorizontalReflectionPoint(raw: String): Option[Int] =
    getReflectionPoint(raw.linesIterator.toList)

  private def getReflectionPoint(rows: List[String]): Option[Int] = rows
    .lazyZip(rows.drop(1))
    .lazyZip(rows.indices)
    .flatMap {
      case (left, right, index) if validReflectionPoint(left, right, index, rows) => Some(index)
      case _                                                                      => None
    }
    .headOption
    .map(_ + 1)

  private def validReflectionPoint(left: String, right: String, index: Int, rows: List[String]): Boolean = {
    if (left != right) false
    else {
      val slicePoint = index + 1
      val leftSliceLength = slicePoint
      val rightSliceLength = rows.length - slicePoint
      val evenSliceLength = Math.min(leftSliceLength, rightSliceLength)

      val leftStartPoint = slicePoint - evenSliceLength
      val rightEndPoint = slicePoint + evenSliceLength

      val leftSlice = rows.slice(leftStartPoint, slicePoint)
      val rightSlice = rows.slice(slicePoint, rightEndPoint).reverse

      leftSlice == rightSlice
    }
  }

  def getVerticalReflectionPoint(raw: String): Option[Int] =
    getReflectionPoint(
      raw.linesIterator.map(row => row.toCharArray().toList.map(char => char.toString)).reduce[List[String]] {
        case (left, right) =>
          left.lazyZip(right).map(_ + _)
      }
    )

  def getNotesSummary(raw: String): Int = {
    val accumulator =
      raw.stripLineEnd.split("\n\n").map(fullstrip).foldLeft(Accumulator(0, 0)) { (acc, pattern) =>
        getHorizontalReflectionPoint(pattern)
          .map(acc.addHorizontal(_).some)
          .getOrElse(getVerticalReflectionPoint(pattern).map(acc.addVertical))
          .getOrElse(throw new IllegalArgumentException(s"Invalid pattern with no reflection point:$pattern"))
      }

    accumulator.vertical + (accumulator.horizontal * 100)
  }

  private def fullstrip(raw: String): String = raw.stripLineEnd.linesIterator
    .flatMap {
      case "" => None
      case c  => c.some
    }
    .mkString("\n")

  private case class Accumulator(vertical: Int, horizontal: Int) {
    def addVertical(amount: Int): Accumulator = copy(vertical = vertical + amount)
    def addHorizontal(amount: Int): Accumulator = copy(horizontal = horizontal + amount)
  }

}

trait Day13 {
  def getHorizontalReflectionPoint(raw: String): Option[Int]
  def getVerticalReflectionPoint(raw: String): Option[Int]
  def getNotesSummary(raw: String): Int
}
