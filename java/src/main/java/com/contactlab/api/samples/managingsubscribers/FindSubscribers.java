package com.contactlab.api.samples.managingsubscribers;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.LookupMatchingMode;
import com.contactlab.api.ws.domain.LookupPreferences;
import com.contactlab.api.ws.domain.LookupSortingMode;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;
import com.contactlab.api.ws.domain.Subscribers;
import java.util.ArrayList;
import java.util.List;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;
import static com.contactlab.api.samples.managingsubscribers.AddSubscriber.createAttribute;

public class FindSubscribers {

    /* Example of Data Ids */
    private static Integer SUBSCRIBER_SOURCE_ID = 6;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/findSubscribers/
    public static void main(String[] args) {

        LookupPreferences lookupPrefs = new LookupPreferences();

        lookupPrefs.setMatchingMode(LookupMatchingMode.EQUALS);
        lookupPrefs.setSortingMode(LookupSortingMode.ASCENDING);
        lookupPrefs.setPageNumber(1);

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        List<Subscriber> subscribersList = new ArrayList<>();

        // execute findSubscriber in loop for pagination
        findSubscribersExecution(service, token, lookupPrefs, createAttribute("lastName", "Rossi"), subscribersList);

        for (Subscriber subscriber : subscribersList) {
            System.out.println("Find subscriber with id [" + subscriber.getIdentifier() + "]");
        }
    }

    private static void findSubscribersExecution(ClabService service, AuthToken token, LookupPreferences lookupPrefs,
                                                      SubscriberAttribute subscriberAttribute, List<Subscriber> subscribersList) {

        Subscribers subscribers = service.findSubscribers(
                token,
                SUBSCRIBER_SOURCE_ID,
                subscriberAttribute,
                lookupPrefs);
        
        if (!subscribers.getCurrentPageItems().isEmpty()) {
            subscribersList.addAll(subscribers.getCurrentPageItems());

            // increment page number
            lookupPrefs.setPageNumber(lookupPrefs.getPageNumber()+1);

			// every page has only 15 items. Use loop for all the subscribers in source
            findSubscribersExecution(service, token, lookupPrefs, subscriberAttribute, subscribersList);
        }
    }
}
