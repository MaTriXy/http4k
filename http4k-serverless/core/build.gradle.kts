description = "Http4k Serverless core"

plugins {
    id("org.http4k.license-check")
    id("org.http4k.publishing")
}

dependencies {
    api(project(":http4k-core"))
    api(project(":http4k-format-core"))
    testImplementation(project(":http4k-testing-hamkrest"))
    testImplementation(testFixtures(project(":http4k-core")))
    testFixturesImplementation(testFixtures(project(":http4k-core")))
}
