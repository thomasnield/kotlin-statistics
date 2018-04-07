package org.nield.kotlinstatistics

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate

class NaiveBayesClassifierTest {

    @Test
    fun spamExample() {


        class Email(val message: String, val isSpam: Boolean)

        val emails = listOf(
                Email("Hey there! I thought you might find this interesting. Click here.", isSpam = true),
                Email("Get viagra for a discount as much as 90%", isSpam = true),
                Email("Viagra prescription for less", isSpam = true),
                Email("Even better than Viagra, try this new prescription drug", isSpam = true),

                Email("Hey, I left my phone at home. Email me if you need anything. I'll be in a meeting for the afternoon.", isSpam = false),
                Email("Please see attachment for notes on today's meeting. Interesting findings on your market research.", isSpam = false),
                Email("An item on your Amazon wish list received a discount", isSpam = false),
                Email("Your prescription drug order is ready", isSpam = false),
                Email("Your Amazon account password has been reset", isSpam = false),
                Email("Your Amazon order", isSpam = false)
        )

        val nbc = emails.toNaiveBayesClassifier(
                        featuresSelector = { it.message.splitWords().toSet() },
                        categorySelector = {it.isSpam }
                )


        // TEST 1
        val input = "discount viagra wholesale, hurry while this offer lasts".splitWords().toSet()
        val predictedCategory = nbc.predict(input)
        Assert.assertTrue(predictedCategory == true)

        // TEST 2
        val input2 = "interesting meeting on amazon cloud services discount program".splitWords().toSet()
        val predictedCategory2 = nbc.predict(input2)
        Assert.assertTrue(predictedCategory2 == false)

    }


    @Test
    fun bankTransactionExample() {

        class BankTransaction(
                val date: LocalDate,
                val amount: Double,
                val memo: String,
                val category: String? = null
        )

        val bankTransactions = listOf(
                BankTransaction(date = LocalDate.of(2018,3,13),
                        amount = 12.69,
                        memo = "WHOLEFDS HPK 10140",
                        category = "GROCERY"
                ),
                BankTransaction(date = LocalDate.of(2018,3,13),
                        amount = 4.64,
                        memo = "BIGGBY COFFEE #370",
                        category = "COFFEE"
                ),
                BankTransaction(date = LocalDate.of(2018,3,13),
                        amount = 14.23,
                        memo = "AMAZON SALE",
                        category = "ELECTRONICS"
                ),
                BankTransaction(date = LocalDate.of(2018,3,13),
                        amount = 7.99,
                        memo = "AMAZON KINDLE EBOOK SALE",
                        category = "BOOK"
                ),
                BankTransaction(date = LocalDate.of(2018,3,10),
                        amount = 5.40,
                        memo = "AMAZON VIDEO ON DEMAND",
                        category = "ENTERTAINMENT"
                ),
                BankTransaction(date = LocalDate.of(2018,3,10),
                        amount = 61.27,
                        memo = "WHOLEFDS PLN 10030",
                        category = "GROCERY"
                ),
                BankTransaction(date = LocalDate.of(2018,3,12),
                        amount = 61.27,
                        memo = "STARBUCKS COFFEE #370",
                        category = "COFFEE"
                ),
                BankTransaction(date = LocalDate.of(2018,3,7),
                        amount = 2.29,
                        memo = "REDBOX VIDEO RENTAL #271",
                        category = "ENTERTAINMENT"
                )
        )

        val nbc = bankTransactions.toNaiveBayesClassifier(
                featuresSelector = { it.memo.splitWords().toSet() },
                categorySelector = { it.category!! }
        )

        // TEST 1
        val input1 = BankTransaction(date = LocalDate.of(2018,3,31),
                amount = 13.99,
                memo = "NETFLIX VIDEO ON DEMAND #21"
        )
        val result1 = nbc.predictWithProbability(input1.memo.splitWords().toSet())
        Assert.assertTrue(result1?.category == "ENTERTAINMENT")


        // TEST 2
        val input2 = BankTransaction(date = LocalDate.of(2018,3,6),
                amount = 17.21,
                memo = "FROGG COFFEE BAR AND CREPERIE"
        )
        val result2 = nbc.predictWithProbability(input2.memo.splitWords().toSet())
        Assert.assertTrue(result2?.category == "COFFEE")
    }



    fun String.splitWords() =  split(Regex("\\s")).asSequence()
            .map { it.replace(Regex("[^A-Za-z]"),"").toLowerCase() }
            .filter { it.isNotEmpty() }
}