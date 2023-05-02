package com.app.realjobs.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;

import com.app.realjobs.R;
import com.app.realjobs.databinding.ActivityLoginBinding;
import com.app.realjobs.databinding.ActivityRegisterBinding;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvCreateAccount;
    ActivityLoginBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        btnLogin = findViewById(R.id.btnLogin);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);

        btnLogin.setOnClickListener(v -> {
            if (validateFields()) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        tvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

        });


    }

    private boolean validateFields() {
        boolean isValid = true;
        if (binding.etMobile.getText().toString().length() < 10) {
            binding.etMobile.setError("Mobile number should be minimum 10 digits");
            isValid = false;
        } else {
            binding.etMobile.setError(null);
        }
        if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError("Password is required");
            isValid = false;
        } else {
            binding.etPassword.setError(null);
        }

        return isValid;
    }
}
