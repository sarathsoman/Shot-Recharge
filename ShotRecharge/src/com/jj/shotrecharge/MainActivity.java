package com.jj.shotrecharge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {
private static final int PICK_FROM_CAMERA = 2;
private static final int PICK_FROM_GALLERY = 1;
ImageView imgview;

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
this.requestWindowFeature(Window.FEATURE_NO_TITLE);
setContentView(R.layout.activity_main);

SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
if (isFirstRun) {
	TextView tv2 = (TextView) findViewById(R.id.textView1);
	tv2.setText("Welcome..!!!!");
// run your one time code
	try {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("Files");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        	tv2.setText("Exception2");
        }

        for(String filename : files) {
            System.out.println("File name => "+filename);
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open("Files/"+filename);   // if files resides inside the "Files" directory itself
              File folder = new File(Environment.getExternalStorageDirectory() + "/tessdata");
              if (!folder.exists()) {
            	folder.mkdir();
            	}out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/tessdata/" + filename);
              byte[] buffer = new byte[1024];
              int read;
              while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
              }
              in.close();
              in = null;
              out.flush();
              out.close();
              out = null;
            } catch(Exception e) {
            	tv2.setText("Exception3");
                Log.e("tag", e.getMessage());
            }
        }

	}
	catch (Exception e){
		tv2.setText("Exception4");
		Log.e("tag", "Failed to copy asset file: ", e);
	}

	SharedPreferences.Editor editor = wmbPreference.edit();
    editor.putBoolean("FIRSTRUN", false);
    editor.commit();
}

imgview = (ImageView) findViewById(R.id.imageView1);
Button buttonCamera = (Button) findViewById(R.id.btn_take_camera);
Button buttonGallery = (Button) findViewById(R.id.btn_select_gallery);

TextView tv2 = (TextView) findViewById(R.id.textView2);
tv2.setTextColor(Color.parseColor("#FFFFFF"));
tv2.setOnClickListener(new View.OnClickListener()
{
    public void onClick(View view) 
    {
    	String ussdCode = "*123" + Uri.encode("#");
    	startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
                }

   });

TextView tv3 = (TextView) findViewById(R.id.textView3);
tv3.setTextColor(Color.parseColor("#FFFFFF"));
tv3.setOnClickListener(new View.OnClickListener()
{
    public void onClick(View view) 
    {
    	String ussdCode = "*123*21" + Uri.encode("#");
    	startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
                }

   });

TextView tv4 = (TextView) findViewById(R.id.textView4);
tv4.setTextColor(Color.parseColor("#FFFFFF"));
tv4.setOnClickListener(new View.OnClickListener()
{
    public void onClick(View view) 
    {
    	String ussdCode = "*125*5" + Uri.encode("#");
    	startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
                }

   });


buttonCamera.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
// calling android default camera
	try {
Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
intent.putExtra(MediaStore.EXTRA_OUTPUT,
MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
// ******** code for crop image
intent.putExtra("crop", "true");
intent.putExtra("aspectX", 0);
intent.putExtra("aspectY", 0);
intent.putExtra("outputX", 200);
intent.putExtra("outputY", 150);



//intent.putExtra("return-data", true);
startActivityForResult(intent, PICK_FROM_CAMERA);

} catch (NullPointerException e) {
//Do nothing
}
}
});
buttonGallery.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View v) {
Intent intent = new Intent();
// calling android default gallery
intent.setType("image/*");
intent.setAction(Intent.ACTION_GET_CONTENT);
//code for crop image
intent.putExtra("crop", "true");
intent.putExtra("aspectX", 0);
intent.putExtra("aspectY", 0);
intent.putExtra("outputX", 200);
intent.putExtra("outputY", 150);

try {

intent.putExtra("return-data", true);
startActivityForResult(Intent.createChooser(intent,
"Complete action using"), PICK_FROM_GALLERY);

} catch (ActivityNotFoundException e) {
// Do nothing for now
}
}
});
}
@SuppressLint("CutPasteId")
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

if (requestCode == PICK_FROM_CAMERA) {
Bundle extras = data.getExtras();
if (extras != null) {
Bitmap photo = extras.getParcelable("data");
imgview.setImageBitmap(photo);
TessBaseAPI baseApi = new TessBaseAPI();
photo = photo.copy(Bitmap.Config.ARGB_8888, true);
File externalStorageDirectory = Environment.getExternalStorageDirectory();
baseApi.init(externalStorageDirectory.getAbsolutePath()+
"/","eng");
baseApi.setImage(photo);
final String recognizedText = baseApi.getUTF8Text();
baseApi.end();
TextView tv1 = (TextView) findViewById(R.id.textView1);
tv1.setText(recognizedText);
try
{
tv1.setOnClickListener(new View.OnClickListener()
{
    public void onClick(View view) 
    {
    	String ussdCode = "*101*" + recognizedText + Uri.encode("#");
    	startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
                }

   });
}
catch (Exception e){
	TextView tv2 = (TextView) findViewById(R.id.textView1);
	tv2.setText("Exception");
	imgview.setImageBitmap(photo);
}

}
}

if (requestCode == PICK_FROM_GALLERY) {
Bundle extras2 = data.getExtras();
if (extras2 != null) {
Bitmap photo = extras2.getParcelable("data");
imgview.setImageBitmap(photo);
TessBaseAPI baseApi = new TessBaseAPI();
photo = photo.copy(Bitmap.Config.ARGB_8888, true);
File externalStorageDirectory = Environment.getExternalStorageDirectory();
baseApi.init(externalStorageDirectory.getAbsolutePath()+
"/","eng");
baseApi.setImage(photo);
final String recognizedText = baseApi.getUTF8Text();
baseApi.end();
TextView tv1 = (TextView) findViewById(R.id.textView1);
tv1.setText(recognizedText);
try
{
tv1.setOnClickListener(new View.OnClickListener()
{
    public void onClick(View view) 
    {
    	String ussdCode = "*101*" + Uri.encode(recognizedText) + Uri.encode("#");
    	startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode)));
                }

   });
}
catch (Exception e){
	TextView tv2 = (TextView) findViewById(R.id.textView1);
	tv2.setText("Exception");
	imgview.setImageBitmap(photo);
}
}
}
}

}