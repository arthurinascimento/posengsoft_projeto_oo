package com.engsoft.poli.upe.quitandaverdeapp.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.engsoft.poli.upe.quitandaverdeapp.models.CartItem;
import com.engsoft.poli.upe.quitandaverdeapp.models.Product;
import com.engsoft.poli.upe.quitandaverdeapp.providers.QuitandaVerdeContentProvider;
import com.engsoft.poli.upe.quitandaverdeapp.providers.QuitandaVerdeDBCreator;

import java.util.ArrayList;


public class ShoppingCartDao {

    private final String[] allColumns = {QuitandaVerdeDBCreator.CART_COLUMN_ID,
            QuitandaVerdeDBCreator.CART_COLUMN_PRODUCTID,
            QuitandaVerdeDBCreator.CART_COLUMN_QTD};

    private ContentResolver qvContentResolver;

    public ShoppingCartDao(ContentResolver qvContentResolver){
        this.qvContentResolver = qvContentResolver;
    }

    public void deleteAll(){
        qvContentResolver.delete(QuitandaVerdeContentProvider.CONTENT_URI_CART, null, null);
    }

    public void deleteById(int id){
        qvContentResolver.delete(QuitandaVerdeContentProvider.CONTENT_URI_CART,
                QuitandaVerdeDBCreator.CART_COLUMN_ID + "=" + id,
                null);
    }

    public void addToCart(Product product){

        Cursor cursor = qvContentResolver.query(QuitandaVerdeContentProvider.CONTENT_URI_CART,
                allColumns, QuitandaVerdeDBCreator.CART_COLUMN_PRODUCTID + "= ?",
                new String[] { product.getId() + "" }, null);

        if(cursor != null) {
            if (cursor.getCount() == 0) {

                ContentValues values = new ContentValues();
                values.put(QuitandaVerdeDBCreator.CART_COLUMN_PRODUCTID, product.getId());
                values.put(QuitandaVerdeDBCreator.CART_COLUMN_QTD, 1);
                qvContentResolver.insert(QuitandaVerdeContentProvider.CONTENT_URI_CART, values);

            } else {
                cursor.moveToFirst();
                CartItem cartItem = cursorToCartItem(cursor);
                int currentQt = cartItem.getQuantity() + 1;

                ContentValues values = new ContentValues();
                values.put(QuitandaVerdeDBCreator.CART_COLUMN_QTD, currentQt);
                qvContentResolver.update(QuitandaVerdeContentProvider.CONTENT_URI_CART,
                        values, QuitandaVerdeDBCreator.CART_COLUMN_ID + "=" + cartItem.getId(), null);
            }
        }

        cursor.close();
    }

    public boolean removeFromCart(Product product){

        Cursor cursor = qvContentResolver.query(QuitandaVerdeContentProvider.CONTENT_URI_CART,
                allColumns, QuitandaVerdeDBCreator.CART_COLUMN_PRODUCTID + "= ?",
                new String[] { product.getId() + "" }, null);

        if(cursor != null && cursor.getCount() > 0) {

                cursor.moveToFirst();
                CartItem cartItem = cursorToCartItem(cursor);
                int currentQt = cartItem.getQuantity();

                if(currentQt > 1) {
                    currentQt -= 1;
                    ContentValues values = new ContentValues();
                    values.put(QuitandaVerdeDBCreator.CART_COLUMN_QTD, currentQt);
                    qvContentResolver.update(QuitandaVerdeContentProvider.CONTENT_URI_CART,
                            values, QuitandaVerdeDBCreator.CART_COLUMN_ID + "=" + cartItem.getId(), null);
                }else if(currentQt == 1){
                    this.deleteById(cartItem.getId());
                }

        }else{
            return false;
        }

        cursor.close();
        return true;
    }

    public ArrayList<CartItem> getAllItems(){

        Cursor cursor = qvContentResolver.query(QuitandaVerdeContentProvider.CONTENT_URI_CART, allColumns, null, null, null);
        ArrayList<CartItem> cartArrayList = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CartItem item = cursorToCartItem(cursor);
            cartArrayList.add(item);
            cursor.moveToNext();
        }
        cursor.close();

        return cartArrayList;
    }

    private CartItem cursorToCartItem(Cursor cursor) {
        CartItem cartItem = new CartItem(cursor.getInt(1), cursor.getInt(2));
        cartItem.setId(cursor.getInt(0));
        return cartItem;
    }
}
