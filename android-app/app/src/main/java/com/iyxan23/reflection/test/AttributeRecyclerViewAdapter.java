package com.iyxan23.reflection.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AttributeRecyclerViewAdapter extends RecyclerView.Adapter<AttributeRecyclerViewAdapter.ViewHolder> {

    ArrayList<Attribute> attributes;

    public AttributeRecyclerViewAdapter(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rv_attribute_item, parent)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attribute attribute = attributes.get(position);

        holder.type.setText(attribute.type.toString());
        holder.func_name.setText(attribute.toString());
    }

    @Override
    public int getItemCount() {
        return attributes.size() - 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView type;
        TextView func_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.type);
            func_name = itemView.findViewById(R.id.function_name);
        }
    }
}
