package com.example.appshopping.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appshopping.R;
import com.example.appshopping.ultils.CheckConnection;
import com.example.appshopping.ultils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InformatinCustomer extends AppCompatActivity {
    EditText edtcustomername,edtphonenumber,edtemail;
    Button btnvertify,btnback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatin_customer);
        setWidget();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check your connection !!!");
        }
    }

    private void EventButton() {
        btnvertify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edtcustomername.getText().toString().trim();
                final String phone = edtphonenumber.getText().toString().trim();
                final String email = edtemail.getText().toString().trim();
                if (name.length() >0 && phone.length() >0 && email.length()>0){
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if (Integer.parseInt(madonhang)>0){
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.arrcart.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"You have successfully added Shopping Cart Data !!!");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please Continue Shopping !!!");
                                        }else{
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Shopping Cart Data is Defective !!!");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i=0;i<MainActivity.arrcart.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.arrcart.get(i).getProductId());
                                                jsonObject.put("tensanpham",MainActivity.arrcart.get(i).getProductName());
                                                jsonObject.put("giasanpham",MainActivity.arrcart.get(i).getPrice());
                                                jsonObject.put("soluongsanpham",MainActivity.arrcart.get(i).getProductNumber());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang",name);
                            hashMap.put("email",email);
                            hashMap.put("sodienthoai",phone);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Please check your database !!!");
                }
            }
        });
    }

    private void setWidget() {
        edtcustomername = findViewById(R.id.edittextcustomername);
        edtemail = findViewById(R.id.edittextemail);
        edtphonenumber = findViewById(R.id.edittextphonenumber);
        btnvertify = findViewById(R.id.buttonvertify);
        btnback = findViewById(R.id.buttonback);
    }
}
