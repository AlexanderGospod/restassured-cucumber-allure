package utilities;


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

    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final String CLIENT_SECRETS_FILE = "/client_secret_CLIENTID.json";

    public Credential authorize(String scope) throws IOException, GeneralSecurityException {
        Iterable<String> SCOPES = getScope(scope); //
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

    public String getAccessToken(String scope)  {
        try {
            return authorize(scope).getAccessToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
    private Iterable<String> getScope(String scope){
        switch(scope) {
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
