package com.imquarantined.data

/* Created by ashiq.buet16 **/

data class UserEntity (
    var position: Int = -1,
    var userName: String,
    var imageUrl: String,
    var points: Int,
    var donated: Float
)