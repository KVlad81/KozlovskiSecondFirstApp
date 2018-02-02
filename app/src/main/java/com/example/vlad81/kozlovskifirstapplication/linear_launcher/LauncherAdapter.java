package com.example.vlad81.kozlovskifirstapplication.linear_launcher;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

public class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG;
    @NonNull
    private final List<Integer> mData;

    private Context context;

    public LauncherAdapter(@NonNull final List<Integer> data, Context context) {
        this.context = context;
        mData = data;
        TAG = context.getString(R.string.linear_adapter);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_linear_item, parent, false);
        return new Holder.GridHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Holder.GridHolder myHolder = (Holder.GridHolder) holder;
        myHolder.setListIterator(mData.listIterator(position));
        bindGridView(myHolder, position);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final View view = gridHolder.getImageView();
        Drawable background = view.getBackground();
        background.setColorFilter(mData.get(position), PorterDuff.Mode.MULTIPLY);
        TextView itemTextView = gridHolder.itemView.findViewById(R.id.griditem_text);
        String text = "#"+Integer.toHexString(mData.get(position)).substring(2);
        itemTextView.setText(text);

        String description = mData.toString();
        TextView descriptionTextView = gridHolder.itemView.findViewById(R.id.grid_linear_description);
        descriptionTextView.setText(description);


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar snackbar = Snackbar.make(v, R.string.really_delete, 5000);
                snackbar.setAction(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridHolder.getListIterator().next();
                        gridHolder.getListIterator().remove();
                        Log.i(TAG, context.getString(R.string.deleted_item)+position);
                        notifyDataSetChanged();
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
