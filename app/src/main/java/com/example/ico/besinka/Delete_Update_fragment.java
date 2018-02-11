package com.example.ico.besinka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class Delete_Update_fragment extends Fragment {
 ListView deleteUpdateListView;
    MyAdapter adapter;
    ArrayList<String> items=new ArrayList<String>();
    DBHelper dbHelper;

    public Delete_Update_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delete_update_fragment, container, false);
        dbHelper=new DBHelper(view.getContext());
        deleteUpdateListView=(ListView) view.findViewById(R.id.deleteUpdateListView);
        items=dbHelper.getAllWords();
        adapter=new MyAdapter(view.getContext(),items,R.layout.adapter_update_delete_layout);
         deleteUpdateListView.setAdapter(adapter);
        return view;
    }

}
