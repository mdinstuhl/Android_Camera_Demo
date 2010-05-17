package org.dinstuhl.android.demo;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.provider.MediaStore;

import android.view.View.OnClickListener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;


/*

Written by Martin Dinstuhl
mdinstuhl@gmail.com

I put this together in order to help other Android developers who might be having the same problem I've
had for the past few days.  Please let me know if you have any questions.

*/

public class CameraActivity extends Activity
{
	
	final int PICTURE_ACTIVITY = 1; // This is only really needed if you are catching the results of more than one activity.  It'll make sense later.
	
    /* Override the onCreate method */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState); // Blah blah blah call the super.
        setContentView(R.layout.main); // It is VERY important that you do this FIRST.  If you don't, the next line will throw a null pointer exception.  And God will kill a kitten.
		final Button cameraButton = (Button)findViewById(R.id.camera_button); // Get a handle to the button so we can add a handler for the click event 
		cameraButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Normally you would populate this with your custom intent.
				startActivityForResult(cameraIntent, PICTURE_ACTIVITY); // This will cause the onActivityResult event to fire once it's done
			}
		});
		
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		AlertDialog msgDialog;
		if (resultCode == RESULT_CANCELED) { // The user didn't like the photo.  ;_;
				msgDialog = createAlertDialog("Q_Q", "Kitty wouldn't sit still, eh?  It's ok - you can try again!", "OK!  I'LL TRY AGAIN!");

		} else {
			/*
			This is where you would trap the requestCode (in this case PICTURE_ACTIVITY).  Seeing as how this is the ONLY 
			Activity that we are calling from THIS activity, it's kind of a moot point.  If you had more than one activity that
			you were calling for results, you would need to throw a switch statement in here or a bunch of if-then-else
			constructs.  Whatever floats your boat.
			*/
			msgDialog = createAlertDialog("ZOMG!", "YOU TOOK A PICTURE!  WITH YOUR PHONE! HOLY CRAP!", "I KNOW RITE??!?");
			
			/*
			Yes, I know that throwing a simple alert dialog doesn't really do anything impressive.
			If you wanna do something with the picture (save it, display it, shoot it to a web server, etc) then you can get the 
			image data like this:
			
			Bitmap = getIntent().getExtras().get("data");
			
			Then do whatever you want with it.
			
			*/
			
		}
		
		msgDialog.show();
	}
	
	private AlertDialog createAlertDialog(String title, String msg, String buttonText){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		AlertDialog msgDialog = dialogBuilder.create();
		msgDialog.setTitle(title);
		msgDialog.setMessage(msg);
		msgDialog.setButton(buttonText, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int idx){
				return; // Nothing to see here...
			}
		});
		
		return msgDialog;
	}

}
