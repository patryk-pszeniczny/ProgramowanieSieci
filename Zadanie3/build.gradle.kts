plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            // Legacy activation examples do not compile on JDK 17+ (java.rmi.activation removed)
            exclude("rmi/DBActivate/**")
            exclude("rmi/InfoActivate/**")
        }
    }
}

dependencies {
    implementation(files("src/main/java/shopdata/lib/hsqldb.jar"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}
