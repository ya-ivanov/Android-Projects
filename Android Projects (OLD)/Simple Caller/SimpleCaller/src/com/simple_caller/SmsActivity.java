package com.simple_caller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SmsActivity extends Activity {
	 
	 Button sendSms;
	 EditText smsText;
	 TextView contactName;
	 static String textToSend = "";
	 static String contactNameStr = "";
	 static String numberToSendTo = "";
	 
	 @Override
     public void onCreate(Bundle savedInstanceState) {         

        super.onCreate(savedInstanceState);    
        setContentView(R.layout.activity_sms);
        
        Intent intent = getIntent();
   	 	final String value = intent.getStringExtra("contactNum");
   	    final String personName = intent.getStringExtra("contactName");
   	 	
        final Builder sss  = new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Send SMS?")
        .setMessage("Do you want to send the text message to " + personName + "(" + value + ") ?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        	
        	try
			{
        		SmsManager.getDefault().sendTextMessage(value, null, String.valueOf(smsText.getText()) , null,null);
    			Toast.makeText(getApplicationContext(), "SMS sent to " + personName + " (" + value + ") succesfully.", Toast.LENGTH_SHORT).show();
    		}
			catch(Exception e)
			{
				Toast.makeText(getApplicationContext(), "Failed to send SMS! Please try again later.", Toast.LENGTH_SHORT).show();
			}
        }

    })
    .setNegativeButton("No", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        	Toast.makeText(getApplicationContext(), "SMS not sended.", Toast.LENGTH_SHORT).show();
		}

    });
        
        sendSms = (Button)findViewById(R.id.send);
        smsText = (EditText)findViewById(R.id.smsTxt);
        contactName = (TextView)findViewById(R.id.contactName);
        contactName.setText(personName + "( " + value + " )");
        sendSms.setOnClickListener(new OnClickListener(){
        	public void onClick(View arg0) {
				sss.show();
			}
        	
        });
        
        
        
    }
	 
}
