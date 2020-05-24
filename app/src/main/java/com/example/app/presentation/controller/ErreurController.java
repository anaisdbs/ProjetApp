
package com.example.app.presentation.controller;

import android.widget.Button;

import com.example.app.presentation.view.ErreurActivity;

public class ErreurController {

    private ErreurActivity view4;

    public ErreurController(ErreurActivity erreurActivity) {
        this.view4 = erreurActivity;
    }

    public void onStart(){
    }

    public void onButtonClick(Button retour_menu){
        view4.navigateToInfoMenu();
    }

}
