package com.contactlab.api.samples.immediatev2.managecampaign;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.Campaign;
import com.contactlab.api.ws.domain.CampaignType;
import com.contactlab.api.ws.domain.EmailMessage;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class CreateImmediateCampaign {

    /* Example of Data Ids */
    private static Integer MESSAGE_MODEL_ID = 5;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/createCampaign/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // prepare message from message model previously created
        EmailMessage message = (EmailMessage) service.getMessageModelById(token, MESSAGE_MODEL_ID);

        message.setSubject("My first Immediate Message");
        message.setTextContent("Hi ${firstName}$ ${lastName}$, I'm contactlab immediate campaign message");
        message.setHtmlContent("<html><body><p>Hi ${firstName}$ ${lastName}$,</p><p>I'm <b>contactlab</b> immediate campaign message</p></body></html>");

        // CALL WS SERVICE createCampaign passing token and data
        Campaign campaignCreated = service.createCampaign(token, getImmediateCampaign(message));

        System.out.println("Created immediate campaign with id [" + campaignCreated.getIdentifier() + "]");
    }

    private static Campaign getImmediateCampaign(EmailMessage message) {
        Campaign campaign = new Campaign();
        campaign.setName("Immediate Campaign1");
        campaign.setAlias("Campaign_Alias");
        campaign.setModifier(CampaignType.IMMEDIATE);
        campaign.setMessage(message);
        return campaign;
    }

}
