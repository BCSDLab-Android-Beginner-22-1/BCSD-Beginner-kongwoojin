package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.PersonResponse
import com.example.myapplication.data.model.PersonRootResponse
import com.example.myapplication.domain.model.Person
import com.example.myapplication.domain.model.PersonRoot

object PersonMapper {
    fun mapToPersonList(list: PersonRootResponse): PersonRoot {
        val mappedList = ArrayList<Person>()

        for (person in list.results) {
            mappedList.add(
                Person(
                    name = person.name,
                    height = person.height,
                    mass = person.mass,
                    birthYear = person.birthYear,
                    gender = person.gender,
                    homeWorld = person.homeWorld,
                    films = person.films,
                    url = person.url
                )
            )
        }

        return PersonRoot(
            previous = list.previous,
            next = list.next,
            results = mappedList
        )
    }

    fun mapToPerson(personResponse: PersonResponse): Person {
        return Person(
            name = personResponse.name,
            height = personResponse.height,
            mass = personResponse.mass,
            birthYear = personResponse.birthYear,
            gender = personResponse.gender,
            homeWorld = personResponse.homeWorld,
            films = personResponse.films,
            url = personResponse.url
        )
    }
}