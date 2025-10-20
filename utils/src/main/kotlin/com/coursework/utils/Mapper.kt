package com.coursework.utils

interface Mapper<F, T> {

    fun map(from: F): T

    operator fun invoke(from: F): T = map(from)
}

fun <F, T> Mapper<F, T>.mapList(list: List<F>): List<T> =
    list.map(this::map)

interface ParameterizedMapper<F, P, T> {

    fun map(from: F, params: P): T

    operator fun invoke(from: F, params: P): T = map(from, params)
}

fun <F, P, T> ParameterizedMapper<F, P, T>.mapList(list: List<F>, params: (F) -> P): List<T> =
    list.map {
        map(it, params(it))
    }
