package edu.ucb.project.di

import org.koin.core.module.Module

fun sharedModule(): List<Module> = listOf(
    dataModule,
    domainModule,
    presentationModule
)
