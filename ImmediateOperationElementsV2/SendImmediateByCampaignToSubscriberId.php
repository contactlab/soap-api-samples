<?php

require __DIR__.'/../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

$campaignId = <<id campaign>>;
$sourceId = <<id source>>;
$subscriberId = <<id subscriber>>;

// Optional

$bccRecipients = array('bcc.email1@contactlab.com','bcc.email2@contactlab.com');
$ccRecipients = array('cc.email1@contactlab.com','cc.email2@contactlab.com');
$includeDefaultAttachments = false;

$subject = 'TEST - email subject';

// ${key}$ is the value of an SubscriberAttribute that you want to display
$htmlContent = '<b>HTML CONTENT ${key}$</b>';
$textContent = 'Alternative Test ${key}$';

$bccRecipients = array('bcc.email1@contactlab.com','bcc.email2@contactlab.com');
$ccRecipients = array('cc.email1@contactlab.com','cc.email2@contactlab.com');
$includeDefaultAttachments = false;

$attachmentName = 'change_name.txt';
$attachmentContent = 'change_content';
$mimeType = MimeType::TXT;

/**
 * Soap Service
 */
$clabService = new ClabService(
    array(
        'soap_version' => SOAP_1_2,
        'connection_timeout' => 30,
        'trace' => true,
        'keep_alive' => true,
    )
);


/**
 * Preparing campaign
 */

// borrowToken
$borrowTokenParameters = new borrowToken();
$borrowTokenParameters->apiKey = $apiKey;
$borrowTokenParameters->userKey = $userKey;
$token = $clabService->borrowToken($borrowTokenParameters)->return;

// getCampaign
$getCampaign = new getCampaign();
$getCampaign->token = $token;
$getCampaign->campaignIdentifier = $campaignId;
$campaign = $clabService->getCampaign($getCampaign)->return;

// Invalidate Token because sendImmediate methods don't need token
$invalidateTokenParameters = new invalidateToken();
$invalidateTokenParameters->token = $token;
$clabService->invalidateToken($invalidateTokenParameters);

/**
 * Optional customization
 */
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

// SoapVar is necessary for Message because every message can be EmailMessage, TextMessage, FaxMessage or PushMessage
$campaign->message = new \SoapVar($campaign->message, SOAP_ENC_OBJECT, 'EmailMessage', 'domain.ws.api.contactlab.com');


/**
 * Preparing sendImmediateOption
 */
$sendImmediateOptions = new SendImmediateOptions();

if (isset($bccRecipients)) {
    $sendImmediateOptions->bccRecipients = $bccRecipients;
}

if (isset($ccRecipients)) {
    $sendImmediateOptions->ccRecipients = $ccRecipients;
}

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

$sendImmediateOptions->customAttachments = $attachments;

if (isset($includeDefaultAttachments)) {
    $sendImmediateOptions->includeDefaultAttachments = $includeDefaultAttachments;
}

/**
 * Object sendImmediateByCampaignToSubscriberId
 * @var string $apiKey
 * @var string $userKey
 * @var Campaign $campaign
 * @var int $sourceId
 * @var int $subscriberId
 * @var SendImmediateOptions $sendImmediateOptions
 */
$sendImmediateByCampaignToSubscriberIdParameters = new sendImmediateByCampaignToSubscriberId();

$sendImmediateByCampaignToSubscriberIdParameters->apiKey = $apiKey;
$sendImmediateByCampaignToSubscriberIdParameters->userKey = $userKey;
$sendImmediateByCampaignToSubscriberIdParameters->campaign = $campaign;
$sendImmediateByCampaignToSubscriberIdParameters->sourceId = $sourceId;
$sendImmediateByCampaignToSubscriberIdParameters->subscriberId = $subscriberId;
$sendImmediateByCampaignToSubscriberIdParameters->sendImmediateOptions = $sendImmediateOptions;

$uuid = $clabService->sendImmediateByCampaignToSubscriberId($sendImmediateByCampaignToSubscriberIdParameters)->return;
