package com.dhernandez.calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * time:     12:26 PM
 * project : Calculator
 * package : com.dhernandez.calculator
 */
public class HistoryAdapter extends ArrayAdapter<Calculator.HistoryItem> {

    protected Context mContext;
    protected ArrayList<Calculator.HistoryItem> mHistoryItems;


    public HistoryAdapter(Context context, ArrayList<Calculator.HistoryItem> items) {
        super(context, R.layout.history_item, items);
        mContext = context;
        mHistoryItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.history_item, null);
            holder = new ViewHolder();
            holder.expressionLabel = (TextView)convertView.findViewById(R.id.expressionLabel);
            holder.resultLabel = (TextView)convertView.findViewById(R.id.resultLabel);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Calculator.HistoryItem historyItem = mHistoryItems.get(position);

        holder.expressionLabel.setText( historyItem.getExpression() );
        holder.resultLabel.setText( historyItem.getResult() );

        return convertView;

    }

    private static class ViewHolder{
        TextView expressionLabel;
        TextView resultLabel;
    }

}
