package org.gdgankara.app.activities;


import org.gdgankara.app.utils.Util;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.result.AddressBookResultHandler;
import com.google.zxing.client.android.result.ResultHandler;
import com.google.zxing.client.android.result.ResultHandlerFactory;

public class DecoderActivity extends CaptureActivity {

	@Override
	public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
		
		if(checkQRMessage(rawResult.getText())){
		
			Intent intent = new Intent(this,ParticipantIdActivity.class);
			intent.putExtra("SCAN_RESULT",rawResult.getText() );
			startActivity(intent);
		}
		else{
			Util.qr_state=0;
		}
		
		finish();
	}
	
	private boolean checkQRMessage(String message){
		String[] temp=message.split("\n");
		if(temp!=null){
			if(temp[0].equals("BEGIN:VCARD")){
				return true;
			}
		}
		return false;
	}

}
