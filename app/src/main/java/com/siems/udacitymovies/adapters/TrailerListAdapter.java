package com.siems.udacitymovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.siems.udacitymovies.R;
import com.siems.udacitymovies.models.Trailer;

import java.util.List;

public class TrailerListAdapter extends ArrayAdapter<Trailer> {

    private LayoutInflater mLayoutInflater;
    private List<Trailer> mData;

    static class ViewHolder {
        public TextView nameTextView;
    }

    public TrailerListAdapter(Context context, int resource, List<Trailer> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if ( view == null ) {
            view = mLayoutInflater.inflate(R.layout.list_item_trailer, null);
            holder = new ViewHolder();
            holder.nameTextView = (TextView) view.findViewById(R.id.list_item_name_textview);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Trailer trailer = getItem(position);
        holder.nameTextView.setText(trailer.getName());

        return view;
    }



    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Trailer getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }








}
