package com.app.cropimage;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
private static final int PICK_FROM_CAMERA = 1;
private static final int PICK_FROM_GALLERY = 2;
ImageView imgview;

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);

imgview = (ImageView) findViewById(R.id.imageView1);
Button buttonCamera = (Button) findViewById(R.id.btn_take_camera);
Button buttonGallery = (Button) findViewById(R.id.btn_select_gallery);
buttonCamera.setOnClickListener(new View.OnClickListener() {

public void onClick(View v) {
// call android default camera
Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

intent.putExtra(MediaStore.EXTRA_OUTPUT,
MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
// ******** code for crop image
intent.putExtra("crop", "true");
intent.putExtra("aspectX", 0);
intent.putExtra("aspectY", 0);
intent.putExtra("outputX", 200);
intent.putExtra("outputY", 150);

try {

intent.putExtra("return-data", true);
startActivityForResult(intent, PICK_FROM_CAMERA);

} catch (ActivityNotFoundException e) {
// Do nothing for now
}
}
});
buttonGallery.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View v) {
// TODO Auto-generated method stub
Intent intent = new Intent();
// call android default gallery
intent.setType("image/*");
intent.setAction(Intent.ACTION_GET_CONTENT);
// ******** code for crop image
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
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

if (requestCode == PICK_FROM_CAMERA) {
Bundle extras = data.getExtras();
if (extras != null) {
Bitmap photo = extras.getParcelable("data");
imgview.setImageBitmap(photo);

}
}

if (requestCode == PICK_FROM_GALLERY) {
Bundle extras2 = data.getExtras();
if (extras2 != null) {
Bitmap photo = extras2.getParcelable("data");
imgview.setImageBitmap(photo);

}
}
}
}
