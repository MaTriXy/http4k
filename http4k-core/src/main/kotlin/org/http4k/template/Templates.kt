package org.http4k.template

typealias TemplateRenderer = (ViewModel) -> String

/**
 * Supported template implementations for templating engine implementations
 */
interface Templates {

    /**
     * Loads and caches templates from the compiled classpath
     *
     * @param baseClasspathPackage the root package to load from (defaults to root)
     */
    fun CachingClasspath(baseClasspathPackage: String = ""): TemplateRenderer

    /**
     * Load and caches templates from a file path
     *
     * @param baseTemplateDir the root path to load templates from
     */
    fun Caching(baseTemplateDir: String = "./"): TemplateRenderer

    /**
     * Hot-reloads (no-caching) templates from a file path
     *
     * @param baseTemplateDir the root path to load templates from
     */
    fun HotReload(baseTemplateDir: String = "./"): TemplateRenderer
}

/**
 * Compose a TemplateRenderer with another, so you can fall back.
 */
fun TemplateRenderer.then(that: TemplateRenderer): TemplateRenderer = {
    try {
        this(it)
    } catch (e: ViewNotFound) {
        that(it)
    }
}
