package com.contactlab.api.samples.dataexchange;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.DetailedRequestStatus;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

/**
 * This example calls the method getSubscriberDataExchangeDetailedStatus
 * to get the status of the last dataexchange request made by the customer
 * on the given configuration ID
 */
public class GetSubscriberDataExchangeDetailedStatus {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/getSubscriberDataExchangeDetailedStatus/
    public static void main(String[] args) throws InterruptedException {
        // Data exchange configuration identifier as found in configure -> automation -> DataExchange® on SEND
        final int dataExchangeConfigIdentifier = 4067;

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE addSubscribers passing token and dataExchangeConfigIdentifier
        DetailedRequestStatus status = service.getSubscriberDataExchangeDetailedStatus(token, dataExchangeConfigIdentifier);
        System.out.println("Dataexchange status: [" + status .getActivityStatus()+ "]");

        // The activityDetails property contains the outcome of the last dataExchange request, if the latter is not
        // in a transient state
        if (status.getActivityDetails() != null) {
            System.out.println("            Deleted: [" + status.getActivityDetails().getDeleted() + "]");
            System.out.println("           Inserted: [" + status.getActivityDetails().getInserted() + "]");
            System.out.println("            Updated: [" + status.getActivityDetails().getUpdated() + "]");
            System.out.println("        Not deleted: [" + status.getActivityDetails().getNotDeleted() + "]");
            System.out.println("       Not inserted: [" + status.getActivityDetails().getNotInserted() + "]");
            System.out.println("        Not updated: [" + status.getActivityDetails().getNotUpdated() + "]");
        }
    }
}
