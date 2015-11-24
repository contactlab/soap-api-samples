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
 * Object sendImmediateByCampaignIdToSubscriberId
 * @var string $apiKey
 * @var string $userKey
 * @var int $campaignId
 * @var int $sourceId
 * @var int $subscriberId
 * @var SendImmediateOptions $sendImmediateOptions
 */
$sendImmediateByCampaignIdToSubscriberIdParameters = new sendImmediateByCampaignIdToSubscriberId();

$sendImmediateByCampaignIdToSubscriberIdParameters->apiKey = $apiKey;
$sendImmediateByCampaignIdToSubscriberIdParameters->userKey = $userKey;
$sendImmediateByCampaignIdToSubscriberIdParameters->campaignId = $campaignId;
$sendImmediateByCampaignIdToSubscriberIdParameters->sourceId = $sourceId;
$sendImmediateByCampaignIdToSubscriberIdParameters->subscriberId = $subscriberId;
$sendImmediateByCampaignIdToSubscriberIdParameters->sendImmediateOptions = $sendImmediateOptions;

$uuid = $clabService->sendImmediateByCampaignIdToSubscriberId($sendImmediateByCampaignIdToSubscriberIdParameters)->return;
