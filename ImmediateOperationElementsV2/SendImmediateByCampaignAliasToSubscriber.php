<?php

require __DIR__.'/../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

$recipient = 'existing.email@contactlab.com';
$campaignAlias = <<alias campaign>>;

// Optional

$nameRecipient = 'Mario';

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
 * Preparing subscriber
 */
$userData = new Subscriber();

$attributes = array();

$attribute = new SubscriberAttribute();
// "RECIPIENT" is a mandatory attribute for sending an email using sendImmediateMessages
$attribute->key = 'RECIPIENT';
// SoapVar is necessary for SubscriberAttribute because every attribute can have different type from others
$attribute->value = new SoapVar($recipient, XSD_ANYTYPE, 'string', 'http://www.w3.org/2001/XMLSchema', 'value');
$attributes[] = $attribute;

if (isset($nameRecipient)) {
    $attribute = new SubscriberAttribute();
    $attribute->key = 'nameRecipient';
    $attribute->value = new SoapVar($nameRecipient, XSD_ANYTYPE, 'string', 'http://www.w3.org/2001/XMLSchema', 'value');
    $attributes[] = $attribute;
}
$userData->attributes = $attributes;

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
 * Object sendImmediateByCampaignAliasToSubscriber
 * @var string $apiKey
 * @var string $userKey
 * @var string $campaignAlias
 * @var Subscriber $userData
 * @var SendImmediateOptions $sendImmediateOptions
 */
$sendImmediateByCampaignAliasToSubscriberParameters = new sendImmediateByCampaignAliasToSubscriber();

$sendImmediateByCampaignAliasToSubscriberParameters->apiKey = $apiKey;
$sendImmediateByCampaignAliasToSubscriberParameters->userKey = $userKey;
$sendImmediateByCampaignAliasToSubscriberParameters->campaignAlias = $campaignAlias;
$sendImmediateByCampaignAliasToSubscriberParameters->subscriber = $userData;
$sendImmediateByCampaignAliasToSubscriberParameters->sendImmediateOptions = $sendImmediateOptions;

$uuid = $clabService->sendImmediateByCampaignAliasToSubscriber($sendImmediateByCampaignAliasToSubscriberParameters)->return;
