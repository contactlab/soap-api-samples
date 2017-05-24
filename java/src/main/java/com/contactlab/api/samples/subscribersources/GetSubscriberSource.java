package com.contactlab.api.samples.subscribersources;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.SubscriberSource;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class GetSubscriberSource {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/getSubscriberSource/
    public static void main(String[] args) throws InterruptedException {
        // Source identifier to retrieve. Customize this value to fit your needs
        final int sourceIdentifier = 2;


        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE getSubscriberSource passing token and sourceID
        SubscriberSource subscriberSource = service.getSubscriberSource(token, sourceIdentifier);

        // Output
        System.out.println("    Charset: " + subscriberSource.getCharset());
        System.out.println("Description: " + subscriberSource.getDescription());
        System.out.println("       Name: " + subscriberSource.getName());
    }
}
