package com.example.weatherapp.domain.commands

interface Command<out T>{
    suspend fun execute(): T
}