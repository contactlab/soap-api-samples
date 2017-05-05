package com.contactlab.api.samples.sendcampaignoperations;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.EmailMessage;
import com.contactlab.api.ws.domain.Message;
import com.contactlab.api.ws.domain.Recipients;
import com.contactlab.api.ws.domain.Sender;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class CreateMessageModel {

    /* Example of Data Ids */
    /**
     * @see com.contactlab.api.samples.sendcampaignoperations.AddSubscriberSource to cerate subscriberSource with desired properties
     */
    private static Integer SUBSCRIBER_SOURCE_ID = 3;
    /**
     * @see com.contactlab.api.samples.sendcampaignoperations.AddSubscriberSourceFilter to cerate subscriberSourceFilter with desired matchs
     */
    private static Integer SUBSCRIBER_SOURCE_FILTER_ID = 2;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/createMessageModel/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE createMessageModel passing token and data
        Message messageModelCreated = service.createMessageModel(token,
                getEmailMessageModel(SUBSCRIBER_SOURCE_ID, SUBSCRIBER_SOURCE_FILTER_ID));

        System.out.println("Created message model with id [" + messageModelCreated.getModelIdentifier() + "]");
    }

    static EmailMessage getEmailMessageModel(Integer subscriberSourceId, Integer subscriberSourceFilterId) {
        Recipients recipient = new Recipients();
        recipient.setSubscriberSourceIdentifier(subscriberSourceId);
        recipient.setSubscriberSourceFilterIdentifier(subscriberSourceFilterId);

        Sender sender = new Sender();
        sender.setName("Sender");
        sender.setEmail("noreply@sender.com");
        sender.setReplyTo("noreply@sender.com"); // mandatory for sendimmediate

        EmailMessage message = new EmailMessage();

        message.setRecipients(recipient);
        message.setSender(sender);
        message.setPrefAttachmentCount(0);
        message.setMinAttachmentCount(0);
        message.setMaxAttachmentCount(0);
        return message;
    }
}
