package com.xo.tripplanner.domain

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.xo.tripplanner.data.remote.TripPlannerApi
import com.xo.tripplanner.model.CollaboratorRequest
import com.xo.tripplanner.model.CollaboratorResponse
import com.xo.tripplanner.model.Trip
import javax.inject.Inject

class TripsRepository@Inject constructor(
    private val api: TripPlannerApi
) {
    suspend fun getTrips(token : String): List<Trip> {
        return api.getTrips(token)
    }

    suspend fun inviteToTrip(tripId: String, email: String, token: String):String{
        try {
            val response = api.inviteToTrip(
                tripId = tripId,
                token = "Bearer $token",
                email = mapOf("email" to email)
            )
            if (response.isSuccessful) {
                Log.d("TripViewModel", "User invited successfully!")
                return "User invited successfully!"

            } else {
                Log.e("TripViewModel", "Error inviting user: ${response.errorBody()?.string()}")
                return "Error inviting user: ${response.errorBody()?.string()}"
            }
        } catch (e: Exception) {
            Log.e("TripViewModel", "Exception while inviting user: ${e.message}")
            return "Exception while inviting user: ${e.message}"
        }
    }

    suspend fun createTrip(title: String, description: String, startDate: String, endDate: String, token: String): String{
        try {
            val response = api.createTrip(
                token = "Bearer $token",
                trip = mapOf(
                    "title" to title,
                    "description" to description,
                    "startDate" to startDate,
                    "endDate" to endDate
                )
            )
            if (response.isSuccessful) {
                Log.d("TripViewModel", "Trip created: ${response.body()}")
                return "Trip created: ${response.body()}"
            } else {
                Log.e("TripViewModel", "Error creating trip: ${response.errorBody()?.string()}")
                return "Error creating trip: ${response.errorBody()?.string()}"
            }
        } catch (e: Exception) {
            Log.e("TripViewModel", "Exception while creating trip: ${e.message}")
            return "Exception while creating trip: ${e.message}"
        }
    }

    suspend fun getCollabs(token: String, creatorId: String, collaboratorIds: List<String>): CollaboratorResponse {
        return try {
            val requestBody = CollaboratorRequest(
                creatorId = creatorId,
                collaboratorIds = collaboratorIds
            )

            val response = api.getCollaboratorAndCreatorNames(
                token = "Bearer $token",
                data = requestBody
            )
            if (response.isSuccessful) {
                response.body() ?: CollaboratorResponse(
                    creator = "Unknown",
                    collaborators = emptyList()
                )
            } else {
                Log.e("TripRepository", "Error fetching names: ${response.errorBody()?.string()}")
                CollaboratorResponse(
                    creator = "Error",
                    collaborators = emptyList()
                )
            }
        } catch (e: Exception) {
            Log.e("TripRepository", "Exception: ${e.message}")
            CollaboratorResponse(
                creator = "Error",
                collaborators = emptyList()
            )
        }
    }

    suspend fun addCollaborator(_id: String, email: String, token: String, context: Context) {
        try {
            val response = api.inviteToTrip(_id,"Bearer $token", mapOf("email" to email))
            if (response.isSuccessful) {
                Toast.makeText(context, "Collaborator added successfully!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Error adding collaborator: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                Log.e("TripRepository", "Error adding collaborator: ${response.errorBody()?.string()}")
            }
        }catch (e: Exception) {
            Toast.makeText(context, "Exception while adding collaborator: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("TripRepository", "Exception: ${e.message}")
        }
    }

}