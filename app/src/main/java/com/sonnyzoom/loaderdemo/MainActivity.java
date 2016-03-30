package com.sonnyzoom.loaderdemo;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sonnyzoom.loaderdemo.adapter.BookAdapter;
import com.sonnyzoom.loaderdemo.db.BookDBHelper;
import com.sonnyzoom.loaderdemo.provider.BookProvider;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        initLoader();

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
        }

    }

    private void showDialog() {

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View v = getLayoutInflater().inflate(R.layout.dialog, null);
        dialog.setView(v);
        dialog.setCancelable(false);
        dialog.show();

        final EditText name = (EditText) v.findViewById(R.id.edit_name);
        final EditText price = (EditText) v.findViewById(R.id.edit_price);

        Button cancel = (Button) v.findViewById(R.id.btn_cancel);
        Button save = (Button) v.findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mName = name.getText().toString();
                String mPrice = price.getText().toString();

                ContentValues values = new ContentValues();
                values.put(BookDBHelper.NAME, mName);
                values.put(BookDBHelper.PRICE, Integer.valueOf(mPrice));

                getContentResolver().insert(BookProvider.URI_BOOK_ALL, values);
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void initLoader() {

        getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {

                CursorLoader loader = new CursorLoader(MainActivity.this, BookProvider.URI_BOOK_ALL, null, null, null, null);

                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                adapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                adapter.swapCursor(null);
            }
        });

    }

    private void initViews() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (adapter == null) {

            Cursor c = getContentResolver().query(BookProvider.URI_BOOK_ALL, null, null, null, null);
            adapter = new BookAdapter(this, c, 1);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

}
