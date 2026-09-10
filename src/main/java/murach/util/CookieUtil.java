
package murach.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public static String getCookieValue(
            Cookie[] cookies,
            String cookieName) {

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}

