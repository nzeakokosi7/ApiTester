package com.varscon.apitester.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.varscon.apitester.Model.AuthResponse;
import com.varscon.apitester.R;
import com.varscon.apitester.Retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthActivity extends AppCompatActivity {

    private TextView mSignInText, mSignUpText, mSignInTab, mSignUpTab;
    private RelativeLayout mParentLayout;
    private EditText mEmailInput, mPasswordInput;

    public static final String BASE_URL = "https://reqres.in/api/";

    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()

                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

        }

        mParentLayout = findViewById(R.id.auth_parent);

        mSignInText = findViewById(R.id.sign_in_text_button);
        mSignUpText = findViewById(R.id.sign_up_text);

        mSignInTab = findViewById(R.id.sign_in_tab);
        mSignUpTab = findViewById(R.id.sign_up_tab);

        mEmailInput = findViewById(R.id.input_email);
        mPasswordInput = findViewById(R.id.input_password);

        mSignInTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable background = ContextCompat.getDrawable(AuthActivity.this, R.color.teal_primary);
                mParentLayout.setBackground(background);
                mSignUpText.setVisibility(View.INVISIBLE);
                mSignInText.setVisibility(View.VISIBLE);
            }
        });

        mSignUpTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable background = ContextCompat.getDrawable(AuthActivity.this, R.color.light_blue_primary);
                mParentLayout.setBackground(background);
                mSignUpText.setVisibility(View.VISIBLE);
                mSignInText.setVisibility(View.INVISIBLE);
            }
        });

        mSignInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    String email = mEmailInput.getText().toString();
                    String password = mPasswordInput.getText().toString();

                    signIn(email, password);
                }
            }
        });

        mSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    String email = mEmailInput.getText().toString();
                    String password = mPasswordInput.getText().toString();

                    signUp(email, password);
                }
            }
        });

    }

    private void signIn(String email, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<AuthResponse> saveResponseCall = apiInterface.signIn(email, password);
        saveResponseCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(response.isSuccessful() && response.body().getToken().equals("QpwL5tke4Pnpja7X")) {
//                    showResponse(response.body().toString());

                    progressDialog.dismiss();
                    Toast.makeText(AuthActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                            | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

            }
        });
    }

    private void signUp(String email, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("registering");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<AuthResponse> saveResponseCall = apiInterface.signIn(email, password);
        saveResponseCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(response.isSuccessful() && response.body().getToken().equals("QpwL5tke4Pnpja7X")) {
//                    showResponse(response.body().toString());
                    progressDialog.dismiss();
                    Toast.makeText(AuthActivity.this, "Successful please login with your new details", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {

            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailInput.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailInput.setError("Required.");
            valid = false;
        } else {
            mEmailInput.setError(null);
        }

        String password = mPasswordInput.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordInput.setError("Required.");
            valid = false;
        } else {
            mPasswordInput.setError(null);
        }

        return valid;
    }
}
