package org.gdgankara.app.listeners;

import org.gdgankara.app.activities.MainActivity;
import org.gdgankara.app.activities.SearchActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**************************************************************************************
 *      Bu sýnýf her activity içerisinde tablarýn ne yapacaðýný implement etmek
 *      yerine , sadece burada yazýp her activity içerisinde bu sýnýfýn nesnesini
 *      kullanmak için oluþturuldu.
 * 
 **************************************************************************************/

public class TabListener implements OnClickListener {

	private Context context;
	
	public TabListener(Context context){
		this.context=context;
	}
	

	public void onClick(View v) {
		
		Intent i = new Intent(context , SearchActivity.class);
		context.startActivity(i);
	
	}
	/*******************************************************************************************/
	
	
}