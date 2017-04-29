# Kotlin Statistics
Math and statistical extensions for Kotlin

This library will contain helpful extension functions to perform exploratory and production statistics and math operations.

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

There are a number of extension function operators that support `Int`, `Long`, `Double`, `Float`, `BigDecimal` and `Short` numeric types, for both Sequences, Arrays, primitive arrays, and Iterables:

* `sum()`
* `average()`
* `min()`
* `max()`
* `mode()`
* `variance()`
* `standardDeviation()`

Here is an example of using the `median()` extension function against a `Sequence` of Doubles:

```kotlin
val median = sequenceOf(1.0, 3.0, 5.0).median() 
println(median) // prints "3.0"
```

There are also simple but powerful `xxxBy()` operators that allow you slice these statistical operators on a given key:

* `sumBy()`
* `averageBy()`
* `minBy()`
* `maxBy()`
* `varianceBy()`
* `standardDeviationBy()`

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

## Slicing Aggregations Using Data Classes

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
data class CategoryAndSection(val category: String, val section: Int)

// Get Count by Category and Section
val countByCategoryAndSection =
        products.countBy { CategoryAndSection(it.category, it.section) }

println("Counts by Category and Section")
countByCategoryAndSection.entries.forEach { println(it) }

// Get Average Defect Rate by Category and Section
val averageDefectByCategoryAndSection =
        products.averageBy(keySelector = { CategoryAndSection(it.category, it.section) }, doubleMapper = { it.defectRate })

println("\nAverage Defect Rate by Category and Section")
averageDefectByCategoryAndSection.entries.forEach { println(it) }
```

**OUTPUT:**

```
CategoryAndSection(category=ABR, section=3)=4
CategoryAndSection(category=ABZ, section=4)=2
CategoryAndSection(category=ZBN, section=1)=3
CategoryAndSection(category=ABZ, section=2)=1
CategoryAndSection(category=ABR, section=2)=1

Average Defect Rate by Category and Section
CategoryAndSection(category=ABR, section=3)=1.4000000000000001
CategoryAndSection(category=ABZ, section=4)=1.9500000000000002
CategoryAndSection(category=ZBN, section=1)=2.1333333333333333
CategoryAndSection(category=ABZ, section=2)=5.1
CategoryAndSection(category=ABR, section=2)=1.1
```

# Road Map


Before doing a Maven release, I am looking to add these operators and more:

* `weightedAverage()`
* `percentile()`
* `quartile()`
* `median()`
* `weightedMedian()`
* `trimmedAverage()`
* `zScore()`


