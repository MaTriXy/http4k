description = "Http4k Pug4j templating support"

plugins {
    id("org.http4k.license-check")
    id("org.http4k.publishing")
}

dependencies {
    api(project(":http4k-template-core"))
    api("de.neuland-bfi:pug4j:_")
    testImplementation(testFixtures(project(":http4k-core")))
    testImplementation(testFixtures(project(":http4k-template-core")))
}
