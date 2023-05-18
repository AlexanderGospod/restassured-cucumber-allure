package steps;

import io.cucumber.java.BeforeAll;
import utilities.token.AccessTokenProvider;

public class Hooks {
    @BeforeAll
    public static void getTokens() {
        AccessTokenProvider accessTokenProvider = new AccessTokenProvider();
        accessTokenProvider.getTokenWithNecessaryAccessRights("YOUTUBE_FORCE_SSL");
        accessTokenProvider.getTokenWithNecessaryAccessRights("YOUTUBE_READONLY");
    }
}
