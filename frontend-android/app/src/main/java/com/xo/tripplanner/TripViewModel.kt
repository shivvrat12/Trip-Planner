package com.xo.tripplanner

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xo.tripplanner.domain.AuthRepository
import com.xo.tripplanner.domain.TripsRepository
import com.xo.tripplanner.model.AuthResponse
import com.xo.tripplanner.model.CollaboratorResponse
import com.xo.tripplanner.model.LoginRequest
import com.xo.tripplanner.model.RegisterRequest
import com.xo.tripplanner.model.Trip
import com.xo.tripplanner.model.UserData
import com.xo.tripplanner.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tripsRepository: TripsRepository,
    private val tokenManager: TokenManager,
) : ViewModel() {

    val token = tokenManager.token

    private val _authState = MutableStateFlow<AuthResponse?>(null)
    val authState: StateFlow<AuthResponse?> = _authState
    val isLoading = MutableStateFlow(false)

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    private val _collaborators = MutableStateFlow<CollaboratorResponse>(CollaboratorResponse( creator = "", collaborators = emptyList()))
    val collaborators: StateFlow<CollaboratorResponse> = _collaborators

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = authRepository.registerUser(
                    RegisterRequest(
                        name = name,
                        email = email,
                        password = password
                    )
                )
                tokenManager.saveToken(response.token)  //saving the jwt token
                _authState.value = response
                isLoading.value = false
                Log.d("AuthViewModel", "User registered: $response")
            } catch (e: Exception) {
                Log.d("AuthViewModel", "Error registering user: $e")
                _errorState.value = e.message
                isLoading.value = false
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = authRepository.loginUser(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )
                tokenManager.saveToken(response.token)  //saving the jwt token
                _authState.value = response
                isLoading.value = false
                Log.d("AuthViewModel", "User logged in: $response")
            } catch (e: Exception) {
                Log.d("AuthViewModel", "Error logging in user: $e")
                _errorState.value = e.message
                isLoading.value = false
            }
        }
    }

    fun fetchTrips() {
        viewModelScope.launch {
            tokenManager.token.collect { token ->
                if (!token.isNullOrEmpty()) {
                    Log.d("Token", "Fetching trips with token: $token")
                    try {
                        val fetchedTrips = tripsRepository.getTrips("Bearer $token")
                        _trips.value = fetchedTrips
                        Log.d("AuthViewModel", "Trips fetched: $fetchedTrips")
                    } catch (e: Exception) {
                        Log.e("AuthViewModel", "Error fetching trips: $e")
                    }
                }
            }
        }
    }

    fun createTrip(
        title: String,
        description: String,
        startDate: String,
        endDate: String,
        context: Context,
    ) {
        viewModelScope.launch {
            tokenManager.token.collect { token ->
                if (!token.isNullOrEmpty()) {
                    val response = tripsRepository.createTrip(
                        title = title,
                        description = description,
                        startDate = startDate,
                        endDate = endDate,
                        token = token
                    )
                } else {
                    Toast.makeText(context, "Token is null or empty", Toast.LENGTH_SHORT).show()
                    Log.e("TripViewModel", "Token is null or empty")
                }
            }
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            tokenManager.token.collect { token ->
                if (!token.isNullOrEmpty()) {
                    try {
                        val response = authRepository.getUserData(token)
                        _userData.value = response
                        Log.d("AuthViewModel", "User data fetched: $response")
                    } catch (e: Exception) {
                        Log.e("AuthViewModel", "Error fetching user data: $e")
                    }
                } else {
                    Log.e("AuthViewModel", "Token is null or empty")
                }
            }
        }
    }

    fun findTrip(id: String): Trip {
        return trips.value.find { it._id == id } ?: throw IllegalArgumentException("Trip not found")
    }

    fun getCollaboratorAndCreatorNames(creatorId: String, collaboratorIds: List<String>) {
        viewModelScope.launch {
            val token = tokenManager.token.firstOrNull() // Collect token only once
            if (!token.isNullOrEmpty()) {
                try {
                    val result = tripsRepository.getCollabs(token, creatorId, collaboratorIds)
                    _collaborators.value = result // Update collaborators state
                } catch (e: Exception) {
                    Log.e("TripViewModel", "Exception: ${e.message}")
                    _collaborators.value = CollaboratorResponse(
                        creator = "Error",
                        collaborators = emptyList()
                    )
                }
            } else {
                Log.e("TripViewModel", "Token is null or empty")
            }
        }
    }

    fun addCollaborator(_id: String, email: String, context: Context) {
        viewModelScope.launch {
            tokenManager.token.collect { token ->
                if (!token.isNullOrEmpty()) {
                    tripsRepository.addCollaborator(_id, email, token, context)
                    fetchTrips()
                } else {
                    Log.e("TripViewModel", "Token is null or empty")
                    Toast.makeText(context, "Token is null or empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


//    if need to grab the token
//    tokenManager.token.collect { token ->
//        if (!token.isNullOrEmpty()) {
//            println("Token: $token")
//        }
//    }

}