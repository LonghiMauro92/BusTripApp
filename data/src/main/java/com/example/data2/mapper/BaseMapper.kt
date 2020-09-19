package com.example.data2.mapper

interface BaseMapper<E, D> {
    fun transform(type: E): D
}
