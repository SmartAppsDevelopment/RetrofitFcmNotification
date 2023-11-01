package com.example.templatesampleapp.model.senreqtopic

import com.example.templatesampleapp.model.sendreq.Notification

data class Topic(
    val notification: Notification,
    val topic: String
)