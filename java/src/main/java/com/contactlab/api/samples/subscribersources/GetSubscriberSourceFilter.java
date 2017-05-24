package com.contactlab.api.samples.subscribersources;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.SubscriberSourceFilter;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class GetSubscriberSourceFilter {
    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/getSubscriberSourceFilter/
    public static void main(String[] args) throws InterruptedException {
        // Identifier of the filter to retrieve. Customize this value to fit your needs
        final int filterIdentifier = 3;

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE getSubscriberSourceFilter passing token and filterIdentifier
        SubscriberSourceFilter subscriberSourceFilter = service.getSubscriberSourceFilter(token, filterIdentifier);

        // Output
        System.out.println("    Content: " + subscriberSourceFilter.getContent());
        System.out.println(" Extensions: " + subscriberSourceFilter.getExtensions());
        System.out.println("       Name: " + subscriberSourceFilter.getName());
    }
}
