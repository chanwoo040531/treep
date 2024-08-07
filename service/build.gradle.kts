apply(plugin = "kotlin-jpa")

val bootVersion = "3.3.2"
val jacksonVersion = "2.15.0"
val kotestVersion = "5.9.0"
val flywayVersion = "10.12.0"

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web:$bootVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$bootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$bootVersion")

    // Database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$bootVersion")
    runtimeOnly("org.postgresql:postgresql:42.7.2")

    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")

    // Docker
    developmentOnly("org.springframework.boot:spring-boot-docker-compose:$bootVersion")

    // Serialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}