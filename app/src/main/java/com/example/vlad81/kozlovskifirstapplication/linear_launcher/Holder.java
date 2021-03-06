package com.example.vlad81.kozlovskifirstapplication.linear_launcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.vlad81.kozlovskifirstapplication.R;

import java.util.ListIterator;


class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {

        private final View mImageView;
        private ListIterator<Integer> listIterator;

        GridHolder(final View view) {
            super(view);

            mImageView = view.findViewById(R.id.launcher_image);
        }

        View getImageView() {
            return mImageView;
        }

        public ListIterator<Integer> getListIterator() {
            return listIterator;
        }

        public void setListIterator(ListIterator<Integer> listIterator) {
            this.listIterator = listIterator;
        }
    }
}
