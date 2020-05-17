package com.example.app.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.Constant;
import com.example.app.R;
import com.example.app.Singletons;
import com.example.app.presentation.controller.InfoProduitController;
import com.example.app.presentation.controller.IngredientsListController;
import com.example.app.presentation.model.Ingredients;
import com.example.app.presentation.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InfoProduit extends AppCompatActivity {

    private InfoProduitController controller2;
    public String code;

    private TextView txtname;
    private TextView txtorigins;
    private TextView txtallergens;
    private TextView txtnutriscore;
    public Button buttonlist;
    public ImageView image_produit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_produit);

        txtname = findViewById(R.id.txtname);
        txtorigins = findViewById(R.id.txtorigins);
        txtallergens = findViewById(R.id.txtallergens);
        txtnutriscore = findViewById(R.id.txtnutriscore);
        image_produit = findViewById(R.id.image_produit);
        buttonlist = findViewById(R.id.button);


        Intent intent2 = getIntent();
        code = intent2.getStringExtra(Constant.KEY_CODE_PRODUIT); //if it's a string you stored.

        controller2 = new InfoProduitController(this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        controller2.onStart();

        buttonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller2.onButtonClick(buttonlist);
            }
        });
    }

    public void showError() {
        Toast.makeText(this,"Api Erreur", Toast.LENGTH_SHORT ).show();
    }

    public void showImage(){
        Picasso.get()
                .load(controller2.product.getImage_url())
                // .resizeDimen(R.dimen.image_size,R.dimen.image_size)
                .fit()
                .centerInside()
                .into(image_produit);
    }

    public void showProduit() {
        if (controller2.product.getProduct_name() == null) {
            txtname.setText("le nom du produit est inconnu");
        } else {
            if (controller2.product.getProduct_name().equals("")) {
                txtname.setText("le nom du produit est inconnu");
            } else {
                txtname.setText(controller2.product.getProduct_name());
            }
        }

        if(controller2.product.getOrigins() == null ){
            txtorigins.setText("l'origine du produit est inconnue");
        }else {
            if (controller2.product.getOrigins().equals("") ) {
                txtorigins.setText("l'origine du produit est inconnue");
            } else {
                txtorigins.setText(controller2.product.getOrigins());
            }
        }

        if (controller2.product.getAllergens() ==  null){
            txtallergens.setText("Aucun allergène connu pour ce produit");
        }else {
            if (controller2.product.getAllergens().equals("")) {
                txtallergens.setText("Aucun allergène connu pour ce produit");
            } else {
                txtallergens.setText(controller2.product.getAllergens());
            }
        }
        if (controller2.product.getNutriscore_grade() == null){
            txtnutriscore.setText("le nutriscore est inconnu");
        }else{
            if (controller2.product.getNutriscore_grade().equals("")){
                txtnutriscore.setText("le nutriscore est inconnu");
            }else{
                txtnutriscore.setText(controller2.product.getNutriscore_grade());
            }
        }
    }

    public void navigateToIngredientsList() {
        Intent myIntent = new Intent(InfoProduit.this, IngredientsList.class);
        myIntent.putExtra(Constant.KEY_CODE_PRODUIT, code);
        myIntent.putExtra("baba",controller2.ancien_code);
        InfoProduit.this.startActivity(myIntent);
    }
    public void navigateToErreur() {
        Intent myIntent = new Intent(InfoProduit.this, ErreurActivity.class);
        InfoProduit.this.startActivity(myIntent);
    }

}
