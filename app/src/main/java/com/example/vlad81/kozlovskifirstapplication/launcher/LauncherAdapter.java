package com.example.vlad81.kozlovskifirstapplication.launcher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.vlad81.kozlovskifirstapplication.R;

import java.util.List;
import java.util.ListIterator;

public class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final List<Integer> mData;

    public static String TAG;
    private Context context;

    public LauncherAdapter(@NonNull final List<Integer> data, Context context) {
        this.context = context;
        mData = data;
        TAG = context.getString(R.string.launcher_adapter);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new Holder.GridHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Holder.GridHolder myHodler = (Holder.GridHolder) holder;
        myHodler.setListIterator(mData.listIterator(position));
        bindGridView(myHodler, position);

    }


    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final View view = gridHolder.getImageView();
        view.setBackgroundColor(mData.get(position));
        TextView itemTextView = gridHolder.itemView.findViewById(R.id.griditem_text);
        String text = "#"+Integer.toHexString(mData.get(position)).substring(2);
        itemTextView.setText(text);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar snackbar = Snackbar.make(v, R.string.really_delete, 5000);
                snackbar.setAction(context.getString(R.string.yes), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridHolder.getListIterator().next();
                        gridHolder.getListIterator().remove();
                        notifyDataSetChanged();
                        Log.i(TAG, context.getString(R.string.deleted_item) + position);
                    }
                });
                snackbar.addCallback(new Snackbar.Callback(){
                    @Override
                    public void onShown(Snackbar sb) {
                        Log.i(TAG, context.getString(R.string.snackbar_at_position) + position + context.getString(R.string.was_shown));
                    }

                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        switch (event) {
                            case Snackbar.Callback.DISMISS_EVENT_ACTION:
                                Log.i(TAG, context.getString(R.string.snackbar_at_position) + position +
                                        context.getString(R.string.via_click));
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE:
                                Log.i(TAG, context.getString(R.string.snackbar_at_position) + position +
                                        context.getString(R.string.new_snackbar));
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_MANUAL:
                                Log.i(TAG, context.getString(R.string.snackbar_at_position) + position +
                                        context.getString(R.string.dismiss));
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_SWIPE:
                                Log.i(TAG, context.getString(R.string.snackbar_at_position) + position +
                                        context.getString(R.string.via_swipe));
                                break;
                            case Snackbar.Callback.DISMISS_EVENT_TIMEOUT:
                                Log.i(TAG, context.getString(R.string.snackbar_at_position) + position +
                                        context.getString(R.string.via_timeout));
                                break;

                        }
                    }
                });
                snackbar.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
