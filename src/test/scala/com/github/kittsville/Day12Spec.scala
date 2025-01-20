package com.github.kittsville

class Day12Spec extends munit.FunSuite {
  test("Reports 0 permutations for an undamaged spring report that runs out of grouped report values") {
    val report = "## 1"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 0)
  }

  test("Reports 0 permutations for an undamaged spring report that runs out of report values") {
    val report = "# 2"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 0)
  }

  test("Counts 1 permutation for an undamaged spring report") {
    val report = "## 2"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 1)
  }

  test("Counts 1 permutation for an undamaged spring report with an empty space") {
    val report = "##. 2"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 1)
  }

  test("Counts 0 permutations for an undamaged report that would require adjacent groups") {
    val report = "### 2,1"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 0)
  }

  test("Counts 1 permutation for a damaged report with two groups") {
    val report = "##?# 2,1"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 1)
  }

  test("Counts 0 permutations for a damaged report that would require adjacent groups") {
    val report = "#?# 2,1"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 0)
  }

  test("Counts 2 permutations for a damaged report with two groups") {
    val report = "##??? 2,1"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 2)
  }

  test("Does not split a group in an undamaged report into multiple parts") {
    val report = "#.# 2"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 0)
  }

  test("Does not split a group in a damaged report into multiple parts") {
    val report = "??? 2"

    assertEquals(Day12Solution.countPossiblePermutations(report, 1), 2)
  }

  test("Counts the correct permutations for all example") {
    Map(
      "???.### 1,1,3" -> 1,
      ".??..??...?##. 1,1,3" -> 4,
      "?#?#?#?#?#?#?#? 1,3,1,6" -> 1,
      "????.#...#... 4,1,1" -> 1,
      "????.######..#####. 1,6,5" -> 4,
      "?###???????? 3,2,1" -> 10
    ).foreach { case (report, expectedCount) =>
      assertEquals(
        Day12Solution.countPossiblePermutations(report, 1),
        expectedCount,
        s"Expected report '$report' to have $expectedCount permutations"
      )
    }
  }

  test("Sums the permutation count for each row") {
    val raw = """???.### 1,1,3
                |.??..??...?##. 1,1,3
                |?#?#?#?#?#?#?#? 1,3,1,6
                |????.#...#... 4,1,1
                |????.######..#####. 1,6,5
                |?###???????? 3,2,1""".stripMargin

    assertEquals(Day12Solution.sumRowPermutations(raw), 21)
  }

  test("Sums the permutation count for each unfolded row") {
    val raw = """???.### 1,1,3
                |.??..??...?##. 1,1,3
                |?#?#?#?#?#?#?#? 1,3,1,6
                |????.#...#... 4,1,1
                |????.######..#####. 1,6,5
                |?###???????? 3,2,1""".stripMargin

    assertEquals(Day12Solution.sumUnfoldedRowPermutations(raw), 525152)
  }
}
