package com.contactlab.api.samples.immediatev2.send.withsubscriberid;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.Attachment;
import com.contactlab.api.ws.domain.MimeType;
import com.contactlab.api.ws.domain.SendImmediateOptions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class sendImmediateByCampaignIdToSubscriberId {

    /* Example of Data Ids */
    private static Integer SUBSCRIBER_SOURCE_ID = 3;
    private static Integer CAMPAIGN_ID = 8;
    private static Integer SUBSCRIBER_ID = 2;

    private static String FILEPATH = "/path/to/your/file.txt";

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/sendImmediateByCampaignIdToSubscriberId/
    public static void main(String[] args) {

        SendImmediateOptions sendImmediateOptions = new SendImmediateOptions();

        // add attachment file
        List<Attachment> campaignAttachments = sendImmediateOptions.getCustomAttachments();
        campaignAttachments.add(getAttachmentFromPath(FILEPATH));

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // CALL WS SERVICE createCampaign passing token and data
        String campaignUuid = service.sendImmediateByCampaignIdToSubscriberId(
                API_KEY, USER_KEY, CAMPAIGN_ID, SUBSCRIBER_SOURCE_ID, SUBSCRIBER_ID, sendImmediateOptions);

        System.out.println("Send immediate campaign with id [" + campaignUuid + "]");
    }

    private static Attachment getAttachmentFromPath(String attachmentPath) {

        Attachment attachment = null;
        byte[] attachmentData;

        try {
            File file = new File(attachmentPath);

            attachmentData = Files.readAllBytes(file.toPath());

            attachment = new Attachment();
            attachment.setContent(attachmentData);
            attachment.setMimeType(MimeType.BINARY);
            attachment.setName(file.getName());

        } catch (IOException e) {
            System.out.println("Error while opening or reading: " + attachmentPath);
            e.printStackTrace();
        }

        return attachment;
    }

}
