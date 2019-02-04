package com.example.kotlincoroutines.model

data class CodeStandardResponse(
    var `data`: List<Data>,
    var description: String,
    var error: Error,
    var success: Boolean
)