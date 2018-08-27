package com.azens1995.mockjson;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.azens1995.mockjson.database.AppDatabase;
import com.azens1995.mockjson.database.Data;

import java.util.Arrays;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<Data> data;
    private Context context;
    private final OnItemClickListener listener;


    public DataAdapter(Context context, List<Data> data, OnItemClickListener clickListener) {
        this.data = data;
        this.context = context;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if(i == data.size()) {
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        else{
            viewHolder.name.setText(data.get(i).getDesc());
            if (data.get(i).getType().equalsIgnoreCase("radiobutton")) {
                viewHolder.radioGroup.setVisibility(View.VISIBLE);
                for (int item = 0; item < data.get(i).getOptions().size(); item++) {
                    final RadioButton radioButton = new RadioButton(context);
                    radioButton.setId(item + 1);
                    radioButton.setText(data.get(i).getOptions().get(item));
                    viewHolder.radioGroup.addView(radioButton);
                }
                viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        for (int j=0; j<group.getChildCount(); j++){
                            RadioButton btn = (RadioButton) group.getChildAt(j);
                            if (btn.getId() ==checkedId){
                                Toast.makeText(context,"The checked item is: " + btn.getText() , Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                });
            } else if (data.get(i).getType().equalsIgnoreCase("text")) {
                viewHolder.answer.setVisibility(View.VISIBLE);

                String answer = viewHolder.answer.getText().toString();
                if (!answer.isEmpty()){
                    viewHolder.answer.setText(answer);
                    viewHolder.answer.setSelection(answer.length());
                    viewHolder.answer.requestFocus();
                }
            }
            /**else if (data.get(i).getType().equalsIgnoreCase("checkbox")){
                viewHolder.linearLayout.setVisibility(View.VISIBLE);
                 viewHolder.linearLayout.removeAllViews();
                 for (int item =0; item < data.get(i).getOptions().size(); item++){
                 AppCompatCheckBox checkBox = new AppCompatCheckBox(context);
                 checkBox.setId(item +1);
                 checkBox.setText(data.get(i).getOptions().get(item));
                 linearLayout.addView(checkBox);
                 checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                    Toast.makeText(context, "The item is checked", Toast.LENGTH_SHORT).show();
                    }else {
                    Toast.makeText(context, "The item is Unchecked", Toast.LENGTH_SHORT).show();
                    }
            }
                });
             }

             }**/
        }
    }

    @Override
    public int getItemCount() {
        return data.size() +1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        EditText answer;
        RadioGroup radioGroup;
        Button button;
        //LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            answer = (EditText) itemView.findViewById(R.id.answer);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radiogroup);
            button = (Button) itemView.findViewById(R.id.save);
            //linearLayout = (LinearLayout) itemView.findViewById(R.id.linearcheck);

        }

    }
        interface OnItemClickListener{
            void onRowClick(Data data);
        }

}
