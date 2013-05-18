package org.gdgankara.app.activities;

import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class TweetWallActivity extends ListActivity implements Runnable {

	private PullToRefreshListView pullToRefreshView;
	private ArrayList<Tweet> tweets;
	private TabListener tabListener;
	private ProgressDialog pd;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_wall);
        tabAktif();
        pullToRefreshView = (PullToRefreshListView) findViewById(R.id.tweetList);
        pd = ProgressDialog.show(this,"Please Wait" , "Getting tweets from Twitter",true,false);
        Thread thread = new Thread(this);
        thread.start();
        pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            	
            	
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
    }
	
	public void tabAktif(){
		tabListener=new TabListener(this);
		((ImageView)findViewById(R.id.search_button)).setOnClickListener(tabListener);	
		
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected void onPostExecute(String[] result) {
            // Call onRefreshComplete when the list has been refreshed.
        	pullToRefreshView.setAdapter(new UserItemAdapter(TweetWallActivity.this, R.layout.child_of_tweetwall, tweets));
            pullToRefreshView.onRefreshComplete();
            super.onPostExecute(result);
        }

		@Override
		protected String[] doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			tweets=getTweets("AndroidDevDays", 1);
			return null;
		}
    }

	public class UserItemAdapter extends ArrayAdapter<Tweet> {
		private ArrayList<Tweet> tweets;

		public UserItemAdapter(Context context, int textViewResourceId, ArrayList<Tweet> tweets) {
			super(context, textViewResourceId, tweets);
			this.tweets = tweets;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.child_of_tweetwall, null);
			}

			Tweet tweet = tweets.get(position);
			if (tweet != null) {
				TextView username = (TextView) v.findViewById(R.id.username);
				TextView message = (TextView) v.findViewById(R.id.message);
				ImageView image = (ImageView) v.findViewById(R.id.avatar);

				if (username != null) {
					username.setText(tweet.username);
				}

				if(message != null) {
					message.setText(tweet.message);
				}
				
				if(image != null) {
					image.setImageBitmap(getBitmap(tweet.image_url));
				}
			}
			return v;
		}
	}

	public Bitmap getBitmap(String bitmapUrl) {
		try {
			URL url = new URL(bitmapUrl);
			return BitmapFactory.decodeStream(url.openConnection() .getInputStream()); 
		}
		catch(Exception ex) {return null;}
	}
	
	public ArrayList<Tweet> getTweets(String searchTerm, int page) {
		String searchUrl = "http://search.twitter.com/search.json?q=" 
							+ searchTerm;
		
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		
		HttpClient client = new  DefaultHttpClient();
		HttpGet get = new HttpGet(searchUrl);
	      
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		String responseBody = null;
		try{
			responseBody = client.execute(get, responseHandler);
		}catch(Exception ex) {
			ex.printStackTrace();
		}

		JSONObject jsonObject = null;
		JSONParser parser=new JSONParser();
		
		try {
			Object obj = parser.parse(responseBody);
			jsonObject=(JSONObject)obj;
			
		}catch(Exception ex){
			Log.v("TEST","Exception: " + ex.getMessage());
		}
		
		JSONArray arr = null;
		
		try {
			Object j = jsonObject.get("results");
			arr = (JSONArray)j;
		}catch(Exception ex){
			Log.v("TEST","Exception: " + ex.getMessage());
		}

		for(Object t : arr) {
			Tweet tweet = new Tweet(
					((JSONObject)t).get("from_user").toString(),
					((JSONObject)t).get("text").toString(),
					((JSONObject)t).get("profile_image_url").toString()
					);
			tweets.add(tweet);
		}
		
		return tweets;
	}
	
	public class Tweet {
		public String username;
		public String message;
		public String image_url;
		
		public Tweet(String username, String message, String url) {
			this.username = username;
			this.message = message;
			this.image_url = url;
		}
	}

	@Override
	public void run() {
        tweets = getTweets("AndroidDevDays", 1);
        handler.sendEmptyMessage(0);
	}
	private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                pd.dismiss();
                pullToRefreshView.setAdapter(new UserItemAdapter(TweetWallActivity.this, R.layout.child_of_tweetwall, tweets));
        }
};
}