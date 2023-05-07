import io.gitlab.arturbosch.detekt.Detekt

val detektVersion = "1.20.0"

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detektVersion")
}

detekt {
    toolVersion = detektVersion
    source = files("$rootDir/")
    parallel = true
    config = files("$rootDir/config/detekt/detekt-config.yml")
    buildUponDefaultConfig = true
    allRules = false
    baseline = file("$rootDir/config/detekt/detekt-baseline.xml")
}

tasks.named<Detekt>("detekt").configure {
    description = "Runs Detekt on the whole project at once."
    reports {
        html.required.set(true)
        html.outputLocation.set(
            file("$rootDir/build/reports/detekt.html"),
        )
    }
    parallel = true
    setSource(projectDir)
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")

    // exclude other custom generated files
}
