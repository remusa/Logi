package com.rms.logi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etPasswordRe)
    EditText etPasswordRe;
    @Bind(R.id.btnSignUp)
    Button btnSignUp;
    @Bind(R.id.tvLinkLogin)
    TextView tvLoginLink;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Registro");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.signup_createAccount));
        progressDialog.show();

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String reEnterPassword = etPasswordRe.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmailAndPassword: Failed=" + task.getException().getMessage());

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            onSignupFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 1000);
                        } else {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            onSignupSuccess();
                                            progressDialog.dismiss();
                                        }
                                    }, 1000);
                        }

                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onSignupSuccess() {
        btnSignUp.setEnabled(true);
        setResult(RESULT_OK, null);

        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(SignupActivity.this, R.string.signup_failed, Toast.LENGTH_SHORT).show();

        btnSignUp.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String reEnterPassword = etPasswordRe.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.email_valid));
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            etPassword.setError(getString(R.string.password_length));
            valid = false;
        } else {
            etPassword.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 6 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            etPasswordRe.setError(getString(R.string.password_matches));
            valid = false;
        } else {
            etPasswordRe.setError(null);
        }

        return valid;
    }
}
