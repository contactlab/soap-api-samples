package com.contactlab.api.samples.immediatev2.send.withoutsubscriberid;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.SendImmediateOptions;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;
import java.util.List;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;
import static com.contactlab.api.samples.managingsubscribers.AddSubscriber.createAttribute;

public class sendImmediateByCampaignToSubscriber {

    /* Example of Data Ids */
    private static Integer IMMEDIATE_CAMPAIGN_ID = 8;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/sendImmediateByCampaignIdToSubscriber/
    public static void main(String[] args) {

        // prepare Subscriber data
        Subscriber subscriber = new Subscriber();

        List<SubscriberAttribute> attributes = subscriber.getAttributes();

        attributes.add(createAttribute("RECIPIENT", "m.rossi@test.com"));// mandatory attribute
        attributes.add(createAttribute("firstName", "Mario"));
        attributes.add(createAttribute("lastName", "Rossi"));

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // CALL WS SERVICE createCampaign passing token and data
        String campaignUuid = service.sendImmediateByCampaignIdToSubscriber(
                API_KEY, USER_KEY, IMMEDIATE_CAMPAIGN_ID, subscriber, new SendImmediateOptions());

        System.out.println("Send immediate campaign with id [" + campaignUuid + "]");
    }

}
