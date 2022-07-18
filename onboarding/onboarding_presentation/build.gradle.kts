apply {
    from ("$rootDir/compose-module.gradle")
}


dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))  // for compose
    "implementation"(project(Modules.onboardingDomain))
}