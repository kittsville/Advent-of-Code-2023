package com.github.kittsville

object Day14Solution extends Day14 {
  def rolledLoad(platform: String): Int = {
    val rows = platform.linesIterator.toList.filterNot(_ == "")

    val baseAcc = Accumulator(0, (0 to rows.head.length).map(_ -> 0).toMap)

    rows.zipWithIndex
      .foldLeft(baseAcc) {
        case (rowAcc, (row, y)) => {
          row.toCharArray().zipWithIndex.foldLeft(rowAcc) {
            case (acc, ('.', _)) => acc
            case (acc, ('O', x)) => {
              val newTotal = acc.total + (rows.length - acc.minimums(x))
              val newMinimums = acc.minimums + (x -> (acc.minimums(x) + 1))

              acc.copy(total = newTotal, minimums = newMinimums)
            }
            case (acc, ('#', x)) => {
              val newMinimums = acc.minimums + (x -> (y + 1))

              acc.copy(minimums = newMinimums)
            }
            case (_, (char, x)) => throw new IllegalArgumentException(s"Unexpected char '$char' at position ($x, $y)")
          }
        }
      }
      .total
  }

  private case class Accumulator(total: Int, minimums: Map[Int, Int])
}

trait Day14 {
  def rolledLoad(platform: String): Int
}
