package com.example.kotlincoroutines.model

data class Data(
    var code: String,
    var codeComponent: List<Any>,
    var description: String,
    var id: Int,
    var inspectionCodeComponent: List<Any>,
    var type: Int,
    var typeNavigation: Any?
)