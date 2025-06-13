package com.example.lab2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.R;
import com.example.lab2.data.Phone;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Phone phone);
        void onItemLongClick(Phone phone);
    }

    private final LayoutInflater mInflater;
    private List<Phone> mPhones;
    private final OnItemClickListener listener;

    public PhoneListAdapter(Context context, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    class PhoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textViewProducer;
        private final TextView textViewModel;

        public PhoneViewHolder(View itemView) {
            super(itemView);
            textViewProducer = itemView.findViewById(R.id.textView1);
            textViewModel = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);


            itemView.setOnLongClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(mPhones.get(pos));
                }
                return true;
            });
        }

        public void bind(Phone phone) {
            textViewProducer.setText(phone.getProducer());
            textViewModel.setText(phone.getModel());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (listener != null && pos != RecyclerView.NO_POSITION) {
                listener.onItemClick(mPhones.get(pos));
            }
        }
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PhoneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        if (mPhones != null) {
            Phone current = mPhones.get(position);
            holder.bind(current);
        }
    }

    public void setPhones(List<Phone> phones) {
        this.mPhones = phones;
        notifyDataSetChanged();
    }

    public Phone getPhoneAt(int position) {
        return mPhones.get(position);
    }

    @Override
    public int getItemCount() {
        return (mPhones != null) ? mPhones.size() : 0;
    }
}
