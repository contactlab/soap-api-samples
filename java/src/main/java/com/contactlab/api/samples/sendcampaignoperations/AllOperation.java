package com.contactlab.api.samples.sendcampaignoperations;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.Campaign;
import com.contactlab.api.ws.domain.EmailMessage;
import com.contactlab.api.ws.domain.SubscriberSource;
import com.contactlab.api.ws.domain.SubscriberSourceFilter;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

import static com.contactlab.api.samples.sendcampaignoperations.AddSubscriberSource.getSubscriberSource;
import static com.contactlab.api.samples.sendcampaignoperations.AddSubscriberSourceFilter.getSubscriberSourceFilter;
import static com.contactlab.api.samples.managingsubscribers.AddSubscribers.getSubscribers;
import static com.contactlab.api.samples.sendcampaignoperations.CreateCampaign.getCampaign;
import static com.contactlab.api.samples.sendcampaignoperations.CreateMessageModel.getEmailMessageModel;

public class AllOperation {

    /**
    * Step by Step operations to send a campaign:
    *
    * > addSubscriberSource
    * > addSubscribers
    * > addSubscriberSourceFilter
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
        SubscriberSource subscriberSourceCreated = service.addSubscriberSource(token, getSubscriberSource());

        System.out.println("Created subscriber source with identifier [" + subscriberSourceCreated.getIdentifier()+ "]");

        // > addSubscribers [ADD USERS]
        int created = service.addSubscribers(token, subscriberSourceCreated.getIdentifier(), getSubscribers());

        System.out.println("Created [" + created + "] subscribers");

        // > addSubscriberSourceFilter [CREATE FILTER]
        SubscriberSourceFilter subscriberSourceFilterCreated = service.addSubscriberSourceFilter(token,
                getSubscriberSourceFilter(subscriberSourceCreated.getIdentifier()));

        System.out.println("Created subscriber source filter with id [" + subscriberSourceFilterCreated.getIdentifier() + "]");

        // > createMessageModel [CREATE MESSAGE MODEL]
        EmailMessage messageModelCreated = (EmailMessage) service.createMessageModel(token,
                getEmailMessageModel(subscriberSourceCreated.getIdentifier(), subscriberSourceFilterCreated.getIdentifier()));

        System.out.println("Created message model with id [" + messageModelCreated.getModelIdentifier() + "]");

        // > createCampaign [CREATE CAMPAIGN FROM MESSAGE MODEL]
        messageModelCreated.setSubject("My first Message");
        messageModelCreated.setTextContent("Hi, I'm contactlab campaign message");
        messageModelCreated.setHtmlContent("<html><body><p>Hi,</p><p>I'm <b>contactlab</b> campaign message</p></body></html>");

        Campaign campaignCreated = service.createCampaign(token, getCampaign(messageModelCreated));

        System.out.println("Created campaign with id [" + campaignCreated.getIdentifier() + "]");

        // > sendCampaign [SEND CAMPAIGN]
        Integer requestId = service.sendCampaign(token, campaignCreated.getIdentifier());

        System.out.println("Send campaign with request id [" + requestId + "]");

    }

}
