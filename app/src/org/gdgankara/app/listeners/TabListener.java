package org.gdgankara.app.listeners;

import org.gdgankara.app.activities.DecoderActivity;
import org.gdgankara.app.activities.MainActivity;
import org.gdgankara.app.activities.SearchActivity;
import org.gdgankara.app.tasks.PrepareListsTask;
import org.gdgankara.app.utils.Util;
import org.gdgankara.app.R;

import com.google.zxing.client.android.CaptureActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**************************************************************************************
 *      Bu s�n�f her activity i�erisinde tablar�n ne yapaca��n� implement etmek
 *      yerine , sadece burada yaz�p her activity i�erisinde bu s�n�f�n nesnesini
 *      kullanmak i�in olu�turuldu.
 * 
 **************************************************************************************/

public class TabListener implements OnClickListener {
	private static final String TAG = TabListener.class.getSimpleName();
	private Context context;
	
	public TabListener(Context context){
		this.context=context;
	}
	

	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.search_button:
			Intent i = new Intent(context , SearchActivity.class);
			context.startActivity(i);
			break;
			
		case R.id.qr_decoder_button:
			Log.i("Sedat","Decoder butonuna bas�ld�");
			i = new Intent(context, DecoderActivity.class);
			i.putExtra("SCAN_MODE", "QR_CODE_MODE");
			i.putExtra("return-data", true);
			context.startActivity(i);
			break;
			
		case R.id.update_button:
			try {
				PrepareListsTask prepareListsTask = new PrepareListsTask(context);
				prepareListsTask.execute();
			} catch (Exception e) {
				Log.e(TAG, "Error: " + e.getMessage().toString());
				e.printStackTrace();
			}
			break;
		}
	
	}
	
	/*******************************************************************************************/
	
	
	public void checkQRState(){
		if(Util.qr_state==0){
			Toast.makeText(context,Util.getDefaultLanguage().equals("tr")?"Ge�ersiz QR Code":"Invalid QR Code", Toast.LENGTH_SHORT).show();
			Util.qr_state=1;
		}
	}
	
}