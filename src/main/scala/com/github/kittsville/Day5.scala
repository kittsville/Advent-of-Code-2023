package com.github.kittsville

import scala.collection.immutable.NumericRange

object Day5Solution {
  def closestLocationOfRanges(almanac: String): Long = {
    val lines = almanac.linesIterator.toList
    val seeds = lines.head
      .split(':')
      .tail
      .head
      .strip()
      .split(' ')
      .map(_.toLong)
      .toList
      .sliding(2, 2)
      .collect { case List(start, length) =>
        seedsFromRange(start, length)
      }
      .toSeq
    val rawMappings = lines.tail.mkString("\n")
    val mappingBlocks = parseMappings(rawMappings).map(RangeBlock.apply)

    mappingBlocks.foldLeft(seeds)(applyMappings).map(_.start).min
  }

  def applyMappings(seedRanges: Seq[NumericRange[Long]], block: RangeBlock): Seq[NumericRange[Long]] = {
    val (unmodifiedSeedRanges, newSeedRanges) =
      block.mappingRanges.foldLeft((seedRanges, Seq.empty[NumericRange[Long]])) {
        case ((sR, nSR), range) => {
          sR.map(applyMappingToSeedRange(range))
            .foldLeft((Seq.empty[NumericRange[Long]], nSR)) { case ((lUSR, lMSR), (rUSR, rMSR)) =>
              (lUSR ++ rUSR, lMSR ++ rMSR)
            }
        }
      }
    unmodifiedSeedRanges ++ newSeedRanges
  }

  def applyMappingToSeedRange(mappingRange: MappingRange)(
      seedRange: NumericRange[Long]
  ): (Seq[NumericRange[Long]], Seq[NumericRange[Long]]) = {

    if (seedRange.start == mappingRange.from.start && seedRange.end == mappingRange.from.end) {
      val unmodified = Seq.empty[NumericRange[Long]]
      val modifed = Seq((seedRange.start + mappingRange.diff to seedRange.end + mappingRange.diff))

      (unmodified, modifed)
    } else if (seedRange.contains(mappingRange.from.start) && seedRange.contains(mappingRange.from.end)) {
      val unmodified = Seq(
        (seedRange.start to mappingRange.from.start - 1),
        (mappingRange.from.end + 1 to seedRange.end)
      )
      val modifed = Seq((mappingRange.from.start + mappingRange.diff to mappingRange.from.end + mappingRange.diff))

      (unmodified, modifed)
    } else if (mappingRange.from.contains(seedRange.start) && mappingRange.from.contains(seedRange.end)) {
      val unmodified = Seq.empty[NumericRange[Long]]
      val modifed = Seq((seedRange.start + mappingRange.diff to seedRange.end + mappingRange.diff))

      (unmodified, modifed)
    } else if (seedRange.contains(mappingRange.from.start) && !seedRange.contains(mappingRange.from.end)) {
      val unmodified = (seedRange.start to mappingRange.from.start - 1)
      val modifed = (mappingRange.from.start + mappingRange.diff to seedRange.end + mappingRange.diff)

      (Seq(unmodified), Seq(modifed))
    } else if (seedRange.contains(mappingRange.from.end) && !seedRange.contains(mappingRange.from.start)) {
      val unmodified = Seq((mappingRange.from.end + 1 to seedRange.end))
      val modifed = Seq((seedRange.start + mappingRange.diff to mappingRange.from.end + mappingRange.diff))

      (unmodified, modifed)
    } else (Seq(seedRange), Seq.empty[NumericRange[Long]])
  }

  def closestLocation(almanac: String): Long = {
    val lines = almanac.linesIterator.toList
    val seeds = lines.head.split(':').tail.head.strip().split(' ').map(_.toLong).toList
    val rawMappings = lines.tail.mkString("\n")
    val mappingBlocks = parseMappings(rawMappings)

    closestLocation(seeds, mappingBlocks)
  }

  private def seedsFromRange(start: Long, length: Long): NumericRange[Long] =
    Range.Long.inclusive(start, start + length - 1, 1)

  private def closestLocation(seeds: Iterable[Long], mappingBlocks: Seq[MappingBlock]): Long =
    seeds.map(seed => convert(seed, mappingBlocks)).reduce((a, b) => Math.min(a, b))

  def convert(seed: String, mappings: String): Long =
    convert(seed.toLong, parseMappings(mappings))

  private def convert(seed: Long, mappingBlocks: Seq[MappingBlock]): Long =
    mappingBlocks.foldLeft(seed)((value, block) =>
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

case class RangeBlock(name: String, mappingRanges: Seq[MappingRange])

object RangeBlock {
  def apply(mappingBlock: MappingBlock): RangeBlock =
    RangeBlock(mappingBlock.name, mappingRanges = mappingBlock.mappings.map(MappingRange.apply))
}

case class MappingRange(from: NumericRange[Long], diff: Long)

object MappingRange {
  def apply(mapping: Mapping): MappingRange =
    MappingRange(
      from = (mapping.start to mapping.end),
      diff = mapping.diff
    )
}

case class MappingBlock(name: String, mappings: Seq[Mapping])
case class Mapping(start: Long, length: Long, diff: Long) {
  def mapValue(value: Long): Option[Long] = {
    if (value >= start && value < (start + length)) Some(value + diff)
    else None
  }

  def end: Long = start + (length - 1)
  def destinationStart: Long = start + diff
  def destinationEnd: Long = end + diff
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
  def closestLocationOfRanges(almanac: String): Long
  def applyMappings(seedRanges: Seq[NumericRange[Long]], block: RangeBlock): Seq[NumericRange[Long]]
  def applyMappingToSeedRange(mappingRange: MappingRange)(
      seedRange: NumericRange[Long]
  ): (Seq[NumericRange[Long]], Seq[NumericRange[Long]])
}
