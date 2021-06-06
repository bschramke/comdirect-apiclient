import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
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
