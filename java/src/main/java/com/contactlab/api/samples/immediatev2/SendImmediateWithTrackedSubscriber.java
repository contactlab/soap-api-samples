package com.contactlab.api.samples.immediatev2;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.SendImmediateOptions;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;

public class SendImmediateWithTrackedSubscriber {
    private final String apiKey = "<< api key >>";
    private final String userKey = "<< user key >>";
    private int campaignId = 0;

    private ClabService clabService;

    public static void main(String[] args) {
        new SendImmediateByCampaignIdToSubscriberIdWithAttachments();
    }

    public SendImmediateWithTrackedSubscriber() {
        // init variables
        startVariables();

        // send immediate message with a trackable subscriber
        sendImmediateWithTrackableSubscriber();

        // send immediate message with NOT a trackable subscriber
        sendImmediate_NOT_TrackableSubscriber_1();
        sendImmediate_NOT_TrackableSubscriber_2();
    }

    // In this case, the USER_PK attribute is set, but the flag userUserPk is not passed and so the default value is false
    private void sendImmediate_NOT_TrackableSubscriber_2() {
        final Subscriber subscriber = initSubscriber("someone@exemple.com", 12);
        // Add some other attribute
        SendImmediateOptions sendImmediateOptions = new SendImmediateOptions();
        sendImmediateOptions.setUseUserPk(false); // If false is not rracked

        this.clabService.sendImmediateByCampaignIdToSubscriber(
                apiKey,
                userKey,
                campaignId,
                subscriber,
                null);
    }


    // In this case, the USER_PK attribute is set, but the flag userUserPk is false (or not set)
    private void sendImmediate_NOT_TrackableSubscriber_1() {
        final Subscriber subscriber = initSubscriber("someone@exemple.com", 12);
        // Add some other attribute
        SendImmediateOptions sendImmediateOptions = new SendImmediateOptions();
        sendImmediateOptions.setUseUserPk(false); // If false is not rracked

        this.clabService.sendImmediateByCampaignIdToSubscriber(
                apiKey,
                userKey,
                campaignId,
                subscriber,
                sendImmediateOptions);
    }

    private void sendImmediateWithTrackableSubscriber() {
        final Subscriber subscriber = initSubscriber("someone@exemple.com", 12);
        // Add some other attribute
        SendImmediateOptions sendImmediateOptions = new SendImmediateOptions();
        sendImmediateOptions.setUseUserPk(true); // Mandatory to be tracked

        this.clabService.sendImmediateByCampaignIdToSubscriber(
                apiKey,
                userKey,
                campaignId,
                subscriber,
                sendImmediateOptions);
    }

    private Subscriber initSubscriber(String recipientAddress, Integer userPkId) {
        // Minimum attribute in order to be trackable
        Subscriber subscriber = new Subscriber();
        SubscriberAttribute  recipient = new SubscriberAttribute();
        recipient.setKey("RECIPIENT"); // mandatory in order to be send
        recipient.setKey(recipientAddress);
        subscriber.getAttributes().add(recipient);

        if (userPkId != null) {
            SubscriberAttribute userPk = new SubscriberAttribute();
            recipient.setKey("USER_PK"); // mandatory in order to be tracked
            recipient.setKey(userPkId.toString());
            subscriber.getAttributes().add(userPk);
        }

        return subscriber;
    }

    private void startVariables() {

        this.clabService = new ClabService_Service().getClabServicePort();
    }
}
