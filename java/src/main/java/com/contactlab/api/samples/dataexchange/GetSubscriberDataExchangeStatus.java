package com.contactlab.api.samples.dataexchange;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.ActivityStatus;
import com.contactlab.api.ws.domain.AuthToken;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class GetSubscriberDataExchangeStatus {
    public static void main(String[] args) throws InterruptedException {
        final int dataExchangeConfigIdentifier = 4067;

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE addSubscribers passing token and data
        ActivityStatus status = service.getSubscriberDataExchangeStatus(token, dataExchangeConfigIdentifier);
        System.out.println("Dataexchange status: [" + status + "]");
    }
}
