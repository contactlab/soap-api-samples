package com.contactlab.api.samples.managingsubscribers;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class GetSubscriber {
    private final static int SAMPLE_USERDB_ID = 2;
    private final static int SAMPLE_SUBSCRIBER_ID = 1;

    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE addSubscribers passing token and data
        Subscriber subscriber = service.getSubscriber(token, SAMPLE_USERDB_ID, SAMPLE_SUBSCRIBER_ID);

        // Output
        System.out.println("Subscriber attributes:");
        for (SubscriberAttribute attribute : subscriber.getAttributes()) {
            System.out.println(String.format("%15s : %s", attribute.getKey(), attribute.getValue()));
        }
    }
}
