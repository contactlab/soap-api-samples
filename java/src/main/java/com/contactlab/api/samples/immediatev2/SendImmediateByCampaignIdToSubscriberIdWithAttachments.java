package com.contactlab.api.samples.immediatev2;

import com.contactlab.api.ws.ClabService;
import com.contactlab.api.ws.ClabService_Service;
import com.contactlab.api.ws.domain.Attachment;
import com.contactlab.api.ws.domain.MimeType;
import com.contactlab.api.ws.domain.SendImmediateOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class SendImmediateByCampaignIdToSubscriberIdWithAttachments
{
	// REQUIRED

	private final String apiKey = "<< api key >>";
	private final String userKey = "<< user key >>";
	private int campaignId = 0;
	private int subscriberSourceId = 0;
	private int subscriberId = 0;
	private String filepath = "/path/to/your/file.txt";

	// Other variables

	private ClabService clabService;
	private SendImmediateOptions sendImmediateOptions;

	public static void main(String[] args) {
		new SendImmediateByCampaignIdToSubscriberIdWithAttachments();
	}

	public SendImmediateByCampaignIdToSubscriberIdWithAttachments() {
		// init variables
		startVariables();

		// send immediate message with attachments.
		SendImmediateWithAttachments();


	}

	private void startVariables() {

		this.clabService = new ClabService_Service().getClabServicePort();

		/* Fill a list of file-paths to send with the Campaign */
		List<String> attachmentsPaths = new LinkedList<>();

		attachmentsPaths.add(filepath);

		sendImmediateOptions = new SendImmediateOptions();
		List<Attachment> campaignAttachments = sendImmediateOptions.getCustomAttachments();
		for (String attachmentPath : attachmentsPaths)
		{
			campaignAttachments.add(getAttachmentFromPath(attachmentPath));
		}
	}

	private boolean SendImmediateWithAttachments()
	{
		try
		{
			this.clabService.sendImmediateByCampaignIdToSubscriberId(
					apiKey,
					userKey,
					campaignId,
					subscriberSourceId,
					subscriberId,
					sendImmediateOptions);
			System.out.println("Immediate Campaign [" + campaignId
					+ "] with [" + sendImmediateOptions.getCustomAttachments().size()
					+ "] attachments was successfully sent to Subscriber [" + subscriberId + "]");
		} catch (Exception e)
		{
			System.err.println("Error while sending the Campaign [" + campaignId
					+ "] with [" + sendImmediateOptions.getCustomAttachments().size() + "] attachments " +
					"to Subscriber [" + subscriberId + "]");
			e.printStackTrace();

			return false;
		}

		return true;
	}

	private Attachment getAttachmentFromPath(String attachmentPath)
	{
		Attachment attachment = null;
		byte[] attachmentData;
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
