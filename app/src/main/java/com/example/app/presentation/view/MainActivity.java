package com.example.app.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.app.data.FoodApi;
import com.example.app.R;
import com.example.app.presentation.model.Ingredients;
import com.example.app.presentation.model.ResFoodResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    static final String BASE_URL = "https://world.openfoodfacts.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("application_anais", Context.MODE_PRIVATE);


         gson = new GsonBuilder()
                .setLenient()
                .create();
        List<Ingredients> ingredientsList = getDataFromCache();

        if(ingredientsList != null){
             showList(ingredientsList);
         }else{
             makeApiCall();

         }

/*      EditText coderentré = (EditText)findViewById(R.id.mon_code_barre);
        IngredientCode code= new IngredientCode();
        code.setCode(coderentré.getText().toString());

        Button mainButton =  findViewById(R.id.button);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(code) , Toast.LENGTH_SHORT ).show();
            }
        });*/

        // Toast.makeText(this, (CharSequence) code, Toast.LENGTH_SHORT ).show();

    }

    private List<Ingredients> getDataFromCache(){
        String jsonIngredient = sharedPreferences.getString("jsonIngredientsList", null);
        if(jsonIngredient == null){
            return null;
        }else {
            Type listType = new TypeToken<List<Ingredients>>() {
            }.getType();
            return gson.fromJson(jsonIngredient, listType);
        }
    }

    private void showError() {
        Toast.makeText(this,"Api Erreur", Toast.LENGTH_SHORT ).show();
    }

    private  void showList(List<Ingredients> ingredientsList){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new ListAdapter(ingredientsList);
        recyclerView.setAdapter(mAdapter);
    }
    private void makeApiCall(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FoodApi foodApi = retrofit.create(FoodApi.class);

        Call<ResFoodResponse> call = foodApi.getFoodResponse("0737628064502");
        call.enqueue(new Callback<ResFoodResponse>() {
            @Override
            public void onResponse(Call<ResFoodResponse> call, Response<ResFoodResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ingredients> ingredientsList = response.body().getProduct().getIngredients();
                    saveList(ingredientsList);
                    showList(ingredientsList);
                } else{
                    showError();
                }
            }
            @Override
            public void onFailure(Call<ResFoodResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<Ingredients> ingredientsList) {
        String jsonString = gson.toJson(ingredientsList);
        sharedPreferences
                .edit()
                .putString("jsonIngredientsList", jsonString)
                .apply();
        Toast.makeText(this,"Données sauvegardée", Toast.LENGTH_SHORT ).show();
    }
}
