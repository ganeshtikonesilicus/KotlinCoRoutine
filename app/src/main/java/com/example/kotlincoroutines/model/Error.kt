package com.example.kotlincoroutines.model

data class Error(
    var code: Int,
    var description: Any?,
    var message: Any?
)