package com.example.exolearnapp.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.exolearnapp.learningmodel.model.Module;
import com.example.exolearnapp.learningmodel.model.UserProgress;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseUtils {
    private static final String TAG = "FirebaseUtils";
    private final FirebaseFirestore db;
    private final FirebaseAuth mAuth;

    // Singleton instance for FirebaseUtils
    private static FirebaseUtils instance = null;

    private FirebaseUtils() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseUtils getInstance() {
        if (instance == null) {
            instance = new FirebaseUtils();
        }
        return instance;
    }

    /**
     * Method to save a module object to Firestore under the user's UUID.
     * @param module The Module object to be saved.
     */
    public void saveModuleToFirestore(Module module) {
        if (mAuth.getCurrentUser() != null) {
            String userUUID = mAuth.getCurrentUser().getUid();
            CollectionReference userModulesRef = db.collection(userUUID).document("modules").collection("modules");

            userModulesRef.document(module.getModuleId())
                    .set(module)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Module " + module.getModuleId() + " added successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error adding module: ", e);
                        }
                    });
        } else {
            Log.e(TAG, "User is not authenticated. Cannot save module.");
        }
    }

    /**
     * Method to save user progress to Firestore.
     * @param userProgress The UserProgress object to be saved.
     */
    public void saveUserProgress(UserProgress userProgress) {
        if (mAuth.getCurrentUser() != null) {
            String userUUID = mAuth.getCurrentUser().getUid();
            DocumentReference progressRef = db.collection(userUUID).document("UserProgress");

            progressRef.set(userProgress)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "User progress saved successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error saving user progress: ", e);
                        }
                    });
        } else {
            Log.e(TAG, "User is not authenticated. Cannot save progress.");
        }
    }

    /**
     * Method to load user progress from Firestore.
     * @param callback The callback function to handle the loaded UserProgress object.
     */
    public void loadUserProgress(final ProgressCallback callback) {
        if (mAuth.getCurrentUser() != null) {
            String userUUID = mAuth.getCurrentUser().getUid();
            DocumentReference progressRef = db.collection(userUUID).document("UserProgress");

            progressRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        UserProgress userProgress = documentSnapshot.toObject(UserProgress.class);
                        callback.onProgressLoaded(userProgress);
                    } else {
                        Log.d(TAG, "No user progress found.");
                        callback.onProgressLoaded(null);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Error loading user progress: ", e);
                }
            });
        } else {
            Log.e(TAG, "User is not authenticated. Cannot load progress.");
        }
    }

    /**
     * Method to load all modules from Firestore for a specific user.
     * @param callback The callback function to handle the loaded modules.
     */
    public void loadModules(final ModuleCallback callback) {
        if (mAuth.getCurrentUser() != null) {
            String userUUID = mAuth.getCurrentUser().getUid();
            CollectionReference modulesRef = db.collection(userUUID).document("modules").collection("modules");

            modulesRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    callback.onModulesLoaded(queryDocumentSnapshots);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Error loading modules: ", e);
                }
            });
        } else {
            Log.e(TAG, "User is not authenticated. Cannot load modules.");
        }
    }

    // Define an interface for progress callback
    public interface ProgressCallback {
        void onProgressLoaded(UserProgress userProgress);
    }

    // Define an interface for module callback
    public interface ModuleCallback {
        void onModulesLoaded(QuerySnapshot queryDocumentSnapshots);
    }
}
