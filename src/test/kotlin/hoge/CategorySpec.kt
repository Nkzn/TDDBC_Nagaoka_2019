package hoge

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object CategorySpec : Spek({
    describe("品目の名前を与えると軽減税率対象かを返す") {
        it ("foodは軽減税率対象である") {
            val actual = Category.food.isReducable()
            assertTrue(actual)
        }
        it ("liquorは軽減税率対象ではない") {
            val actual = Category.liquor.isReducable()
            assertFalse(actual)
        }
    }
})