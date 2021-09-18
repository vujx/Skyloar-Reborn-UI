package com.example.data.mapper

interface EntityMapper<Entity, DomainModel> {

  fun mapFromEntity(entity: Entity): DomainModel
}
