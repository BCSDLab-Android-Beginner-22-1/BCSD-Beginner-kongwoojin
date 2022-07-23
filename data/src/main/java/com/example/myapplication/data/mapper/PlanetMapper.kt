package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.PlanetResponse
import com.example.myapplication.data.model.PlanetRootResponse
import com.example.myapplication.domain.model.Planet
import com.example.myapplication.domain.model.PlanetRoot

object PlanetMapper {
    fun mapToPlanetList(list: PlanetRootResponse): PlanetRoot {
        val mappedList = ArrayList<Planet>()

        for (planet in list.results) {
            mappedList.add(
                Planet(
                    name = planet.name,
                    rotationPeriod = planet.rotationPeriod,
                    orbitalPeriod = planet.orbitalPeriod,
                    climate = planet.climate,
                    gravity = planet.gravity,
                    terrain = planet.terrain,
                    population = planet.population,
                    residents = planet.residents,
                    films = planet.films,
                    url = planet.url
                )
            )
        }

        return PlanetRoot(
            previous = list.previous,
            next = list.next,
            results = mappedList
        )
    }

    fun mapToPlanet(planetResponse: PlanetResponse): Planet {
        return Planet(
            name = planetResponse.name,
            rotationPeriod = planetResponse.rotationPeriod,
            orbitalPeriod = planetResponse.orbitalPeriod,
            climate = planetResponse.climate,
            gravity = planetResponse.gravity,
            terrain = planetResponse.terrain,
            population = planetResponse.population,
            residents = planetResponse.residents,
            films = planetResponse.films,
            url = planetResponse.url
        )
    }
}