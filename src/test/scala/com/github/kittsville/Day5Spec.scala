package com.github.kittsville

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
}
