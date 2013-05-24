package org.gdgankara.app.activities;


import org.gdgankara.app.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.result.ResultHandler;
import com.google.zxing.client.android.result.ResultHandlerFactory;

public class DecoderActivity extends CaptureActivity {

	@Override
	public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
		ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(
				this, rawResult);
		Log.i("Sedat","DecoderActivity icerisine girdi");
		if(checkQRMessage(resultHandler.getResult().toString())){
			Intent intent = new Intent(this,EmployeeIdActivity.class);
			intent.putExtra("SCAN_RESULT",resultHandler.getResult().toString() );
			startActivity(intent);
		}
		else{
			Util.qr_state=0;
		}
		
		finish();
	}
	
	private boolean checkQRMessage(String message){
		String[] temp=message.split("<#>");
		if(temp.length==3){
			return true;
		}
		return false;
	}

}
