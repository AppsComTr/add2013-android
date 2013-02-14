package org.gdgankara.app;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetWallPrepare {

	static Twitter twitter;

	public static void getTweets(String searchParam) {

		int i = 0;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(Constants.CONSUMER_KEY)
				.setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
				.setOAuthAccessToken(Constants.ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(Constants.ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		try {
			Query query = new Query("@orhunmertsimsek");
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					System.out.println(tweet.getUser()
							.getBiggerProfileImageURL()
							+ " @"
							+ tweet.getUser().getScreenName()
							+ " - "
							+ tweet.getText());
				}
				i++;
			} while ((query = result.nextQuery()) != null || i == 20);
			System.exit(0);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
	}
}
