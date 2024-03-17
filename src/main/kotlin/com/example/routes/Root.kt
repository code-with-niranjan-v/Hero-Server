package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.root(){
    get("/") {
        call.respondText("Welcome to Hero Sever", status = HttpStatusCode.OK)
    }
}