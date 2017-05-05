package com.contactlab.api.samples.sendcampaignoperations;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.Field;
import com.contactlab.api.ws.domain.SubscriberSource;
import com.contactlab.api.ws.domain.SubscriberSourceField;
import java.util.List;

import static com.contactlab.api.samples.ConfigProperties.API_KEY;
import static com.contactlab.api.samples.ConfigProperties.USER_KEY;

public class AddSubscriberSource {

    // https://explore.contactlab.com/developers/Contactsend/documentazione/SoapApi/addSubscriberSource/
    public static void main(String[] args) {

        // Prepare data to send
        SubscriberSource subscriberSource = getSubscriberSource();

        // Clab Service Initialization
        ClabService service = new ClabService_Service().getClabServicePort();

        // borrow token
        AuthToken token = service.borrowToken(API_KEY, USER_KEY);

        // CALL WS SERVICE addSubscriberSource passing token and data
        SubscriberSource subscriberSourceCreated = service.addSubscriberSource(token, subscriberSource);

        System.out.println("Created subscriber source with identifier [" + subscriberSourceCreated.getIdentifier()+ "]");

    }

    static SubscriberSource getSubscriberSource() {
        SubscriberSource subscriberSource = new SubscriberSource();
        subscriberSource.setName("Source1");
        List<SubscriberSourceField> fields = subscriberSource.getFields();

        /*
         * Create fields for subscriber source:
         *   > firstName (string)
         *   > lastName (string)
         *   > enabled (flag)
         *   > points (integer)
         *   > registrationDate (datetime)
         *   > email(string) setting emailField to true to identify email field
         */
        fields.add(createField("firstName", Field.STRING));
        fields.add(createField("lastName", Field.STRING));
        fields.add(createField("enabled", Field.FLAG));
        fields.add(createField("points", Field.INTEGER));
        fields.add(createField("registrationDate", Field.DATETIME));

        SubscriberSourceField emailField = createField("email", Field.STRING);
        emailField.setEmailField(true);

        fields.add(emailField);
        return subscriberSource;
    }

    private static SubscriberSourceField createField(String name, Field fieldType) {
        SubscriberSourceField sourceField = new SubscriberSourceField();
        sourceField.setName(name);
        sourceField.setType(fieldType);
        return sourceField;
    }
}
