package com.engsoft.poli.upe.quitandaverdeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.engsoft.poli.upe.quitandaverdeapp.dao.ProductDao;
import com.engsoft.poli.upe.quitandaverdeapp.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductsActivity extends AppCompatActivity {

    private ListView listView;
    private ProductsListAdapter listAdapter;
    private ArrayList<Product> productsArrayList;
    private ProductDao productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_items);

        listView = (ListView) findViewById(R.id.listView);
        productDAO = new ProductDao(getContentResolver());
        productsArrayList = productDAO.getAllProducts();

        listAdapter = new ProductsListAdapter(productsArrayList, ProductsActivity.this);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("selectedProduct",productsArrayList.get(position));
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                listAdapter.filter(searchQuery.toString().trim());
                listView.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ProductsListAdapter extends BaseAdapter {
        public class ViewHolder {
            TextView txtTitle;
        }

        public List<Product> productsList;
        public Context context;
        ArrayList<Product> arraylist;

        private ProductsListAdapter(List<Product> items, Context context) {
            this.productsList = items;
            this.context = context;
            arraylist = new ArrayList<Product>();
            arraylist.addAll(productsList);

        }

        @Override
        public int getCount() {
            return productsList.size();
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
            ViewHolder viewHolder;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_item_view, null);

                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.itemTitle);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText(productsList.get(position).getProductName() + "");
            return rowView;
        }

        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            productsList.clear();
            if (charText.length() == 0) {
                productsList.addAll(arraylist);

            } else {
                for (Product item : arraylist) {
                    if (charText.length() != 0 && item.getProductName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        productsList.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
