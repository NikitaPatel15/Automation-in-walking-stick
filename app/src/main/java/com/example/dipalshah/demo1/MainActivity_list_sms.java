package com.example.dipalshah.demo1;

        import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;


public class MainActivity_list_sms extends AppCompatActivity
{
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    ArrayList<String> StoreContacts;
    ArrayAdapter<String> arrayAdapter;
    Cursor cursor;
    String name;
    String phonenumber;

    public static final int RequestPermissionCode = 1;
    Button button2;
    String Temp = "";
    static int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        listView = (ListView) findViewById(R.id.listview1);
        dataModels = new ArrayList<>();
        button2 = (Button) findViewById(R.id.button2);

        Intent i = getIntent();

        Temp = i.getStringExtra("key");
        try {
            hasPermissions(this, PERMISSIONS);

            if (Temp.toString().length() > 0) {

                //came from result activity
            } else {
                ArrayList<String> temp_list = SharedPref.getMyStringPrefNo(getApplicationContext());
                if (temp_list.size() > 0) {
                    startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ArrayList<String> temp_list = SharedPref.getMyStringPrefNo(getApplicationContext());
                if (temp_list.size() > 0) {
                    startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
     /*   adapter = new CustomAdapter_new(dataModels,getApplicationContext());
        listView.setAdapter(adapter);*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataModel dataModel= dataModels.get(position);
                dataModel.select_num = !dataModel.select_num;
                adapter.notifyDataSetChanged();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedItems = new ArrayList<String>();
                ArrayList<String> selectedItemsNo = new ArrayList<String>();
                for (int i = 0; i < dataModels.size(); i++) {
                    // Item position in adapter
                    // Add sport if it is checked i.e.) == TRUE!
                    if (dataModels.get(i).select_num == true) {
                        selectedItems.add(dataModels.get(i).name);
                        selectedItemsNo.add(dataModels.get(i).phone_num);
                    }
                }
                SharedPref.setMyStringPref(getApplicationContext(), selectedItems);
                SharedPref.setMyStringPrefNo(getApplicationContext(), selectedItemsNo);

                Log.e("selectedItems size", "" + selectedItems.size());
                Intent intent = new Intent(getApplicationContext(),
                        ResultActivity.class);
                startActivity(intent);

              /*  long pressTime = System.currentTimeMillis();
                // If double click...
                if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
                    Toast.makeText(getApplicationContext(), "Double Click Event", Toast.LENGTH_SHORT).show();
                    mHasDoubleClicked = true;
                } else {     // If not double click....
                    mHasDoubleClicked = false;
                    Handler myHandler = new Handler() {
                        public void handleMessage(NotificationCompat.MessagingStyle.Message m) {
                            if (!mHasDoubleClicked) {
                                Toast.makeText(getApplicationContext(), "Single Click Event", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    Message m = new Message();
                    myHandler.sendMessageDelayed(m, DOUBLE_PRESS_INTERVAL);
                }
                // record the last time the menu button was pressed.
                lastPressTime = pressTime;*/
            }
        });

    }
    public void GetContactsIntoArrayList()
    {
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            try
            {
                boolean select_num;
                ArrayList<String> array_pckg=SharedPref.getMyStringPrefNo(getApplicationContext());
                int c=0;
                for(int i=0;i<array_pckg.size();i++){
                    if (array_pckg.get(i).equalsIgnoreCase(phonenumber)){
                        c=1;
                    }
                }
                if (c==1)
                    select_num=true;
                else
                    select_num=false;

                dataModels.add(new DataModel(name, phonenumber, select_num));
            }catch (Exception e){
                e.printStackTrace();
                dataModels.add(new DataModel(name, phonenumber, false));
            }
            //  StoreContacts.add(name + " "  + ":" + " " + phonenumber);
        }
        cursor.close();
        adapter = new CustomAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);
    }


    public void hasPermissions(Context context, String[] permissions) {
        // Check the SDK version and whether the permission is already granted or not.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && context != null && permissions != null) {
            requestPermissions(permissions, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            GetContactsIntoArrayList();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                hasPermissions(this, permissions);
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
