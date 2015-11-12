<?php


    require __DIR__.'/../vendor/autoload.php';

    /*  Start Mandatory   */

    $apiKey = <<api key >>;
    $userKey = <<user key >>;

    $recipient = 'existing.email@contactlab.com';
    $campaignId = <<id campaign>>;

    /*  End Mandatory   */

    /*  Start Optional  */

    $nameRecipient = 'Mario';

    $senderName = 'ContactLab';
    $senderEmail = 'noreply@contactlab.com';
    $senderReplyTo = 'noreply@contactlab.com';

    $subject = 'TEST - email subject';

    $htmlContent = '<b>HTML CONTENT ${nameRecipient}$</b>';
    $textContent = 'Alternative Test ${nameRecipient}$';

    $bccRecipients = array('bcc.email1@contactlab.com','bcc.email2@contactlab.com');
    $ccRecipients = array('cc.email1@contactlab.com','cc.email2@contactlab.com');
    $includeDefaultAttachments = false;

    $attachmentName = 'change_name.txt';
    $attachmentContent = 'change_content';
    $mimeType = MimeType::TXT;

    /*  End Optional   */

    /*  Soap Service */

    $clabService = new ClabService(
        array(
            'soap_version' => SOAP_1_2,
            'connection_timeout' => 30,
            'trace' => true,
            'keep_alive' => true,
        )
    );

    /*
     * Object Subscriber
     * @var int $identifier
     * @var SubscriberAttribute[] $attributes
    */

    $userData = new Subscriber();

    $attributes = array();

    /*
     *  SoapVar is necessary for SubscriberAttribute because
     *  every attribute can have different type from others
    */

    /*
     * "RECIPIENT" is a mandatory attribute for sending an email using sendImmediateMessages
    */

    $attribute = new SubscriberAttribute();
    $attribute->key = 'RECIPIENT';
    $attribute->value = new SoapVar($recipient, XSD_ANYTYPE, 'string', 'http://www.w3.org/2001/XMLSchema', 'value');
    $attributes[] = $attribute;

    if (isset($nameRecipient)) {
        $attribute = new SubscriberAttribute();
        $attribute->key = 'nameRecipient';
        $attribute->value = new SoapVar($nameRecipient, XSD_ANYTYPE, 'string', 'http://www.w3.org/2001/XMLSchema', 'value');
        $attributes[] = $attribute;
    }
    $userData->attributes = $attributes;

    /*
     * Object borrowToken
     * @var string $apiKey
     * @var string $userKey
     * @return AuthToken $token (in 'return' key)
    */

    $borrowTokenParameters = new borrowToken();

    $borrowTokenParameters->apiKey = $apiKey;
    $borrowTokenParameters->userKey = $userKey;

    $token = $clabService->borrowToken($borrowTokenParameters)->return;

    /*
     * Object getCampaign
     * @var AuthToken $token
     * @var int $campaignIdentifier
     * @return Campaign $campaign (in 'return' key)
    */

    $getCampaign = new getCampaign();

    $getCampaign->token = $token;

    $getCampaign->campaignIdentifier = $campaignId;

    $campaign = $clabService->getCampaign($getCampaign)->return;

    if (isset($senderName)) {
        $campaign->message->sender->name = $senderName;
    }
    if (isset($senderEmail)) {
        $campaign->message->sender->email = $senderEmail;
    }
    if (isset($senderReplyTo)) {
        $campaign->message->sender->replyTo = $senderReplyTo;
    }

    if (isset($subject)) {
        $campaign->message->subject = $subject;
    }

    if (isset($htmlContent)) {
        $campaign->message->htmlContent = $htmlContent;
    }

    if (isset($textContent)) {
        $campaign->message->textContent = $textContent;
    }

    /*
     *  SoapVar is necessary for Message because
     *  every message can be EmailMessage, TextMessage, FaxMessage or PushMessage
    */

    $campaign->message = new \SoapVar($campaign->message, SOAP_ENC_OBJECT, 'EmailMessage', 'domain.ws.api.contactlab.com');

    $attachments = array();
    if (isset($attachmentName)) {
        $attachment = new Attachment();
        $attachment->campaignIdentifier = $campaignId;
        $attachment->name = $attachmentName;
        $attachment->content = $attachmentContent;
        $attachment->mimeType = MimeType::BINARY;
        if (isset($mimeType)) {
            $attachment->mimeType = $mimeType;
        }
        $attachments[] = $attachment;
    }

    /*
     *  Invalidate Token because sendImmediate methods don't need token
    */

    /*
     * Object invalidateToken
     * @var AuthToken $token
    */

    $invalidateTokenParameters = new invalidateToken();
    $invalidateTokenParameters->token = $token;

    $clabService->invalidateToken($invalidateTokenParameters);

    /*
     * Object SendImmediateOptions
     * @var string[] $bccRecipients
     * @var string[] $ccRecipients
     * @var Attachment[] $attachments
     * @var boolean $includeDefaultAttachments
    */

    $sendImmediateOptions = new SendImmediateOptions();

    if (isset($bccRecipients)) {
        $sendImmediateOptions->bccRecipients = $bccRecipients;
    }

    if (isset($ccRecipients)) {
        $sendImmediateOptions->ccRecipients = $ccRecipients;
    }

    $sendImmediateOptions->customAttachments = $attachments;

    if (isset($includeDefaultAttachments)) {
        $sendImmediateOptions->includeDefaultAttachments = $includeDefaultAttachments;
    }

    /*
     * Object sendImmediateByCampaignToSubscriber
     * @var string $apiKey
     * @var string $userKey
     * @var Campaign $campaign
     * @var Subscriber $userData
     * @var SendImmediateOptions $sendImmediateOptions
    */
    $sendImmediateByCampaignToSubscriberParameters = new sendImmediateByCampaignToSubscriber();

    $sendImmediateByCampaignToSubscriberParameters->apiKey = $apiKey;
    $sendImmediateByCampaignToSubscriberParameters->userKey = $userKey;
    $sendImmediateByCampaignToSubscriberParameters->campaign = $campaign;
    $sendImmediateByCampaignToSubscriberParameters->subscriber = $userData;
    $sendImmediateByCampaignToSubscriberParameters->sendImmediateOptions = $sendImmediateOptions;

    $uuid = $clabService->sendImmediateByCampaignToSubscriber($sendImmediateByCampaignToSubscriberParameters)->return;
