package com.example.hussain.allinone;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    EditText mEditTextNumber;
    EditText msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextNumber = findViewById(R.id.editText);
        msg = findViewById(R.id.editText2);
        Button imageCall = findViewById(R.id.button);
        Button msgCall = findViewById(R.id.button2);
        Button browserCall = findViewById(R.id.button3);
        Button Clearbtn = findViewById(R.id.button4);

        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        Clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearAll();
            }
        });
        msgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_SendSMS_OnClick();
            }
        });


        ImageView btn1 = (ImageView) findViewById(R.id.imageView6);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Close();
            }
        });


        if(msg.getText().toString()!= "")
        {

            browserCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http:www." + msg.getText().toString() +".com"));
                    startActivity(browserIntent);
                }
            });
        }
        else
        {
            browserCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    msg.requestFocus();

                }
            });
        }

    }

    private void Close() {
        finish();
        System.exit(0);
    }
    private void ClearAll() {
        mEditTextNumber.setText("");
        msg.setText("");
        msg.setHint("Write Message or Webpage Name");
        mEditTextNumber.setHint("Enter Number");
    }

    private void makePhoneCall() {
        String number = mEditTextNumber.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(MainActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void btn_SendSMS_OnClick()
    {
        String txt = msg.getText().toString();
        String numb = mEditTextNumber.getText().toString();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.SEND_SMS} , 1);
        }
        else
        {
            SmsManager mysms = SmsManager.getDefault();
            mysms.sendTextMessage(numb , null , txt , null,null);
        }

    }

}
