package com.contactlab.api.samples.immediatev2.managecampaign;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class CancelCampaign {

    /* Example of Data Ids */
    private static Integer CAMPAIGN_ID = 7;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/cancelCampaign/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE createCampaign passing token and data
        service.cancelCampaign(token, CAMPAIGN_ID);

        System.out.println("Cancelled campaign with id [" + CAMPAIGN_ID + "]");
    }
}
