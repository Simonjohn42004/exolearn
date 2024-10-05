package com.example.exolearnapp.login;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.exolearnapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInputForgotPassword;
    private FrameLayout loadingScreen;
    private LottieAnimationView loadingAnimation;
    Button forgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        emailInputForgotPassword = findViewById(R.id.emailInputForgotPassword);
        loadingScreen = findViewById(R.id.loadingScreen);
        loadingAnimation = findViewById(R.id.loadingAnimation);

        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        forgotPasswordButton.setOnClickListener(v -> {
            String email = emailInputForgotPassword.getText().toString().trim();
            if (validateEmail(email)) {
                showLoadingScreen();
                resetPassword(email);
            }
        });
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void resetPassword(String email) {
        if (!isValidEmail(email)) {
            hideLoadingScreen();
            Toast.makeText(ForgotPasswordActivity.this, "Invalid email format. Please enter a valid email.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Show loading screen
        showLoadingScreen();

        // Send password reset email
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            hideLoadingScreen(); // Hide loading screen

            if (task.isSuccessful()) {
                // Show success dialog
                showPasswordResetDialog();
            } else {
                // Show error toast
                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred";
                Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset email: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Show Password Reset Dialog with "Change Password" and "Continue" button
    private void showPasswordResetDialog() {
        Dialog dialog = new Dialog(ForgotPasswordActivity.this);
        dialog.setContentView(R.layout.dialog_email_verification);  // Create a custom layout for the dialog
        dialog.setCancelable(false);

        TextView title = dialog.findViewById(R.id.dialogTitle);
        TextView body = dialog.findViewById(R.id.dialogBody);
        Button continueButton = dialog.findViewById(R.id.continueButton);

        title.setText("Change Password");
        body.setText("A password reset link has been sent to your email. Please check your inbox and follow the instructions to reset your password.");

        continueButton.setOnClickListener(v -> {
            openEmailApp();
            dialog.dismiss();
        });

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
            Toast.makeText(ForgotPasswordActivity.this, "No email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    private void showLoadingScreen() {
        loadingScreen.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();
    }

    private void hideLoadingScreen() {
        loadingScreen.setVisibility(View.GONE);
        loadingAnimation.cancelAnimation();
    }


    private boolean isValidEmail(CharSequence email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}