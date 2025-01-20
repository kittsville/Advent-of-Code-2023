package com.github.kittsville

object Day11Solution extends Day11 {
  def sumDistancesInUniverse(raw: String): Long = sumDistancesBetweenPairs(
    expandUniverse(parseUniverse(raw), 2)
  )

  def sumDistancesInOldUniverse(raw: String): Long = sumDistancesBetweenPairs(
    expandUniverse(parseUniverse(raw), 1000000)
  )

  def parseUniverse(raw: String): Set[Position] = raw.linesIterator.zipWithIndex.flatMap {
    case ("", _) => List.empty[Position]
    case (row, y) =>
      row.toCharArray.zipWithIndex.collect { case ('#', x) =>
        Position(x, y)
      }
  }.toSet

  def expandUniverse(universe: Set[Position], expansionFactor: Int): Set[Position] = {
    val clearYValues = identifyGaps(universe.map(_.y))
    val clearXValues = identifyGaps(universe.map(_.x))

    val multiplier = expansionFactor - 1

    universe.map(galaxy => {
      Position(
        x = galaxy.x + (clearXValues.count(_ < galaxy.x) * multiplier),
        y = galaxy.y + (clearYValues.count(_ < galaxy.y) * multiplier)
      )
    })
  }

  private def identifyGaps(numbers: Set[Int]): Set[Int] = (0 to numbers.max).filterNot(numbers.contains).toSet

  def sumDistancesBetweenPairs(universe: Set[Position]): Long = {
    val yDistances = sumDistances(universe.toList.map(_.y))
    val xDistances = sumDistances(universe.toList.map(_.x))

    (yDistances + xDistances) / 2
  }

  private def sumDistances(numbers: List[Int]): Long =
    numbers
      .flatMap(p1 => (numbers.filterNot(_ == p1)).map(p2 => (Math.min(p1, p2), Math.max(p1, p2))))
      .map { case (p1, p2) => (p2 - p1).toLong }
      .foldLeft(0L)(_ + _)
}

trait Day11 {
  def sumDistancesInUniverse(raw: String): Long
  def sumDistancesInOldUniverse(raw: String): Long
  def parseUniverse(raw: String): Set[Position]
  def expandUniverse(universe: Set[Position], expansionFactor: Int): Set[Position]
  def sumDistancesBetweenPairs(universe: Set[Position]): Long
}
