package com.example.ico.besinka;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Add_fragment extends android.support.v4.app.Fragment{
    ArrayList<String> items=new ArrayList<String>();
    //ArrayAdapter<String> adapter;
    MyAdapter adapter;
    ListView addListViewID;
    TextView addWordEditText;
    Button addWordButton;

    public Add_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_fragment, container, false);
        DBHelper dbHelper=new DBHelper(view.getContext());
        addListViewID=(ListView) view.findViewById(R.id.addListViewID);
        addWordEditText=(EditText) view.findViewById(R.id.addWordEditText);
        addWordButton=(Button) view.findViewById(R.id.addWordButton);
        items.removeAll(items);
        items=dbHelper.getAllWords();

       // adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
        adapter=new MyAdapter(view.getContext(),items,R.layout.adapter_add_layout);
        addListViewID.setAdapter(adapter);
        addWordButton.setOnClickListener(onClick);
        return view;
    }
    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DBHelper dbHelper=new DBHelper(getView().getContext());
            String word =addWordEditText.getText().toString();
            if(!(word.length()>0)){
                Toast.makeText(getView().getContext(), "Something is wrong",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Word w=new Word(word);
            dbHelper.addWord(w,getView().getContext());
            items.removeAll(items);
            items=dbHelper.getAllWords();
            adapter=new MyAdapter(getView().getContext(),items,R.layout.adapter_add_layout);
            addListViewID.setAdapter(adapter);
        }
    };
}
