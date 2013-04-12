package com.olegtyshchenko.PicYouAPI;


import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

//Service class for Scribe library
public class PicYouApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "http://picyou.com/oauth/authorize?oauth_token=%s";
    private static final String REQUEST_TOKEN_RESOURCE = "http://picyou.com/oauth/request_token";
    private static final String ACCESS_TOKEN_RESOURCE = "http://picyou.com/oauth/access_token";

    @Override
    public String getRequestTokenEndpoint() {
        return REQUEST_TOKEN_RESOURCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return ACCESS_TOKEN_RESOURCE;
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }
}
