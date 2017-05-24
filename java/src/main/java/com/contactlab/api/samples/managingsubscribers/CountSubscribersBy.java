package com.contactlab.api.samples.managingsubscribers;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.LookupMatchingMode;
import com.contactlab.api.ws.domain.SubscriberAttributeFilter;

import java.util.Collections;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class CountSubscribersBy {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/countSubscribersBy/
    public static void main(String[] args) {
        // Userdb of which count the subscribers
        final int sourceIdentifier = 2;

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        //Search parameters
        SubscriberAttributeFilter attributeFilter = new SubscriberAttributeFilter();
        attributeFilter.setAttributeName("EMAIL");
        attributeFilter.setAttributeValue("%rossi%");
        attributeFilter.setLookupMatchingMode(LookupMatchingMode.LIKE);

        // CALL WS SERVICE countSubscribersBy passing token, sourceIdentifier and subscriberAttributeFilter
        final int count =
                service.countSubscribersBy(token, sourceIdentifier, null, Collections.singletonList(attributeFilter), null, false);

        System.out.println("Matching subscribers: " + count);
    }
}
