package com.rasyidf.kontakku.database;

import android.provider.BaseColumns;

public final class TemanContract {

  // To prevent someone from accidentally instantiating the contract class,
  // make the constructor private.
  private TemanContract() {}

  public static class TemanEntry implements BaseColumns {

    public static final String TABLE_NAME = "teman";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_PHONE = "phone";
  }
}
