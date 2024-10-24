import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  kotlin("plugin.serialization")
  application
}

application {
  mainClass.set("com.github.bschramke.comdirect.rest.sample.cli.AppKt")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(libs.kotlinx.serialization.json)

  implementation(project(":apiclient"))

  implementation(libs.clikt)
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    apiVersion = "1.9"
    languageVersion = "1.9"
    jvmTarget = "1.8"
  }
}