package com.engsoft.poli.upe.quitandaverdeapp.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.engsoft.poli.upe.quitandaverdeapp.models.Product;
import com.engsoft.poli.upe.quitandaverdeapp.providers.QuitandaVerdeContentProvider;
import com.engsoft.poli.upe.quitandaverdeapp.providers.QuitandaVerdeDBCreator;
import java.util.ArrayList;

public class ProductDao {

    private ContentResolver qvContentResolver;
    private final String[] allColumns = {QuitandaVerdeDBCreator.PRODUCTS_COLUMN_ID,
            QuitandaVerdeDBCreator.PRODUCTS_COLUMN_NAME,
            QuitandaVerdeDBCreator.PRODUCTS_COLUMN_VALUE};

    public ProductDao(ContentResolver qvContentResolver){
        this.qvContentResolver = qvContentResolver;
    }

    public void seedDBData(){

        Cursor cursor = qvContentResolver.query(QuitandaVerdeContentProvider.CONTENT_URI_PRODUCTS, allColumns, null, null, null);

        if(cursor != null && cursor.getCount() == 0) {
            for (Product p : getProductsList()) {
                ContentValues values = new ContentValues();
                values.put(QuitandaVerdeDBCreator.PRODUCTS_COLUMN_NAME, p.getProductName());
                values.put(QuitandaVerdeDBCreator.PRODUCTS_COLUMN_VALUE, p.getProductValue());
                qvContentResolver.insert(QuitandaVerdeContentProvider.CONTENT_URI_PRODUCTS, values);
            }
        }

        cursor.close();
    }

    public ArrayList<Product> getAllProducts(){

        Cursor cursor = qvContentResolver.query(QuitandaVerdeContentProvider.CONTENT_URI_PRODUCTS, allColumns, null, null, null);
        ArrayList<Product> productsArrayList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            productsArrayList.add(product);
            cursor.moveToNext();
        }
        cursor.close();

        return productsArrayList;
    }

    public Product getProductById(int id) {

        Cursor cursor = qvContentResolver.query(QuitandaVerdeContentProvider.CONTENT_URI_PRODUCTS,
                allColumns, QuitandaVerdeDBCreator.PRODUCTS_COLUMN_ID + "= ?",
                new String[] { id + "" }, null);

        Product result = null;

        if(cursor != null && cursor.getCount() > 0) {

            cursor.moveToFirst();
            result = cursorToProduct(cursor);
        }

        cursor.close();
        return result;
    }

    private Product cursorToProduct(Cursor cursor) {
        Product product = new Product(cursor.getString(1), cursor.getDouble(2));
        product.setId(cursor.getInt(0));
        return product;
    }

    private ArrayList<Product> getProductsList(){

        ArrayList<Product> productsArrayList = new ArrayList<>();
        productsArrayList.add(new Product("Banana", 0.54));
        productsArrayList.add(new Product("Tomate", 0.21));
        productsArrayList.add(new Product("Maçã", 0.32));
        productsArrayList.add(new Product("Abacaxi", 0.41));
        productsArrayList.add(new Product("Alface", 0.21));
        productsArrayList.add(new Product("Cebola", 0.45));
        productsArrayList.add(new Product("Limão", 0.25));
        productsArrayList.add(new Product("Beterraba", 0.31));
        productsArrayList.add(new Product("Pêra", 0.34));
        productsArrayList.add(new Product("Melão", 0.45));
        productsArrayList.add(new Product("Batata", 0.32));
        productsArrayList.add(new Product("Mamão", 0.21));
        productsArrayList.add(new Product("Cenoura", 0.12));
        productsArrayList.add(new Product("Coentro", 0.34));
        productsArrayList.add(new Product("Brócolis", 0.62));

        return productsArrayList;
    }
}
