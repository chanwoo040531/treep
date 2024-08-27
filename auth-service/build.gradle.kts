apply(plugin = "kotlin-jpa")

dependencies {
    implementation(libs.spring.boot.data.jpa)
    implementation(libs.spring.boot.web)
    testImplementation(libs.spring.boot.test)
    implementation(libs.spring.boot.data.redis)
    implementation(libs.spring.boot.validation)
    runtimeOnly(libs.postgresql)

    implementation(libs.spring.boot.security)

    developmentOnly(libs.spring.boot.dockercompose)

    implementation("com.auth0:java-jwt:4.4.0")

    implementation(libs.kotlinlogging)

//    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("at.favre.lib:bcrypt:0.9.0")
}