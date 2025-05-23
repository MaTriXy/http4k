package org.http4k.postbox

import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.UriTemplate.Companion.from
import org.http4k.events.StdOutEvents
import org.http4k.postbox.PendingResponseGenerators.redirect
import org.http4k.postbox.RequestIdResolvers.fromPath
import org.http4k.postbox.storage.exposed.PostboxTransactor
import org.http4k.postbox.storage.exposed.postgresDataSource
import org.http4k.postbox.processing.PostboxProcessing
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val transactor = PostboxTransactor(postgresDataSource()).also { transactor ->
        PostboxProcessing(transactor, SlowInternalHandler, events = StdOutEvents).start()
    }

    val inbox = PostboxHandlers(transactor, redirect("taskId", from("http://localhost:9000/workload/status/{taskId}")))

    routes(
        "/workload/submit/{taskId}" bind POST to inbox.intercepting(fromPath("taskId")),
        "/workload/status/{taskId}" bind GET to inbox.status(fromPath("taskId"))
    ).asServer(SunHttp(9000)).start()
}

private val SlowInternalHandler =
    { request: Request -> Thread.sleep(10000); Response(OK).body("work for ${request.uri.path} is done") }
