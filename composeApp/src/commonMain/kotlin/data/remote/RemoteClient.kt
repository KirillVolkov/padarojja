package data.remote

import data.remote.model.City
import data.remote.model.Place
import data.remote.model.Route
import debugEmulatorLocalHost
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RemoteClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                port = 8080
                host = debugEmulatorLocalHost()
            }
        }
    }

    suspend fun getPlaces(cityId: Int): List<Place> {
        return client.get("places/$cityId").body<List<Place>>()
    }

    suspend fun getRoutes(): List<Route> {
        return client.get("trips").body<List<Route>>()
    }

    suspend fun getCities(): List<City> {
        return client.get("/cities").body<List<City>>()
    }
}
