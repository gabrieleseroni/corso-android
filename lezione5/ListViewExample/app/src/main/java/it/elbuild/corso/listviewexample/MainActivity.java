package it.elbuild.corso.listviewexample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private MyAdapter mAdapter;
    private static final String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new MyAdapter(getBaseContext(),R.layout.rowlayout,values);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),mAdapter.getItem(position),Toast.LENGTH_SHORT).show();
            }
        });
    }



    private class MyAdapter extends ArrayAdapter<String>{

        private final Context context;

        public MyAdapter(Context context,int layout ,String[] values) {
            super(context, layout, values);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder viewHolder = new ViewHolder();

            if(rowView ==  null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.rowlayout, parent, false);
                viewHolder.label = (TextView) rowView.findViewById(R.id.label);
                viewHolder.icon = (ImageView)rowView.findViewById(R.id.icon);
                rowView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) rowView.getTag();
            }

            viewHolder.label.setText(getItem(position));

            viewHolder.icon .setVisibility((position % 2 == 0) ? View.VISIBLE : View.INVISIBLE);

            return rowView;
        }

        private class ViewHolder{
            private TextView label;
            private ImageView icon;
        }
    }
}
