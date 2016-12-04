package com.engsoft.poli.upe.quitandaverdeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.engsoft.poli.upe.quitandaverdeapp.dao.ProductDao;
import com.engsoft.poli.upe.quitandaverdeapp.dao.ShoppingCartDao;
import com.engsoft.poli.upe.quitandaverdeapp.models.CartItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private CartListAdapter listAdapter;
    private ListView listView;
    private ArrayList<CartItem> cartArrayList;
    private ShoppingCartDao cartDAO;
    private ProductDao productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listView = (ListView) findViewById(R.id.listCartView);
        productDAO = new ProductDao(getContentResolver());
        cartDAO = new ShoppingCartDao(getContentResolver());
        cartArrayList = cartDAO.getAllItems();

        double total = processInCart();
        TextView txtCartTotal = (TextView) findViewById(R.id.cartTotal);
        txtCartTotal.setText("Total: R$ " + total);

        listAdapter = new CartListAdapter(cartArrayList, CartActivity.this);
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_cart_items_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_trash) {
            cartDAO.deleteAll();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_home) {
            Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentHome);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private double processInCart(){
        double total = 0;

        for (CartItem item : cartArrayList) {
            item.setProduct(productDAO.getProductById(item.getProductId()));
            total += item.getProduct().getProductValue() * item.getQuantity();
        }

        return round(total,2);
    }

    public class CartListAdapter extends BaseAdapter {
        public class ViewHolder {
            TextView txtTitle;
            TextView txtValue;
        }

        public List<CartItem> cartList;
        public Context context;
        ArrayList<CartItem> arraylist;

        private CartListAdapter(List<CartItem> items, Context context) {
            this.cartList = items;
            this.context = context;
            arraylist = new ArrayList<>();
            arraylist.addAll(cartList);
        }

        @Override
        public int getCount() {
            return cartList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            CartListAdapter.ViewHolder viewHolder;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.cart_item_view, null);

                viewHolder = new CartListAdapter.ViewHolder();
                viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.itemCartTitle);
                viewHolder.txtValue = (TextView) rowView.findViewById(R.id.itemCartValue);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (CartListAdapter.ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText(cartList.get(position).getProduct().getProductName());
            viewHolder.txtValue.setText(cartList.get(position).getTotal());
            return rowView;
        }
    }
}
