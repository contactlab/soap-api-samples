package com.contactlab.api.samples.sendcampaignoperations;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.SubscriberSourceFilter;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class AddSubscriberSourceFilter {

    /* Example of Data Ids */
    /**
     * @see com.contactlab.api.samples.sendcampaignoperations.AddSubscriberSource to cerate subscriberSource with desired properties
     */
    private static Integer SUBSCRIBER_SOURCE_ID = 3;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/addSubscriberSourceFilter/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE addSubscriberSourceFilter passing token and data
        SubscriberSourceFilter subscriberSourceFilterCreated = service.addSubscriberSourceFilter(token,
                getSubscriberSourceFilter(SUBSCRIBER_SOURCE_ID));

        System.out.println("Created subscriber source filter with id [" + subscriberSourceFilterCreated.getIdentifier() + "]");
    }

    static SubscriberSourceFilter getSubscriberSourceFilter(Integer subscriberSourceId) {
        SubscriberSourceFilter subscriberSourceFilter = new SubscriberSourceFilter();
        subscriberSourceFilter.setName("Filter1");
        subscriberSourceFilter.setSubscriberSourceIdentifier(subscriberSourceId);
        subscriberSourceFilter.setContent("enabled = 1");
        return subscriberSourceFilter;
    }


}
