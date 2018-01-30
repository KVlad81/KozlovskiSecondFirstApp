package com.example.vlad81.kozlovskifirstapplication.launcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vlad81.kozlovskifirstapplication.R;


class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {

        private final View mImageView;

        GridHolder(final View view) {
            super(view);

            mImageView = view.findViewById(R.id.launcher_image);
        }

        View getImageView() {
            return mImageView;
        }
    }
}
