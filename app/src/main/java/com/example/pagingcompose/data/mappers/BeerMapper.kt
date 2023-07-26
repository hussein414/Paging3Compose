package com.example.pagingcompose.data.mappers

import com.example.pagingcompose.data.model.Beer
import com.example.pagingcompose.data.model.api.BeerDto
import com.example.pagingcompose.data.model.db.BeerEntity

fun BeerDto.toBeerEntity(): BeerEntity = BeerEntity(
    id = id,
    name = name,
    tagline = tagline,
    description = description,
    first_brewed = first_brewed,
    image_url = image_url
)

fun BeerEntity.toBeer(): Beer = Beer(
    id = id,
    name = name,
    tagline = tagline,
    description = description,
    first_brewed = first_brewed,
    image_url = image_url
)