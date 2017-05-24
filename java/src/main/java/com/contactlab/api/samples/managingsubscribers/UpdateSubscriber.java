package com.contactlab.api.samples.managingsubscribers;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class UpdateSubscriber {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/updateSubscriber/
    public static void main(String[] args) {
        // Parameters to customize
        final int userDbId = 2;
        final int subscriberIdToEdit = 1;

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // Initializing subscriber
        Subscriber subscriber = new Subscriber();
        subscriber.setIdentifier(subscriberIdToEdit);

        // Define attributes to update; attributes you don't pass in the list will remain unchanged
        SubscriberAttribute newEmail = new SubscriberAttribute();
        newEmail.setKey("EMAIL");
        newEmail.setValue("example@contactlab.com");
        subscriber.getAttributes().add(newEmail);

        // CALL WS SERVICE updateSubscriber passing token, userdb and subscriber
        Subscriber editedSubscriber = service.updateSubscriber(token, userDbId, subscriber);

        // Output
        System.out.println("Subscriber attributes after edit:");
        for (SubscriberAttribute attribute : editedSubscriber.getAttributes()) {
            System.out.println(String.format("%15s : %s", attribute.getKey(), attribute.getValue()));
        }
    }
}
