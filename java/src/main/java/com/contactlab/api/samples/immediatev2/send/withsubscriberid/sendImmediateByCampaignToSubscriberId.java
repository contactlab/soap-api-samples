package com.contactlab.api.samples.immediatev2.send.withsubscriberid;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.Campaign;
import com.contactlab.api.ws.domain.CampaignType;
import com.contactlab.api.ws.domain.Charset;
import com.contactlab.api.ws.domain.EmailMessage;
import com.contactlab.api.ws.domain.SendImmediateOptions;
import com.contactlab.api.ws.domain.Sender;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class sendImmediateByCampaignToSubscriberId {

    /* Example of Data Ids */
    private static Integer SUBSCRIBER_SOURCE_ID = 3;
    private static Integer MESSAGE_MODEL_ID = 5;
    private static Integer SUBSCRIBER_ID = 2;

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/sendImmediateByCampaignToSubscriberId/
    public static void main(String[] args) {

        // prepare message from message model previously created
        EmailMessage message = new EmailMessage();

        message.setModelIdentifier(MESSAGE_MODEL_ID);

        Sender sender = new Sender();
        sender.setName("Sender");
        sender.setEmail("noreply@sender.com");
        sender.setReplyTo("noreply@sender.com"); // mandatory field for sendimmediate
        message.setSender(sender);

        message.setCharset(Charset.UTF_8); // mandatory field
        message.setSubject("My first Immediate Message with subscriber id");
        message.setTextContent("Hi ${firstName}$, I'm contactlab immediate campaign message with subscriber id");
        message.setHtmlContent("<html><body><p>Hi ${firstName}$,</p><p>I'm <b>contactlab</b> immediate campaign message with subscriber id</p></body></html>");

        // prepare campaign
        Campaign campaign = new Campaign();
        campaign.setName("Immediate Campaign1");
        campaign.setAlias("Campaign_Alias");
        campaign.setModifier(CampaignType.IMMEDIATE);
        campaign.setMessage(message);

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // CALL WS SERVICE createCampaign passing token and data
        String campaignUuid = service.sendImmediateByCampaignToSubscriberId(
                API_KEY, USER_KEY, campaign, SUBSCRIBER_SOURCE_ID, SUBSCRIBER_ID, new SendImmediateOptions());

        System.out.println("Send immediate campaign with id [" + campaignUuid + "]");
    }
}
