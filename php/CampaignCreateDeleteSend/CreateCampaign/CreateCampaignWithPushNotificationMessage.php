<?php

require __DIR__.'/../../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

// See "API Model Id" in https://dmc.contactlab.it/client/build/push/
$modelId = <<model_id>>;

$gcmRawContent = '{"notification":{'
// GCM push title
.'"title":"PUSH TITLE",'
// GCM push body
.'"body":"PUSH BODY",'
// GMC show application icon
.'"icon":"ic_notification",'
// GCM on click open the application
.'"click-action":"com.contactlab.clabpush_android_sample.OPEN_MAIN_ACTIVITY"},'
// GCM custom data
.'"data":{"customKey":"customValue"}}';

$apnRawContent = '{"aps":{'
// APN push body
.'"alert":"PUSH BODY",'
// APN push badge
.'"badge":0,'
// APN push sound
.'"sound":"apnsound"},'
// APN custom data
.'"data":{"customKey":"customValue"}}';

// Optional

$campaignName = 'Campaign name';
$campaignAlias = 'CAMPAIGN_ALIAS';

// Set CampaignType::IMMEDIATE for immediate message
$campaignModifier = CampaignType::NORMAL;

/*
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

// borrowToken
$borrowTokenParameters = new borrowToken();
$borrowTokenParameters->apiKey = $apiKey;
$borrowTokenParameters->userKey = $userKey;
$token = $clabService->borrowToken($borrowTokenParameters)->return;

/*
 * Preparing campaign
 */
$getMessageModelByIdParameters = new getMessageModelById();
$getMessageModelByIdParameters->token = $token;
$getMessageModelByIdParameters->modelId = $modelId;
$getPushNotificationMessage = $clabService->getMessageModelById($getMessageModelByIdParameters)->return;
// Warning: check selected filter in $getPushNotificationMessage->recipients->subscriberSourceFilterIdentifier

// Preparing push notification content
$endPoints = array();

$pushEndPointEntryGCM = new PushEndPointEntry();
$pushEndPointEntryGCM->endpointType = 'GCM';
$pushEndPointEntryGCM->rawContent = $gcmRawContent;
array_push($endPoints, $pushEndPointEntryGCM);

$pushEndPointEntryAPN = new PushEndPointEntry();
$pushEndPointEntryAPN->endpointType = 'APN';
$pushEndPointEntryAPN->rawContent = $apnRawContent;
array_push($endPoints, $pushEndPointEntryAPN);

$getPushNotificationMessage->endPoints = $endPoints;

// SoapVar is necessary for Message because every message can be EmailMessage, TextMessage, FaxMessage or PushMessage
$pushNotificationMessage = new \SoapVar(
    $getPushNotificationMessage,
    SOAP_ENC_OBJECT,
    'PushNotificationMessage',
    'domain.ws.api.contactlab.com'
);

// Create campaign
$campaign = new Campaign();
$campaign->name = $campaignName;
$campaign->alias = $campaignAlias;
$campaign->message = $pushNotificationMessage;
$campaign->modifier = $campaignModifier;
$campaign->removeDuplicates = true;

$createCampaignParameters = new createCampaign();
$createCampaignParameters->token = $token;
$createCampaignParameters->campaign = $campaign;

$createdCampaign = $clabService->createCampaign($createCampaignParameters)->return;

// invalidateToken
$invalidateTokenParameters = new invalidateToken();
$invalidateTokenParameters->token = $token;
$clabService->invalidateToken($invalidateTokenParameters);
