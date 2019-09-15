plugins {
    kotlin("multiplatform") version "1.3.50"
}

group = "org.nield"
version = "1.2.1-mpp"

repositories{
    mavenLocal()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    jcenter()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    js {
        browser {}
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.apache.commons:commons-math3:3.6.1")
            }
        }
        val jvmTest by getting{
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting{
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting{
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
//        mingwMain {
//        }
//        mingwTest {
//        }
    }
}