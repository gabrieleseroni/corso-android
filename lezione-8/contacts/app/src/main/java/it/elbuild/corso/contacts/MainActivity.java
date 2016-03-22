package it.elbuild.corso.contacts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<ContactAdapterModel> contactsFound;
    private ContactAdapter adp;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            readUsers();
        }

    }

    private void bind() {
        listView = (ListView)findViewById(R.id.contact_list);

    }

    private void readUsers(){
        new AsyncTask<Void, Void, List<ContactAdapterModel>>(){

            @Override
            protected List<ContactAdapterModel> doInBackground(Void... params) {
                return readContacts();
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(List<ContactAdapterModel> contacts) {
                contactsFound = contacts;
                adp = new ContactAdapter(getBaseContext(),R.layout.row_contacts);
                listView.setAdapter(adp);
            }
        }.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                readUsers();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public List<ContactAdapterModel> readContacts() {

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        String phone = null;
        String emailContact = null;
        String emailType = null;
        String image_uri = "";
        Bitmap bitmap = null;
        ArrayList<ContactAdapterModel> contacts = new ArrayList<>();
        ContactAdapterModel contact = null;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                contact = new ContactAdapterModel();

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if(name == null || name.isEmpty()){
                    continue;
                }


                image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    contact.setName(name);
                    contact.setUriProfileImage(image_uri);

                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contact.getNumbers().add(phone);
                    }
                    pCur.close();

                    Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (emailCur.moveToNext()) {
                        emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        contact.getEmails().add(emailContact);

                    }

                    emailCur.close();
                }else {
                    continue;
                }

                if (image_uri != null) {
                    try {
                        // bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(image_uri));
                        BitmapFactory.Options options =  new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        AssetFileDescriptor fileDescriptor =null;
                        fileDescriptor = getContentResolver().openAssetFileDescriptor( Uri.parse(image_uri),"r");
                        bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(),null,options);
                        contact.setImageProfile(bitmap);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                if(!contact.getNumbers().isEmpty() && contact.getName()!=null && !contact.getName().isEmpty())
                    contacts.add(contact);

            }


        }
        return contacts;
    }

    private class ContactAdapter extends ArrayAdapter<ContactAdapterModel> {


        private int layout;
        private List<ContactAdapterModel> contactAdapterModelList;
        private List<ContactAdapterModel> contactFromSearch;
        public ContactAdapter(Context context, int resource) {
            super(context, resource);
            layout = resource;
            int i = 0;
            contactAdapterModelList = new ArrayList<>();
            contactFromSearch = new ArrayList<>();

            contactAdapterModelList = new ArrayList<>(contactsFound);
            addAll(contactAdapterModelList);
        }

        @Override
        public ContactAdapterModel getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder viewHolder = new ViewHolder();
            if(v == null){
                LayoutInflater inflater = getLayoutInflater();
                v = inflater.inflate(layout,parent,false);

                viewHolder.profileImage = (ImageView)v.findViewById(R.id.profile_Image_view);
                viewHolder.name = (TextView)v.findViewById(R.id.name_text_view);
                v.setTag(viewHolder);

            }else {
                viewHolder = (ViewHolder) v.getTag();
            }

            viewHolder.name.setText(getItem(position).getName());

            if(getItem(position).getImageProfile()!=null)
                viewHolder.profileImage.setImageBitmap(getItem(position).getImageProfile());
            else {
                viewHolder.profileImage.setImageResource(R.mipmap.ic_launcher);
            }


            return v;
        }

        public Filter getFilter() {

            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    contactFromSearch.clear();
                    if(constraint.toString().isEmpty()){
                        contactFromSearch.addAll(new ArrayList<>(contactAdapterModelList));
                    } else {
                        for(ContactAdapterModel contact: contactAdapterModelList){
                            if(contact.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                                contactFromSearch.add(contact);
                            }
                        }
                    }

                    return null;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    addAll(contactFromSearch);
                    notifyDataSetChanged();
                }
            };
        }
    }

    private static class ViewHolder{

        private ImageView profileImage;
        private TextView name;

    }

    private class ContactAdapterModel extends Contact{
        private boolean selected;
        private int position;

        public ContactAdapterModel() {
            this.selected = false;
        }

        public ContactAdapterModel(Contact c,int position) {
            super(c);
            this.position = position;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}
