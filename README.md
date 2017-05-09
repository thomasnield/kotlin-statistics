![](http://i.imgur.com/v3FqiEA.png) 
# Kotlin Statistics
### Math and statistical extensions for Kotlin

This library contains helpful extension functions to perform exploratory and production statistics in a Kotlin-idiomatic way.

**NOTE:** This library is a DSL around [Apache Commons Math](http://commons.apache.org/proper/commons-math/userguide/stat.html). Please feel free to contribute.

## Build Instructions

Until I get this released into Maven Central, you can use Maven or Gradle with JitPack to directly build a snapshot as a dependency.

**Gradle**

```
repositories {		
    maven { url 'https://jitpack.io' }
}
dependencies {
    compile 'com.github.thomasnield:kotlin-statistics:-SNAPSHOT'
}
```

**Maven**

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependency>
    <groupId>com.github.thomasnield</groupId>
    <artifactId>kotlin-statistics</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```

## Basic Operators

There are a number of extension function operators that support `Int`, `Long`, `Double`, `Float`, `BigDecimal` and `Short` numeric types for Sequences, Arrays, and Iterables:

* `descriptiveStatistics`
* `sum()`
* `average()`
* `min()`
* `max()`
* `mode()`
* `median()`
* `percentile()`
* `variance()`
* `standardDeviation()`
* `geometricMean()`
* `sumOfSquares()`
* `normalize()`
* `simpleRegression()`
* `kurtosis`
* `skewness`

Here is an example of using the `median()` extension function against a `Sequence` of Doubles:

```kotlin
val median = sequenceOf(1.0, 3.0, 5.0).median() 
println(median) // prints "3.0"
```


## Slicing Operators

There are also simple but powerful `xxxBy()` operators that allow you slice many of these statistical operators on a given key:

* `countBy()`
* `sumBy()`
* `averageBy()`
* `geometricMeanBy()`
* `minBy()`
* `maxBy()`
* `varianceBy()`
* `standardDeviationBy()`
* `descriptiveStatisticsBy()`
* `simpleRegressionBy()`

Below, we slice a sequence of `Item` objects by their lengths and get the averages and standard deviations by each length.

```kotlin
class Item(val name: String, val value: Double)

val sequence = sequenceOf(
        Item("Alpha", 4.0),
        Item("Beta", 6.0),
        Item("Gamma", 7.2),
        Item("Delta", 9.2),
        Item("Epsilon", 6.8),
        Item("Zeta", 2.4),
        Item("Iota", 8.8)
)

// find sums by name length
val sumsByLengths = sequence
       .sumBy(keySelector = { it.name.length }, doubleMapper = {it.value} )

println("Sums by lengths: $sumsByLengths") 

// find averages by name length
val averagesByLength = sequence
        .averageBy(keySelector = { it.name.length }, doubleMapper = {it.value})

println("Averages by lengths: $averagesByLength") 

//find standard deviations by name length
val standardDeviationsByLength = sequence
        .standardDeviationBy(keySelector = { it.name.length }, doubleMapper = {it.value}) 

println("Std Devs by lengths: $standardDeviationsByLength")
```

**OUTPUT:**

```
Sums by lengths: {5=20.4, 4=17.200000000000003, 7=6.8}
Averages by lengths: {5=6.8, 4=5.733333333333334, 7=6.8}
Std Devs by lengths: {5=2.1416504538945342, 4=2.619584360585134, 7=0.0}
```

These slicing operators are backed by a common `groupApply()` function, which can be used to implement other slicing operators easily. 


## Slicing Using Data Classes

You can slice on multiple fields using data classes with the `xxxBy()` operators as well. This is similar to using a GROUP BY on multiple fields in SQL:

```kotlin
//declare Product class
class Product(val id: Int,
              val name: String,
              val category: String,
              val section: Int,
              val defectRate: Double)

// Create list of Products
val products = listOf(Product(1, "Rayzeon", "ABR", 3, 1.1),
        Product(2, "ZenFire", "ABZ", 4, 0.7),
        Product(3, "HydroFlux", "ABR", 3, 1.9),
        Product(4, "IceFlyer", "ZBN", 1, 2.4),
        Product(5, "FireCoyote", "ABZ", 4, 3.2),
        Product(6, "LightFiber", "ABZ",2,  5.1),
        Product(7, "PyroKit", "ABR", 3, 1.4),
        Product(8, "BladeKit", "ZBN", 1, 0.5),
        Product(9, "NightHawk", "ZBN", 1, 3.5),
        Product(10, "NoctoSquirrel", "ABR", 2, 1.1),
        Product(11, "WolverinePack", "ABR", 3, 1.2)
        )

// Data Class for Grouping
data class Key(val category: String, val section: Int)

// Get Count by Category and Section
val countByCategoryAndSection =
        products.countBy { Key(it.category, it.section) }

println("Counts by Category and Section")
countByCategoryAndSection.entries.forEach { println(it) }

// Get Average Defect Rate by Category and Section
val averageDefectByCategoryAndSection =
        products.averageBy(keySelector = { Key(it.category, it.section) }, doubleMapper = { it.defectRate })

println("\nAverage Defect Rate by Category and Section")
averageDefectByCategoryAndSection.entries.forEach { println(it) }
```

**OUTPUT:**

```
Counts by Category and Section
Key(category=ABR, section=3)=4
Key(category=ABZ, section=4)=2
Key(category=ZBN, section=1)=3
Key(category=ABZ, section=2)=1
Key(category=ABR, section=2)=1

Average Defect Rate by Category and Section
Key(category=ABR, section=3)=1.4000000000000001
Key(category=ABZ, section=4)=1.9500000000000002
Key(category=ZBN, section=1)=2.1333333333333333
Key(category=ABZ, section=2)=5.1
Key(category=ABR, section=2)=1.1
```

## Slicing by Ranges/Bins/Buckets

You can also group by ranges (or known in statistics as "bins", "buckets", or a "histogram"). This functionality is still a work-in-progress to have all statistical functions applicable. 

currently you can group any `T` items into bins composed of `Comparable` ranges. Below, we group up items by yearly quarters by mapping each item to a `Month`, and then setting the `bucketSize` to 3. We also have to provide an `incrementer` so the model knows how to build the bins incrementally. 

```kotlin

import java.time.LocalDate

fun main(args: Array<String>) {

    data class Sale(val accountId: Int, val date: LocalDate, val value: Double)

    val sales = listOf(
            Sale(1, LocalDate.of(2016,12,3), 180.0),
            Sale(2, LocalDate.of(2016, 7, 4), 140.2),
            Sale(3, LocalDate.of(2016, 6, 3), 111.4),
            Sale(4, LocalDate.of(2016, 1, 5), 192.7),
            Sale(5, LocalDate.of(2016, 5, 4), 137.9),
            Sale(6, LocalDate.of(2016, 3, 6), 125.6),
            Sale(7, LocalDate.of(2016, 12,4), 164.3),
            Sale(8, LocalDate.of(2016, 7,11), 144.2)
            )

    //bucket by quarter
    val byQuarter = sales.binByComparable(
            binMapper = { it.date.month },
            bucketIncrements = 3,
            incrementer = { it.plus(1L) }
    )

    byQuarter.forEach(::println)
}
```

**OUTPUT:**

```
Bin(range=JANUARY..MARCH, value=[Sale(accountId=4, date=2016-01-05, value=192.7), Sale(accountId=6, date=2016-03-06, value=125.6)])
Bin(range=APRIL..JUNE, value=[Sale(accountId=3, date=2016-06-03, value=111.4), Sale(accountId=5, date=2016-05-04, value=137.9)])
Bin(range=JULY..SEPTEMBER, value=[Sale(accountId=2, date=2016-07-04, value=140.2), Sale(accountId=8, date=2016-07-11, value=144.2)])
Bin(range=OCTOBER..DECEMBER, value=[Sale(accountId=1, date=2016-12-03, value=180.0), Sale(accountId=7, date=2016-12-04, value=164.3)])
```

If you want to perform a mathematical aggregation on a certain property for each item (rather than group up the items into a `List` for a given bin), provide a `groupOp` argument specifying how to calculate a value  on each grouping. Below, we find the sum of values by quarter. 

```kotlin
import java.time.LocalDate

fun main(args: Array<String>) {

    data class Sale(val accountId: Int, val date: LocalDate, val value: Double)

    val sales = listOf(
            Sale(1, LocalDate.of(2016,12,3), 180.0),
            Sale(2, LocalDate.of(2016, 7, 4), 140.2),
            Sale(3, LocalDate.of(2016, 6, 3), 111.4),
            Sale(4, LocalDate.of(2016, 1, 5), 192.7),
            Sale(5, LocalDate.of(2016, 5, 4), 137.9),
            Sale(6, LocalDate.of(2016, 3, 6), 125.6),
            Sale(7, LocalDate.of(2016, 12,4), 164.3),
            Sale(8, LocalDate.of(2016, 7,11), 144.2)
    )

    //bucket by quarter
    val totalValueByQuarter = sales.binByComparable(
            binMapper = { it.date.month },
            bucketIncrements = 3,
            incrementer = { it.plus(1L) },
            groupOp = { it.map(Sale::value).sum() }
    )

    totalValueByQuarter.forEach(::println)
}
```

**OUTPUT:**

```
Bin(range=JANUARY..MARCH, value=318.29999999999995)
Bin(range=APRIL..JUNE, value=249.3)
Bin(range=JULY..SEPTEMBER, value=284.4)
Bin(range=OCTOBER..DECEMBER, value=344.3)
```

There are also specialized bin operators that deal with numeric ranges for `Int`, `Long`, `Double`, `Float`, and `BigDecimal`. Below, we bin the sales items by increments of 20.0 for the `value`. 

```kotlin

import java.time.LocalDate

fun main(args: Array<String>) {

    data class Sale(val accountId: Int, val date: LocalDate, val value: Double)

    val sales = listOf(
            Sale(1, LocalDate.of(2016,12,3), 180.0),
            Sale(2, LocalDate.of(2016, 7, 4), 140.2),
            Sale(3, LocalDate.of(2016, 6, 3), 111.4),
            Sale(4, LocalDate.of(2016, 1, 5), 192.7),
            Sale(5, LocalDate.of(2016, 5, 4), 137.9),
            Sale(6, LocalDate.of(2016, 3, 6), 125.6),
            Sale(7, LocalDate.of(2016, 12,4), 164.3),
            Sale(8, LocalDate.of(2016, 7,11), 144.2)
            )

    //bucket by double ranges
    val binned = sales.binByDouble(
            binMapper = { it.value },
            binSize = 20.0,
            gapSize = .01,
            rangeStart = 100.0
    )

    binned.forEach(::println)
}
```

**OUTPUT:**

```
Bin(range=100.0..120.0, value=[Sale(accountId=3, date=2016-06-03, value=111.4)])
Bin(range=120.01..140.0, value=[Sale(accountId=5, date=2016-05-04, value=137.9), Sale(accountId=6, date=2016-03-06, value=125.6)])
Bin(range=140.01..160.0, value=[Sale(accountId=2, date=2016-07-04, value=140.2), Sale(accountId=8, date=2016-07-11, value=144.2)])
Bin(range=160.01..180.0, value=[Sale(accountId=1, date=2016-12-03, value=180.0), Sale(accountId=7, date=2016-12-04, value=164.3)])
Bin(range=180.01..200.0, value=[Sale(accountId=4, date=2016-01-05, value=192.7)])
```

## Aggregating Multiple Fields

Using the Kotlin `let()` operator, it is easy to take a collection of items and aggregate multiple fields into another "summary" object. Below, we take a collection `Email` objects and find the distribution of instances of `subject` and `sender`. 

```kotlin
package org.nield.kotlinstatistics

fun main(args: Array<String>) {

    class Email(val subject: String, val sender: String)

    val data = listOf(
            Email("I make u offer", "princeofnigeria2000@aol.com"),
            Email("Congratulations!", "lotterybacklog@gmail.com"),
            Email("Your Inheritance is waiting!", "lotterybacklog@gmail.com"),
            Email("Hey", "jessica47@gmail.com")
    )

    data class FieldDistributions(val subject: Map<String,Int>, val sender: Map<String,Int>)

    val distributions = data.let {
        FieldDistributions(
                subject = it.countBy { it.subject },
                sender = it.countBy { it.sender }
        )
    }

    println(distributions)
}

```

**OUTPUT:**

```
FieldDistributions(subject={I make u offer=1, Congratulations!=1, Your Inheritance is waiting!=1, Hey=1}, sender={princeofnigeria2000@aol.com=1, lotterybacklog@gmail.com=2, jessica47@gmail.com=1})
```

You can also do various transformations for each field, such as splitting words and lowercasing them before getting a distribution.

```kotlin
package org.nield.kotlinstatistics

fun main(args: Array<String>) {

    class Email(val subject: String, val sender: String)

    val data = listOf(
            Email("I make u offer", "princeofnigeria2000@aol.com"),
            Email("Congratulations!", "lotterybacklog@gmail.com"),
            Email("Your Inheritance offer is waiting!", "princeofnigeria2000@aol.com"),
            Email("Hey", "jessica47@gmail.com")
    )

    data class FieldDistributions(val subjectWords: Map<String,Int>, val sender: Map<String,Int>)

    val distributions = data.let {
        FieldDistributions(
                subjectWords = it.asSequence()
                        .flatMap { it.subject.split(Regex(" ")).asSequence() }
                        .filter { it.isNotEmpty() }
                        .map { it.toLowerCase() }
                        .countBy(),
                sender = it.countBy { it.sender.toLowerCase() }
        )
    }

    println(distributions)
}

```

**OUTPUT:**

```
FieldDistributions(subjectWords={i=1, make=1, u=1, offer=2, congratulations!=1, your=1, inheritance=1, is=1, waiting!=1, hey=1}, sender={princeofnigeria2000@aol.com=2, lotterybacklog@gmail.com=1, jessica47@gmail.com=1})
```

## Linear Regression

Linear regression is being implemented as well. You can get a `SimpleRegression` on a `Sequence` or `Iterable` emitting `Double` pairs, for instance.

```kotlin
fun main(args: Array<String>) {
    val r = sequenceOf(
            1.0 to 3.0,
            2.0 to 6.0,
            3.0 to 9.0
        ).simpleRegression()

    //prints the slope "3.0"
    println(r.slope)
}
```

You can also select the `x` and `y` on any arbitrary type `T`. Currently, `x` and `y` must be mapped to a `Double`. Below, we plot a regression against sale numbers across calendar dates:

```kotlin
import java.time.LocalDate

fun main(args: Array<String>) {

    class SaleDate(val date: LocalDate, val sales: Int)
    
    val salesDates = listOf(
                SaleDate(LocalDate.of(2017,1,1), 1080),
                SaleDate(LocalDate.of(2017,1,2), 2010),
                SaleDate(LocalDate.of(2017,1,3), 1020),
                SaleDate(LocalDate.of(2017,1,4), 907),
                SaleDate(LocalDate.of(2017,1,5), 805),
                SaleDate(LocalDate.of(2017,1,6), 2809),
                SaleDate(LocalDate.of(2017,1,7), 2600)
            )

    val regression = salesDates.simpleRegression(
                xSelector = { it.date.dayOfYear.toDouble() },
                ySelector = { it.sales.toDouble() }
            )

    //print slope of regression
    println(regression.slope)
}
```

## Related Projects

* [Apache Commons Math](http://commons.apache.org/proper/commons-math/) - Math and statistical Java library
* [Koma](https://github.com/kyonifer/koma) - Scientific computing library for Kotlin
* [Krangl](https://github.com/holgerbrandl/krangl) - Tabular data wrangling library for Kotlin
