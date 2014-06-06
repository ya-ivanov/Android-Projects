package com.zip.it;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity implements OnCheckedChangeListener {

	static EditText files, rarName, endDir, desk;
	Spinner spinner;
	Button zipIt, browse, choose;
	String[] ko4o;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Holo_Light);
		setContentView(R.layout.activity_main);
		
		files = (EditText)findViewById(R.id.files);
		rarName = (EditText)findViewById(R.id.archiveName);
		endDir = (EditText)findViewById(R.id.endDir);
		desk = (EditText)findViewById(R.id.desk);
		zipIt = (Button)findViewById(R.id.button1);
		browse = (Button)findViewById(R.id.browse);
		choose = (Button)findViewById(R.id.choose);
		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.planets_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		
		ko4o = files.getText().toString().split(",");
		
		
		zipIt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (endDir.getText().length() > 5 && files.getText().length() > 4 && rarName.getText().length() > 3)
				{
					boolean ttt = generateZipFile(files.getText().toString(), endDir.getText().toString(), rarName.getText().toString() + spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString().toLowerCase(Locale.ENGLISH));
					if (ttt)
						Toast.makeText(getApplicationContext(), "Files successfully archived.", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(), "Some error occurred. Please check your data.", Toast.LENGTH_SHORT).show();
				}
				else 
					Toast.makeText(getApplicationContext(), "Please check your inputs. The name should be 3 or more simbols.", Toast.LENGTH_SHORT).show();
			}
		});
		
		choose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				File mPath = new File(Environment.getExternalStorageDirectory() + "//DIR//");
		        FileDialog fileDialog = new FileDialog(MainActivity.this, mPath);
		        fileDialog.setFileEndsWith("");
		        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
		            public void fileSelected(File file) {
		                Log.d(getClass().getName(), "selected file " + file.toString());
		                int diff = file.toString().length() - file.toString().lastIndexOf("/");
		                endDir.setText(file.toString().substring(0, file.toString().length()-diff));
		            }
		        });
		        fileDialog.showDialog();
		        Toast.makeText(getApplicationContext(), "Choose any file in the desired folder", Toast.LENGTH_SHORT).show();
			}
		});
		
		browse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				File mPath = new File(Environment.getExternalStorageDirectory() + "//DIR//");
		        FileDialog fileDialog = new FileDialog(MainActivity.this, mPath);
		        fileDialog.setFileEndsWith("");
		        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {
		            public void fileSelected(File file) {
		                Log.d(getClass().getName(), "selected file " + file.toString());
		                files.setText(file.toString());
		            }
		        });
		        fileDialog.showDialog();
			}
		});
		
	}

	public static Boolean generateZipFile(String filename,
			
			String destinationDir, String zipFilename) {
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];
		try {
			boolean result = (new File(destinationDir)).mkdirs();

			String zipFullFilename = destinationDir + "/" + zipFilename;

			System.out.println(result);

			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFullFilename));
			// Compress the files
			//for (String filename : sourcesFilenames) {
				FileInputStream in = new FileInputStream(filename);
				// Add ZIP entry to output stream.
				File file = new File(filename); //"Users/you/image.jpg"
				out.putNextEntry(new ZipEntry(file.getName())); //"image.jpg"
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				
				out.closeEntry();
				in.close();
			//} // Complete the ZIP file
			out.setComment(desk.getText().toString());
			out.close();

			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

}
