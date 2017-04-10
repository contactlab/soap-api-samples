package com.contactlab.api.samples.managingsubscribers;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;

public class AddSubscriber {

    // REQUIRED

    private final String apiKey = "<< api key >>";
    private final String userKey = "<< user key >>";
    private final int subscriberSourceId = 0;
    private String attributeKey = "<< key >>"; // Subscriber source's field name
    private String attributeValue = "<< value >>"; // Subscriber source's field value

    // Other variables

    private ClabService clabService;
    private AuthToken authToken;
    private Subscriber subscriberToBeAdded;


    public static void main(String[] args) {
        new AddSubscriber();
    }

    public AddSubscriber() {
        // init variables
        startVariables();

        // adds a subscriber to a subscriber source
        Subscriber createdSubscriber = addSubscriber(subscriberToBeAdded);

        if (createdSubscriber != null)
        {
            System.out.println("Subscriber [" + createdSubscriber.getIdentifier()
                    + "] was successfully added to userDB [" + subscriberSourceId + "]");
        }

        // invalidateToken
        clabService.invalidateToken(authToken);

    }

    private void startVariables() {

        this.clabService = new ClabService_Service().getClabServicePort();

        // borrow token
        this.authToken = clabService.borrowToken(apiKey, userKey);

        /* Initialize a new Subscriber */
        this.subscriberToBeAdded = new Subscriber();

		/* Add the EMAIL field to Subscriber's attributes. */
		/*
		 * Only EMAIL field is mandatory, but you can set an attribute for each
		 * field in the specified subscriber source.
		 */
        SubscriberAttribute subscriberAttributeEmail = new SubscriberAttribute();
        subscriberAttributeEmail.setKey(attributeKey);
        subscriberAttributeEmail.setValue(attributeValue);
        subscriberToBeAdded.getAttributes().add(subscriberAttributeEmail);
    }

    private Subscriber addSubscriber(Subscriber subscriber)
    {
        Subscriber addedSubscriber = null;
        try {
            addedSubscriber = this.clabService.addSubscriber(authToken, subscriberSourceId, subscriber);
        } catch (Exception e) {
            System.err.println("Error while adding the Subscriber ["
                    + (subscriber.getAttributes().get(0)).getValue() + "]");
            e.printStackTrace();
        }

        return addedSubscriber;
    }
}
