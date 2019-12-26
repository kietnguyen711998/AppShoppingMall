package com.example.appshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appshopping.R;
import com.example.appshopping.activities.CartActivity;
import com.example.appshopping.activities.MainActivity;
import com.example.appshopping.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrCart;

    public CartAdapter(Context context, ArrayList<Cart> arrCart) {
        this.context = context;
        this.arrCart = arrCart;
    }

    @Override
    public int getCount() {
        return arrCart.size();
    }

    @Override
    public Object getItem(int position) {
        return arrCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtcartname,txtcartcost;
        public ImageView imgcart;
        public Button btnminus,btnvalues,btnplus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.dong_cart,null);
            viewHolder.txtcartname = (TextView) convertView.findViewById(R.id.textviewcartname);
            viewHolder.txtcartcost = (TextView) convertView.findViewById(R.id.textviewcartcost);
            viewHolder.imgcart = (ImageView) convertView.findViewById(R.id.imageviewcart);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = (Button) convertView.findViewById(R.id.buttonvalue);
            viewHolder.btnplus = (Button) convertView.findViewById(R.id.buttonplus);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Cart cart= (Cart) getItem(position);
        viewHolder.txtcartname.setText(cart.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtcartcost.setText(decimalFormat.format(cart.getPrice()) + " Đ");
        Picasso.get().load(cart.getProductImage())
                .error(R.drawable.error)
                .into(viewHolder.imgcart);
        viewHolder.btnvalues.setText(cart.getProductNumber()+"");

        int SL = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (SL>=10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if(SL <=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if (SL >=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SLNew = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) +1;
                int SLNow = MainActivity.arrcart.get(position).getProductNumber();
                long PriceNow = MainActivity.arrcart.get(position).getPrice();

                MainActivity.arrcart.get(position).setProductNumber(SLNew);
                long PriceNew = (PriceNow * SLNew)/ SLNow;
                MainActivity.arrcart.get(position).setPrice(PriceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtcartcost.setText(decimalFormat.format(PriceNew) + " Đ");
                CartActivity.EventUltil();
                if (SLNew > 9){
                    finalViewHolder2.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder2.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnvalues.setText(String.valueOf(SLNew));
                }else {
                    finalViewHolder2.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnvalues.setText(String.valueOf(SLNew));
                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int SLNew = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) - 1;
                int SLNow = MainActivity.arrcart.get(position).getProductNumber();
                long PriceNow = MainActivity.arrcart.get(position).getPrice();

                MainActivity.arrcart.get(position).setProductNumber(SLNew);
                long PriceNew = (PriceNow * SLNew)/ SLNow;
                MainActivity.arrcart.get(position).setPrice(PriceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtcartcost.setText(decimalFormat.format(PriceNew) + " Đ");
                CartActivity.EventUltil();
                if (SLNew < 2){
                    finalViewHolder2.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder2.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnvalues.setText(String.valueOf(SLNew));
                }else {
                    finalViewHolder2.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnvalues.setText(String.valueOf(SLNew));
                }
            }
        });
        return convertView;

    }
}
