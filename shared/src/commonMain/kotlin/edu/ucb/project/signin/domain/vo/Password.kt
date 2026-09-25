package edu.ucb.project.signin.domain.vo

import kotlin.jvm.JvmInline

@JvmInline
value class Password(val value: String) {
    fun isValid(): Boolean = value.length >= 6
}
