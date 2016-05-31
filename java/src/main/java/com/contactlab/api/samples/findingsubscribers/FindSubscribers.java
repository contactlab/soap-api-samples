package com.contactlab.api.samples.findingsubscribers;

import java.util.ArrayList;
import java.util.List;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.LookupMatchingMode;
import com.contactlab.api.ws.domain.LookupPreferences;
import com.contactlab.api.ws.domain.LookupSortingMode;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;
import com.contactlab.api.ws.domain.Subscribers;

public class FindSubscribers {

    // REQUIRED
	
    private String apiKey = << api key >>;
    private String userKey = << user key >>;
    
    private String attributeKey = << field_name >>; // userDB's field name to select
    private String attirbuteValue = << field_value >>; // userDB's field value to select, use "%%" for all users ( in LookupMatchingMode.LIKE )
    
    private int sourceIdentifier = << source_identifier >>; //userDB identifier
	
	//Optional
	
    private LookupMatchingMode matchingMode = LookupMatchingMode.LIKE;
    private LookupSortingMode sortingMode = LookupSortingMode.ASCENDING;
    private int pageNumber = 1;

	// Other variables

    private ClabService service;
    private AuthToken token;
    private Subscribers subscribers;
    private List<Subscriber> subscribersList;
    private SubscriberAttribute subscriberAttribute;
    private LookupPreferences lookupPrefs;
    
    
    public static void main(String[] args) {
        
        new FindSubscribers();

    }
    
    public FindSubscribers(){
        
		// init variables
		
        startVariables();

        // execute findSubscriber in loop
		
        findSubscribersExecution();
		
        for (Subscriber subscriber : this.subscribersList) {
            System.out.print(subscriber.getIdentifier());
        }
    }
    
    private void startVariables() {
        
        this.service = new ClabService_Service().getClabServicePort();
		
		// borrow token
        
        this.token = service.borrowToken(this.apiKey, this.userKey);

        this.lookupPrefs = new LookupPreferences();
        
        this.lookupPrefs.setMatchingMode(this.matchingMode);
        this.lookupPrefs.setSortingMode(this.sortingMode);
        this.lookupPrefs.setPageNumber(this.pageNumber);
        
        this.subscriberAttribute = new SubscriberAttribute();
        
        this.subscriberAttribute.setKey(attributeKey);
        this.subscriberAttribute.setValue(attirbuteValue);
        
        this.subscribersList = new ArrayList<Subscriber>();
    
    }
    
    private List<Subscriber> findSubscribersExecution() {
        
        this.subscribers = service.findSubscribers(
                this.token,
                this.sourceIdentifier,
                this.subscriberAttribute,
                this.lookupPrefs
            );
        
        if(!this.subscribers.getCurrentPageItems().isEmpty()){
            this.subscribersList.addAll(this.subscribers.getCurrentPageItems());            
            this.lookupPrefs.setPageNumber(++this.pageNumber);
			
			// every page has only 15 items. Use loop for all the subscribers in source
			
            this.subscribersList.addAll(findSubscribersExecution());
        }
        return this.subscribersList;
    }
}
