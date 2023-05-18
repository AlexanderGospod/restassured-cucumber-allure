package utilities.token;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTubeScopes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;


public class AccessTokenProvider {
    public void getTokenWithNecessaryAccessRights(String scope) {
        //Get token with necessary access rights and save it to the storage
//        TokenStorage.setToken("YOUTUBE_FORCE_SSL", "ya29.a0AWY7CkmDySpGHZUibFaBM_u_anM9-LCzBdLm0-BqU7ASiAuj84cjdER0OsZ8iqU8hmdTrxUgna-okUC9myDfhGID1-Nq4AhbPYfvN--pwJLp1pEICVwC19grucsumF4GAo3RE-Own3kxHCaxfpKkBZof6kRFaCgYKAdMSARISFQG1tDrp6tC-artCWWNbVnl-OHoeFA0163");
//        TokenStorage.setToken("YOUTUBE_READONLY", "ya29.a0AWY7CkluFbfQ0ZEAFOm4tsjjN33MEINEDg9SweFsp3dcjzvyiS_wQrc8sum7mGe9h8VzN4ZXz1aAZf7D5vdie-MKHLMHRz5UNPUx0FHHykjhxzGm3mNQSElzG1pnsvhEh3l75OxiB9n8u_czdrubtq0eguU5aCgYKARQSARISFQG1tDrp_5ngjvPklDo_HNgc5agopA0163");

        if (TokenStorage.getToken(scope) == null) {
            String token;
            try {
                token = authorize(scope).getAccessToken();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
//            System.out.println(scope + " - " + token);
            TokenStorage.setToken(scope, token);
        }
    }

    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private Credential authorize(String scope) throws IOException, GeneralSecurityException {
        String CLIENT_SECRETS_FILE = "/client_secret_CLIENTID.json";
        Iterable<String> SCOPES = getScope(scope);
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(AccessTokenProvider.class.getResourceAsStream(CLIENT_SECRETS_FILE)));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, (Collection<String>) SCOPES)
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private Iterable<String> getScope(String scope) {
        switch (scope) {
            case "YOUTUBE":
                return Collections.singletonList(YouTubeScopes.YOUTUBE);
            case "YOUTUBE_FORCE_SSL":
                return Collections.singletonList(YouTubeScopes.YOUTUBE_FORCE_SSL);
            case "YOUTUBE_READONLY":
                return Collections.singletonList(YouTubeScopes.YOUTUBE_READONLY);
            case "YOUTUBE_UPLOAD":
                return Collections.singletonList(YouTubeScopes.YOUTUBE_UPLOAD);
            case "YOUTUBEPARTNER":
                return Collections.singletonList(YouTubeScopes.YOUTUBEPARTNER);
            case "YOUTUBEPARTNER_CHANNEL_AUDIT":
                return Collections.singletonList(YouTubeScopes.YOUTUBEPARTNER_CHANNEL_AUDIT);
            default:
                throw new IllegalArgumentException("Invalid YouTubeScope: " + scope);
        }
    }
}
