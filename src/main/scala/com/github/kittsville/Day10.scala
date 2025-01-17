package com.github.kittsville

object Day10Solution extends Day10 {
  def furthestDistance(maze: String): Int = {
    val parsedMaze = parseMaze(maze)

    val startPos = {
      val y = parsedMaze.indexWhere(row => row.contains('S'))
      val x = parsedMaze(y).indexOf('S')
      Position(x, y)
    }

    def findLoopPositions(
        currentPosition: Position,
        previousPositions: Set[Position]
    ): Set[Position] = {
      val surroundingPositions = Set(
        currentPosition.copy(x = currentPosition.x - 1), // up
        currentPosition.copy(x = currentPosition.x + 1), // down
        currentPosition.copy(y = currentPosition.y - 1), // left
        currentPosition.copy(y = currentPosition.y + 1) // right
      )
      val unvisitedSurroundingPositions = surroundingPositions.filterNot(previousPositions.contains)
      val loopContinuesTo = unvisitedSurroundingPositions.filter(positionsConnect(parsedMaze, currentPosition))

      val newPreviousPositions = previousPositions + currentPosition

      loopContinuesTo.headOption.fold(newPreviousPositions)(newPositon =>
        findLoopPositions(loopContinuesTo.head, newPreviousPositions)
      )
    }

    findLoopPositions(startPos, Set(startPos)).size / 2
  }

  def parseMaze(maze: String): Vector[Vector[Char]] = maze.linesIterator.flatMap {
    case ""   => None
    case line => Some(line.toCharArray().toVector)
  }.toVector

  def positionsConnect(parsedMaze: Vector[Vector[Char]], a: Position)(b: Position): Boolean = {
    val aConnections = evaluatePosition(parsedMaze, a).map(charConnections).getOrElse(Set.empty)
    val bConnections = evaluatePosition(parsedMaze, b).map(charConnections).getOrElse(Set.empty)

    val aToBDirection: Direction = (b.y - a.y, b.x - a.x) match {
      case (yDiff, _) if yDiff > 0 => Down
      case (yDiff, _) if yDiff < 0 => Up
      case (_, xDiff) if xDiff > 0 => Right
      case (_, xDiff) if xDiff < 0 => Left
      case _ => throw new IllegalArgumentException(s"Unable to identify if identical positions are connected: $a, $b")
    }

    // parsedMaze.foreach(println)
    // println(a, b)
    // println(aToBDirection)
    // println(aConnections)
    // println(bConnections)

    aToBDirection match {
      case Down  => aConnections.contains(Down) && bConnections.contains(Up)
      case Up    => aConnections.contains(Up) && bConnections.contains(Down)
      case Right => aConnections.contains(Right) && bConnections.contains(Left)
      case Left  => aConnections.contains(Left) && bConnections.contains(Right)
    }
  }

  private def evaluatePosition(maze: Vector[Vector[Char]], pos: Position): Option[Char] =
    maze.lift(pos.y).flatMap(row => row.lift(pos.x))

  private def charConnections(char: Char): Set[Direction] = char match {
    case 'S'     => Set(Up, Down, Left, Right)
    case '.'     => Set.empty
    case '-'     => Set(Left, Right)
    case 'F'     => Set(Down, Right)
    case 'J'     => Set(Up, Left)
    case '|'     => Set(Up, Down)
    case '7'     => Set(Down, Left)
    case 'L'     => Set(Up, Right)
    case unknown => throw new IllegalArgumentException(s"Unknown maze symbol: '$unknown'")
  }

  private sealed trait Direction

  private final case object Up extends Direction
  private final case object Down extends Direction
  private final case object Left extends Direction
  private final case object Right extends Direction
}

trait Day10 {
  def furthestDistance(maze: String): Int
}
