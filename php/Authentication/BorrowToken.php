<?php

require __DIR__.'/../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

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

// http://developer.contactlab.com/soap-api/Using-the-API

// invalidateToken
$invalidateTokenParameters = new invalidateToken();
$invalidateTokenParameters->token = $token;
$clabService->invalidateToken($invalidateTokenParameters);
