package com.github.kittsville

object Day11Solution extends Day11 {
  def sumDistancesInUniverse(raw: String): Int = sumDistancesBetweenPairs(expandUniverse(parseUniverse(raw)))

  def parseUniverse(raw: String): Set[Position] = raw.linesIterator.zipWithIndex.flatMap {
    case ("", _) => List.empty[Position]
    case (row, y) =>
      row.toCharArray.zipWithIndex.collect { case ('#', x) =>
        Position(x, y)
      }
  }.toSet

  def expandUniverse(universe: Set[Position]): Set[Position] = {
    val clearYValues = identifyGaps(universe.map(_.y))
    val clearXValues = identifyGaps(universe.map(_.x))

    universe.map(galaxy => {
      Position(
        x = galaxy.x + clearXValues.count(_ < galaxy.x),
        y = galaxy.y + clearYValues.count(_ < galaxy.y)
      )
    })
  }

  private def identifyGaps(numbers: Set[Int]): Set[Int] = (0 to numbers.max).filterNot(numbers.contains).toSet

  def sumDistancesBetweenPairs(universe: Set[Position]): Int = {
    val yDistances = sumDistances(universe.toList.map(_.y))
    val xDistances = sumDistances(universe.toList.map(_.x))

    (yDistances + xDistances) / 2
  }

  private def sumDistances(numbers: List[Int]): Int =
    numbers
      .flatMap(p1 => (numbers.filterNot(_ == p1)).map(p2 => (Math.min(p1, p2), Math.max(p1, p2))))
      .map { case (p1, p2) => p2 - p1 }
      .foldLeft(0)(_ + _)
}

trait Day11 {
  def sumDistancesInUniverse(raw: String): Int
  def parseUniverse(raw: String): Set[Position]
  def expandUniverse(universe: Set[Position]): Set[Position]
  def sumDistancesBetweenPairs(universe: Set[Position]): Int
}
