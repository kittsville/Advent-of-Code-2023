package com.github.kittsville

class Day15Spec extends munit.FunSuite {
  test("Gets the hash of a single ASCII character") {
    assertEquals(Day15Solution.hash("H"), 200)
  }

  test("Accumulate the result of hashing multiple characters") {
    assertEquals(Day15Solution.hash("HASH"), 52)
  }

  test("Sums the result of multiple hashed steps") {
    val input = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"
    assertEquals(Day15Solution.sumStepHashes(input), 1320)
  }

  test("Ignores newlines in an initialization sequence") {
    val input = """rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
                  |
                  |
                  |""".stripMargin
    assertEquals(Day15Solution.sumStepHashes(input), 1320)
  }
}
