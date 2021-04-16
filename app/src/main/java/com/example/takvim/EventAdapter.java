package com.example.takvim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private ArrayList<Event> events;
    ItemClicked activity;
    public interface ItemClicked{
        void onItemClicked(int index);
    }


    public EventAdapter (Context context, ArrayList<Event> list){
        events=list;
        activity= (ItemClicked)context;
    }
    public void setArrayList(ArrayList<Event> list){
        events=list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvEvent;
        TextView tvTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEvent=itemView.findViewById(R.id.tvEvent);
            tvTime=itemView.findViewById(R.id.tvTime);
            imageView=itemView.findViewById(R.id.imageView);

            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(events.indexOf((Event) v.getTag()));

                }
            });
        }
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(events.get(position));
        holder.tvEvent.setText(events.get(position).getEvent_name());
        holder.tvTime.setText(events.get(position).getTime_interval());


    }

    @Override
    public int getItemCount() {
        return events.size()    ;
    }
}