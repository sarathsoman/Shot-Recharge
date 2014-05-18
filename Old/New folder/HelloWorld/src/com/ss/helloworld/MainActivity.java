package com.ss.helloworld;


import android.os.Bundle;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	public TextView s1;
	int a=0;

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		s1=(TextView)findViewById(R.id.textView1);
		
	}
	public void print(View v){

if(a==0)
{
	s1.setText("shabeer");
a++;	
}
else if(a==1)
{
	
	s1.setText("kalamadan");
	a++;
}
else if(a==2)
{
	s1.setText("rose");
	a++;
}
else
{
	s1.setText("emiliya");
	a=0;
}


	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
