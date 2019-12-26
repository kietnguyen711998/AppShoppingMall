package com.example.appshopping.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appshopping.R;
import com.example.appshopping.model.Cart;
import com.example.appshopping.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetail extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txttensp,txtgiasp,txtmotasp;
    Spinner spinner;
    Button btnorder;

    int id =0;
    String Tenchitietsp = "";
    int Giachitietsp =0;
    String Hinhanhchitietsp = "";
    String Motachitietsp = "";
    int Idsanpham =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setWidget();
        ActionToolbarchitiet();
        getInformation();
        CatchEventSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrcart.size() >0){
                    int SL = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i=0; i< MainActivity.arrcart.size(); i++){
                        if (MainActivity.arrcart.get(i).getProductId() == id){
                            MainActivity.arrcart.get(i).setProductNumber(MainActivity.arrcart.get(i).getProductNumber() + SL);
                            if (MainActivity.arrcart.get(i).getProductNumber()>=10){
                                MainActivity.arrcart.get(i).setProductNumber(10);
                            }
                            MainActivity.arrcart.get(i).setPrice(Giachitietsp* MainActivity.arrcart.get(i).getProductNumber());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong*Giachitietsp;
                        MainActivity.arrcart.add(new Cart(id,Tenchitietsp,Giamoi,Hinhanhchitietsp,soluong));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong*Giachitietsp;
                    MainActivity.arrcart.add(new Cart(id,Tenchitietsp,Giamoi,Hinhanhchitietsp,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(getApplicationContext(),R.layout.spinner_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getInformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        Tenchitietsp = sanpham.getTensanpham();
        Giachitietsp = sanpham.getGiasanpham();
        Hinhanhchitietsp = sanpham.getHinhanhsanpham();
        Motachitietsp = sanpham.getMotasanpham();
        Idsanpham = sanpham.IDSanpham;

        txttensp.setText(Tenchitietsp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiasp.setText("Gia : " + decimalFormat.format(Giachitietsp) + "Đ ");
        txtmotasp.setText(Motachitietsp);
        Picasso.get().load(Hinhanhchitietsp)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgchitiet);
    }

    private void ActionToolbarchitiet() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setWidget() {
        toolbarchitiet = findViewById(R.id.toolbarchitietsp);
        imgchitiet = findViewById(R.id.imagechitietsanpham);
        txttensp = findViewById(R.id.textviewtenchitietsanpham);
        txtgiasp = findViewById(R.id.textviewgiachitietsanpham);
        txtmotasp = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinners);
        btnorder = findViewById(R.id.buttondatmua);
    }
}
