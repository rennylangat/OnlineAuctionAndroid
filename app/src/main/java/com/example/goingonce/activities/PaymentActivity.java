package com.example.goingonce.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.goingonce.Common.Common;
import com.example.goingonce.R;
import com.example.goingonce.Remote.IFCMService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    /*
    * We will have to configure our app to use Braintree Payment Gateway for PayPal and Card Payment
    * As for the M-pesa part, we shall use Daraja API
    * One can also pay from the App's Wallet
    */
    private static final int REQUEST_CODE=1234;

    private Context mContext=PaymentActivity.this;


    private String API_GET_TOKEN="https://goingonce.herokuapp.com//main.php";
    private String API_CHECK_OUT="https://goingonce.herokuapp.com//checkout.php";
    private String token,amount;
    private HashMap<String, String> paramHash;

    private Button mPaymentBtn;
    private TextView mEditAmt,mCancelBtn;
    private LinearLayout mGroupWaiting, mGroupPayment;

    private String currUserID, cost;

    private IFCMService ifcmService;
    private DatabaseReference mRef;
    private FirebaseDatabase mFirebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mFirebaseDatabase.getReference();

        currUserID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        getActivityData();

        ifcmService= Common.getFCMService();
        init();
        getUserInfo();

        new getToken().execute();

        mPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPayment();
            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void submitPayment() {
        DropInRequest drop=new DropInRequest().clientToken(token);
        startActivityForResult(drop.getIntent(mContext),REQUEST_CODE);
    }

    private void init() {
        mPaymentBtn=(Button)findViewById(R.id.paymentBtn);
        mCancelBtn=(TextView)findViewById(R.id.cancelBtn);
        mEditAmt=(TextView)findViewById(R.id.moneyTxtView);
        mGroupPayment=(LinearLayout)findViewById(R.id.paymentGroup);
        mGroupWaiting=(LinearLayout)findViewById(R.id.waiting_group);

        mEditAmt.setText(cost);
    }

    private void getActivityData() {
        Bundle extras=getIntent().getExtras();

        if (extras!=null){
            cost=getIntent().getStringExtra("amount");
        }else{
            Toast.makeText(mContext,"Error getting data from previous screen", Toast.LENGTH_SHORT).show();
        }

    }
    private void getUserInfo(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            if (resultCode==RESULT_OK){
                DropInResult result=data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce=result.getPaymentMethodNonce();
                String strNonce=nonce.getNonce();

                if (!mEditAmt.getText().toString().isEmpty()|| Integer.parseInt(mEditAmt.getText().toString())!=0){
                    amount=getCurrentAmount();
                    paramHash=new HashMap<>();
                    paramHash.put("amount",amount);
                    paramHash.put("nonce",strNonce);

                    sendPayments();
                }else{
                    Toast.makeText(mContext,"Please enter a valid amount",Toast.LENGTH_SHORT).show();
                }
            }else if (resultCode==RESULT_CANCELED){
                Toast.makeText(mContext,"User Cancelled",Toast.LENGTH_SHORT).show();
            }else{
                Exception exception=(Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Toast.makeText(mContext,exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendPayments() {
        RequestQueue queue= Volley.newRequestQueue(mContext);
        StringRequest request=new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().contains("Successful")) {
                            Toast.makeText(mContext, "Transaction Successful", Toast.LENGTH_SHORT).show();
                            clearCart();
                            startActivity(new Intent(mContext,MainActivity.class));
                        }else{
                            Toast.makeText(mContext,"Transaction Failed "+response,Toast.LENGTH_SHORT).show();
                            Log.d("PaymentActivity:",response);
                            finish();
                        }
                    }
                }, error -> Toast.makeText(mContext,"onResponse: "+error.getMessage(),Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramHash==null)
                    return null;
                Map<String,String> params=new HashMap<>();
                for (String key:paramHash.keySet()){
                    params.put(key,paramHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        RetryPolicy retryPolicy=new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        queue.add(request);
    }

    private void clearCart() {
        mRef.child("Cart").child(currUserID).removeValue();
    }

    private String getCurrentAmount() {
        return mEditAmt.getText().toString();
    }

    private class getToken  extends AsyncTask {
        private ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog=new ProgressDialog(mContext, android.R.style.Theme_DeviceDefault);
            mDialog.setCancelable(false);
            mDialog.setMessage("Please Wait");
            mDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client=new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(String responseBody) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGroupWaiting.setVisibility(View.GONE);
                            mGroupPayment.setVisibility(View.VISIBLE);

                            token=responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Toast.makeText(mContext,"failure: "+exception.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mDialog.dismiss();
        }
    }
}