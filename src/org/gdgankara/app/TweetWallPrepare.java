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

	public static String getTweets(String searchParam) {

		int i = 0;
		String totalWall = "";
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(Constants.CONSUMER_KEY)
				.setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
				.setOAuthAccessToken(Constants.ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(Constants.ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		try {
			Query query = new Query(searchParam);
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					totalWall += "\t@"
							+ tweet.getUser().getScreenName()
							+ " - "
							+ tweet.getText() + "\n\n";
				}
				i++;
			} while ((query = result.nextQuery()) != null || i == 20);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
		return totalWall;
	}
}
