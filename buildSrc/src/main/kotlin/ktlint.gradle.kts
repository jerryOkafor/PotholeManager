val ktlintConfig by configurations.creating

val ktlintVerrsion = "0.49.0"

dependencies {
    ktlintConfig("com.pinterest:ktlint:$ktlintVerrsion") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
    // ktlintConfig(project(":custom-ktlint-ruleset")) // in case of custom ruleset
}

val outputDir = "${project.buildDir}/reports/ktlint/"
val inputFiles = project.fileTree(mapOf("dir" to "src", "include" to "**/*.kt"))

val ktlint by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Check Kotlin code style."
    classpath = ktlintConfig
    mainClass.set("com.pinterest.ktlint.Main")
    args(
        "**/src/**/*.kt",
        "**.kts",
        "!**/build/**",
    )
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    inputs.files(inputFiles)
    outputs.dir(outputDir)

    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Fix Kotlin code style deviations."
    classpath = ktlintConfig
    mainClass.set("com.pinterest.ktlint.Main")
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
    args(
        "-F",
        "**/src/**/*.kt",
        "**.kts",
        "!**/build/**",
    )
}

val ktlintAndroidStudio by tasks.creating(JavaExec::class) {
    description = "Setup Android Studio to use the same code style as ktlint."
    classpath = ktlintConfig
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("--android", "applyToIDEAProject", "applyToIDEA")
    workingDir = getRootDir()
}
