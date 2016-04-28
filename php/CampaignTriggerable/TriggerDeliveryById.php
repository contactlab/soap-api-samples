<?php

require __DIR__.'/../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

$campaignId = <<campaign_id>>;
$subscriberId = <<subscriber_id>>;

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

// triggerDeliveryById
$triggerDeliveryByIdParameters = new triggerDeliveryById();
$triggerDeliveryByIdParameters->token = $token;
$triggerDeliveryByIdParameters->campaignIdentifier = $campaignId;
$triggerDeliveryByIdParameters->subscriberIdentifier = $subscriberId;

$requestId = $clabService->triggerDeliveryById($triggerDeliveryByIdParameters)->return;

// invalidateToken
$invalidateTokenParameters = new invalidateToken();
$invalidateTokenParameters->token = $token;
$clabService->invalidateToken($invalidateTokenParameters);
