package com.example.goingonce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goingonce.R;

public class ViewItem extends AppCompatActivity implements View.OnClickListener {
    public Context c;
    private ImageView imgItem,imgAdd,imgMinus,imgBack,imgMore;
    private Button btnBid;
    private TextView txtType,txtLocation,txtTitle,txtPrice,txtDesc;
    private String itemId,itemTitle,itemDesc,itemType,itemPrice,itemLocation,itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.single_item_view);
        
        setUpWidgets();
    }

    private void setUpWidgets() {
        txtType=findViewById(R.id.txtCat);
        txtLocation=findViewById(R.id.txtLocation);
        txtTitle=findViewById(R.id.txtItemTitle);
        txtPrice=findViewById(R.id.txtPrice);
        txtDesc=findViewById(R.id.txtItemDesc);
        imgItem=findViewById(R.id.imgItem);
        imgAdd=findViewById(R.id.btnAdd);
        imgMinus=findViewById(R.id.minusBtn);
        imgBack=findViewById(R.id.bckBtn);
        imgMore=findViewById(R.id.moreBtn);
        btnBid=findViewById(R.id.btnBid);
    }


    @Override
    public void onClick(View v) {

    }
}