package com.example.goingonce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.goingonce.R;

public class PaymentActivity extends AppCompatActivity {

    /*
    * We will have to configure our app to use Braintree Payment Gateway for PayPal and Card Payment
    * As for the M-pesa part, we shall use Daraja API
    * One can also pay from the App's Wallet
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
}