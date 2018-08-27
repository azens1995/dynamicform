package com.azens1995.mockjson.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.azens1995.mockjson.DataAdapter;
import com.azens1995.mockjson.DataResponse;
import com.azens1995.mockjson.R;
import com.azens1995.mockjson.database.AppDatabase;
import com.azens1995.mockjson.database.Data;

import java.util.List;

import retrofit2.Callback;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public interface OnItemClickListener{
        void onItemClick(Data data);
    }

    private final List<Data> data;
    private final OnItemClickListener listener;
    private Context context;

    AppDatabase db;

    public MyAdapter(Context context,List<Data> data, OnItemClickListener clickListener) {
        this.context =context;
        this.data = data;
        this.listener = clickListener;
    }


    @Override
    public int getItemViewType(int position){
        return (position == data.size() ? R.layout.button : R.layout.recyclerview_row);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i ==R.layout.recyclerview_row){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_row,viewGroup, false);
        }else {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.button,viewGroup, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(data.get(i), listener);

    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        EditText answer;
        RadioGroup radioGroup;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            answer = (EditText) itemView.findViewById(R.id.answer);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radiogroup);
            button = (Button) itemView.findViewById(R.id.save);
        }

        public void bind(final Data data, final OnItemClickListener clickListener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(data);
                }
            });

            name.setText(data.getDesc());
            if (data.getType().equalsIgnoreCase("text")){
                answer.setVisibility(View.VISIBLE);
            }else if (data.getType().equalsIgnoreCase("radiobutton")){
                radioGroup.setVisibility(View.VISIBLE);
                for (int item = 0; item < data.getOptions().size(); item++) {
                    final RadioButton radioButton = new RadioButton(context);
                    radioButton.setId(item + 1);
                    radioButton.setText(data.getOptions().get(item));
                    radioGroup.addView(radioButton);
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        for (int j=0; j<group.getChildCount(); j++){
                            RadioButton btn = (RadioButton) group.getChildAt(j);
                            int checkedpos = group.getCheckedRadioButtonId();
                            if (btn.getId() ==checkedId){
                                Toast.makeText(context,"The checked item is: " + btn.getText() , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }

        }
    }


}
