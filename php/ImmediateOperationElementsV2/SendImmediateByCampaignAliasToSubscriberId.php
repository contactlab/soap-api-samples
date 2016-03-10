<?php

require __DIR__.'/../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

$subscriberId = <<id subscriber>>;
$sourceId = <<id source>>;
$campaignAlias = <<alias campaign>>;

// Optional

$bccRecipients = array('bcc.email1@contactlab.com','bcc.email2@contactlab.com');
$ccRecipients = array('cc.email1@contactlab.com','cc.email2@contactlab.com');
$includeDefaultAttachments = false;

$attachmentName = 'change_name.txt';
$attachmentContent = 'change_content';

// If you want to send an Attachment you need to know the Campaign Id
$campaignId = <<id campaign>>;
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
 * Object sendImmediateByCampaignAliasToSubscriberId
 * @var string $apiKey
 * @var string $userKey
 * @var string $campaignAlias
 * @var int $subscriberId
 * @var int $sourceId
 * @var SendImmediateOptions $sendImmediateOptions
 */
$sendImmediateByCampaignAliasToSubscriberIdParameters = new sendImmediateByCampaignAliasToSubscriberId();

$sendImmediateByCampaignAliasToSubscriberIdParameters->apiKey = $apiKey;
$sendImmediateByCampaignAliasToSubscriberIdParameters->userKey = $userKey;
$sendImmediateByCampaignAliasToSubscriberIdParameters->campaignAlias = $campaignAlias;
$sendImmediateByCampaignAliasToSubscriberIdParameters->subscriberId = $subscriberId;
$sendImmediateByCampaignAliasToSubscriberIdParameters->sourceId = $sourceId;
$sendImmediateByCampaignAliasToSubscriberIdParameters->sendImmediateOptions = $sendImmediateOptions;

$uuid = $clabService->sendImmediateByCampaignAliasToSubscriberId($sendImmediateByCampaignAliasToSubscriberIdParameters)->return;
