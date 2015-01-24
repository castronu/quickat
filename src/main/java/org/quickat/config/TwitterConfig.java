package org.quickat.config;

import org.quickat.jobs.TweetProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

/**
 * Created by david on 2014-12-26.
 */
@Configuration
public class TwitterConfig {

    @Bean
    // Params injected from application.properties file:
    public Twitter twitter(@Value("${spring.social.twitter.appId}") String appId,
                           @Value("${spring.social.twitter.appSecret}") String appSecret,
                           @Value("${spring.social.twitter.accessToken}") String accessToken,
                           @Value("${spring.social.twitter.accessTokenSecret}") String accessTokenSecret) {
        return new TwitterTemplate(appId, appSecret, accessToken, accessTokenSecret);
    }

    @Bean
    // Params injected from application.properties file:
    public TweetProcessor tweetProcessor(){
        return new TweetProcessor();

    }
}
