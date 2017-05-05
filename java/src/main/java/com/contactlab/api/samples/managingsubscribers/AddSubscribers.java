package com.contactlab.api.samples.managingsubscribers;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;
import com.contactlab.api.ws.domain.Subscribers;
import java.util.List;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;
import static com.contactlab.api.samples.managingsubscribers.AddSubscriber.createSubscriber;

public class AddSubscribers {

    /* Example of Data Ids */
    /**
     * @see com.contactlab.api.samples.sendcampaignoperations.AddSubscriberSource to cerate subscriberSource with desired properties
     */
    private static Integer SUBSCRIBER_SOURCE_ID = 6;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/addSubscribers/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE addSubscribers passing token and data
        int created = service.addSubscribers(token, SUBSCRIBER_SOURCE_ID, getSubscribers());

        System.out.println("Created [" + created + "] subscribers");
    }

    /* Data to send */
    public static Subscribers getSubscribers() {
        Subscribers subscribers = new Subscribers();

        List<Subscriber> subscriberItems = subscribers.getCurrentPageItems();

        subscriberItems.add(createSubscriber("Mario", "Rossi", "m.rossi@test.com",
                100, "2017-02-10 14:23:52", true));
        subscriberItems.add(createSubscriber("Carla", "Bruni", "c.bruni@test.com",
                150, "2017-03-05 14:53:02",false));
        subscriberItems.add(createSubscriber("Gianni", "Bianchi", "g.bianchi@test.com",
                200, "2017-01-20 16:24:12", true));

        return subscribers;
    }

}
