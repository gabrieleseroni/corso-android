package it.elbuild.corso.recyclerviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MyAdapter myAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<DataModel>  list = new ArrayList<>();
        list.add(new DataModel("8", "Froyo", "0.1%", "2.2"));
        list.add(new DataModel("10", "Gingerbread", "2.7%", "2.3.3-\n2.3.7"));
        list.add(new DataModel("15", "Ice Cream Sandwich", "2.5%", "4.0.3-\n4.0.4"));
        list.add(new DataModel("16", "Jelly Bean", "8.8%", "4.1.x"));
        list.add(new DataModel("17", "Jelly Bean", "11.7%", "4.2.x"));
        list.add(new DataModel("18", "Jelly Bean", "3.4%", "4.3"));
        list.add(new DataModel("19", "KitKat", "35.5%", "4.4"));
        list.add(new DataModel("21", "Lollipop", "17.0%", "5.0"));
        list.add(new DataModel("22", "Lollipop", "17.1%", "5.1"));
        list.add(new DataModel("23", "Marshmallow", "1.2%", "6.0"));

        myAdapter = new MyAdapter(list);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(myAdapter);

    }



    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        public static final int HEADER_TYPE = 0;
        public static final int CONTENT_TYPE = 1;

        private List<DataModel> dataModelList;

        MyAdapter(List<DataModel> dataModelList){
            this.dataModelList = dataModelList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view =  null;
            RecyclerView.ViewHolder viewHolder =  null;
            if(viewType == HEADER_TYPE){
                view = layoutInflater.inflate(R.layout.header_row, parent, false);
                viewHolder = new HeaderViewHolder(view);
            }else {
                view = layoutInflater.inflate(R.layout.content_row, parent,false);
                viewHolder = new ContentViewHolder(view);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if(holder instanceof ContentViewHolder){

                    DataModel data = dataModelList.get(position - 1);
                    ((ContentViewHolder) holder).version.setText(data.getVersion());
                    ((ContentViewHolder) holder).codeName.setText(data.getCodeName());
                    ((ContentViewHolder) holder).api.setText(data.getApi());
                    ((ContentViewHolder) holder).distribution.setText(data.getDistribution());
            }

        }

        @Override
        public int getItemViewType(int position) {
            return (position == 0) ? HEADER_TYPE : CONTENT_TYPE;
        }

        @Override
        public int getItemCount() {
            return dataModelList.size() + 1;
        }

        public class ContentViewHolder extends RecyclerView.ViewHolder{

            private TextView api;
            private TextView codeName;
            private TextView version;
            private TextView distribution;

            public ContentViewHolder(View itemView) {
                super(itemView);
                api = (TextView)itemView.findViewById(R.id.api_text_view);
                codeName = (TextView)itemView.findViewById(R.id.codename_text_view);
                version = (TextView)itemView.findViewById(R.id.version_text_view);
                distribution = (TextView)itemView.findViewById(R.id.distribution_text_view);
            }
        }


        public class HeaderViewHolder extends RecyclerView.ViewHolder{

            private TextView api;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                api = (TextView) itemView.findViewById(R.id.api_button);
                api.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         Collections.reverse(dataModelList);
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }


    private class DataModel{
        private String version;
        private String codeName;
        private String api;
        private String distribution;

        public DataModel(String api, String codeName, String distribution, String version) {
            this.api = api;
            this.codeName = codeName;
            this.distribution = distribution;
            this.version = version;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getDistribution() {
            return distribution;
        }

        public void setDistribution(String distribution) {
            this.distribution = distribution;
        }
    }


}
