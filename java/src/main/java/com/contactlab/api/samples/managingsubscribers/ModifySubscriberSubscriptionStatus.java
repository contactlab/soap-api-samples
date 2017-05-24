package com.contactlab.api.samples.managingsubscribers;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class ModifySubscriberSubscriptionStatus {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/modifySubscriberSubscriptionStatus/
    public static void main(String[] args) {
        // Customize this values to fit your needs
        final String webFormCode = "230004001355";
        final int subscriberIdentifier = 1;
        final boolean isSubscribed = true;

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE modifySubscriberSubscriptionStatus passing token, webFormCode, subscriberIdentifier and the new status to set
        service.modifySubscriberSubscriptionStatus(token, webFormCode, subscriberIdentifier, isSubscribed);
    }
}
