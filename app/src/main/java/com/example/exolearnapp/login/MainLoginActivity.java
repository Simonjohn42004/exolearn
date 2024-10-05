package com.example.exolearnapp.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.exolearnapp.homescreen.MainHomePage; // Replace with your actual MainHomePage activity
import com.example.exolearnapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainLoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private FrameLayout loadingScreen;
    private LottieAnimationView loadingAnimation;
    private LottieAnimationView lottieCheckbox;
    private boolean isPasswordVisible = false;
    TextView createNewAccount;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        loadingScreen = findViewById(R.id.loadingScreen);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        lottieCheckbox = findViewById(R.id.lottieAnimationView);
        createNewAccount = findViewById(R.id.createNewAccount);
        forgotPassword = findViewById(R.id.forgotPassword);

        showLoadingScreen();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingScreen();
            }
        }, 3000);


        // Set Lottie Checkbox onClickListener to toggle password visibility
        lottieCheckbox.setOnClickListener(v -> togglePasswordVisibility());

        // Set onClickListener for Login Button
        loginButton.setOnClickListener(v -> loginUser());

        // Set onClickListener for "Create New Account"
        createNewAccount.setOnClickListener(v -> showLoadingAndNavigateToRegister());

        // Set onClickListener for "Forgot Password?"
        forgotPassword.setOnClickListener(v -> showLoadingAndNavigateToForgotPassword());
    }

    private void loginUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Input validation
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainLoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading screen
        showLoadingScreen();

        // Sign in the user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    hideLoadingScreen();

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null && user.isEmailVerified()) {
                            // Email is verified, proceed to the MainHomePage
                            navigateToMainHomePage();
                        } else if (user != null) {
                            // Email not verified, send a verification email
                            sendEmailVerification(user);
                        }
                    } else {
                        // Login failed, show error
                        Toast.makeText(MainLoginActivity.this, "Authentication failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendEmailVerification(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Show custom dialog for email verification success
                        showEmailVerificationDialog();
                    } else {
                        // Failed to send verification email
                        Toast.makeText(MainLoginActivity.this, "Failed to send verification email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //on successful login user moves to home page
    private void navigateToMainHomePage() {
        Intent intent = new Intent(MainLoginActivity.this, MainHomePage.class); // Replace with your actual home activity
        startActivity(intent);
        finish();
    }

    // shows the loading screen when the user start the login process
    private void showLoadingScreen() {
        loadingScreen.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();
    }

    //hides loading screen when the user completes login
    private void hideLoadingScreen() {
        loadingScreen.setVisibility(View.GONE);
        loadingAnimation.cancelAnimation();
    }

    //Allows user to view password
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide the password, set it to password mode
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordInput.setSelection(passwordInput.getText().length()); // Move the cursor to the end of the text
            lottieCheckbox.setSpeed(-1); // Set Lottie to the unchecked state
            lottieCheckbox.playAnimation();
        } else {
            // Show the password in plain text
            passwordInput.setInputType(InputType.TYPE_CLASS_TEXT);
            passwordInput.setSelection(passwordInput.getText().length()); // Move the cursor to the end of the text
            lottieCheckbox.setSpeed(1); // Set Lottie to the checked state
            lottieCheckbox.playAnimation();
        }

        // Toggle the boolean flag
        isPasswordVisible = !isPasswordVisible;
    }

    // moving user to registration page for signing up
    private void showLoadingAndNavigateToRegister() {
        // Show the loading screen
        loadingScreen.setVisibility(View.VISIBLE);

        // Delay for 1.5 seconds to simulate a loading effect before navigating
        new Handler().postDelayed(() -> {
            // Hide the loading screen
            loadingScreen.setVisibility(View.GONE);

            // Navigate to the RegisterationActivity
            Intent intent = new Intent(MainLoginActivity.this, RegisterationActivity.class);
            startActivity(intent);
        }, 1500); // 1.5 seconds delay
    }

    //moving user to forgot password page
    private void showLoadingAndNavigateToForgotPassword() {
        // Show the loading screen
        loadingScreen.setVisibility(View.VISIBLE);

        // Delay for 1.5 seconds to simulate a loading effect before navigating
        new Handler().postDelayed(() -> {
            // Hide the loading screen
            loadingScreen.setVisibility(View.GONE);

            // Navigate to the ForgotPasswordActivity
            Intent intent = new Intent(MainLoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }, 1500); // 1.5 seconds delay
    }

    private void showEmailVerificationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainLoginActivity.this);
        builder.setTitle("Email Verification");

        // Set up the message for the dialog
        builder.setMessage("A verification email has been sent to your email address. Please check your inbox and verify your account.");

        // Set up the "Continue" button
        builder.setPositiveButton("Continue", (dialog, which) -> {
            // Open email application when the user clicks "Continue"
            openEmailApp();
            dialog.dismiss(); // Close the dialog after clicking "Continue"
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Open email application
    private void openEmailApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Optional, to open a new task

        try {
            // Open email app with an intent to view the inbox
            startActivity(Intent.createChooser(intent, "Choose email app"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainLoginActivity.this, "No email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}



