package com.example.dipalshah.demo1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Dipal Shah on 15-12-2017.
 */

public class ResultActivity extends AppCompatActivity {


    Button confirm;
    public SharedPreferences.Editor editor;
    ArrayList<String> arrPackage, arrPackageNo;
    TextView tv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        arrPackage = new ArrayList<>();
        arrPackageNo=new ArrayList<>();

        Button confirm = (Button) findViewById(R.id.confirm);
        tv_list=(TextView) findViewById(R.id.tv_list);

        arrPackage=SharedPref.getMyStringPref(getApplicationContext());
        arrPackageNo=SharedPref.getMyStringPrefNo(getApplicationContext());

        Log.e("arrPackage size",""+arrPackage.size());
            String Temp_ ="";
        for (int i=0;i<arrPackage.size();i++){
           // Temp_=Temp_+arrPackage.get(i).toString()+" ("+arrPackageNo.get(i).toString()+" ) \n";
            Temp_=Temp_+arrPackage.get(i).toString() + "\n";
        }

        tv_list.setText(Temp_);

        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                ActivityCompat.requestPermissions(ResultActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
                ActivityCompat.requestPermissions(ResultActivity.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
                alertMessage();
        }

        });
    }

    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        Toast.makeText(ResultActivity.this, "Your contact is saved",
                                Toast.LENGTH_LONG).show();

                        //send sms here
                     /*   String toNumbers = "";
                        for ( String s : arrPackageNo)
                        {
                            toNumbers = toNumbers + s + ";";
                        }
                        toNumbers = toNumbers.substring(0, toNumbers.length() - 1);
                        String message= "this is a custom message";

                        Uri sendSmsTo = Uri.parse("smsto:" + toNumbers);
                        Intent intent = new Intent(
                                Intent.ACTION_SENDTO, sendSmsTo);
                        intent.putExtra("sms_body", message);
                        startActivity(intent);
                        */
                     Intent i = new Intent(getApplicationContext(),Main_page.class);
                        startActivity(i);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing
                        Toast.makeText(ResultActivity.this, "No Clicked",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent=new Intent(getApplicationContext(),MainActivity_list_sms.class);
                intent.putExtra("key","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
