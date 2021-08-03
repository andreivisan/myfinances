package io.programminglife.myfinances.util.authentication;

import io.programminglife.myfinances.entity.Client;
import io.programminglife.myfinances.payload.SignupRequest;

public class AuthenticationUtil {

    public static Client signupRequestToClient(SignupRequest signupRequest) {
        Client client = new Client();

        client.setName(signupRequest.getName());
        client.setUsername(signupRequest.getUsername());
        client.setEmail(signupRequest.getEmail());

        return client;
    }
    
}