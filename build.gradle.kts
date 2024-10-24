plugins {
  kotlin("jvm") version "1.5.0" apply false
  kotlin("plugin.serialization") version "1.5.0" apply false
  id("org.jetbrains.dokka") version "1.4.32" apply false
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
    gradleVersion = "7.6.4"
  }
}
