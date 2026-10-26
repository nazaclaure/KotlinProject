package edu.ucb.ds

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform