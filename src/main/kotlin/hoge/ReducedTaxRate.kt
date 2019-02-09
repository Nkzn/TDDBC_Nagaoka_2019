package hoge

import java.lang.IllegalArgumentException

data class ProductAmount(val name: String, val exTax: Int, val amount: Int)

object ReducedTaxRate {
    fun isReducable(productName: String): Boolean = convertProductToCategory(productName).isReducable()

    fun convertProductToCategory(productName: String): Category {
        return when (productName) {
            "オロナミンC" -> Category.beverage
            "リポビタンD" -> Category.quasi_drug
            "手巻直火焼き紅しゃけ" -> Category.food
            "大きなおむすびマヨネーズ" -> Category.food
            "からあげ棒" -> Category.food
            "キリン 生茶 555ml ペットボトル" -> Category.beverage
            "キリンチューハイ氷結グレープフルーツ350ml缶" -> Category.liquor
            "ストロングゼロ〈ダブルグレープフルーツ〉350ml缶" -> Category.liquor
            "新ルルＡ錠ｓ 50錠" -> Category.drug
            else -> throw IllegalArgumentException()
        }
    }

    fun calcInTax(productName: String, exTax: Int): Int {
        return (exTax * if (isReducable(productName)) 1.08 else 1.10).toInt()
    }

    fun calcSameProduct(productName: String, price: Int, amout: Int): Int {
        return calcInTax(productName, price) * amout
    }

    fun calcMultiProduct(vararg productAmounts: ProductAmount): Int {
        return productAmounts.map { calcSameProduct(it.name, it.exTax, it.amount) }.sum()
    }
}

enum class Category {
    food,
    beverage,
    liquor,
    drug,
    quasi_drug;

    fun isReducable(): Boolean {
        return when (this) {
            food, beverage -> true
            else -> false
        }
    }
}