package com.github.kittsville

import scala.annotation.tailrec

object Day8Solution extends Day8 {
  def stepsRequired(raw: String): Int = 0

  def parse(raw: String): RouteMap = {
    val source = raw.strip.linesIterator
    val rawDirections = source.next()
    val _ = source.next() // Empty line

    val directions: Seq[Direction] = rawDirections.toCharArray().toSeq.map {
      case 'L'          => Left
      case 'R'          => Right
      case unrecognised => throw new IllegalArgumentException(s"Unrecognised direction: '$unrecognised'")
    }
    val map = source.flatMap(parseGraphEntry).toMap

    RouteMap(directions, map)
  }

  def countSteps(raw: String): Int = {
    val routeMap = parse(raw)

    countStepsToEnd(routeMap, _.value == "ZZZ", Node("AAA"), 0)
  }

  def countGhostSteps(raw: String): Long = {
    val routeMap = parse(raw)
    val startNodes = routeMap.map.keys.filter(_.value.endsWith("A")).toSeq
    val stepsRequired = startNodes.map(startNode => countStepsToEnd(routeMap, _.value.endsWith("Z"), startNode, 0))

    println(stepsRequired)

    stepsRequired.sorted
      .map(_.toLong)
      .reduce((l, r) => {
        Utilities.lcm(l, r)
      })
  }

  @tailrec
  def countStepsToEnd(routeMap: RouteMap, reachedEnd: Node => Boolean, location: Node, stepCounter: Int): Int = {
    if (stepCounter > stepsLimit) {
      throw new InternalError(s"Steps exceeded sensible limit of $stepsLimit")
    } else if (reachedEnd(location)) {
      stepCounter
    } else {
      val directionIndex = stepCounter % routeMap.directions.length
      val direction = routeMap.directions(directionIndex)
      val edges =
        routeMap.map.getOrElse(location, throw new StepsExceeded)
      val nextLocation = edges(direction)

      countStepsToEnd(routeMap, reachedEnd, nextLocation, stepCounter + 1)
    }
  }

  private def parseGraphEntry(rawEntry: String): Option[(Node, Edges)] = rawEntry match {
    case "" => None
    case graphEntryMatcher(rawSourceNode, rawLeftNode, rawRightNode) =>
      Some(Node(rawSourceNode) -> Edges(Node(rawLeftNode), Node(rawRightNode)))
    case unrecognised => throw new IllegalArgumentException(s"Unable to parse graph entry: '$unrecognised'")
  }

  private val graphEntryMatcher = """^([A-Z0-9]{3})\s=\s\(([A-Z0-9]{3})\,\s([A-Z0-9]{3})\)$""".r
  private val stepsLimit = 99999999
  class StepsExceeded extends InternalError(s"Steps exceeded sensible limit of $stepsLimit")
}

object Utilities {
  // Modified from: https://rosettacode.org/wiki/Least_common_multiple#Scala
  def gcd(a: Long, b: Long): Long = if (b == 0) a.abs else gcd(b, a % b)
  def lcm(a: Long, b: Long) = (a * b).abs / gcd(a, b)

}

trait Day8 {
  def stepsRequired(raw: String): Int
  def countSteps(raw: String): Int
}

case class RouteMap(directions: Seq[Direction], map: Map[Node, Edges])
case class Node(value: String) extends AnyVal
case class Edges(left: Node, right: Node) {
  def apply(direction: Direction): Node = direction match {
    case Left  => left
    case Right => right
  }
}

sealed trait Direction
final case object Left extends Direction
final case object Right extends Direction
