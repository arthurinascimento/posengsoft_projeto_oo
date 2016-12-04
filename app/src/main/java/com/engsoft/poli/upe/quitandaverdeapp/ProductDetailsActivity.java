package com.engsoft.poli.upe.quitandaverdeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.engsoft.poli.upe.quitandaverdeapp.dao.ShoppingCartDao;
import com.engsoft.poli.upe.quitandaverdeapp.models.Product;

public class ProductDetailsActivity extends AppCompatActivity {

    private Product product = null;
    private ShoppingCartDao cartDAO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        product = (Product) data.getSerializable("selectedProduct");

        TextView txtProductName = (TextView) findViewById(R.id.itemName);
        TextView txtProductValue = (TextView) findViewById(R.id.itemValue);

        txtProductName.setText(getResources().getString(R.string.details_name) + " " + product.getProductName());
        txtProductValue.setText(getResources().getString(R.string.details_value) + " " + product.getProductValue().toString());

        cartDAO = new ShoppingCartDao(getContentResolver());

        Button buttonAddToCart = (Button) findViewById(R.id.addToCartBtn);
        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cartDAO.addToCart(product);
                Toast.makeText(getApplicationContext(), R.string.add_product, Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonRemoveFromCart = (Button) findViewById(R.id.removeFromCartBtn);
        buttonRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean removed = cartDAO.removeFromCart(product);
                if(removed) {
                    Toast.makeText(getApplicationContext(), R.string.remove_product, Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(), R.string.remove_none_product, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cartList) {
            Intent intentCart = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(intentCart);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
