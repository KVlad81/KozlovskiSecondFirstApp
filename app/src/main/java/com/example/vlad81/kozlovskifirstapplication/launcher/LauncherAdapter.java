package com.example.vlad81.kozlovskifirstapplication.launcher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.vlad81.kozlovskifirstapplication.R;

import java.util.List;

public class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final List<Integer> mData;

    private Context context;

    public LauncherAdapter(@NonNull final List<Integer> data, Context context) {
        this.context = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_linear_item, parent, false);
        return new Holder.GridHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.GridHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final View view = gridHolder.getImageView();
        view.setBackgroundColor(mData.get(position));
        TextView itemTextView = gridHolder.itemView.findViewById(R.id.griditem_text);
        String text = "#"+Integer.toHexString(mData.get(position)).substring(2);
        itemTextView.setText(text);

        String description = mData.toString();
        TextView descriptionTextView = gridHolder.itemView.findViewById(R.id.grid_linear_description);
        if (descriptionTextView != null) {
            descriptionTextView.setText(description);
        }

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar.make(v, context.getString(R.string.color)+" = " + Integer.toHexString(mData.get(position)).substring(2), Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
