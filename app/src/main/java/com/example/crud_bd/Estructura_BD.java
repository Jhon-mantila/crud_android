package com.example.crud_bd;

import android.provider.BaseColumns;

public class Estructura_BD {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Estructura_BD() {}

    /* Inner class that defines the table contents */

    //public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "datos_personales";
        public static final String NOMBRE_COLUMN1 = "id";
        public static final String NOMBRE_COLUMN2 = "nombre";
        public static final String NOMBRE_COLUMN3 = "apellido";
    //}

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BD.TABLE_NAME + " (" +
                    Estructura_BD.NOMBRE_COLUMN1 + " INTEGER PRIMARY KEY," +
                    Estructura_BD.NOMBRE_COLUMN2 + " TEXT," +
                    Estructura_BD.NOMBRE_COLUMN3 + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BD.TABLE_NAME;
}
