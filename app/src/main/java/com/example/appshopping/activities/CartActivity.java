package com.example.appshopping.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appshopping.R;
import com.example.appshopping.adapter.CartAdapter;
import com.example.appshopping.ultils.CheckConnection;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
    ListView lvcart;
    TextView txtNotify;
    static TextView txtTotalCash;
    Button btnPay,btnContinueBuy;
    Toolbar toolbarcart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setWidget();
        ActionToolbar();
        CheckData();
        EventUltil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnContinueBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrcart.size()>0){
                    Intent intent = new Intent(getApplicationContext(),InformatinCustomer.class);
                    startActivity(intent);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Your cart is empty. Please continue to Shopping !!!");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        lvcart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Conform Delete Products ???");
                builder.setMessage("Do you sure delete this product ???");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.arrcart.size()<=0){
                            txtNotify.setVisibility(View.VISIBLE);

                        }else  {
                            MainActivity.arrcart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.arrcart.size()<=0){
                                txtNotify.setVisibility(View.VISIBLE);
                            }else{
                                txtNotify.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    static public void EventUltil() {
        long totalcash = 0;
        for (int i=0; i< MainActivity.arrcart.size();i++){
            totalcash += MainActivity.arrcart.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTotalCash.setText(decimalFormat.format(totalcash)+ "D");
    }

    private void CheckData() {
        if (MainActivity.arrcart.size()<=0){
            cartAdapter.notifyDataSetChanged();
            txtNotify.setVisibility(View.VISIBLE);
            lvcart.setVisibility(View.INVISIBLE);
        }else {
            cartAdapter.notifyDataSetChanged();
            txtNotify.setVisibility(View.INVISIBLE);
            lvcart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarcart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setWidget() {
        lvcart =  findViewById(R.id.listviewcart);
        txtNotify = findViewById(R.id.textviewnotify);
        txtTotalCash = findViewById(R.id.textviewtotalcash);
        btnPay = findViewById(R.id.buttonpaycart);
        btnContinueBuy = findViewById(R.id.buttoncontinueshopping);
        toolbarcart = findViewById(R.id.toolbarcart);
        cartAdapter = new CartAdapter(CartActivity.this,MainActivity.arrcart);
        lvcart.setAdapter(cartAdapter);
    }
}
