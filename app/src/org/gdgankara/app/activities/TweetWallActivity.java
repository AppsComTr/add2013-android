package org.gdgankara.app.activities;

import java.net.URL;
import java.util.ArrayList;

import org.gdgankara.app.R;
import org.gdgankara.app.listeners.TabListener;
import org.gdgankara.app.utils.Util;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class TweetWallActivity extends ListActivity implements Runnable {

	private PullToRefreshListView pullToRefreshView;
	private ArrayList<Tweet> tweets;
	private TabListener tabListener;
	private ProgressDialog pd;
	private AlertDialog alertDialog;
	private Twitter twitterObject;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		overridePendingTransition(0, 0);
		setContentView(R.layout.activity_tweet_wall);
		tabAktif();

		pullToRefreshView = (PullToRefreshListView) findViewById(R.id.tweetList);
		childItemsActive();
		if (Util.isInternetAvailable(this)) {

			if (pd == null) {
				try {
					pd = new ProgressDialog(TweetWallActivity.this);
					pd.setMessage(getResources().getString(
							R.string.getting_tweets));
					pd.setTitle(getResources().getString(R.string.loading));
					pd.setCancelable(false);
					pd.show();

				} catch (Exception e) {
					pd = null;
					e.printStackTrace();
				}
			}
			Thread thread = new Thread(this);
			thread.start();
			pullToRefreshView
					.setOnRefreshListener(new OnRefreshListener<ListView>() {
						@Override
						public void onRefresh(
								PullToRefreshBase<ListView> refreshView) {

							// Do work to refresh the list here.
							new GetDataTask().execute();
						}
					});
		} else {
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle(getResources().getString(
					R.string.tweet_wall_internet_title));
			alertDialog.setMessage(getResources().getString(
					R.string.tweet_wall_internet_message));
			alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,
					getResources().getString(android.R.string.ok),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							alertDialog.cancel();
							finish();
						}
					});
			alertDialog.setIcon(R.drawable.launcher_icon);
			alertDialog.show();
		}
	}
	
	private void childItemsActive() {
		pullToRefreshView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				startTwitterApp(arg2);
				
			}
		});
		
	}
	
	private void startTwitterApp(int index){
		   Intent i = new Intent(Intent.ACTION_VIEW);
	       i.setData(Uri.parse((tweets.get(index-1).tweetAdress)));
//	       i.setType("application/twitter");
	       startActivity(i);
	}

	public void tabAktif() {
		tabListener = new TabListener(this);
		((ImageView) findViewById(R.id.search_button))
				.setOnClickListener(tabListener);
		((ImageView) findViewById(R.id.update_button))
				.setOnClickListener(tabListener);
		((ImageView) findViewById(R.id.qr_decoder_button))
				.setOnClickListener(tabListener);

	}

	private void initTwitter() {
		twitterObject = new TwitterFactory().getInstance();
		twitterObject.setOAuthConsumer("******",
				"*****");
		twitterObject.setOAuthAccessToken(new AccessToken(
				"****",
				"****"));
		try {
			twitterObject.getAccountSettings();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected void onPostExecute(String[] result) {
			// Call onRefreshComplete when the list has been refreshed.
			pullToRefreshView
					.setAdapter(new UserItemAdapter(TweetWallActivity.this,
							R.layout.child_of_tweetwall, tweets));
			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... arg0) {
			tweets = getTweets("AndroidDevDays", 1, 100);
			return null;
		}
	}

	public class UserItemAdapter extends ArrayAdapter<Tweet> {
		private ArrayList<Tweet> tweets;

		public UserItemAdapter(Context context, int textViewResourceId,
				ArrayList<Tweet> tweets) {
			super(context, textViewResourceId, tweets);
			this.tweets = tweets;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.child_of_tweetwall, null);
			}

			Tweet tweet = tweets.get(position);
			if (tweet != null) {
				TextView username = (TextView) v.findViewById(R.id.username);
				TextView message = (TextView) v.findViewById(R.id.message);
				ImageView image = (ImageView) v.findViewById(R.id.avatar);
				TextView screenname = (TextView) v
						.findViewById(R.id.user_twitter_name);

				if (username != null) {
					String usrnamestr = "@" + tweet.username;
					username.setText(usrnamestr);
				}

				if (message != null) {
					message.setText(tweet.message);
				}

				if (image != null) {
					UrlImageViewHelper.setUrlDrawable(image, tweet.image_url,
							R.drawable.loading);
				}
				if (screenname != null) {
					screenname.setText(tweet.screen_name);
				}
			}
			return v;
		}
	}

	public Bitmap getBitmap(String bitmapUrl) {
		try {
			URL url = new URL(bitmapUrl);
			return BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
		} catch (Exception ex) {
			return null;
		}
	}

	public ArrayList<Tweet> getTweets(String searchTerm, int page, int rpp) {
		initTwitter();
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		Query query = new Query("AndroidDevDays");
		query.setCount(100);
		QueryResult result = null;
		try {
			result = twitterObject.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		try{
			for (Status status : result.getTweets()) {
				StringBuffer address = new StringBuffer();
		    	address.append("http://twitter.com/#!/");
		    	address.append(status.getUser().getScreenName());
		    	address.append("/status/");
		    	address.append(status.getId());
	
		    	String tweetAdress = address.toString();
		    	
				tweets.add(new Tweet(status.getUser().getScreenName(),
						status.getText(), status.getUser().getProfileImageURL(),
						status.getUser().getName(),tweetAdress));
				
			}
		}
		catch(NullPointerException e){
			
		}
		return tweets;
	}

	public class Tweet {
		public String username;
		public String message;
		public String image_url;
		public String screen_name;
		public String tweetAdress;
		
		public Tweet(String username, String message, String url,
				String screen_name, String tweetAdress) {
			this.username = username;
			this.message = message;
			this.image_url = url;
			this.screen_name = screen_name;
			this.tweetAdress = tweetAdress;
		}
	}

	@Override
	public void run() {
		tweets = getTweets("AndroidDevDays", 1, 100);
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pullToRefreshView
					.setAdapter(new UserItemAdapter(TweetWallActivity.this,
							R.layout.child_of_tweetwall, tweets));
			try {
				if (pd != null)
					pd.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
}