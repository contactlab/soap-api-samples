package com.contactlab.api.samples.managingsubscribers;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class RemoveSubscriber {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/removeSubscriber/
    public static void main(String[] args) {
        // Subscriber to remove
        final int subscriberId = 2;

        // Userdb the subscriber belongs to
        final int userDb = 2;

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE removeSubscriber passing token, userdb and subscriber
        boolean removed = service.removeSubscriber(token, userDb, subscriberId);

        System.out.println("Removal outcome: " + removed);
    }
}
