package com.contactlab.api.samples.subscribersources;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.LookupPreferences;
import com.contactlab.api.ws.domain.LookupSortingMode;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class RenameSubscriberSourceFilter {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/renameSubscriberSourceFilter/index.html
    public static void main(String[] args) throws InterruptedException {
        // Replace this value with the ID of the filter you want to rename
        final int filterIdentifier = 2;

        // Replace this value with the new name you want to give to the filter
        final String newName = "New filter name";

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // Borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // Retrieving result from oldest to latest
        LookupPreferences lookupPreferences = new LookupPreferences();
        lookupPreferences.setSortingMode(LookupSortingMode.ASCENDING);

        // CALL WS SERVICE addSubscribers passing token, filter identifier and new name
        boolean successful = service.renameSubscriberSourceFilter(token, filterIdentifier, newName);
        System.out.print("Rename successful: " + successful);
    }
}
