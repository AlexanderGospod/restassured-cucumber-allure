package utilities;

import java.util.HashMap;
import java.util.Map;

public class TokenStorage {
    private static final Map<String, String> tokenMap = new HashMap<>();

    public static String getToken(String tokenName) {
        return tokenMap.get(tokenName);
    }

    public static void setToken(String tokenName, String newToken) {
        tokenMap.put(tokenName, newToken);
    }
}
