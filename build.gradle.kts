plugins {
  kotlin("jvm") version "1.9.25" apply false
  kotlin("plugin.serialization") version "1.9.25" apply false
  id("org.jetbrains.dokka") version "1.9.20" apply false
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
    gradleVersion = "8.8"
  }
}
