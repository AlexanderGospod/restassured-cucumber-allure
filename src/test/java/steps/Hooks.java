package steps;

import io.cucumber.java.BeforeAll;

public class Hooks {
    @BeforeAll
    public static void getTokens() {
        CommonSteps commonSteps = new CommonSteps();
        //commonSteps.getTokenWithNecessaryAccessRights("YOUTUBE_FORCE_SSL");
        //commonSteps.getTokenWithNecessaryAccessRights("YOUTUBE_READONLY");
    }
}
