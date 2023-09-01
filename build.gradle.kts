plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.uroria"
version = project.properties["projectVersion"].toString()

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven {
        url = uri("https://gitlab.zyonicsoftware.com/api/v4/groups/622/-/packages/maven")
        credentials(HttpHeaderCredentials::class) {
            if (System.getenv("CI") == "true") {
                name = "Private-Token"
                value = System.getenv("U_PIPELINE_DEFAULT_TOKEN")
            } else {
                name = "Private-Token"
                value = project.properties["uroriaGitlabToken"].toString()
            }
        }
        authentication {
            create<HttpHeaderAuthentication>("header")
        }
    }
}

val jetbrainsAnnotationsVersion: String by project.extra
val lombokVersion: String by project.extra
val jsonVersion: String by project.extra
val fastUtilVersion: String by project.extra
val fastConfigVersion: String by project.extra
val adventureVersion: String by project.extra
val guavaVersion: String by project.extra
val log4jVersion: String by project.extra
val slf4jVersion: String by project.extra

dependencies {
    api("org.jetbrains:annotations:${jetbrainsAnnotationsVersion}")
    api("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.jetbrains:annotations:${jetbrainsAnnotationsVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    api("org.slf4j:slf4j-api:$slf4jVersion")

    implementation("org.apache.logging.log4j:log4j-api:${log4jVersion}")
    implementation("org.apache.logging.log4j:log4j-core:${log4jVersion}")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${log4jVersion}")
    implementation("org.apache.logging.log4j:log4j-iostreams:${log4jVersion}")
    implementation("org.apache.logging.log4j:log4j-jul:${log4jVersion}")


    api("it.unimi.dsi:fastutil:${fastUtilVersion}")

    api("com.google.code.gson:gson:${guavaVersion}")

    api("com.uroria:FastConfig:${fastConfigVersion}")

    api("net.kyori:adventure-api:${adventureVersion}")
    api("net.kyori:adventure-text-serializer-gson:${adventureVersion}")
    api("net.kyori:adventure-text-minimessage:${adventureVersion}")
    api("net.kyori:adventure-text-serializer-plain:${adventureVersion}")
    api("net.kyori:adventure-text-serializer-legacy:${adventureVersion}")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("${System.getenv("CI_API_V4_URL")}/projects/${System.getenv("CI_PROJECT_ID")}/packages/maven")
            name = "GitLab"
            if (System.getenv("CI") == "true") {
                credentials(HttpHeaderCredentials::class) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}

tasks {
    shadowJar {
        minimize()
        archiveFileName.set("Base-${project.version}.jar")

        exclude("*.md", "*.txt", "*.html")
    }
}