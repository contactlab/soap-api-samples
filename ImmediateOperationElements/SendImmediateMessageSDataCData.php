<?php

require __DIR__.'/../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

$recipient = 'existing.email@contactlab.com';
$campaignId = <<id campaign>>;

// Optional

$nameRecipient = 'Mario';

$senderName = 'ContactLab';
$senderEmail = 'noreply@contactlab.com';
$senderReplyTo = 'noreply@contactlab.com';

$subject = 'TEST - email subject';

$htmlContent = '<b>HTML CONTENT ${nameRecipient}$</b>';
$textContent = 'Alternative Test ${nameRecipient}$';


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

/*
 * Object sendImmediateMessageSDataCData
 * @var string $apiKey
 * @var string $userKey
 * @var Campaign $campaign
 * @var Subscriber $userData
*/

$sendImmediateMessageSDataCDataParameters = new sendImmediateMessageSDataCData();

$sendImmediateMessageSDataCDataParameters->apiKey = $apiKey;
$sendImmediateMessageSDataCDataParameters->userKey = $userKey;
$sendImmediateMessageSDataCDataParameters->campaign = $campaign;
$sendImmediateMessageSDataCDataParameters->userData = $userData;

$clabService->sendImmediateMessageSDataCData($sendImmediateMessageSDataCDataParameters);
