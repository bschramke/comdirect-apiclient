plugins {
    kotlin("jvm") version "1.4.10" apply false
    id("org.jetbrains.dokka") version "0.10.1" apply false
}

allprojects {
    group = "com.github.bschramke.comdirect"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

tasks {
    named<Wrapper>("wrapper") {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "7.0.2"
    }
}
