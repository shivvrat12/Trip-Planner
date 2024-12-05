package com.xo.tripplanner.model

data class CollaboratorResponse(
    val creator: String,
    val collaborators: List<String>
)