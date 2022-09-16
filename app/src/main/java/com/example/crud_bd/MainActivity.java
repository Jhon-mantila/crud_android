package com.example.crud_bd;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button botonInsertar, botonActualizar, botonBorrar, botonBuscar;

    EditText textoId, textoNombre, textoApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonInsertar = (Button) findViewById(R.id.insertar);
        botonActualizar = (Button) findViewById(R.id.actualizar);
        botonBorrar = (Button) findViewById(R.id.borrar);
        botonBuscar = (Button) findViewById(R.id.buscar);

        textoId = (EditText) findViewById(R.id.id);
        textoNombre = (EditText) findViewById(R.id.nombre);
        textoApellido = (EditText) findViewById(R.id.apellido);

        final BD_HELPER dbHelper = new BD_HELPER(this);

        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Gets the data repository in write mode
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(Estructura_BD.NOMBRE_COLUMN1, textoId.getText().toString());
                values.put(Estructura_BD.NOMBRE_COLUMN2, textoNombre.getText().toString());
                values.put(Estructura_BD.NOMBRE_COLUMN3, textoApellido.getText().toString());

// Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(Estructura_BD.TABLE_NAME, null, values);

                Toast.makeText(getApplicationContext(), "Se guardo el registro con clave: " + newRowId, Toast.LENGTH_LONG).show();
            }
        });

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // New value for one column
                String title = "MyNewTitle";
                ContentValues values = new ContentValues();
                values.put(Estructura_BD.NOMBRE_COLUMN2, textoNombre.getText().toString());
                values.put(Estructura_BD.NOMBRE_COLUMN3, textoApellido.getText().toString());

                // Which row to update, based on the title
                String selection = Estructura_BD.NOMBRE_COLUMN1 + " LIKE ?";
                String[] selectionArgs = { textoId.getText().toString() };

                int count = db.update(
                        Estructura_BD.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Toast.makeText(getApplicationContext(), "Se Actualizo el registro: " + textoId.getText().toString() + " Filas " + count, Toast.LENGTH_LONG).show();

            }


        });

        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Gets the data repository in write mode
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Define 'where' part of query.
                String selection = Estructura_BD.NOMBRE_COLUMN1 + " LIKE ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = { textoId.getText().toString() };
                // Issue SQL statement.
                int deletedRows = db.delete(Estructura_BD.TABLE_NAME, selection, selectionArgs);

                if(deletedRows==1) {
                    Toast.makeText(getApplicationContext(), "Se borro el registro: " + textoId.getText().toString() + " Filas " + deletedRows, Toast.LENGTH_LONG).show();
                    textoId.setText("");
                    textoNombre.setText("");
                    textoApellido.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "No se encontro registro para borrar " + textoId.getText().toString() + " Filas " + deletedRows, Toast.LENGTH_LONG).show();
                }
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
                String[] projection = {
                        Estructura_BD.NOMBRE_COLUMN1,
                        Estructura_BD.NOMBRE_COLUMN2,
                        Estructura_BD.NOMBRE_COLUMN3
                };

                // Filter results WHERE "title" = 'My Title'
                String selection = Estructura_BD.NOMBRE_COLUMN1 + " = ?";
                String[] selectionArgs = { textoId.getText().toString() };

                // How you want the results sorted in the resulting Cursor
                String sortOrder =
                        Estructura_BD.NOMBRE_COLUMN1 + " DESC";
                try {

                Cursor cursor = db.query(
                        Estructura_BD.TABLE_NAME,   // The table to query
                        projection,             // The array of columns to return (pass null to get all)
                        selection,              // The columns for the WHERE clause
                        selectionArgs,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        sortOrder               // The sort order
                );

               /*Datos data;

               ArrayList<Datos> itemIds = new ArrayList<Datos>();

                   while(cursor.moveToNext()) {

                        String itemid= cursor.getString(0);
                        String itemnombre = cursor.getString(1);
                        String itemapellido = cursor.getString(2);

                        data = new Datos(itemid, itemnombre, itemapellido);

                        itemIds.add(data);
                        //System.out.println("Nombre: "+itemnombre +" Apellido: "+itemapellido);

                    }

                    for(Datos it:itemIds) {

                        String id_it = it.getId();
                        String nom_it = it.getNombre();
                        String ape_it = it.getApellido();

                        System.out.println("id " + id_it + " Nombre: "+nom_it +" Apellido: "+ape_it);
                    }*/


                  cursor.moveToNext();
                  textoNombre.setText(cursor.getString(1));
                  textoApellido.setText(cursor.getString(2));

                  cursor.close();
                }catch (Exception e){

                    Toast.makeText(getApplicationContext(), "No Se encontro registro: " + textoId.getText().toString(), Toast.LENGTH_LONG).show();
                    textoNombre.setText("");
                    textoApellido.setText("");
                }






            }
        });


    }
}