import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `java-library`
  kotlin("jvm")
  kotlin("plugin.serialization")
  id("org.jetbrains.dokka")
  `maven-publish`
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")

  // define a BOM and its version
  implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.1"))

  // define any required OkHttp artifacts without version
  implementation("com.squareup.okhttp3:okhttp")
  implementation("com.squareup.okhttp3:logging-interceptor")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
}

publishing {
  publications {
    create<MavenPublication>("apiclient") {
      artifactId = "apiclient-comdirect"

      from(components["java"])
      configurePom(pom)
    }
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    apiVersion = "1.4"
    languageVersion = "1.4"
    jvmTarget = "1.8"
    useIR = true
  }
}

fun configurePom(pom: MavenPom) {
  pom.apply {
    name.set("Comdirect REST API Client")
    description.set("A client library for comdirect REST API written in Kotlin.")
    url.set("https://github.com/bschramke/comdirect-apiclient")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        id.set("bschramke")
        name.set("Björn Schramke")
        email.set("bschramke@users.noreply.github.com")
      }
    }
    scm {
      connection.set("scm:git:https://github.com/bschramke/comdirect-apiclient.git")
      developerConnection.set("scm:git:ssh://git@github.com:bschramke/comdirect-apiclient.git")
      url.set("https://github.com/bschramke/comdirect-apiclient")
    }
  }
}
