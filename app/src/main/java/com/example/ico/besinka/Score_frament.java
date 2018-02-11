package com.example.ico.besinka;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Ico on 28.3.2017 г..
 */

public class Score_frament extends android.support.v4.app.Fragment {
    ListView scoreListView;
    Button cliarScoreBtn;
    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> adapter;
    DBHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_fragment, container, false);
        scoreListView=(ListView) view.findViewById(R.id.scoreListView);
        cliarScoreBtn=(Button)view.findViewById(R.id.scoreCliarbtn) ;
        cliarScoreBtn.setOnClickListener(onClick);
        dbHelper=new DBHelper(view.getContext());
        items=dbHelper.getAllScor();
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
        scoreListView.setAdapter(adapter);

        return  view;
    }
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dbHelper=new DBHelper(getView().getContext());
            dbHelper.deleteAllScore();
            items.removeAll(items);
            adapter.notifyDataSetChanged();
        }
    };
}
