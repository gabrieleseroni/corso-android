package it.elbuild.corso.contentprovider;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView studentsList;
    private ArrayAdapter<String> adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            studentsList = (ListView)findViewById(R.id.studentsList);
            adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,new ArrayList<String>());
            studentsList.setAdapter(adapter);
        }



        public void onClickAddName(View view) {
            // Add a new student record
            ContentValues values = new ContentValues();

            values.put(StudentsProvider.NAME,
                    ((EditText)findViewById(R.id.editText2)).getText().toString());

            values.put(StudentsProvider.GRADE,
                    ((EditText)findViewById(R.id.editText3)).getText().toString());

            Uri uri = getContentResolver().insert(
                    StudentsProvider.CONTENT_URI, values);

            Toast.makeText(getBaseContext(),
                    uri.toString(), Toast.LENGTH_LONG).show();
        }

        public void onClickRetrieveStudents(View view) {

            // Retrieve student records
            String URL = "content://it.elbuild.corso.contentprovider.College/students";

            Uri students = Uri.parse(URL);
            Cursor c = managedQuery(students, null, null, null, "name");

            if (c.moveToFirst()) {
                adapter.clear();
                do{

                    adapter.add("ID:" + c.getString(c.getColumnIndex(StudentsProvider._ID)) +
                            "  NAME: " +  c.getString(c.getColumnIndex( StudentsProvider.NAME)) +
                            "  GRADE: " + c.getString(c.getColumnIndex( StudentsProvider.GRADE)));
//                    Toast.makeText(this,
//                            c.getString(c.getColumnIndex(StudentsProvider._ID)) +
//                                    ", " +  c.getString(c.getColumnIndex( StudentsProvider.NAME)) +
//                                    ", " + c.getString(c.getColumnIndex( StudentsProvider.GRADE)),
//                            Toast.LENGTH_SHORT).show();
                } while (c.moveToNext());
            }
        }
    }

