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
  implementation(libs.kotlinx.serialization.json)

  // Align versions of all OkHttp components
  implementation(platform(libs.okhttp.bom))

  // define any required OkHttp artifacts without version
  implementation(libs.okhttp.core)
  implementation(libs.okhttp.logging.interceptor)

  implementation(libs.retrofit.core)
  implementation(libs.retrofit.converter.kotlinx.serialization)

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
    apiVersion = "1.9"
    languageVersion = "1.9"
    jvmTarget = "1.8"
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
