package com.contactlab.api.samples.immediatev2.send.withoutsubscriberid;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.SendImmediateOptions;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;
import java.util.List;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class sendImmediateByCampaignAliasToSubscriber {

    /* Example of Data Ids */
    private static String IMMEDIATE_CAMPAIGN_ALIAS = "Campaign_Alias";

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/sendImmediateByCampaignAliasToSubscriber/
    public static void main(String[] args) {

        // prepare Subscriber data
        Subscriber subscriber = new Subscriber();

        List<SubscriberAttribute> attributes = subscriber.getAttributes();

        attributes.add(createAttribute("RECIPIENT", "m.rossi@test.com"));// mandatory attribute
        attributes.add(createAttribute("fistName", "Mario"));
        attributes.add(createAttribute("lastName", "Rossi"));

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // CALL WS SERVICE createCampaign passing token and data
        String campaignUuid = service.sendImmediateByCampaignAliasToSubscriber(
                API_KEY, USER_KEY, IMMEDIATE_CAMPAIGN_ALIAS, subscriber, new SendImmediateOptions());

        System.out.println("Send immediate campaign by alias with id [" + campaignUuid + "]");
    }

    private static SubscriberAttribute createAttribute(String key, Object value) {
        SubscriberAttribute attribute = new SubscriberAttribute();
        attribute.setKey(key);
        attribute.setValue(value);
        return attribute;
    }

}
