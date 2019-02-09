package hoge

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object ReducedTaxRateSpec : Spek({
    describe("商品名を与えられると軽減税率対象か判定できる") {
        it ("オロナミンCは軽減税率対象") {
            val isReducable = ReducedTaxRate.isReducable("オロナミンC")
            assertTrue(isReducable)
        }

        it ("リポビタンDは軽減税率対象でない") {
            val isReducable = ReducedTaxRate.isReducable("リポビタンD")
            assertFalse(isReducable)
        }

        it ("知らない商品名のとき例外を投げる") {
            assertFailsWith<IllegalArgumentException> {
                ReducedTaxRate.isReducable("焼き鳥")
            }
        }
    }

    describe("商品名が品目に変換できる") {
        it ("リポビタンDは医薬部外品") {
            assertEquals(ReducedTaxRate.convertProductToCategory("リポビタンD"), Category.quasi_drug)
        }

        it ("オロナミンCは飲料品") {
            assertEquals(ReducedTaxRate.convertProductToCategory("オロナミンC"), Category.beverage)
        }

        it ("知らない商品名のとき例外を投げる") {
            assertFailsWith<IllegalArgumentException> {
                ReducedTaxRate.convertProductToCategory("イエーイ")
            }
        }
    }

    describe("税込み金額に変換できる") {
        it ("軽減税率対象内の税抜き金額と商品名を入力すると8パーセントが加算される") {
            assertEquals(ReducedTaxRate.calcInTax("オロナミンC", 105), 113)
        }

        it ("軽減税率対象内の税抜き金額と商品名を入力すると10パーセントが加算される") {
            assertEquals(ReducedTaxRate.calcInTax("リポビタンD", 146), 160)
        }
    }

    describe("合計金額に変換できる") {
        it ("複数の同一の商品を入力すると合計金額が帰る") {
            assertEquals(
                    ReducedTaxRate.calcSameProduct("手巻直火焼き紅しゃけ", 139, 2),
                    300)
        }

        it ("異なる複数の同一の商品を入力すると合計金額が帰る") {
            assertEquals(ReducedTaxRate.calcMultiProduct(
                    ProductAmount("手巻直火焼き紅しゃけ", 139, 2),
                    ProductAmount("キリンチューハイ氷結グレープフルーツ350ml缶", 141, 3)
            ), 765)
        }
    }

})