package com.example.prak_3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.example.prak_3.Frag.pagerAdapter;

public class FragItem extends Fragment {
    private List<Info> data;
    private int pos;
    TextView textView1;
    Button bt;

    public static FragItem newInstance(List<Info> data, int pos) {
        FragItem fragment = new FragItem();
        fragment.data = data;
        fragment.pos = pos;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag, container, false);

        TextView textView = view.findViewById(R.id.Title);
        textView1 = view.findViewById(R.id.Quantity);
        TextView textView2 = view.findViewById(R.id.Description);
        bt = view.findViewById(R.id.Button);

        final Info dt = data.get(pos);
        textView.setText(dt.getName());
        textView2.setText(dt.getDescription());

        String s;
        if(dt.getQuantity() == 0){
            s = "Нет в наличии";
        }
        else{
            s = "Количество: " + dt.getQuantity();
        }
        textView1.setText(s);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getContext(), "Товар куплен!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                DbHelper dbHelper = new DbHelper(getContext());
                int a = dt.getQuantity() - 1;
                dt.setQuantity(a);
                dbHelper.update(dt);
                if(a != 0){
                    String s = "Количество: " + Integer.toString(a);
                    textView1.setText(s);
                }
                else{
                    bt.setEnabled(false);
                    textView1.setText("Нет в наличии");
                    data.remove(dt);
                    pagerAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }
}
