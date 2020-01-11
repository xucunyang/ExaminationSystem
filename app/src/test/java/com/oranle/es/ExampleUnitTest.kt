package com.oranle.es

import com.oranle.es.module.examination.FIRST_LETTER
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        var char: Char = 'a'

        val fisrtLetter = 65

        val char1 = fisrtLetter.toChar()

        for(index in 0..20) {
//            val char = (fisrtLetter + index).toChar()

            val char = (index + FIRST_LETTER).toChar().toString()

            println("xxccccxxx ${char}")
        }

    }
}
