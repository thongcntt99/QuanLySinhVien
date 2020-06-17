package com.example.homework4_6_2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IteamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    List<iteamModel> items;
    List<iteamModel> displayitems;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public IteamAdapter( List<iteamModel> item) {
        this.displayitems = item;
        items = new ArrayList<>();
        items.addAll(item);
    }

    public void showAll() {
        items.clear();
        items.addAll(displayitems);
        notifyDataSetChanged();
    }
    public void search(String keywork) {
        items.clear();
        for (iteamModel iteam : displayitems) {
            if (iteam.getFullname().contains(keywork)) {
                items.add(iteam);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_sv, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final ListViewHolder viewHolder = (ListViewHolder) holder;
        iteamModel item = items.get(position);

        viewHolder.text_letter.setText(item.getFullname().substring(0,1));

        Drawable background = viewHolder.text_letter.getBackground();
        background.setColorFilter(new PorterDuffColorFilter(item.getColor(), PorterDuff.Mode.SRC_ATOP));

        viewHolder.tv_mssv.setText("MSSV: " + item.getMssv());
        viewHolder.tv_fullName.setText(item.getFullname());
        viewHolder.tv_Email.setText("Email: "+item.getEmail());
        viewHolder.tv_birthplace.setText("Quê quán: "+ item.getBirthplace());
        viewHolder.tv_dob.setText("Ngày sinh: "+ item.getDob());
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(viewHolder.getAdapterPosition());
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {

        return items.size();
    }
    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView text_letter;
        TextView tv_fullName;
        TextView tv_Email;
        TextView tv_dob;
        TextView tv_birthplace;
        TextView tv_mssv;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mssv = itemView.findViewById(R.id.tv_mssv);
            tv_fullName = itemView.findViewById(R.id.tv_fullname);
            tv_Email = itemView.findViewById(R.id.tv_email);
            tv_dob = itemView.findViewById(R.id.tv_dob);
            tv_birthplace = itemView.findViewById(R.id.tv_birthplace);
            text_letter = itemView.findViewById(R.id.text_letter);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.Delete,
                    Menu.NONE, R.string.delete);
            menu.add(Menu.NONE, R.id.Update,
                    Menu.NONE, R.string.update);
        }
    }

}
