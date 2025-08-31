package com.hileni.quickchatapp

import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuickChatApp()
        }
    }
}

@Composable
fun QuickChatApp() {
    var isLoggedIn by remember { mutableStateOf(false) }
    var showSignUp by remember { mutableStateOf(false) }
    var smsHistory by remember { mutableStateOf(listOf<String>()) }
    val context = LocalContext.current

    if (!isLoggedIn) {
        if (showSignUp) {
            SignUpScreen(
                onSignUpSuccess = { isLoggedIn = true },
                onBackToLogin = { showSignUp = false }
            )
        } else {
            LoginScreen(
                onLoginSuccess = { isLoggedIn = true },
                onSignUpClick = { showSignUp = true }
            )
        }
    } else {
        ChatScreen(
            smsHistory = smsHistory,
            onSendSms = { phone, msg ->
                if (phone.isNotEmpty() && msg.isNotEmpty()) {
                    // Update local SMS history
                    smsHistory = smsHistory + "To: $phone\n$msg"

                    // Show toast
                    Toast.makeText(context, "Message added to history", Toast.LENGTH_SHORT).show()

                    // Try to send SMS (real device required)
                    try {
                        val smsManager = SmsManager.getDefault()
                        smsManager.sendTextMessage(phone, null, msg, null, null)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Failed to send SMS", Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            },
            onLogout = { isLoggedIn = false }
        )
    }
}


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("QuickChat", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone or Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { if (phone.isNotEmpty() && password.isNotEmpty()) onLoginSuccess() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onSignUpClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Don't have an account? Sign Up")
        }
    }
}

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("QuickChat - Sign Up", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone or Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (phone.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                    onSignUpSuccess()
                } else {
                    Toast.makeText(
                        null,
                        "Please fill all fields and make sure passwords match",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onBackToLogin,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back to Login")
        }
    }
}

@Composable
fun ChatScreen(
    smsHistory: List<String>,
    onSendSms: (String, String) -> Unit,
    onLogout: () -> Unit
) {
    var phone by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Chat") })
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("History") })
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            0 -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Enter Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("Type Message") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (phone.isNotEmpty() && message.isNotEmpty()) {
                            onSendSms(phone, message)
                            message = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Send SMS") }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Logout") }
            }
            1 -> {
                Text("Message History", style = MaterialTheme.typography.titleMedium)
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(smsHistory) { sms ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) { Text(text = sms, modifier = Modifier.padding(8.dp)) }
                    }
                }
            }
        }
    }
}
