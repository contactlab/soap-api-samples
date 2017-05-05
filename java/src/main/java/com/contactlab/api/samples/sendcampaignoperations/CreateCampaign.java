package com.contactlab.api.samples.sendcampaignoperations;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.Campaign;
import com.contactlab.api.ws.domain.EmailMessage;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class CreateCampaign {

    /* Example of Data Ids */
    /**
     * @see com.contactlab.api.samples.sendcampaignoperations.CreateMessageModel to cerate a message model
     */
    private static Integer MESSAGE_MODEL_ID = 2;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/createCampaign/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // prepare message from message model previously created
        EmailMessage message = (EmailMessage) service.getMessageModelById(token, MESSAGE_MODEL_ID);

        message.setSubject("My first Message");
        message.setTextContent("Hi, I'm contactlab campaign message");
        message.setHtmlContent("<html><body><p>Hi,</p><p>I'm <b>contactlab</b> campaign message</p></body></html>");

        // CALL WS SERVICE createCampaign passing token and data
        Campaign campaignCreated = service.createCampaign(token, getCampaign(message));

        System.out.println("Created campaign with id [" + campaignCreated.getIdentifier() + "]");
    }

    static Campaign getCampaign(EmailMessage message) {
        Campaign campaign = new Campaign();
        campaign.setName("Campaign1");
        campaign.setMessage(message);
        return campaign;
    }
}
