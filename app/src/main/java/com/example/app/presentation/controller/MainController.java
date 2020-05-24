
package com.example.app.presentation.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.example.app.presentation.view.MainActivity;

public class MainController {

    private MainActivity view;

    public MainController(MainActivity mainActivity) {
        this.view = mainActivity;
    }

    public void onStart(){
        checkButton();
    }

    public void onButtonClick(Button recherche){
        view.navigateToInfoProduit();
    }

    public void recupererCode(){
        view.coderentre_string = view.coderentre.getText().toString();
    }

    public void checkButton(){
        view.recherche.setEnabled(false);

        view.coderentre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view.recherche.setEnabled(s.toString().length() == 13);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
