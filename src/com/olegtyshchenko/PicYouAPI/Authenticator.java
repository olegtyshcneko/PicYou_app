package com.olegtyshchenko.PicYouAPI;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

/**
 * Encapsulates all login logic
 * Provides requests with oAuth Secrets
 */
public class Authenticator {
    public static String CONSUMER_KEY = "vO6kd0AAXsBn6d31i04FNEV64dXfcExePSpEdF8Y";
    public static String CONSUMER_SECRET = "HNajndzvkQeJV12eSs8mR0N3vWhm5YrD513oUQp0";

    private static Authenticator _instance;

    //Singleton
    public static Authenticator getAuthenticator() {
        if(_instance == null) {
            _instance = new Authenticator();
        }
        return _instance;
    }

    private Boolean initialized = false;
    private OAuthService service;
    private Token requestToken = Token.empty();
    private Token accessToken = Token.empty();
    private Connector connector = new Connector();

    public void initializeWithOptions(Class<? extends Api> apiClass, String callBackUrl) {
        if(!initialized) {
            service = new ServiceBuilder()
                .provider(apiClass)
                .apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET)
                .callback(callBackUrl)
                .build();
            requestToken = service.getRequestToken();
            initialized = true;
        }
    }

    private Authenticator() {
    }

    public void initializeAccessToken(Verifier verifier) {
        if(isInitialized() && accessToken.isEmpty()) {
            accessToken = service.getAccessToken(requestToken, verifier);
        }
    }

    public void setAccessToken(Token accessToken) {
        if(this.accessToken.isEmpty()) {
            this.accessToken = accessToken;
        }
    }
    public Token getAccessToken() {
        return accessToken;
    }
    public String getAuthorizationUrl() {
        if(isInitialized()) {
            return service.getAuthorizationUrl(requestToken);
        } else {
            return null;
        }
    }
    public Boolean isInitialized() {
        return (initialized && !requestToken.isEmpty());
    }

    public Connector getConnector() {
        if(!accessToken.isEmpty()) {
            return connector;
        } else {
            return null;
        }
    }

    public class Connector {
        public Connector(){}

        public String recent() {
            OAuthRequest req = new OAuthRequest(Verb.GET,
                    "http://picyou.com/api/v1/images/recent.json");
            service.signRequest(accessToken, req);
            Response response = req.send();

            return getResponseString(response);
        }

        public String popular() {
            OAuthRequest req = new OAuthRequest(Verb.GET,
                    "http://picyou.com/api/v1/images/popular.json");
            service.signRequest(accessToken, req);
            Response response = req.send();

            return getResponseString(response);
        }

        public String image(String imageUrl) {
            OAuthRequest req = new OAuthRequest(Verb.GET,
                    "http://picyou.com/api/v1/images/"+imageUrl+".json");
            service.signRequest(accessToken, req);
            Response response = req.send();

            return getResponseString(response);
        }

        private String getResponseString(Response response) {
            if(response.isSuccessful()) {
                return response.getBody();
            } else {
                return errorResponse(response.getCode());
            }
        }

        private String errorResponse(int code) {
            return "{\"error\": \"Bad response with code " +
                    String.valueOf(code)+"\"}";
        }
    }

}
