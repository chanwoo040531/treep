package me.chnu.treep

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TreepApplicationTests {

    fun <T, U, V> Iterable<T>.mapWith(iterable: Iterable<U>, func: (T, U) -> V): List<V> {
        val iteratorOfT = this.iterator()
        val iteratorOfU = iterable.iterator()

        val arrayList = ArrayList<V>()

        while (iteratorOfT.hasNext() and iteratorOfU.hasNext()) {
            arrayList.add(func(iteratorOfT.next(), iteratorOfU.next()))
        }
        return arrayList
    }

    @Test
    fun contextLoads() {

        val a = listOf(1, 2, 3, 4, 5)
        val b = listOf("A", "B", "C", "D")
        val result = a.mapWith(b) { aa, bb -> "$bb : $aa" }

        println(result)
    }

}

fun <T, U, V> Iterable<T>.mapWith(iterable: Iterable<U>, func: (T, U) -> V): List<V> {
    val iteratorOfT = this.iterator()
    val iteratorOfU = iterable.iterator()

    val arrayList = ArrayList<V>()

    while (iteratorOfT.hasNext() and iteratorOfU.hasNext()) {
        arrayList.add(func(iteratorOfT.next(), iteratorOfU.next()))
    }
    return arrayList
}


fun main() {

}