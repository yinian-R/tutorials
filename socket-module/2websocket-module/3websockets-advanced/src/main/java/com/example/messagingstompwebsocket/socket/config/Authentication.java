package com.example.messagingstompwebsocket.socket.config;

import java.security.Principal;

public class Authentication implements Principal {
    
    private String token;
    
    public Authentication(String token) {
        this.token = token;
    }
    
    /**
     * Returns the name of this principal.
     *
     * @return the name of this principal.
     */
    @Override
    public String getName() {
        // parse token
        return token;
    }
    
}
