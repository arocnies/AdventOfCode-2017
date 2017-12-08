package day3

import sun.util.calendar.CalendarUtils.mod
import kotlin.math.abs

val example1 = ""
val example2 = ""
val example3 = ""
val example4 = ""

fun main(args: Array<String>) {
    println(part1(1))
    println(part1(12))
    println(part1(23))
    println(part1(1024))
    println(part1(368078))
    println("Corners")
    var corner = center
    (0..100).forEach {
        println("p$corner")
        corner = corner.nextCorner
    }
}

fun part1(target: Int): Int {
    println("------\nTarget: $target")
    val nearestBRCorner = findNearestBottomRightCorner(target)
    return nearestBRCorner.size / 2 + abs(findDistFromCenterLine(target, nearestBRCorner))
}

fun findNearestBottomRightCorner(target: Int): Corner {
    var currentCorner = center
    var nextCorner = currentCorner.nextCorner

    while (diff(target, currentCorner) > diff(target, nextCorner)) {
        currentCorner = nextCorner
        nextCorner = currentCorner.nextCorner
    }

    if (target > currentCorner.value) currentCorner = currentCorner.nextCorner

    println("Nearest BR corner = $currentCorner")
    return currentCorner
}

fun findDistFromCenterLine(target: Int, brCorner: Corner): Int {
    if (brCorner.size == 1) return 0

    val distFromBrCorner = target - brCorner.value
//    val distFromPreviousCorner = ((distFromBrCorner - 1) / 2) + brCorner.cornerIndex
    val midLineIndex = (brCorner.size + 1) / 2

    val distFromCenter = mod(distFromBrCorner, brCorner.size - 1) - (brCorner.size - 1) / 2
    println("Dist from centerline = $distFromCenter")

    return distFromCenter

}

// Utilities:
fun diff(i1: Int, corner: Corner) = abs(i1 - corner.value)
val center = Corner(1, 1)
data class Corner(val cornerIndex: Int, val size: Int, val value: Int = size * size) {
    val nextCorner by lazy { Corner(cornerIndex + 1, size + 2) }
}
// (-2 % 3) - 3 + 1

/*
1x1 3x3 5x5 7x7 9x9 --> bottom right corner values
1   2   3   4   5

 E 1+2n  => 1, 3, 5, 7
n=0

Find nearest B-R corner value.
Find it's diff from the center on it's line
Calculate dist using the line's dist + diff.

       y y y y y
         x x x y
 |---|   x 1 x y
 | X |   x x x y
 |___|



        1x1

          8 3x3

                5x5
--------------------
[(5) % [(5+1) / 2]] - [(5+1) / 2] + 1
(distFromBR % midline) - midline + 1

size / 2 = midLineIndex



 */