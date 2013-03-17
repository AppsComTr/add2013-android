package org.gdgankara.app.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.gdgankara.app.model.Session;
import org.gdgankara.app.tasks.GetJSONObjectTask;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/**
 * API iletişimi için temel metotları barındıran soyut sınıf
 * 
 * @author bkyn 
 */
public abstract class BaseHandler {
	private final String TAG = BaseHandler.class.getSimpleName();
	protected Context context;
	
	public BaseHandler(Context context){
		this.context = context;
	}
		
	/**
	 * Verilen adrese GET isteği yapar.
	 * 
	 * @param url İstek yapılacak adres
	 * @return JSONObject
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public JSONObject doGet(String url) throws InterruptedException, ExecutionException{
		GetJSONObjectTask getJSONObjectTask = new GetJSONObjectTask();
		JSONObject jsonObject = null;
		jsonObject = getJSONObjectTask.execute(url).get();
		return jsonObject;
	}

	/**
	 * Verilen listeyi dosyaya yazar.
	 * 
	 * @param ArrayList list
	 * @throws IOException
	 */
	public void writeListToFile(ArrayList<?> list, String cacheFileName) throws IOException{
		FileOutputStream fileOutputStream;
		fileOutputStream = context.openFileOutput(cacheFileName, Context.MODE_PRIVATE);
		ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
		outputStream.writeObject(list);
		outputStream.close();
	}
	
	/**
	 * Cache dosyasını okuyarak içerisindeki listeyi döndürür.
	 * 
	 * @return ArrayList
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<?> readCacheFile(String cacheFileName) throws IOException, ClassNotFoundException{
		File cacheFile = new File(context.getFilesDir().toString() + "/" + cacheFileName);
		ArrayList<?> list = null;
		if (cacheFile.exists()) {
			FileInputStream fileInputStream = context.openFileInput(cacheFileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			list = (ArrayList<?>)objectInputStream.readObject();
			objectInputStream.close();
		}
		return list;
	}
	
	/**
	 * Verilen json nesnesini işleyerek arraylist döner
	 * 
	 * @param JSONObject jsonObject
	 * @return ArrayList
	 * @throws JSONException
	 */
	public abstract ArrayList<?> parseJSONObject(JSONObject jsonObject, String objectName) throws JSONException;

}
