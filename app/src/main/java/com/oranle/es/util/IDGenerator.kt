package com.oranle.es.util

import java.util.concurrent.atomic.AtomicInteger

object IDGenerator {

    private val count = AtomicInteger()

    val id: Int
        get() = count.incrementAndGet()


    @JvmStatic
    fun main(args: Array<String>) {

        val r = Runnable {
            val id = id
            println("Thread:" + Thread.currentThread().name + ", id: " + id)
        }

        val t1 = Thread(r)
        val t2 = Thread(r)
        val t3 = Thread(r)
        val t4 = Thread(r)
        val t5 = Thread(r)
        val t6 = Thread(r)
        t1.start()
        t2.start()
        t5.start()
        t6.start()
        t3.start()
        t4.start()


        val t = "答案：AAAAA   BAAAB  BABBB  D"
        val b = t.contains("答案：")
        val indexOf = t.indexOf("：")
        val substring = t.substring(indexOf + 1).replace(" ", "").toCharArray()
        val stringList = arrayOfNulls<String>(substring.size)
        substring.forEachIndexed { index: Int, c: Char ->
            stringList.set(index, c.toString())
        }
        val list = stringList.toList()
        println("list $list")
    }

}

