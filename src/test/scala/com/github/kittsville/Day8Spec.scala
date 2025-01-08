package com.github.kittsville

class Day8Spec extends munit.FunSuite {
  test("Parses a desert map") {
    val raw = """RL

                |AAA = (BBB, CCC)
                |BBB = (DDD, EEE)
                |CCC = (ZZZ, GGG)
                |DDD = (DDD, DDD)
                |EEE = (EEE, EEE)
                |GGG = (GGG, GGG)
                |ZZZ = (ZZZ, ZZZ)""".stripMargin

    val expected = RouteMap(
      Seq(Right, Left),
      Map(
        Node("AAA") -> Edges(Node("BBB"), Node("CCC")),
        Node("BBB") -> Edges(Node("DDD"), Node("EEE")),
        Node("CCC") -> Edges(Node("ZZZ"), Node("GGG")),
        Node("DDD") -> Edges(Node("DDD"), Node("DDD")),
        Node("EEE") -> Edges(Node("EEE"), Node("EEE")),
        Node("GGG") -> Edges(Node("GGG"), Node("GGG")),
        Node("ZZZ") -> Edges(Node("ZZZ"), Node("ZZZ"))
      )
    )
    assertEquals(Day8Solution.parse(raw), expected)
  }

  test("Count the steps needed to reach the end") {
    val routeMap = RouteMap(
      Seq(Right, Left),
      Map(
        Node("AAA") -> Edges(Node("BBB"), Node("CCC")),
        Node("BBB") -> Edges(Node("DDD"), Node("EEE")),
        Node("CCC") -> Edges(Node("ZZZ"), Node("GGG")),
        Node("DDD") -> Edges(Node("DDD"), Node("DDD")),
        Node("EEE") -> Edges(Node("EEE"), Node("EEE")),
        Node("GGG") -> Edges(Node("GGG"), Node("GGG")),
        Node("ZZZ") -> Edges(Node("ZZZ"), Node("ZZZ"))
      )
    )
    val expectedStepCount = 2

    assertEquals(Day8Solution.countStepsToEnd(routeMap, Node("AAA"), 0), expectedStepCount)
  }

  test("Count the steps needed to reach the end of a more complex route") {
    val routeMap = RouteMap(
      Seq(Left, Left, Right),
      Map(
        Node("AAA") -> Edges(Node("BBB"), Node("BBB")),
        Node("BBB") -> Edges(Node("AAA"), Node("ZZZ")),
        Node("ZZZ") -> Edges(Node("ZZZ"), Node("ZZZ"))
      )
    )
    val expectedStepCount = 6

    assertEquals(Day8Solution.countStepsToEnd(routeMap, Node("AAA"), 0), expectedStepCount)
  }

  test("Parse a route map and count steps") {
    val raw = """RL
                    |
                    |AAA = (BBB, CCC)
                    |BBB = (DDD, EEE)
                    |CCC = (ZZZ, GGG)
                    |DDD = (DDD, DDD)
                    |EEE = (EEE, EEE)
                    |GGG = (GGG, GGG)
                    |ZZZ = (ZZZ, ZZZ)""".stripMargin

    assertEquals(Day8Solution.countSteps(raw), 2)
  }

  test("Ghost edition: Count the steps needed to reach the end".only) {
    val routeMap = RouteMap(
      Seq(Left, Right),
      Map(
        Node("11A") -> Edges(Node("11B"), Node("XXX")),
        Node("11B") -> Edges(Node("XXX"), Node("11Z")),
        Node("11Z") -> Edges(Node("11B"), Node("XXX")),
        Node("22A") -> Edges(Node("22B"), Node("XXX")),
        Node("22B") -> Edges(Node("22C"), Node("22C")),
        Node("22C") -> Edges(Node("22Z"), Node("22Z")),
        Node("22Z") -> Edges(Node("22B"), Node("22B")),
        Node("XXX") -> Edges(Node("XXX"), Node("XXX"))
      )
    )
    val expectedStepCount = 6

    assertEquals(
      Day8Solution.countGhostStepsToEnd(routeMap, routeMap.map.keys.filter(_.value.endsWith("A")).toSeq, 0),
      expectedStepCount
    )
  }
}
