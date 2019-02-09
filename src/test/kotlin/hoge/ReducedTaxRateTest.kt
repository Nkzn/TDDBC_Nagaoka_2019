package hoge

import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ReducedTaxRateTest {

    @Test
    fun 商品名を与えられると軽減税率対象か判定できる__オロナミンCは軽減税率対象() {
        val isReducable = ReducedTaxRate.isReducable("オロナミンC")
        assertTrue(isReducable)
    }

    @Test
    fun 商品名を与えられると軽減税率対象か判定できる__リポビタンDは軽減税率対象でない() {
        val isReducable = ReducedTaxRate.isReducable("リポビタンD")
        assertFalse(isReducable)
    }

    @Test(expected = IllegalArgumentException::class)
    fun 商品名を与えられると軽減税率対象か判定できる__知らない商品名のとき例外を投げる() {
        ReducedTaxRate.isReducable("焼き鳥")
    }

    @Test
    fun 商品名が品目に変換できる__リポビタンDは医薬部外品() {
        assertEquals(ReducedTaxRate.convertProductToCategory("リポビタンD"), Category.quasi_drug)
    }

    @Test
    fun 商品名が品目に変換できる__オロナミンCは飲料品() {
        assertEquals(ReducedTaxRate.convertProductToCategory("オロナミンC"), Category.beverage)
    }

    @Test(expected = IllegalArgumentException::class)
    fun 商品名が品目に変換できる__知らない商品名のとき例外を投げる() {
        ReducedTaxRate.convertProductToCategory("イエーイ")
    }

    @Test
    fun 税込み金額に変換できる__軽減税率対象内の税抜き金額と商品名を入力すると8パーセントが加算される() {
        assertEquals(ReducedTaxRate.calcInTax("オロナミンC", 105), 113)
    }

    @Test
    fun 税込み金額に変換できる__軽減税率対象内の税抜き金額と商品名を入力すると10パーセントが加算される() {
        assertEquals(ReducedTaxRate.calcInTax("リポビタンD", 146), 160)
    }

    @Test
    fun 合計金額に変換できる__複数の同一の商品を入力すると合計金額が帰る() {
        assertEquals(
                ReducedTaxRate.calcSameProduct("手巻直火焼き紅しゃけ", 139, 2),
                300)
    }

    @Test
    fun 合計金額に変換できる__異なる複数の同一の商品を入力すると合計金額が帰る() {
        assertEquals(ReducedTaxRate.calcMultiProduct(
                ProductAmount("手巻直火焼き紅しゃけ", 139, 2),
                ProductAmount("キリンチューハイ氷結グレープフルーツ350ml缶", 141, 3)
        ), 765)
    }
}