package com.example

import com.example.models.ApiResponse
import com.example.plugins.*
import com.example.repository.HeroRepository
import com.example.repository.HeroRepositoryImpl
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.Identity.decode
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.*

class ApplicationTest {
    private val heroRepository:HeroRepository by inject(HeroRepository::class.java)
    @Test
    fun `access root endpoint and assert whether body is welcome to hero server`() = testApplication{
      environment {
          developmentMode = false
      }

        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Welcome to Hero Server", bodyAsText())
        }

    }

    @Test
    fun `access all heroes endpoint, assert whether it returns heroes list`(){
        testApplication{
            environment {
                developmentMode = false
            }

            client.get("/boruto/heroes").apply {
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = status
                )
                val expected = ApiResponse(
                    success = true,
                    message = "ok",
                    prevPage = null,
                    nextPage = 2,
                    heroes = heroRepository.page1
                )

                val actual = Json.decodeFromString<ApiResponse>(bodyAsText())
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}
