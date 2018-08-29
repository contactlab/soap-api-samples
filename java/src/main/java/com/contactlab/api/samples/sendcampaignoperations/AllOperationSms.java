package com.contactlab.api.samples.sendcampaignoperations;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.*;

import java.util.List;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class AllOperationSms {

    /**
    * Step by Step operations to send a campaign:
    *
    * > addSubscriberSource
    * > addSubscribers
    * > createMessageModel
    * > createCampaign
    * > sendCampaign
    *
    **/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // > addSubscriberSource [CREATE USER DB]
        SubscriberSource subscriberSourceCreated = service.addSubscriberSource(token, getSmsSubscriberSource());

        System.out.println("Created subscriber source with identifier [" + subscriberSourceCreated.getIdentifier()+ "]");

        // > addSubscribers [ADD USERS]
        int created = service.addSubscribers(token, subscriberSourceCreated.getIdentifier(), getSmsSubscribers());

        System.out.println("Created [" + created + "] subscribers");

        // > createMessageModel [CREATE MESSAGE MODEL]
        TextMessage messageModelCreated = (TextMessage) service.createMessageModel(token,
                getSmsMessageModel(subscriberSourceCreated.getIdentifier()));

        System.out.println("Created message model with id [" + messageModelCreated.getModelIdentifier() + "]");

        // > createCampaign [CREATE CAMPAIGN FROM MESSAGE MODEL]
        messageModelCreated.setContent("Sms Message");

        Campaign campaign = new Campaign();
        campaign.setName("Campaign1");
        campaign.setMessage(messageModelCreated);

        Campaign campaignCreated = service.createCampaign(token, campaign);

        System.out.println("Created campaign with id [" + campaignCreated.getIdentifier() + "]");

        // > sendCampaign [SEND CAMPAIGN]
        Integer requestId = service.sendCampaign(token, campaignCreated.getIdentifier());

        System.out.println("Send campaign with request id [" + requestId + "]");

    }

    private static TextMessage getSmsMessageModel(Integer subscriberSourceId) {
        Recipients recipient = new Recipients();
        recipient.setSubscriberSourceIdentifier(subscriberSourceId);

        // set filter -> recipient.setSubscriberSourceFilterIdentifier(subscriberSourceFilterId);

        Sender sender = new Sender();
        sender.setName("Sender");
        // SET SENDER PHONE FIELD
        sender.setPhone("XXXXXX");

        TextMessage message = new TextMessage();

        message.setRecipients(recipient);
        message.setSender(sender);
        return message;
    }

    private static SubscriberSource getSmsSubscriberSource() {
        SubscriberSource subscriberSource = new SubscriberSource();
        subscriberSource.setName("Source1");
        List<SubscriberSourceField> fields = subscriberSource.getFields();

        SubscriberSourceField firstNameField = new SubscriberSourceField();
        firstNameField.setName("firstName");
        firstNameField.setType(Field.STRING);

        SubscriberSourceField phoneNameField = new SubscriberSourceField();
        phoneNameField.setName("phone");
        phoneNameField.setType(Field.STRING);

        // SET PHONE FIELD
        phoneNameField.setPhoneField(true);

        fields.add(firstNameField);
        fields.add(phoneNameField);
        return subscriberSource;
    }

    private static Subscribers getSmsSubscribers() {
        Subscribers subscribers = new Subscribers();

        List<Subscriber> subscriberItems = subscribers.getCurrentPageItems();

        Subscriber subscriber = new Subscriber();

        List<SubscriberAttribute> attributes = subscriber.getAttributes();

        SubscriberAttribute firstNameAttribute = new SubscriberAttribute();
        firstNameAttribute.setKey("firstName");
        firstNameAttribute.setValue("Mario");

        SubscriberAttribute phoneAttribute = new SubscriberAttribute();
        phoneAttribute.setKey("phone");

        // SET SUBSCRIBER PHONE FIELD
        phoneAttribute.setValue("XXXXX");

        attributes.add(firstNameAttribute);
        attributes.add(phoneAttribute);

        subscriberItems.add(subscriber);

        return subscribers;
    }

}
