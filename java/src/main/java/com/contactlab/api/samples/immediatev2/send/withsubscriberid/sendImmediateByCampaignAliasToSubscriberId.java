package com.contactlab.api.samples.immediatev2.send.withsubscriberid;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.SendImmediateOptions;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class sendImmediateByCampaignAliasToSubscriberId {

    /* Example of Data Ids */
    private static String IMMEDIATE_CAMPAIGN_ALIAS = "Campaign_Alias";
    private static Integer SUBSCRIBER_SOURCE_ID = 3;
    private static Integer SUBSCRIBER_ID = 2;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/sendImmediateByCampaignAliasToSubscriberId/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // CALL WS SERVICE createCampaign passing token and data
        String campaignUuid = service.sendImmediateByCampaignAliasToSubscriberId(
                API_KEY, USER_KEY, IMMEDIATE_CAMPAIGN_ALIAS, SUBSCRIBER_SOURCE_ID, SUBSCRIBER_ID, new SendImmediateOptions());

        System.out.println("Send immediate campaign with id [" + campaignUuid + "]");
    }
}
