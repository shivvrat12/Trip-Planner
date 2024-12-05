package com.xo.tripplanner.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.xo.tripplanner.R
import com.xo.tripplanner.Screen
import com.xo.tripplanner.TripViewModel
import com.xo.tripplanner.ui.theme.afacadFlux

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: TripViewModel) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }
    val isLoading = viewModel.isLoading.collectAsState()
    val authState = viewModel.authState.collectAsState()
    val errorState = viewModel.errorState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value, errorState.value) {
        authState.value?.let {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
        errorState.value?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.surface)
            .padding(16.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Image(painter = painterResource(id = R.drawable.business_trip), contentDescription = "Logo")
        Text(
            text = "Register Account",
            fontFamily = afacadFlux,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            style = typography.titleLarge,
            color = colorScheme.onSurface
        )
        Text(
            text = "Unveil the potential to grab the world",
            fontFamily = afacadFlux,
            style = typography.titleLarge,
            color = colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            leadingIcon = {
                Icon(
                    Icons.Outlined.PersonOutline,
                    contentDescription = "Email",
                    modifier = Modifier.size(20.dp),
                    tint = colorScheme.onSurface
                )
            },
            label = {
                Text(
                    text = "Name",
                    style = typography.bodySmall,
                    color = colorScheme.onSurface
                )
            },
            textStyle = TextStyle(fontSize = 10.sp),
            modifier = Modifier
                .width(240.dp)
                .height(60.dp),
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f),
                textColor = colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Email,
                    contentDescription = "Email",
                    modifier = Modifier.size(20.dp),
                    tint = colorScheme.onSurface
                )
            },
            label = {
                Text(
                    text = "Email Address",
                    style = typography.bodySmall,
                    color = colorScheme.onSurface
                )
            },
            textStyle = TextStyle(fontSize = 10.sp),
            modifier = Modifier
                .width(240.dp)
                .height(60.dp),
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f),
                textColor = colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Password,
                    contentDescription = "Email",
                    modifier = Modifier.size(20.dp),
                    tint = colorScheme.onSurface
                )
            },
            trailingIcon = {
                Icon(
                    if (isPasswordVisible.value) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = "Email",
                    tint = colorScheme.onSurface,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            isPasswordVisible.value = !isPasswordVisible.value
                        }
                )
            },
            visualTransformation = if (isPasswordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
            label = {
                Text(
                    text = "Password",
                    style = typography.bodySmall,
                    color = colorScheme.onSurface
                )
            },
            textStyle = TextStyle(fontSize = 10.sp),
            modifier = Modifier
                .width(240.dp)
                .height(60.dp),
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f),
                textColor = colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.registerUser(name.value, email.value, password.value) },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                colorScheme.primary
            ),
            modifier = Modifier.width(240.dp)
        ) {
            if (isLoading.value) {
                Text(text = "Loading...", fontFamily = afacadFlux)
            } else {
                Text(text = "Sign Up", fontFamily = afacadFlux)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Already have an account? ", style = typography.bodySmall,
                color = colorScheme.onSurface
            )
            Text(text = "Sign In", style = typography.bodySmall, color = colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Login.route){
                        popUpTo(0)
                    }
                }
            )
        }
    }
}