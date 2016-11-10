<?php

require __DIR__.'/../vendor/autoload.php';

// REQUIRED

$apiKey = <<api key >>;
$userKey = <<user key >>;

$subscriberSourceIdentifier = << subscriber source identifier >>;

$subscriberEmailFieldName = << email subsbriber source field name >>;
$subscriberEmailFieldValue = << email value to be added >>;

$subscriberTextFieldName = << text subsbriber source field name >>;
$subscriberTextFieldValue = << text value to be added >>;

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

// Prepare subscriber attributes (e.g. EMAIL and TEXT fields)
$attributes = array();

$subscriberAttributeEmail = new \SubscriberAttribute();
$subscriberAttributeEmail->key = $subscriberEmailFieldName;
// @see http://php.net/manual/en/soapvar.soapvar.php
$subscriberAttributeEmail->value = new \SoapVar($subscriberEmailFieldValue, XSD_ANYTYPE, 'string', 'http://www.w3.org/2001/XMLSchema', 'value');
array_push($attributes, $subscriberAttributeEmail);

$subscriberAttributeText = new \SubscriberAttribute();
$subscriberAttributeText->key = $subscriberTextFieldName;
// @see http://php.net/manual/en/soapvar.soapvar.php
$subscriberAttributeText->value = new \SoapVar($subscriberTextFieldValue, XSD_ANYTYPE, 'string', 'http://www.w3.org/2001/XMLSchema', 'value');
array_push($attributes, $subscriberAttributeText);

// Prepare the Subscriber
$subscriber = new \Subscriber();
$subscriber->attributes = $attributes;

$addSubscriberParam = new addSubscriber();
$addSubscriberParam->token = $token;
$addSubscriberParam->sourceIdentifier = $subscriberSourceIdentifier;
$addSubscriberParam->subscriber = $subscriber;

$createdSubscriber = $clabService->addSubscriber($addSubscriberParam)->return;

// invalidateToken
$invalidateTokenParameters = new invalidateToken();
$invalidateTokenParameters->token = $token;
$clabService->invalidateToken($invalidateTokenParameters);