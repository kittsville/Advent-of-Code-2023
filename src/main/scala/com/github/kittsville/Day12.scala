package com.github.kittsville

import cats.syntax.all._

object Day12Solution extends Day12 {
  def sumRowPermutations(raw: String): Int = parseAndSum(raw, 1)
  def sumUnfoldedRowPermutations(raw: String): Int = parseAndSum(raw, 5)

  private def parseAndSum(raw: String, unfoldFactor: Int): Int = raw.linesIterator
    .map {
      case ""  => 0
      case row => countPossiblePermutations(row, unfoldFactor)
    }
    .foldLeft(0)(_ + _)

  def countPossiblePermutations(line: String, unfoldFactor: Int): Int = {
    val (report, groupedReport) = line.split(' ').toList match {
      case rawReport :: rawGroupedReport :: Nil =>
        (
          multiplyString(rawReport, "?", unfoldFactor).toCharArray(),
          multiplyString(rawGroupedReport, ",", unfoldFactor).split(",").map(_.toInt)
        )
      case _ => throw new IllegalArgumentException(s"Invalid line: '$line'")
    }

    recursivelyCountPossibilities(report.toList, groupedReport.toList, None, "")
  }

  private def multiplyString(input: String, sepator: String, unfoldFactor: Int): String =
    List.fill(unfoldFactor)(input).mkString(sepator)

  def recursivelyCountPossibilities(
      report: List[Char],
      groups: List[Int],
      restriction: Option[Restriction],
      output: String
  ): Int = {
    report.headOption.fold {
      if (groups.isEmpty) {
        1
      } else 0
    } {
      case _ if impossibleFuture(report, groups)              => 0
      case _ if restriction.contains(NoGap) && groups.isEmpty => 0
      case '#' if groups.nonEmpty && !restriction.contains(GapRequired) => {
        val (remainingGroups, nextRestrictions) = decrementList(groups)
        recursivelyCountPossibilities(report.tail, remainingGroups, nextRestrictions.some, output + "#")
      }
      case '#'                                 => 0
      case '.' if !restriction.contains(NoGap) => recursivelyCountPossibilities(report.tail, groups, None, output + ".")
      case '.'                                 => 0
      case '?' if restriction.contains(GapRequired) =>
        recursivelyCountPossibilities(report.tail, groups, None, output + ".")
      case '?' if restriction.contains(NoGap) => {
        val (remainingGroups, nextRestrictions) = decrementList(groups)
        recursivelyCountPossibilities(report.tail, remainingGroups, nextRestrictions.some, output + "#")
      }
      case '?' if groups.nonEmpty => {
        val (remainingGroups, nextRestrictions) = decrementList(groups)
        recursivelyCountPossibilities(
          report.tail,
          remainingGroups,
          nextRestrictions.some,
          output + "#"
        ) + recursivelyCountPossibilities(report.tail, groups, None, output + ".")
      }
      case '?' => recursivelyCountPossibilities(report.tail, groups, None, output + ".")
      case invalid =>
        throw new IllegalArgumentException(s"Unrecognised spring report symbol '$invalid'. Must be one of [#.?]")
    }
  }

  private def impossibleFuture(report: List[Char], groups: List[Int]): Boolean =
    groups.sum > report.count(char => char == '?' || char == '#')

  private def decrementList(list: List[Int]): (List[Int], Restriction) = {
    list match {
      case 1 :: next    => (next, GapRequired)
      case head :: next => ((head - 1) :: next, NoGap)
      case Nil => throw new IllegalStateException("Decrement function shouldn't have been called on an empty list")
    }
  }

  private sealed trait Restriction

  private final case object GapRequired extends Restriction
  private final case object NoGap extends Restriction
}

trait Day12 {
  def sumRowPermutations(raw: String): Int
  def sumUnfoldedRowPermutations(raw: String): Int
  def countPossiblePermutations(line: String, unfoldFactor: Int): Int
}
