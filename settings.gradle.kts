pluginManagement {
    repositories {
        maven(
            url = "https://maven.myket.ir"
        )
        maven(
            url = "https://maven.myket.ir/gradle"
        )
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(
            url = "https://maven.myket.ir"
        )
        maven(
            url = "https://maven.myket.ir/gradle"
        )
        google()
        mavenCentral()
    }
}

buildscript {
    repositories {
        maven ( url = "https://maven.myket.ir" )
    }
}

rootProject.name = "Game Base"
include(":app")
