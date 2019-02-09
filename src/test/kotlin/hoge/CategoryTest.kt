package hoge

import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CategoryTest {
    @Test
    fun 品目の名前を与えると軽減税率対象かを返す__foodは軽減税率対象である() {
        val isReducable = Category.food.isReducable()
        assertTrue(isReducable)
    }

    @Test
    fun 品目の名前を与えると軽減税率対象かを返す__liquorは軽減税率対象ではない() {
        val isReduced = Category.liquor.isReducable()
        assertFalse(isReduced)
    }
}