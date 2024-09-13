apply(plugin = "kotlin-jpa")

dependencies {
    // Spring
    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.actuator)
    implementation(libs.spring.boot.validation)
    implementation(libs.spring.boot.aop)
    testImplementation(libs.spring.boot.test)

    // Database
    implementation(libs.spring.boot.data.jpa)
    runtimeOnly(libs.postgresql)
    implementation(libs.spring.boot.data.mongodb)

    implementation(libs.flyway.core)
    implementation(libs.flyway.postgres)

    // Docker
    developmentOnly(libs.spring.boot.dockercompose)

    // Serialization
    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.jsr310)

    // Kotest
    testImplementation(libs.bundles.kotest)

    // Mockk
    testImplementation(libs.mockk)

    // In-memory Cache
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}   