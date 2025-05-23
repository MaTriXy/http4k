import com.microsoft.azure.gradle.configuration.GradleRuntimeConfig

plugins {
    id("org.http4k.conventions")
    id("com.microsoft.azure.azurefunctions")
}


description = "Testing against a functions deployed to Azure Functions"

apply(plugin = "com.microsoft.azure.azurefunctions")

dependencies {
    api(project(":http4k-serverless-azure"))
    api(testFixtures(project(":http4k-core")))
    api(testFixtures(project(":http4k-serverless-core")))
}

// config based on Azure"s example: https://github.com/Azure-Samples/azure-functions-samples-java
azurefunctions {
    resourceGroup = "java-functions-group"
    appName = "http4k-test-function" // this global to Azure and needs to be changed if you"re using your own account
    pricingTier = "Consumption"
    region = "westus"
    setRuntime(closureOf<GradleRuntimeConfig> {
        os("windows")
        javaVersion("8")
    })
    localDebug = "transport=dt_socket,server=y,suspend=n,address=5005"
}
