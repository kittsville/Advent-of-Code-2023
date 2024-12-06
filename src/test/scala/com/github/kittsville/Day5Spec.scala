package com.github.kittsville

import scala.collection.immutable.NumericRange

class Day5Spec extends munit.FunSuite {
  test("Retain values if no mapping exists") {
    val mappings = """first map:
                     |50 98 2
                     |52 50 48""".stripMargin
    assertEquals(Day5Solution.convert("49", mappings), 49.toLong)
  }

  test("Map value using ranges") {
    val mappings = """first map:
                     |50 98 2
                     |52 50 48""".stripMargin
    assertEquals(Day5Solution.convert("50", mappings), 52.toLong)
    assertEquals(Day5Solution.convert("51", mappings), 53.toLong)
    assertEquals(Day5Solution.convert("65", mappings), 67.toLong)
  }

  test("Map value using mutliple blocks of ranges") {
    val mappings = """seed-to-soil map:
                     |50 98 2
                     |52 50 48

                     |soil-to-fertilizer map:
                     |0 15 37
                     |37 52 2
                     |39 0 15

                     |fertilizer-to-water map:
                     |49 53 8
                     |0 11 42
                     |42 0 7
                     |57 7 4""".stripMargin
    assertEquals(Day5Solution.convert("79", mappings), 81.toLong)
  }

  test("Find the closest location to for a given list of seeds") {
    val input = """seeds: 79 14 55 13

                     |seed-to-soil map:
                     |50 98 2
                     |52 50 48

                     |soil-to-fertilizer map:
                     |0 15 37
                     |37 52 2
                     |39 0 15

                     |fertilizer-to-water map:
                     |49 53 8
                     |0 11 42
                     |42 0 7
                     |57 7 4

                     |water-to-light map:
                     |88 18 7
                     |18 25 70

                     |light-to-temperature map:
                     |45 77 23
                     |81 45 19
                     |68 64 13

                     |temperature-to-humidity map:
                     |0 69 1
                     |1 0 69

                     |humidity-to-location map:
                     |60 56 37
                     |56 93 4""".stripMargin

    assertEquals(Day5Solution.closestLocation(input), 35.toLong)
  }

  test("Find the closest location to for a given list of seed ranges") {
    val input = """seeds: 79 14 55 13

                     |seed-to-soil map:
                     |50 98 2
                     |52 50 48

                     |soil-to-fertilizer map:
                     |0 15 37
                     |37 52 2
                     |39 0 15

                     |fertilizer-to-water map:
                     |49 53 8
                     |0 11 42
                     |42 0 7
                     |57 7 4

                     |water-to-light map:
                     |88 18 7
                     |18 25 70

                     |light-to-temperature map:
                     |45 77 23
                     |81 45 19
                     |68 64 13

                     |temperature-to-humidity map:
                     |0 69 1
                     |1 0 69

                     |humidity-to-location map:
                     |60 56 37
                     |56 93 4""".stripMargin

    assertEquals(Day5Solution.closestLocationOfRanges(input), 46.toLong)
  }

//   test("Merge mapping blocks with no overlap") {
//     val a = RangeBlock("a to b", List(MappingRange(1, 5, 2)))
//     val b = RangeBlock("b to c", List(MappingRange(10, 5, 2)))
//     val expected = RangeBlock(
//       "a to b then b to c",
//       List(MappingRange(1, 5, 2), MappingRange(10, 5, 2))
//     )

//     assertEquals(Day5Solution.merge(a, b), expected)
//   }

//   test("Merge mapping blocks with perfect overlap") {
//     val a = RangeBlock("a to b", List(MappingRange(1, 5, 2)))
//     val b = RangeBlock("b to c", List(MappingRange(3, 5, -3)))
//     val expected = RangeBlock(
//       "a to b then b to c",
//       List(MappingRange(1, 5, -1))
//     )

//     assertEquals(Day5Solution.merge(a, b), expected)
//   }

  test("Doesn't modify a seed range") {
    val mappingRange = MappingRange((6L to 10L), -2L)
    val seedRange = (1L to 5L)
    val expectedUnmodified = Seq((1L to 5L))
    val expectedModified = Seq.empty[NumericRange[Long]]

    val (actualUnmodified, actualModified) = Day5Solution.applyMappingToSeedRange(mappingRange)(seedRange)

    assertEquals(actualUnmodified, expectedUnmodified)
    assertEquals(actualModified, expectedModified)
  }

  test("Modifies a seed range whose end touches a mapping range") {
    val mappingRange = MappingRange((3L to 10L), 2L)
    val seedRange = (1L to 5L)
    val expectedUnmodified = Seq((1L to 2L))
    val expectedModified = Seq((5L to 7L))

    val (actualUnmodified, actualModified) = Day5Solution.applyMappingToSeedRange(mappingRange)(seedRange)

    assertEquals(actualUnmodified, expectedUnmodified)
    assertEquals(actualModified, expectedModified)
  }

  test("Modifies a seed range contained entirely within a mapping range") {
    val mappingRange = MappingRange((1L to 10L), 2L)
    val seedRange = (3L to 7L)
    val expectedUnmodified = Seq.empty[NumericRange[Long]]
    val expectedModified = Seq((5L to 9L))

    val (actualUnmodified, actualModified) = Day5Solution.applyMappingToSeedRange(mappingRange)(seedRange)

    assertEquals(actualUnmodified, expectedUnmodified)
    assertEquals(actualModified, expectedModified)
  }

  test("Modifies a seed range whose start touches a mapping range") {
    val mappingRange = MappingRange((1L to 7L), -2L)
    val seedRange = (5L to 10L)
    val expectedUnmodified = Seq((8L to 10L))
    val expectedModified = Seq((3L to 5L))

    val (actualUnmodified, actualModified) = Day5Solution.applyMappingToSeedRange(mappingRange)(seedRange)

    assertEquals(actualUnmodified, expectedUnmodified)
    assertEquals(actualModified, expectedModified)
  }

  test("Modifies a seed range that is larger than the mapping range") {
    val mappingRange = MappingRange((3L to 7L), 10L)
    val seedRange = (1L to 10L)
    val expectedUnmodified = Seq((1L to 2L), (8L to 10L))
    val expectedModified = Seq((13L to 17L))

    val (actualUnmodified, actualModified) = Day5Solution.applyMappingToSeedRange(mappingRange)(seedRange)

    assertEquals(actualUnmodified, expectedUnmodified)
    assertEquals(actualModified, expectedModified)
  }

  test("Applies multiple mappings to multiple seed ranges") {
    val seedRanges = Seq((1L to 3L), (5L to 10L))
    val rangeBlock = RangeBlock("derp", Seq(MappingRange((1L to 5L), 2L), MappingRange((7L to 8L), 10L)))
    val expectedSeedRanges = Set[NumericRange[Long]]((3L to 5L), (7L to 7L), (6L to 6L), (17L to 18L), (9L to 10L))
    val actualSeedRanges = Day5Solution.applyMappings(seedRanges, rangeBlock).toSet

    assertEquals(actualSeedRanges, expectedSeedRanges)
  }
}
