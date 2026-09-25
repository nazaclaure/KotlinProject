package edu.ucb.project.signin.domain.vo

import kotlin.jvm.JvmInline

@JvmInline
value class Email(val value: String) {
    fun isValid(): Boolean = value.contains("@") && value.contains(".")
}
