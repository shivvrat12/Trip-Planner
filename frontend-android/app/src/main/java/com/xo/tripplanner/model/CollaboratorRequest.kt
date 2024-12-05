package com.xo.tripplanner.model

data class CollaboratorRequest(
    val creatorId: String,
    val collaboratorIds: List<String>
)