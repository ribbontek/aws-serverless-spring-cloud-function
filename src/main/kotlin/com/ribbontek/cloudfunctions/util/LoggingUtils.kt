package com.ribbontek.cloudfunctions.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <T : Any> T.logger(): Logger = LoggerFactory.getLogger(this::class.java)
inline fun <reified T : Any> logger(): Logger = LoggerFactory.getLogger(T::class.java)
