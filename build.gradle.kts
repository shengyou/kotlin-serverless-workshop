import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val invoker by configurations.creating
val javafaker_version: String by project
val kotlinx_datetime_version: String by project
val kotlinx_serialization_json_version: String by project
val kt_rss_reader_version: String by project
val okhttp_version: String by project

plugins {
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
    kotlin("kapt") version "1.4.31"
}

group = "studio.mosil.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kapt {
    arguments {
        arg("pureKotlinParser", true)
    }
}

repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("com.google.cloud.functions:functions-framework-api:1.0.1")
    invoker("com.google.cloud.functions.invoker:java-function-invoker:1.0.0-alpha-2-rc5")

    implementation("com.github.javafaker:javafaker:$javafaker_version")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinx_datetime_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_json_version")

    implementation("com.github.ivanisidrowu.KtRssReader:kotlin:$kt_rss_reader_version")
    implementation("com.github.ivanisidrowu.KtRssReader:annotation:$kt_rss_reader_version")
    kapt("com.github.ivanisidrowu.KtRssReader:processor:$kt_rss_reader_version")
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

task<JavaExec>("runFunction") {
    group = "gcp"
    main = "com.google.cloud.functions.invoker.runner.Invoker"
    classpath(invoker)
    inputs.files(configurations.runtimeClasspath, sourceSets["main"].output)
    args(
        "--target", project.findProperty("runFunction.target") ?: "",
        "--port", project.findProperty("runFunction.port") ?: 8080
    )
    doFirst {
        args("--classpath", files(configurations.runtimeClasspath, sourceSets["main"].output).asPath)
    }
}
