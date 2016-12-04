package com.engsoft.poli.upe.quitandaverdeapp.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class QuitandaVerdeContentProvider extends ContentProvider {

    private QuitandaVerdeDBHelper database;

    private static final int PRODUCT_CODE = 1;
    private static final int CART_CODE = 2;
    private static final int USER_CODE = 3;

    private static final String AUTHORITY = "com.upe.poli.engsoft.quitandaverdeapp.contentprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri CONTENT_URI_PRODUCTS = Uri.withAppendedPath(CONTENT_URI, QuitandaVerdeDBCreator.PRODUCTS_TABLE);
    public static final Uri CONTENT_URI_CART = Uri.withAppendedPath(CONTENT_URI, QuitandaVerdeDBCreator.CART_TABLE);
    public static final Uri CONTENT_URI_USER = Uri.withAppendedPath(CONTENT_URI, QuitandaVerdeDBCreator.USER_TABLE);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, QuitandaVerdeDBCreator.CART_TABLE, CART_CODE);
        sURIMatcher.addURI(AUTHORITY, QuitandaVerdeDBCreator.PRODUCTS_TABLE, PRODUCT_CODE);
        sURIMatcher.addURI(AUTHORITY, QuitandaVerdeDBCreator.USER_TABLE, USER_CODE);
    }

    @Override
    public boolean onCreate() {
        database = new QuitandaVerdeDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case PRODUCT_CODE:
                queryBuilder.setTables(QuitandaVerdeDBCreator.PRODUCTS_TABLE);
                break;
            case CART_CODE:
                queryBuilder.setTables(QuitandaVerdeDBCreator.CART_TABLE);
                break;
            case USER_CODE:
                queryBuilder.setTables(QuitandaVerdeDBCreator.USER_TABLE);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        String basePath = null;
        long id = 0;

        switch (uriType) {
            case CART_CODE:
                id = sqlDB.insert(QuitandaVerdeDBCreator.CART_TABLE, null, values);
                basePath = QuitandaVerdeDBCreator.CART_TABLE;
                break;
            case PRODUCT_CODE:
                id = sqlDB.insert(QuitandaVerdeDBCreator.PRODUCTS_TABLE, null, values);
                basePath = QuitandaVerdeDBCreator.PRODUCTS_TABLE;
                break;
            case USER_CODE:
                id = sqlDB.insert(QuitandaVerdeDBCreator.USER_TABLE, null, values);
                basePath = QuitandaVerdeDBCreator.USER_TABLE;
                break;
            default:
                throw new IllegalArgumentException("Tabela inválida: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(basePath + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case CART_CODE:
                rowsDeleted = sqlDB.delete(QuitandaVerdeDBCreator.CART_TABLE, selection, selectionArgs);
                break;
            case PRODUCT_CODE:
                rowsDeleted = sqlDB.delete(QuitandaVerdeDBCreator.PRODUCTS_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Tabela inválida: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case CART_CODE:
                rowsUpdated = sqlDB.update(QuitandaVerdeDBCreator.CART_TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case PRODUCT_CODE:
                rowsUpdated = sqlDB.update(QuitandaVerdeDBCreator.PRODUCTS_TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Tabela inválida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

}
