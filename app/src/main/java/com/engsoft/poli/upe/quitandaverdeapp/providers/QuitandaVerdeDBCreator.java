package com.engsoft.poli.upe.quitandaverdeapp.providers;

import android.database.sqlite.SQLiteDatabase;

public class QuitandaVerdeDBCreator {

    // Tabela de Produtos
    public static final String PRODUCTS_TABLE = "product";
    public static final String PRODUCTS_COLUMN_ID = "_id";
    public static final String PRODUCTS_COLUMN_NAME = "name";
    public static final String PRODUCTS_COLUMN_VALUE = "value";

    // Tabela do Carrinho de Compras
    public static final String CART_TABLE = "shoppingcart";
    public static final String CART_COLUMN_ID = "_id";
    public static final String CART_COLUMN_PRODUCTID = "productid";
    public static final String CART_COLUMN_QTD = "quantity";

    // Tabela de Usuario
    public static final String USER_TABLE = "user";
    public static final String USER_COLUMN_ID = "_id";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_EMAIL = "email";

    // Script de criação da tabela produto
    private static final String SCRIPT_TABLE_PRODUCT =
            "CREATE TABLE " + PRODUCTS_TABLE + "("
                    + PRODUCTS_COLUMN_ID + " integer primary key autoincrement, "
                    + PRODUCTS_COLUMN_NAME + " text not null, "
                    + PRODUCTS_COLUMN_VALUE + " real"
                    + ");";

    // Script de criação da tabela produto
    private static final String SCRIPT_TABLE_USER =
            "CREATE TABLE " + USER_TABLE + "("
                    + USER_COLUMN_ID + " integer primary key autoincrement, "
                    + USER_COLUMN_NAME + " text not null, "
                    + USER_COLUMN_PASSWORD + " text not null, "
                    + USER_COLUMN_EMAIL + " text not null"
                    + ");";

    // Script de criação da tabela carrinho de compras
    private static final String SCRIPT_TABLE_CART =
            "CREATE TABLE " + CART_TABLE + "("
                    + CART_COLUMN_ID + " integer primary key autoincrement, "
                    + CART_COLUMN_PRODUCTID + " integer, "
                    + CART_COLUMN_QTD + " integer, "
                    + "FOREIGN KEY (" + CART_COLUMN_PRODUCTID + ") REFERENCES " + PRODUCTS_TABLE + "(" + PRODUCTS_COLUMN_ID + ")"
                    + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SCRIPT_TABLE_PRODUCT);
        database.execSQL(SCRIPT_TABLE_CART);
        database.execSQL(SCRIPT_TABLE_USER);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(database);
    }
}