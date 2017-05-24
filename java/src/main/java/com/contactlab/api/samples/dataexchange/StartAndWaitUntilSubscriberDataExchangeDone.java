package com.contactlab.api.samples.dataexchange;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.ActivityStatus;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.DetailedRequestStatus;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

/** Complete DataExchange example in which a dataexchange is started, its status is monitored until it's non-transient,
 *  and then the operation outcome is printed.
 */
public class StartAndWaitUntilSubscriberDataExchangeDone {

    public static void main(String[] args) throws InterruptedException {
        final int dataExchangeConfigIdentifier = 4067;

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE startSubscriberDataExchange passing token and dataExchangeConfigIdentifier
        service.startSubscriberDataExchange(token, dataExchangeConfigIdentifier);
        DetailedRequestStatus status = null;
        while (status == null
                || status.getActivityStatus() == ActivityStatus.INREQUEST
                || status.getActivityStatus() == ActivityStatus.RUNNING
                || status.getActivityStatus() == ActivityStatus.RETRY) {
            status = service.getSubscriberDataExchangeDetailedStatus(token, dataExchangeConfigIdentifier);
            System.out.println("Dataexchange status: [" + status.getActivityStatus() + "]");


            Thread.sleep(2000);
        }

        System.out.println("Deleted: [" + status.getActivityDetails().getDeleted() + "]");
        System.out.println("Inserted: [" + status.getActivityDetails().getInserted() + "]");
        System.out.println("Updated: [" + status.getActivityDetails().getUpdated() + "]");
        System.out.println("Not deleted: [" + status.getActivityDetails().getNotDeleted() + "]");
        System.out.println("Not inserted: [" + status.getActivityDetails().getNotInserted() + "]");
        System.out.println("Not updated: [" + status.getActivityDetails().getNotUpdated() + "]");
    }

}
