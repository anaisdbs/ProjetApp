package com.example.app.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Constant;
import com.example.app.R;
import com.example.app.Singletons;
import com.example.app.presentation.controller.InfoProduitController;

import com.squareup.picasso.Picasso;


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
        Toast.makeText(this,Constant.ERROR, Toast.LENGTH_SHORT ).show();
    }

    public void showImage(){

        if(controller2.product.getImage_url() == null){
            image_produit.setImageResource(R.drawable.ic_image);
        }else {
            Picasso.get()
                    .load(controller2.product.getImage_url())
                    .fit()
                    .centerInside()
                    .into(image_produit);
        }
    }

    public void showProduit() {
        if (controller2.product.getProduct_name() == null) {
            txtname.setText(R.string.InfoInconnu);
        } else {
            if (controller2.product.getProduct_name().equals("")) {
                txtname.setText(R.string.InfoInconnu);
            } else {
                txtname.setText(controller2.product.getProduct_name());
            }
        }

        if(controller2.product.getOrigins() == null ){
            txtorigins.setText(R.string.InfoInconnue);
        }else {
            if (controller2.product.getOrigins().equals("") ) {
                txtorigins.setText(R.string.InfoInconnue);
            } else {
                txtorigins.setText(controller2.product.getOrigins());
            }
        }

        if (controller2.product.getAllergens() ==  null){
            txtallergens.setText(R.string.InfoAllergen);
        }else {
            if (controller2.product.getAllergens().equals("")) {
                txtallergens.setText(R.string.InfoAllergen);
            } else {
                txtallergens.setText(controller2.product.getAllergens().replaceAll("en:", " "));
            }
        }
        if (controller2.product.getNutriscore_grade() == null){
            txtnutriscore.setText(R.string.InfoInconnu);
        }else{
            if (controller2.product.getNutriscore_grade().equals("")){
                txtnutriscore.setText(R.string.InfoInconnu);
            }else{
                txtnutriscore.setText(controller2.product.getNutriscore_grade());
            }
        }
    }

    public void navigateToIngredientsList() {
        Intent myIntent = new Intent(InfoProduit.this, IngredientsList.class);
        myIntent.putExtra(Constant.KEY_CODE_PRODUIT, code);
        myIntent.putExtra(Constant.KEY_CODE2,controller2.ancien_code);
        InfoProduit.this.startActivity(myIntent);
    }
    public void navigateToErreur() {
        Intent myIntent = new Intent(InfoProduit.this, ErreurActivity.class);
        myIntent.putExtra(Constant.ERROR_NAME, controller2.erreur);
        InfoProduit.this.startActivity(myIntent);
    }

}
