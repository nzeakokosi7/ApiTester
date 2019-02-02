package com.varscon.apitester.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.varscon.apitester.Adapter.ContactAdapter;
import com.varscon.apitester.Model.Contact;
import com.varscon.apitester.Model.ContactResponse;
import com.varscon.apitester.Model.SaveResponse;
import com.varscon.apitester.R;
import com.varscon.apitester.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mContact_list;
    private LinearLayoutManager mLayoutmanager;
    private GridLayoutManager gridLayoutManager;
    private ContactAdapter contactAdapter;
    private int counter = 1;
    private Integer index = 1;

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String BASE_URL = "https://reqres.in/api/";

    private static Retrofit retrofit = null;
    private FloatingActionButton saveFab;
    private TextView mImportText;
    private ImageView mFavBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        if (firstRun) {
            //if on first time launch the onboarding activity


            Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }

        setContentView(R.layout.activity_main);

        mContact_list = findViewById(R.id.recyclerview);
        gridLayoutManager = new GridLayoutManager(this, 2);
        mContact_list.setHasFixedSize(true);
        mContact_list.setLayoutManager(gridLayoutManager);
        contactAdapter  = new ContactAdapter(MainActivity.this);
        saveFab = findViewById(R.id.fab);

        mFavBtn = findViewById(R.id.fav_button);

        mImportText = findViewById(R.id.notice_text);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveContact();
            }
        });

        mImportText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContact_list.setVisibility(View.VISIBLE);
                mImportText.setText("Loading...");
                connectAndGetApiData();
            }
        });

        mFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Dummy button, still in the works", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveContact() {
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<SaveResponse> saveResponseCall = apiInterface.saveContact("Kosi Nzeako", "Developer");
        saveResponseCall.enqueue(new Callback<SaveResponse>() {
            @Override
            public void onResponse(Call<SaveResponse> call, Response<SaveResponse> response) {
                if(response.isSuccessful()) {
//                    showResponse(response.body().toString());
                    Toast.makeText(MainActivity.this, "post submitted to API." + response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveResponse> call, Throwable t) {

            }
        });
    }

    public void connectAndGetApiData() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()

                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

        }


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ContactResponse> contactResponseCall = apiInterface.getContactList(index.toString());

        contactResponseCall.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                mImportText.setVisibility(View.GONE);
                List<Contact> contacts = response.body().getData();
                counter = response.body().getTotalPages();
                contactAdapter.setContacts(contacts);
                mContact_list.setAdapter(contactAdapter);

                //change recyclerview background color
                mContact_list.setBackgroundColor(getResources().getColor(R.color.white));

                for (int i = 0; i < counter; i++) {
                    index++;
                    updateAdapter(index);
                }
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {

            }
        });


    }

    private void updateAdapter(Integer index) {
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ContactResponse> contactResponseCall = apiInterface.getContactList(index.toString());

        contactResponseCall.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                List<Contact> contacts = response.body().getData();
                counter = response.body().getTotalPages();
                contactAdapter.setContacts(contacts);
                mContact_list.setAdapter(contactAdapter);
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {

            }
        });

    }

}
