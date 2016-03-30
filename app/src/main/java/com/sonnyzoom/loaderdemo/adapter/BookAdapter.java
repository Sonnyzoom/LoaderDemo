package com.sonnyzoom.loaderdemo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonnyzoom.loaderdemo.R;
import com.sonnyzoom.loaderdemo.bean.Book;
import com.sonnyzoom.loaderdemo.db.BookDBHelper;

/**
 * Created by zoom on 2016/3/30.
 */
public class BookAdapter extends RecyclerViewCursorAdapter<BookAdapter.BookViewHolder> {

    private LayoutInflater inflater;

    public BookAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater=LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, Cursor cursor) {

        Book book=new Book();
        book.setId(cursor.getString(cursor.getColumnIndex(BookDBHelper.ID)));
        book.setName(cursor.getString(cursor.getColumnIndex(BookDBHelper.NAME)));
        book.setPrice(cursor.getInt(cursor.getColumnIndex(BookDBHelper.PRICE)));

        holder.name.setText(book.getName());
        holder.price.setText(Integer.toString(book.getPrice()));

    }

    @Override
    protected void onContentChanged() {}

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=inflater.inflate(R.layout.book_item,parent,false);

        return new BookViewHolder(v);
    }

    class BookViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView price;

        public BookViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.book_name);
            price= (TextView) itemView.findViewById(R.id.book_price);

        }
    }
}
