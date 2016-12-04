package com.engsoft.poli.upe.quitandaverdeapp.dao;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.engsoft.poli.upe.quitandaverdeapp.models.User;
import com.engsoft.poli.upe.quitandaverdeapp.providers.QuitandaVerdeContentProvider;
import com.engsoft.poli.upe.quitandaverdeapp.providers.QuitandaVerdeDBCreator;

public class UserDao {

    private final String[] allColumns = {QuitandaVerdeDBCreator.USER_COLUMN_ID,
            QuitandaVerdeDBCreator.USER_COLUMN_NAME,
            QuitandaVerdeDBCreator.USER_COLUMN_EMAIL,
            QuitandaVerdeDBCreator.USER_COLUMN_PASSWORD};

    private ContentResolver qvContentResolver;

    public UserDao(ContentResolver qvContentResolver){
        this.qvContentResolver = qvContentResolver;
    }

    public boolean saveUser(User user){
        try {
            ContentValues values = new ContentValues();
            values.put(QuitandaVerdeDBCreator.USER_COLUMN_NAME, user.getName());
            values.put(QuitandaVerdeDBCreator.USER_COLUMN_EMAIL, user.getEmail());
            values.put(QuitandaVerdeDBCreator.USER_COLUMN_PASSWORD, user.getPassword());
            qvContentResolver.insert(QuitandaVerdeContentProvider.CONTENT_URI_USER, values);

            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public User getByEmailAndPassword(String email, String password) {

        Cursor cursor = qvContentResolver.query(QuitandaVerdeContentProvider.CONTENT_URI_USER,
                allColumns, QuitandaVerdeDBCreator.USER_COLUMN_EMAIL + "= ? AND "
                          + QuitandaVerdeDBCreator.USER_COLUMN_PASSWORD + "= ?",
                new String[] { email, password}, null);

        User result = null;

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = cursorToUser(cursor);
        }

        cursor.close();
        return result;
    }

    public User getByEmail(String email) {

        Cursor cursor = qvContentResolver.query(QuitandaVerdeContentProvider.CONTENT_URI_USER,
                allColumns, QuitandaVerdeDBCreator.USER_COLUMN_EMAIL + "= ?",
                new String[] { email }, null);

        User result = null;

        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = cursorToUser(cursor);
        }

        cursor.close();
        return result;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3));
        user.setId(cursor.getInt(0));
        return user;
    }
}
