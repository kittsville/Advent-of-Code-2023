package com.github.kittsville

import cats.syntax.all._
import scala.collection.mutable

object Day13Solution extends Day13 {
  def getSmudgedHorizontalReflectionPoint(raw: String): Option[Int] =
    getReflectionPoint(smugedRowsEqual, smugedSlicesEqual)(raw.linesIterator.toList)
  def getSmudgedVerticalReflectionPoint(raw: String): Option[Int] =
    getReflectionPoint(smugedRowsEqual, smugedSlicesEqual)(flipVerticalToHorizontal(raw))

  def getHorizontalReflectionPoint(raw: String): Option[Int] =
    getReflectionPoint(_ == _, _ == _)(raw.linesIterator.toList)

  private def smugedRowsEqual(left: String, right: String): Boolean = equal(left, right) != Day13.NotEqual
  private def smugedSlicesEqual(left: List[String], right: List[String]): Boolean = {
    val rowTypes = mutable.Map[Day13.Equality, Int](
      Day13.Equal -> 0,
      Day13.NotEqual -> 0,
      Day13.Smudged -> 0
    )

    left
      .lazyZip(right)
      .takeWhile {
        case (leftRow, rightRow) => {
          val currentRowEquality = equal(leftRow, rightRow)
          rowTypes += currentRowEquality -> (rowTypes(currentRowEquality) + 1)

          rowTypes(Day13.NotEqual) == 0 && rowTypes(Day13.Smudged) <= 1
        }
      }
      .toList

    rowTypes(Day13.NotEqual) == 0 && rowTypes(Day13.Smudged) == 1
  }

  private def getReflectionPoint(
      rowEquality: (String, String) => Boolean,
      sliceEquality: (List[String], List[String]) => Boolean
  )(rows: List[String]): Option[Int] = {
    val reflectsAt = validReflectionPoint(rowEquality, sliceEquality) _

    rows
      .lazyZip(rows.drop(1))
      .lazyZip(rows.indices)
      .flatMap {
        case (left, right, index) if reflectsAt(left, right, index, rows) => Some(index)
        case _                                                            => None
      }
      .headOption
      .map(_ + 1)
  }

  private def validReflectionPoint(
      rowEquality: (String, String) => Boolean,
      sliceEquality: (List[String], List[String]) => Boolean
  )(left: String, right: String, index: Int, rows: List[String]): Boolean = {
    if (!rowEquality(left, right)) false
    else {
      val slicePoint = index + 1
      val leftSliceLength = slicePoint
      val rightSliceLength = rows.length - slicePoint
      val evenSliceLength = Math.min(leftSliceLength, rightSliceLength)

      val leftStartPoint = slicePoint - evenSliceLength
      val rightEndPoint = slicePoint + evenSliceLength

      val leftSlice = rows.slice(leftStartPoint, slicePoint)
      val rightSlice = rows.slice(slicePoint, rightEndPoint).reverse

      sliceEquality(leftSlice, rightSlice)
    }
  }

  def getVerticalReflectionPoint(raw: String): Option[Int] =
    getReflectionPoint(_ == _, _ == _)(flipVerticalToHorizontal(raw))

  private def flipVerticalToHorizontal(raw: String): List[String] =
    raw.linesIterator.map(row => row.toCharArray().toList.map(char => char.toString)).reduce[List[String]] {
      case (left, right) =>
        left.lazyZip(right).map(_ + _)
    }

  def getNotesSummary(raw: String): Int =
    notesSummaryInternal(getHorizontalReflectionPoint, getVerticalReflectionPoint)(raw)

  def getSmudgedNotesSummary(raw: String): Int =
    notesSummaryInternal(getSmudgedHorizontalReflectionPoint, getSmudgedVerticalReflectionPoint)(raw)

  private def notesSummaryInternal(horizontalFetcher: String => Option[Int], verticalFetcher: String => Option[Int])(
      raw: String
  ) = {
    val accumulator =
      raw.stripLineEnd.split("\n\n").map(fullstrip).foldLeft(Accumulator(0, 0)) { (acc, pattern) =>
        horizontalFetcher(pattern)
          .map(acc.addHorizontal(_).some)
          .getOrElse(verticalFetcher(pattern).map(acc.addVertical))
          .getOrElse(throw new IllegalArgumentException(s"Invalid pattern with no reflection point:$pattern"))
      }

    accumulator.vertical + (accumulator.horizontal * 100)
  }

  def equal(left: String, right: String): Day13.Equality = {
    if (left == right) Day13.Equal
    else if (left.length != right.length) Day13.NotEqual
    else if (differentByOne(left, right)) Day13.Smudged
    else Day13.NotEqual
  }

  private def differentByOne(left: String, right: String): Boolean = {
    var differences = 0
    left.toCharArray
      .lazyZip(right.toCharArray)
      .takeWhile {
        case (lChar, rChar) => {
          if (lChar != rChar) differences += 1
          differences <= 1
        }
      }
      .toList

    differences == 1
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
  def getSmudgedHorizontalReflectionPoint(raw: String): Option[Int]
  def getSmudgedVerticalReflectionPoint(raw: String): Option[Int]
  def getNotesSummary(raw: String): Int
  def getSmudgedNotesSummary(raw: String): Int
  def equal(left: String, right: String): Day13.Equality

}

object Day13 {
  sealed trait Equality

  final case object Equal extends Equality
  final case object Smudged extends Equality
  final case object NotEqual extends Equality
}
