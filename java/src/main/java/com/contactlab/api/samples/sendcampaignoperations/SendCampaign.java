package com.contactlab.api.samples.sendcampaignoperations;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class SendCampaign {

    /* Example of Data Ids */
    /**
     * @see com.contactlab.api.samples.sendcampaignoperations.CreateCampaign to cerate a campaign
     */
    public static Integer CAMPAIGN_ID = 3;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/sendCampaign/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE sendCampaign passing token and data
        Integer requestId = service.sendCampaign(token, CAMPAIGN_ID);

        System.out.println("Send campaign with request id [" + requestId + "]");
    }

}
