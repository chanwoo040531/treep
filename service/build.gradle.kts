apply(plugin = "kotlin-jpa")

dependencies {
    // Spring
    implementation(libs.spring.boot.web)
    testImplementation(libs.spring.boot.test)
    implementation(libs.spring.boot.actuator)

    // Database
    implementation(libs.spring.boot.data.jpa)
    runtimeOnly(libs.postgresql)

    implementation(libs.flyway.core)
    implementation(libs.flyway.postgres)

    // Docker
    developmentOnly(libs.spring.boot.dockercompose)

    // Serialization
    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.jsr310)

    // Kotest
    testImplementation(libs.bundles.kotest)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}