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

public class AddSubscriber {

    /* Example of Data Ids */
    /**
     * @see com.contactlab.api.samples.sendcampaignoperations.AddSubscriberSource to cerate subscriberSource with desired properties
     */
    private static Integer SUBSCRIBER_SOURCE_ID = 6;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/addSubscriber/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE addSubscribers passing token and data
        Subscriber subscriberCreated = service.addSubscriber(token, SUBSCRIBER_SOURCE_ID,
                createSubscriber("Mario", "Rossi", "m.rossi@test.com",
                100, "2017-02-10 14:23:52", true));

        System.out.println("Created subscriber with id [" + subscriberCreated.getIdentifier() + "]");
    }

    public static Subscriber createSubscriber(String firstName, String lastName, String email,
                                               Integer points, String registrationDate, Boolean enabled) {
        Subscriber subscriber = new Subscriber();

        List<SubscriberAttribute> attributes = subscriber.getAttributes();

        attributes.add(createAttribute("firstName", firstName));
        attributes.add(createAttribute("lastName", lastName));
        attributes.add(createAttribute("email", email));
        attributes.add(createAttribute("points", points));
        attributes.add(createAttribute("registrationDate", registrationDate));
        attributes.add(createAttribute("enabled", enabled));

        return subscriber;
    }

    public static SubscriberAttribute createAttribute(String key, Object value) {
        SubscriberAttribute attribute = new SubscriberAttribute();
        attribute.setKey(key);
        attribute.setValue(value);
        return attribute;
    }

}
