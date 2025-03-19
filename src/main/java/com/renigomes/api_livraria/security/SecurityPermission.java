package com.renigomes.api_livraria.security;

import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;


public class SecurityPermission {

    public static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**"
    };

    public static final String ADMIN = "ADMIN";
    public static final String PERMIT_ALL = "PERMIT_ALL";
    @SuppressWarnings({"rawtypes", "unchecked" })
    private static final Map<String, Map<HttpMethod, String []>> API_WHITELIST = new HashMap() {
        {
            put(ADMIN, new HashMap<HttpMethod, String []>(){
                {
                    put(HttpMethod.GET, new String[]{
                            "/api/user"
                    });
                    put(HttpMethod.POST, new String[]{
                            "/api/user/register/admin",
                            "/api/book/**"
                    });
                    put(HttpMethod.PATCH, new String[]{
                            "/api/user/{id}/active_user",
                            "/api/book/activate/{id_book_stock}"
                    });
                    put(HttpMethod.DELETE, new String[]{
                            "/api/book/delete_book/{id_book_stock}",
                            "/api/user/delete_user/{id}/admin"
                    });
                }
            });
            put(PERMIT_ALL, new HashMap<HttpMethod, String []>(){
                {
                    put(HttpMethod.GET, new String []{
                            "/api/book/**"
                    });
                    put(HttpMethod.POST, new String[]{
                            "/api/user/register",
                            "/api/user/login"
                    });
                }
            });
        }
    };

    public static final String [] API_PERMISSION_GET_ADMIN = API_WHITELIST.get(ADMIN).get(HttpMethod.GET);
    public static final String [] API_PERMISSION_POST_ADMIN = API_WHITELIST.get(ADMIN).get(HttpMethod.POST);
    public static final String [] API_PERMISSION_PATCH_ADMIN = API_WHITELIST.get(ADMIN).get(HttpMethod.PATCH);
    public static final String [] API_PERMISSION_DELETE_ADMIN = API_WHITELIST.get(ADMIN).get(HttpMethod.DELETE);
    public static final String [] API_PERMISSION_PERMISSION_ALL_POST = API_WHITELIST.get(PERMIT_ALL).get(HttpMethod.POST);
    public static final String [] API_PERMISSION_PERMISSION_ALL_GET = API_WHITELIST.get(PERMIT_ALL).get(HttpMethod.GET);


}
