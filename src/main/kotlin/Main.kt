import java.util.*
import kotlin.math.*
val scanner = Scanner(System.`in`)
class Point(val x: Double, val y: Double) {
    // Рассточние от это1 точки до заданной
    fun distanceTo(other: Point): Double {
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }
}
class Triangle(val a: Point, val b: Point, val c: Point) {
    // Проверяет, находится ли точка p внутри треугольника
    fun containsPoint(p: Point): Boolean {
        val areaABC = calculateTriangleArea(a, b, c)
        val areaPBC = calculateTriangleArea(p, b, c)
        val areaPCA = calculateTriangleArea(p, c, a)
        val areaPAB = calculateTriangleArea(p, a, b)

        // Сумма площадей треугольников PBC, PCA и PAB должна быть равна площади треугольника ABC
        return abs(areaPBC + areaPCA + areaPAB - areaABC) < 0.0001
    }

    // Рассчитывает площадь треугольника, заданного тремя точками
    private fun calculateTriangleArea(p1: Point, p2: Point, p3: Point): Double {
        val area = 0.5 * ((p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y))
        return abs(area)
    }
}

fun main() {
    when (getInput("""
        1. Треугольник описан координатами трех своих вершин. Указаны координаты отдельной точки. Находится ли точка внутри треугольника.
        2. Найти расстояние между указанными точками.
        3. Найти минимальное и максимальное расстояние между точками.
        Выберите программу для проверки: 
    """.trimIndent())) {
        "1" -> {
            /*
                0.0, 0.0, 4.0, 0.0, 2.0, 4.0
                2.5 1
            */
            println("Задайте треугольник (3 точки через пробел в формате: Х У)")
            val triPoints = scanner.nextLine().split(" ").map { it.toDouble() }
            val triangle = Triangle(
                Point(triPoints[0], triPoints[1]),
                Point(triPoints[2], triPoints[3]),
                Point(triPoints[4], triPoints[5])
            )

            print("Задайте точку, для которой нужно проверить её положение относительно треугольника: ")
            val coords = scanner.nextLine().split(" ").map { it.toDouble() }
            if (triangle.containsPoint(Point(coords[0], coords[1]))) {
                println("Точка находится внутри треугольника.")
            } else {
                println("Точка находится вне треугольника.")
            }
        }

        "2" -> { // 1 2 4 6
            print("Введите координаты первой точки через пробел: ")
            val coords1 = scanner.nextLine().split(" ").map { it.toDouble() }
            print("Введите координаты второй точки через пробел: ")
            val coords2 = scanner.nextLine().split(" ").map { it.toDouble() }
            val point1 = Point(coords1[0], coords1[1])
            println("Расстояние между точками: ${point1.distanceTo(Point(coords2[0], coords2[1]))}")
        }

        "3" -> {
            /*
                4
                0 3
                3 0
                0 0
                1 1

                1.4142
                4.2426
            */
            val numPoints = getInput("Введите количество точек: ").toInt()
            val points = mutableListOf<Point>()
            for (i in 1..numPoints) {
                print("Введите координаты точки $i через пробел: ")
                val coords = scanner.nextLine().split(" ").map { it.toDouble() }
                points.add(Point(coords[0], coords[1]))
            }

            var minDistance = Double.MAX_VALUE
            var maxDistance = 0.0
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val distance = points[i].distanceTo(points[j])
                    if (distance < minDistance) minDistance = distance
                    if (distance > maxDistance) maxDistance = distance
                }
            }
            println("Минимальное расстояние между точками: $minDistance")
            println("Максимальное расстояние между точками: $maxDistance")
        }
    }
}

fun getInput(prompt: String): String {
    print(prompt)
    return readln()
}