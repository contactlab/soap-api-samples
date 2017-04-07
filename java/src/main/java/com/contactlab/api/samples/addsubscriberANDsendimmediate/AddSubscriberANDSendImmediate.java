package com.contactlab.api.samples.addsubscriberANDsendimmediate;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.Attachment;
import com.contactlab.api.ws.domain.AuthToken;
import com.contactlab.api.ws.domain.MimeType;
import com.contactlab.api.ws.domain.SendImmediateOptions;
import com.contactlab.api.ws.domain.Subscriber;
import com.contactlab.api.ws.domain.SubscriberAttribute;


public class AddSubscriberANDSendImmediate
{
	private ClabService clabService;
	private String apiKey;
	private String userKey;
	private Integer sourceId;

	public static void main(String[] args)
	{
		/* Initialize the ClientManager (wraps the API calls) */
		AddSubscriberANDSendImmediate sampleManager = new AddSubscriberANDSendImmediate();

		/* Initialize a new Subscriber */
		Subscriber newSubscriber = new Subscriber();

		/* Add the EMAIL field to Subscriber's attributes. */
		/*
		 * Only EMAIL field is mandatory, but you can set an attribute for each
		 * field in the specified UserDB
		 */
		SubscriberAttribute subscriberAttributeEmail = new SubscriberAttribute();
		subscriberAttributeEmail.setKey("EMAIL");
		subscriberAttributeEmail.setValue("Subscriber's Email");
		newSubscriber.getAttributes().add(subscriberAttributeEmail);

		/* Add the Subscriber to userDB */
		Subscriber campaignReceiver = sampleManager.addSubscriber(newSubscriber);

		/* Set the IMMEDIATE Campaign's identifier you want to send */
		Integer immediateCampaignIdentifier = 0;
		/*
		 * Send an IMMEDIATE Campaign with no attachments to a specific
		 * Subscriber (the previously inserted one, in this case)
		 */
		sampleManager.sendImmediate(immediateCampaignIdentifier, campaignReceiver);

		/* Fill a list of file-paths to send with the Campaign */
		List<String> attachmentsPaths = new LinkedList<>();
		attachmentsPaths.add("Path of the file to be included as attachment");

		/* Send an IMMEDIATE Campaign with the declared attachments */
		sampleManager.sendImmediateWithAttachment(immediateCampaignIdentifier, campaignReceiver, attachmentsPaths);

	}

	public AddSubscriberANDSendImmediate()
	{
		this.clabService = new ClabService_Service().getClabServicePort();

		this.apiKey = "Your APIKey";
		this.userKey = "Your UserKey";

		/* Set the target UserDB's Identifier */
		this.sourceId = 0;
	}

	public Subscriber addSubscriber(Subscriber subscriber)
	{
		Subscriber addedSub = null;
		AuthToken token = this.clabService.borrowToken(this.apiKey, this.userKey);
		try
		{
			addedSub = this.clabService.addSubscriber(token, this.sourceId, subscriber);
		} catch (Exception e)
		{
			System.err.println("Error while adding the Subscriber [" + ((SubscriberAttribute) subscriber.getAttributes().get(0)).getValue() + "]");
			e.printStackTrace();
		}
		if (addedSub != null)
		{
			System.out.println("Subscriber [" + addedSub.getIdentifier() + "] was successfully added to userDB [" + this.sourceId + "]");
		}
		return addedSub;
	}

	public boolean sendImmediate(Integer campaignIdentifier, Subscriber subscriber)
	{
		try
		{
			this.clabService.sendImmediateByCampaignIdToSubscriberId(this.apiKey, this.userKey, campaignIdentifier, this.sourceId, subscriber.getIdentifier(), null);
			System.out.println("Immediate Campaign [" + campaignIdentifier + "] was successfully sent to Subscriber [" + subscriber.getIdentifier() + "]");

		} catch (Exception e)
		{
			System.err.println("Error while sending the Campaign [" + campaignIdentifier + "] to Subscriber [" + subscriber.getIdentifier() + "]");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean sendImmediateWithAttachment(Integer campaignIdentifier, Subscriber subscriber, List<String> attachmentsPaths)
	{
		SendImmediateOptions sendImmediateOptions = new SendImmediateOptions();
		List<Attachment> campaignAttachments = sendImmediateOptions.getCustomAttachments();
		for (String attachmentPath : attachmentsPaths)
		{
			campaignAttachments.add(getAttachmentFromPath(attachmentPath));
		}

		try
		{
			this.clabService.sendImmediateByCampaignIdToSubscriberId(this.apiKey, this.userKey, campaignIdentifier, this.sourceId, subscriber.getIdentifier(), sendImmediateOptions);
			System.out.println("Immediate Campaign [" + campaignIdentifier + "] with [" + campaignAttachments.size() + "] attachments was successfully sent to Subscriber [" + subscriber.getIdentifier() + "]");
		} catch (Exception e)
		{
			System.err.println("Error while sending the Campaign [" + campaignIdentifier + "] with [" + campaignAttachments.size() + "] attachments to Subscriber [" + subscriber.getIdentifier() + "]");
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private Attachment getAttachmentFromPath(String attachmentPath)
	{
		Attachment attachment = null;
		byte[] attachmentData = null;
		try
		{
			File file = new File(attachmentPath);

			attachmentData = Files.readAllBytes(file.toPath());
			attachment = new Attachment();
			attachment.setContent(attachmentData);
			attachment.setMimeType(MimeType.BINARY);
			attachment.setName(file.getName());

		} catch (IOException e)
		{
			System.out.println("Error while opening or reading: " + attachmentPath);
			e.printStackTrace();
		}

		return attachment;
	}

}
