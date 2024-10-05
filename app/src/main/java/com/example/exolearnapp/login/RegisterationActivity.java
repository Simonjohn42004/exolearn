package com.example.exolearnapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.exolearnapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterationActivity extends AppCompatActivity {

    private EditText usernameInput, emailInput, passwordInput, confirmPasswordInput;
    private Button signUpButton;
    private LottieAnimationView lottieCheckbox;
    private boolean isPasswordVisible = false;
    private FrameLayout loadingScreen; // Loading screen overlay
    TextView alreadyHaveAccount;
    // Firebase Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registeration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        signUpButton = findViewById(R.id.signUpButton);
        lottieCheckbox = findViewById(R.id.lottieAnimationView);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        loadingScreen = findViewById(R.id.loadingScreen); // Loading screen initialization

        // Set Lottie Checkbox onClickListener to toggle password visibility
        lottieCheckbox.setOnClickListener(v -> togglePasswordVisibility());

        // Set onClickListener for Sign Up Button
        signUpButton.setOnClickListener(v -> registerUser());

        // Set onClickListener for "Already have an account? Log in"
        alreadyHaveAccount.setOnClickListener(v -> showLoadingAndNavigateToLogin());
    }


    // Toggle password visibility
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide the password
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            confirmPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordInput.setSelection(passwordInput.getText().length());
            confirmPasswordInput.setSelection(confirmPasswordInput.getText().length());
            lottieCheckbox.setSpeed(-1); // Set Lottie to the unchecked state
            lottieCheckbox.playAnimation();
        } else {
            // Show the password
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT);
            confirmPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT);
            passwordInput.setSelection(passwordInput.getText().length());
            confirmPasswordInput.setSelection(confirmPasswordInput.getText().length());
            lottieCheckbox.setSpeed(1); // Set Lottie to the checked state
            lottieCheckbox.playAnimation();
        }
        isPasswordVisible = !isPasswordVisible;
    }

    // Handle registration
    private void registerUser() {
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(RegisterationActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(RegisterationActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(RegisterationActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the loading screen
        loadingScreen.setVisibility(View.VISIBLE);

        // Create the user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(RegisterationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        // Hide the loading screen and move to login screen
                        new Handler().postDelayed(() -> {
                            loadingScreen.setVisibility(View.GONE);
                            Intent intent = new Intent(RegisterationActivity.this, MainLoginActivity.class);
                            startActivity(intent);
                            finish(); // Close the current activity
                        }, 1500); // 1.5 seconds delay

                    } else {
                        // If registration fails, display a message to the user.
                        loadingScreen.setVisibility(View.GONE); // Hide the loading screen in case of failure
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(RegisterationActivity.this, "This email is already registered", Toast.LENGTH_SHORT).show();
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(RegisterationActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterationActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Show loading screen and navigate back to login page
    private void showLoadingAndNavigateToLogin() {
        // Show the loading screen
        loadingScreen.setVisibility(View.VISIBLE);

        // Delay for 1.5 seconds to simulate loading effect
        new Handler().postDelayed(() -> {
            // Hide the loading screen
            loadingScreen.setVisibility(View.GONE);

            // Navigate back to MainLoginActivity
            Intent intent = new Intent(RegisterationActivity.this, MainLoginActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        }, 1500); // 1.5 seconds delay
    }
}