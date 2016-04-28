<?php

require __DIR__.'/../../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

$modelId = <<model_id>>;

// Optional

$campaignName = 'Campaign name';
$campaignAlias = 'CAMPAIGN_ALIAS';

// Set CampaignType
$campaignModifier = CampaignType::TRIGGERED;

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
$getTextMessage = $clabService->getMessageModelById($getMessageModelByIdParameters)->return;

/* 
 * SoapVar is necessary for Message because every message can be:
 * EmailMessage (Channel EMAIL),
 * TextMessage (Channel SMS),
 * PushNotificationMessage (Channel PUSH)
 */

$textMessage = new \SoapVar($getTextMessage, SOAP_ENC_OBJECT, 'TextMessage', 'domain.ws.api.contactlab.com');

// Create campaign
$campaign = new Campaign();
$campaign->name = $campaignName;
$campaign->alias = $campaignAlias;
$campaign->message = $textMessage;
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
