package com.sarath.camera;

import android.os.Bundle;		
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	public TextView sarath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*Intent intent=new Intent(this,Second.class);
		startActivity(intent);*/
		sarath=(TextView)findViewById(R.id.textView1);
		sarath.setText("This is oncreate");
		
	}
public void print(View v){
	
	EditText t;
	t=(EditText) findViewById(R.id.editText1);
	String s=t.getText().toString();
	sarath.setText(s);
	
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
