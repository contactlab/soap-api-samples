package com.contactlab.api.samples.immediatev2.managecampaign;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.DeliveryInformation;
import com.contactlab.api.ws.domain.AuthToken;
import java.util.List;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class GetDeliveryStatus {

    /* Example of Data Ids */
    private static String IMMEDIATE_CAMPAIGN_UUID = "0d2a79ce-8a60-44f2-b76f-b60267ec6030";

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/getDeliveryStatus/
    public static void main(String[] args) {

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // CALL WS SERVICE createCampaign passing token and data
        List<DeliveryInformation> informationList = service.getDeliveryStatus(API_KEY, USER_KEY, IMMEDIATE_CAMPAIGN_UUID);

        // status 3 => SENT see https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/DeliveryInformationStatus/ for further information)
        informationList.forEach(i -> System.out.println("Recipient: " + i.getRecipient() + " - status: " + i.getStatus()));

    }
}
