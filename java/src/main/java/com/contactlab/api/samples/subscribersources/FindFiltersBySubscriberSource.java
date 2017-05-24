package com.contactlab.api.samples.subscribersources;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.LookupPreferences;
import com.contactlab.api.ws.domain.LookupSortingMode;
import com.contactlab.api.ws.domain.SubscriberSourceFilter;
import com.contactlab.api.ws.domain.SubscriberSourceFilters;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class FindFiltersBySubscriberSource {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/findFiltersBySubscriberSource/
    public static void main(String[] args) throws InterruptedException {
        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // Retrieving result from oldest to latest
        LookupPreferences lookupPreferences = new LookupPreferences();
        lookupPreferences.setSortingMode(LookupSortingMode.ASCENDING);

        // Cycling on alle the pages to get all the results
        SubscriberSourceFilters subscriberSourceFilters;
        do  {
            // CALL WS SERVICE addSubscribers passing token and data
            subscriberSourceFilters = service.findFiltersBySubscriberSource(token, 2, lookupPreferences);
            for (SubscriberSourceFilter subscriberSourceFilter : subscriberSourceFilters.getCurrentPageItems()) {
                System.out.println();
                System.out.println("         Id: " + subscriberSourceFilter.getIdentifier());
                System.out.println("    Content: " + subscriberSourceFilter.getContent());
                System.out.println("       Name: " + subscriberSourceFilter.getName());
                System.out.println("       Type: " + subscriberSourceFilter.getType());
            }

            //Increment page number for next cycle
            lookupPreferences.setPageNumber(subscriberSourceFilters.getCurrentPageNumber() +1);

        // Stop when currentPageNumber is less or equal than number of pages
        } while (subscriberSourceFilters.getCurrentPageNumber()
                .compareTo(subscriberSourceFilters.getMaxPageNumber()) <= 0);

    }
}
