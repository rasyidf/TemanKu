package com.rasyidf.kontakku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

  // Database Version
  private static final int DATABASE_VERSION = 1;
  // Database Name
  private static final String DATABASE_NAME = "Teman";

  public DBHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_CONTACTS_TABLE =
      "CREATE TABLE " +
      TemanContract.TemanEntry.TABLE_NAME +
      "(" +
      TemanContract.TemanEntry.COLUMN_NAME_ID +
      " INTEGER PRIMARY KEY," +
      TemanContract.TemanEntry.COLUMN_NAME_NAME +
      " TEXT," +
      TemanContract.TemanEntry.COLUMN_NAME_PHONE +
      " TEXT" +
      ")";
    db.execSQL(CREATE_CONTACTS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TemanContract.TemanEntry.TABLE_NAME);
    onCreate(db);
  }

  public void insert(Teman item) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(TemanContract.TemanEntry.COLUMN_NAME_NAME, item.getName());
    values.put(TemanContract.TemanEntry.COLUMN_NAME_PHONE, item.getPhone());

    db.insert(TemanContract.TemanEntry.TABLE_NAME, null, values);
    db.close();
  }

  public Teman getItem(int id) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(
      TemanContract.TemanEntry.TABLE_NAME,
      new String[] {
        TemanContract.TemanEntry.COLUMN_NAME_ID,
        TemanContract.TemanEntry.COLUMN_NAME_NAME,
        TemanContract.TemanEntry.COLUMN_NAME_PHONE,
      },
      TemanContract.TemanEntry.COLUMN_NAME_ID + "=?",
      new String[] { String.valueOf(id) },
      null,
      null,
      null,
      null
    );
    if (cursor != null) cursor.moveToFirst();

    Teman contact = new Teman(
      Integer.parseInt(cursor.getString(0)),
      cursor.getString(1),
      cursor.getString(2)
    );
    cursor.close();
    // return shop
    return contact;
  }

  // Getting All Shops
  public List<Teman> getAll() {
    List<Teman> kontakList = new ArrayList<>();
    String selectQuery = "SELECT * FROM " + TemanContract.TemanEntry.TABLE_NAME;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
      do {
        Teman kontak = new Teman();
        kontak.setId(cursor.getString(0));
        kontak.setName(cursor.getString(1));
        kontak.setPhone(cursor.getString(2));

        kontakList.add(kontak);
      } while (cursor.moveToNext());
    }

    return kontakList;
  }

  public int getCount() {
    String countQuery = "SELECT * FROM " + TemanContract.TemanEntry.TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    cursor.close();

    return cursor.getCount();
  }

  public int update(Teman item) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(TemanContract.TemanEntry.COLUMN_NAME_NAME, item.getName());
    values.put(TemanContract.TemanEntry.COLUMN_NAME_PHONE, item.getPhone());

    return db.update(
      TemanContract.TemanEntry.TABLE_NAME,
      values,
      TemanContract.TemanEntry.COLUMN_NAME_ID + " = ?",
      new String[] { String.valueOf(item.getId()) }
    );
  }

  public void delete(Teman item) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(
      TemanContract.TemanEntry.TABLE_NAME,
      TemanContract.TemanEntry.COLUMN_NAME_ID + " = ?",
      new String[] { String.valueOf(item.getId()) }
    );
    db.close();
  }
}
