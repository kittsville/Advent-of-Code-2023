package com.github.kittsville

object Day5Solution {
  def closestLocation(almanac: String): Long = {
    val lines = almanac.linesIterator.toList
    val rawSeeds = lines.head.split(':').tail.head.strip().split(' ').map(_.toLong).toList
    val rawMappings = lines.tail.mkString("\n")
    val mappingBlocks = parseMappings(rawMappings)

    rawSeeds.map(seed => convert(seed.toString, rawMappings)).reduce((a, b) => Math.min(a, b))
  }

  def convert(seed: String, mappings: String): Long =
    convert(seed, parseMappings(mappings))

  private def convert(seed: String, mappingBlocks: Seq[MappingBlock]): Long =
    mappingBlocks.foldLeft(seed.toLong)((value, block) =>
      block.mappings.flatMap(_.mapValue(value)).headOption.getOrElse(value)
    )

  private def parseMappings(mappings: String): Seq[MappingBlock] = {
    mappings
      .split("\n\n")
      .map(_.split(":"))
      .collect { case Array(mappingName, rawRanges) =>
        MappingBlock(mappingName, rawRanges.strip().linesIterator.filterNot(_ == "").map(Mapping.apply).toList)
      }
      .toList
  }
}

case class MappingBlock(name: String, mappings: Seq[Mapping])
case class Mapping(start: Long, length: Long, diff: Long) {
  def mapValue(value: Long): Option[Long] = {
    if (value >= start && value < (start + length)) Some(value + diff)
    else None
  }
}

object Mapping {
  def apply(rawRange: String): Mapping = {
    val (destination, source, length) = rawRange.split(" ").toList match {
      case rawDestination :: rawSource :: rawLength :: Nil =>
        (rawDestination.toLong, rawSource.toLong, rawLength.toLong)
      case _ => throw InvalidMapping(rawRange)
    }
    val diff = destination - source

    Mapping(source, length, diff)
  }
}

case class InvalidMapping(raw: String)
    extends IllegalArgumentException(s"""Invalid mapping range. Expected 3 numbers got: "$raw"""")

trait Day5 {
  def convert(seed: String, mappings: String): String
  def closestLocation(almanac: String): Long
}
