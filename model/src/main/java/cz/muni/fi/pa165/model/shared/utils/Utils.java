package cz.muni.fi.pa165.model.shared.utils;

import jakarta.servlet.http.HttpServletRequest;

public class Utils {

    public static String getToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            return authHeader.substring(7);
        }

        return null;
    }
}
