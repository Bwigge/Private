/**
 * @class ATT
 * <b>Introduction:</b>
 *
 * The PhoneGap Plugins for AT&T API Platform provides the following APIs.
 *
 *• ATT.Location
 *
 *• ATT.MMS
 *
 *• ATT.IMMN
 *
 *• ATT.Notary
 *
 *• ATT.OAuth
 *
 *• ATT.Payment
 * 
 *• ATT.SMS
 * 
 *• ATT.Speech
 * 
 *• ATT.Ads
 *
 *• ATT.TTS
 *
 */
/**
 * @method getCachedAccessToken Returns the accessToken used for requests that is stored in memory.  If there is no token, it returns undefined
 * @return {String}
 */
/**
 * @method getCachedUserAuthToken Returns the user consented OAuth token used for requests that is stored in memory.  If there is no token, it returns undefined
 * @return {String}
 */
/**
 * @class ATT.SMS <b>Introduction:</b>
 *
 * The Short Message Service (SMS) API enables you to target specific customers with alerts and information. Additionally, the SMS API gives your customers the ability to respond to your messages in near real-time.
 * 
 *<b>Description:</b>
 *
 *The SMS API enables authorized applications to send SMS messages to devices on the AT&T network. In addition, this API allows applications to query the delivery status of SMS messages sent from an application.
 *
 *<b>The SMS API provides the following methods:</b>
 *
 *• getSMS
 *
 *• getSMSDeliveryStatus
 *
 *• sendSMS
 */
/**
 * @method sendSMS
 * Sends an SMS message to a recipient.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {Object} options.body Specifies an object that contains all of the parameters that a user needs to send a message on an AT&T mobile device. Valid parameters are:
 *
 * @param {String} options.body.address Specifies the MSISDN of the recipients.
 * @param {String} options.body.message Specifies the text of the message to send.
 * @param {String} options.contentType Specifies the format of the message. The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 *• application/x-www-form-urlencoded

 * @param {String} [options.accept="application/json"] Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the results. The format of the returned object is specified by the value of the accept parameter in the call to the sendSMS method.
 *
 * <strong>Examples:</strong>
 *
 *<b>Example 1.</b> The following example of the sendSMS method uses a contentType of 'application/json'.
 *
 *      sendSMS({
 *          'body':{"message":"Test AT&T REST",  "address":"tel:xxxxxxxxxx"},//For Multiple Phone Number :- "Address":["tel:xxxxxxxxxx","tel:xxxxxxxxxx"]
 *          'accept' : 'application/json',
 *          'contentType' : 'application/json'
 *       }, function(data) {
 *
 *           //success Callback
 *
 *       }, function(error) {
 *
 *           //error Callback
 *
 *       });
 * 
 *  <i>Note: xxxxxxxxxx represents an AT&T wireless number</i>
 *
 *<b>Example 2.</b> The following example of the sendSMS method uses a contentType of 'application/xml'..
 *
 *      sendSMS({
 *          "body":"<outboundSMSRequest><address>tel:xxxxxxxxxx</address><message>Test XML </message></outboundSMSRequest>",
 *          'accept' : 'application/json',
 *          'contentType' : 'application/xml'
 *       }, function(data) {
 *
 *            //success Callback
 *
 *       }, function(error) {
 *
 *            //error Callback
 *
 *       });
 * 
 *  <i>Note: xxxxxxxxxx represents an AT&T wireless number</i>
 *
 *
 * <b>Example 3.</b>The following example of the sendSMS method uses a contentType of 'application/x-www-form-urlencoded'.
 *
 *      sendSMS({
 *          "body":"address=tel%3A%2B1xxxxxxxxxx&address=tel%3A%2B1xxxxxxxxxx&message=URL%20ENCODED",
 *          'accept' : 'application/json',
 *          'contentType' : 'application/x-www-form-urlencoded'
 *       }, function(data) {
 *
 *            //success Callback
 *
 *       }, function(error) {
 *
 *            //error Callback
 *
 *       });
 *
 * <i>Note: xxxxxxxxxx represents an AT&T wireless number</i>
 */
/**
 * @method getSMSDeliveryStatus
 * Checks the status of a sent SMS message.
 *
 * A final state is reached when the DeliveryStatus field of a DeliveryInfo object is set to one of the following values:
 *
 *• DeliveredToTerminal
 *
 *• DeliveryImpossible
 *
 *• DeliveredToNetwork
 *
 * A single successful delivery status query may be made for a period of thirty minutes following the time a message DeliveryInfo object has been set to a final state. Thirty minutes after a query of the DeliveryInfo object has returned a final state, the DeliveryInfo object is not available for query.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.smsId Specifies the SMS request identifier (ID) that is returned by the sendMMS method as part of the response object.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *•  application/json
 *
 *•  application/xml.
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns error.
 * @return {Object} A JSON or XML formatted object that contains the delivery status. The format of the returned object is specified by the value of the accept parameter in the call to the getSMSDeliveryStatus method.the getSMSDeliveryStatus method.
 *
 * <strong>Example:</strong>
 *
 * The following is an example of the getSMSDeliveryStatus method.
 *
 *      getSMSDeliveryStatus({
 *           'smsId' : <put smsId here>,
 *           'accept' : 'application/json'
 *
 *       }, function(data) {
 *
 *            //success Callback
 *
 *       }, function(error) {
 *
 *            //error Callback
 *
 *       });
 */
/**
 * @method getSMS
 * Retrieves a list of SMS messages that were sent to the application's short code.
 * @param {Object} options A JSON object containing the following properties:
 * @param {String} options.registrationId Specifies the short code identifier (ID) of the application that is obtained when you register your application with AT&T.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml.
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the list of SMS messages The format of the returned object is specified by the value of the accept parameter in the call to the getSMS method.
 *
 * <strong>Example:</strong>
 *
 * The following is an example of the getSMS method.
 *
 *      getSMS({
 *           'accept' : 'application/json',
 *           'registrationId' : <put registrationId here>
 *       }, function(data) {
 *
 *            //success Callback
 *
 *       }, function(error) {
 *
 *            //error Callback
 *
 *       });
 */

/**
 * @class ATT.Notary <b>Introduction:</b>
 *
 * The Notary API allows your application to digitally sign content or data. The digital signing mechanism of the Notary API uses a token hash, a time stamp, and a nonce to ensure the following:
 * 
 * • The sender of the data is who they say they are.
 * 
 * • The content that is sent came from the application that sent it and not from an imposter.
 * 
 *<b>Description:</b>
 *
 * The Notary API is used to encode and sign documents in a secure and tamper resistant manner.This operation must be used to encode and secure the payment detail information when using the Payment.newTransaction and Payment.newSubscription methods. The Notary API provides the following methods:
 * 
 * • signedPayload
 * 
 *<b>Usage:</b>
 *
 * The signedPayload method generates the inputs for the newTransaction and newSubscription methods.
 *
 *<b>Methods:</b>
 *
 *1) signedPayload
 */

/**
 * @method signedPayload Digitally signs the specified content. This method treats the payload as an arbitrary set of text content. This method is used in conjunction with the Payment.newTransaction or Payment.newSubscription methods. 
 * 
 * When calling the signedPayload method, your application must identify which of the standard HTTP content types (such as XML or JSON) is used to encode the payload. Upon receipt of the payload body, the AT&T API Gateway repackages the content into a standard format before encoding and signing the payload for a response. After the AT&T API unpacks and validates the payload content, any parameters associated with the payload are determined and the content is repackaged. The content type of the result is independent of the original content type that is provided by your application.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {Object} options.data Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.data.Amount Specifies the amount of the transaction expressed with a maximum of two decimal places. For example: 0.99 or 2.99.
 * @param {String} options.data.Category Specifies the Product Category. One of the values from a predefined list of categories.
 * @param {String} options.data.Channel Specifies the channel through which the payload is delivered. The only supported value is MOBILE_WEB.
 * @param {String} options.data.DescriptionSpecifies a short description of the entire purchase. The maximum length of the string is 128 characters.
 * @param {String} options.data.MerchantTransactionId Specifies the transaction identifier (id) in the merchant’s system. The id must be unique for every purchase request, and consist of alphanumeric text.
 * @param {String} options.data.MerchantProductId Specifies the product identifier, assigned by the merchant, of the product to be purchased. The maximum length of the String is 50 alphanumeric characters.
 * @param {String} options.data.MerchantPaymentRedirectUrl Specifies the URL where control is returned to your application by the AT&T Gateway. The merchant application must listen at this URL for redirects from the subscriber’s browser.
 * @param {String} options.data.MerchantSubscriptionIdList Specifies a list of merchant subscription identifiers. The maximum length of the string is 12 alphanumeric characters.
 * @param {String} options.data.IsPurchaseOnNoActiveSubscription Specifies whether the purchase was made on a non-active subscription. The acceptable value for this parameter is: FALSE
 * @param {String} options.data.SubscriptionRecurrences Specifies the number of subscription renewals that should be made. The vacceptable value for this parameter is: 99999
 * @param {String} options.data.SubscriptionPeriod Specifies the interval at which the subscription is charged. The acceptable value for this parameter is: MONTHLY
 * @param {String} options.data.SubscriptionPeriodAmount Specifies the number of periods, as specified in the SubscriptionPeriod property, that pass between each renewal. The acceptable value for this parameter is: 1
 * @param {String} options.contentType Specifies he content type of the request. Acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 *• application/x-www-form-urlencoded

 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {String} options.clientId Specifies the API key that is assigned to the application when the developer creates an application account at [https://devconnect-api.att.com/.](https://devconnect-api.att.com/)
 * @param {String} options.clientSecret Specifies the secret key that is assigned to the application when the developer creates an application account at [https://devconnect-api.att.com/.](https://devconnect-api.att.com/)
 * @param {Number} options.contentLength Specifies the size of the entity-body, specified as a decimal number of OCTETs (For IOS Only).
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML object that contains the digitally signed content. The format of the returned object is specified by the value of the accept parameter in the call to the signedPayload method.
 *
 * <b>Example:</b>
 *
 * The following is an example of the signedPayload method.
 *
 *       signedPayload({
 *            'data' : {"Amount" : <value>,
 *                      "Category" : <value>,
 *                      "Channel" : <value>,
 *                      "Description" : <value>,
 *                      "MerchantTransactionId" : <value>,
 *                      "MerchantProductId" : <value>,
 *                      "MerchantPaymentRedirectUrl" : <value>,
 *                      "MerchantSubscriptionIdList" : <value>,
 *                      "IsPurchaseOnNoActiveSubscription" : <value>,
 *                      "SubscriptionRecurrences" : <value>,
 *                      "SubscriptionPeriod" : <value>,
 *                      "SubscriptionPeriodAmount" : <value>},
 *            'clientId' : <accessKey value>,
 *            'clientSecret' : <secretKey value>,
 *            'contentType' : 'application/json'
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 */

/**
 * @class ATT.Payment <b>Introduction:</b>
 *
 * The Payment API provides developers and merchants the ability to charge digital services directly to an AT&T subscriber bill for payment.
 *
 * <b>Description:</b>
 *
 * Using the Payment API, a merchant may create new transactions and subscriptions, get the status of the transaction or subscription, and authorize refunds.
 *
 * The new transaction and subscription process includes:
 *
 *• Authentication of the end-user as an AT&T Subscriber.
 *
 *• Presentation of an Advice-of-Charge (AOC) page and the opportunity to cancel.
 *
 *  If the subscriber chooses to accept the charge, a Thank You message is presented.
 *
 * The merchant also has the option of automatically receiving notifications of refunds and subscription changes triggered through other AT&T systems. Applications in the initial Sandbox state have lower limits on the quantity of transactions. All Sandbox payment transactions are refunded weekly. Further, in the Sandbox environment, a developer may set certain attributes for the application that would not be available to the developer in the production environment. Once an application has been promoted to the Production state, however, a more secure environment is maintained where most of the attributes are read-only and may be changed only by the AT&T administrator. Organizations must have a merchant account set up for the Payment Service to work. If your organization does not have a merchant account, contact your Organizational Profile Administrator to arrange for one.
 * 
 *
 *<b>The Payment API provides the following methods:</b>
 *
 *• acknowledgeNotification
 *
 *• getNotification
 *
 *• getSubscriptionDetails
 *
 *• getSubscriptionStatus
 *
 *• getTransactionStatus
 *
 *• newSubscription
 *
 *• newTransaction
 *
 *• refundTransaction
 * 
 * <b>Usage</b>
 * 
 * Use the Notary API to:
 * 
 * • Sign the payment request.
 * 
 * • Redirect the user agent to the AT&T server to obtain consent.
 * 
 * • Get the response, either an authorization code or an error, through the redirect back from the consent process.
 * 
 * • Use the getTransactionStatus or getSubscriptionStatus methods with the authorization code to get the transaction identifier that is needed for refunds.
 */

/**
 * @method newTransaction Creates a new payment transaction for a one time product purchase (i.e. transactionType = SINGLE_PURCHASE).
 *
 *Before this method is invoked, the application must call the Notary.signedPayload method and provide a payload that contains the information (such as name-value-pairs) necessary to complete the purchase process. 
 *
 *The Notary.signedPayload method returns the result in either JSON or XML format as designed by the HTTP Header Accept included in the request to sign the document. The response will contain two name-value-pairs that may be used to formulate the request for this method.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.signedDocument Specifies the SignedDocument value that is returned by the Notary.signedPayload method.
 * @param {String} options.signature Specifies the signature that is returned by the Notary.signedPayload method.
 * @param {String} options.clientId Specifies the client identifier (ID) of the application making the request.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the results. The format of the returned object is specified by the value of the accept parameter in the call to the newTransaction method.
 *
 * <b>Example:</b>
 *
 * The following is an example of the newTransaction method.
 *
 *       newTransaction({
 *           "signedDocument" : <value of SignedDocument returned by Notary.signedPayload>,
 *           "signature" : <value of Signature returned by Notary.signedPayload>,
 *           "clientId" : <accessKey value>
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 */

/**
 * @method newSubscription Sets up recurring charges for a specified amount, at a specified interval, for a specified number of times.
 * 
 * This method authorizes both the merchant and the user of the subscription, and initializes the product and price data for of the transaction.
 *
 * Before this operation is invoked, the application must call the Notary.signedPayload method and provide a payload containing the required parameters (i.e. name-value-pairs) necessary to complete the purchase process.
 * 
 * The Notary.signedPayload method returns the result in either JSON or XML format, as defined in the HTTP Header "Accept" included in the request to sign the document. The result will contain two name-value-pairs which will be utilized in formulating the request for this method.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.signedDocument Specifies the SignedDocument value that is returned by the Notary.signedPayload method.
 * @param {String} options.signature Specifies the signature that is returned by Notary.signedPayload method.
 * @param {String} options.clientId Specifies the client identifier (ID) of the application making the request.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the results. The format of the returned object is specified by the value of the accept parameter in the call to the newSubscription method.
 *
 *  <b>Example:</b>
 *
 * The following is an example of the newSubscription method.
 *
 *       newSubscription({
 *           "signedDocument" : <Use value of SignedDocument returned by Notary.signedPayload>,
 *           "signature" : <Use value of Signature returned by Notary.signedPayload>,
 *           "clientId" : <accessKey value>
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 */

/**
 * @method getTransactionStatus Retrieves the status and all relevant information of a previously executed transaction.
 * 
 * <b>Syntax</b>
 *
 * Three different identifiers may be used:
 *
 *• TransactionId returned by a previous call to the getTransactionStatus method.
 *
 *• TransactionAuthCode returned at the end of the Merchant Payment Acknowledgement process.
 *
 *• MerchantTransactionId specified in the initial call to the Notary.signedPayload method.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.transactionId (Conditional Parameter) Specifies the transaction identifier (ID) that is generated by the AT&T payment processing system for a purchase. This ID is used for future transaction requests.
 * @param {String} options.merchantTransactionId (Conditional Parameter) Specifies the transaction identifier (ID) in the merchant’s system. This ID must be unique on every purchase request.
 * @param {String} options.transactionAuthCode (Conditional Parameter) Specifies the authentication code that is returned by AT&T to the developer’s application service upon successful conclusion of the Payment.NewTransaction request.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formmated object that contains the transaction status. The format of the returned object is specified by the value of the accept parameter in the call to the getTransactionStatus method.
 *
 *  <b>Example:</b>
 *
 * The following is an example of the getTransactionStatus method.
 *
 *       getTransactionStatus({
 *            "transactionAuthCode" : <The value returned by AT&T to the Developer’s Application Service at upon successful conclusion of the Payment.newTransaction request>
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 *
 * Note : To get the TransactionStatus, pass any of the following IDs:
 *
 * • "transactionAuthCode" - The value returned by AT&T to the Developer’s Application Service upon successful conclusion of the Payment.newTransaction request.
 *
 * • "merchantTransactionId" - The MerchantTransactionId specified in the initial call to the Notary.signedPayload method.
 *
 * •  "transactionId" - The TransactionId returned by a previous call to getTransactionStatus method.
 */

/**
 * @method getSubscriptionStatus Retrieves the status of a previously executed call to the newSubscription method. The TransactionId from the previously executed newSubscription method call is used to retrieve the status of the subscription. This means that the TransactionIds from any new subscriptions must be stored in order to retrieve the status of those subscriptions. This method returns the status as well as several other fields related to the purchase.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.transactionId (Conditional Parameter) Specifies the transaction identifier (ID) generated by the AT&T payment processing system for a purchase. This value is used for future transaction requests.
 * @param {String} options.merchantTransactionId (Conditional Parameter) Specifies the transaction identifier (ID) generated in the merchant’s system. This ID must be unique on every purchase request.
 * @param {String} options.subscriptionAuthCode (Conditional Parameter) Specifies the authentication code that is returned by AT&T to the developer’s application service upon successful conclusion of the Payment.newSubscription request.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contain the subscription status. The format of the returned object is specified by the value of the accept parameter in the call to the getSubscriptionStatus method.
 *
 *  <b>Example:</b>
 *
 * TThe following is an example of the getSubscriptionStatus method.
 *
 *       getSubscriptionStatus({
 *            "subscriptionAuthCode" : <The value returned by AT&T to the Developer’s Application Service at upon successful conclusion of the Payment.newSubscription request.>
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 *
 * Note : To get the SubscriptionStatus, pass any of the following IDs:
 *
 * • "subscriptionAuthCode" - The value returned by AT&T to the Developer’s Application Service upon successful conclusion of the Payment.newSubscription request.
 *
 * • "merchantTransactionId" - The MerchantTransactionId specified in the initial call to the Notary.signedPayload method.
 *
 * • "transactionId" - The TransactionId returned by a previous call to getTransactionStatus method.
 *
 */

/**
 * @method refundTransaction Refunds the value of a transaction to the original consumer. The refundTransaction method is invoked through the merchant application and is automatically passed without additional approvals.
 *
 * There are specific limits configured on the number of refunds within a day, week, and month. These limits are configured for a merchant account in the AT&T payment system.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {Object} options.body Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.body.TransactionOperationStatus Specifies the operation status of the transaction. Acceptable values for this parameter is: "Refunded".
 * @param {String} options.body.RefundReasonCode Specifies the predefined reason for the refund. Must be one of the values in a predefined list.
 * @param {String} options.body.RefundReasonText Specifies the description of the reason for the refund.
 * @param {String} options.transactionId Specifies the original transaction identifier (ID) of the transaction.
 * @param {String} options.action Specifies the action that is performed. The acceptable value of this parameter is: "refund".
 * @param {String} options.contentType Specifies the format of content to be sent. 
 * 
 * Acceptable values for this parameter are:
 * 
 *• application/json
 * 
 *• application/xml
 * 
 *• application/x-www-form-urlencoded.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns error.
 * @return {Object} A JSON or XML formatted object that contains the response. The format of the returned object is specified by the value of the accept parameter in the call to the refundTransaction method.
 *
 *  <strong>Examples:</strong>
 *
 * <b>Example 1.</b> The following example of the refundTransaction method uses a contentType of 'application/json'.
 *      refundTransaction({
 *                   "body":{"TransactionOperationStatus":<value>, "RefundReasonCode":value, "RefundReasonText":<value>},
 *                   "contentType" : "application/json",
 *                   'transactionId' : TransactionId returned by a call to getTransactionStatus,
 *                   'action' : <action value>
 *       }, function(data) {
 *
 *           success Callback
 *
 *       }, function(error) {
 *
 *           error Callback
 *
 *      });
 *
 * <b>Example 2.</b> The following example of the refundTransaction method uses a contentType of 'application/xml'.
 *      refundTransaction({
 *                    "body":"<RefundTransactionRequest><TransactionOperationStatus>value</TransactionOperationStatus><RefundReasonCode>value</RefundReasonCode> <RefundReasonText>value</RefundReasonText></RefundTransactionRequest>",
 *                    "contentType" : "application/xml",
 *                    'transactionId' : TransactionId returned by a call to getTransactionStatus,
 *                    'action' : <action value>
 *       }, function(data) {
 *
 *           success Callback
 *
 *       }, function(error) {
 *
 *           error Callback
 *
 *       });
 *
 */

/**
 * @method getSubscriptionDetails Retrieves the details for a merchant subscription to which a consumer has subscribed. Merchants who manage consumers may use this method to determine the details of a subscriptions for a particular consumer.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.consumerId Specifies the identifier (ID) of the consumer that uses the subscription. This identifier is a unique user ID generated in the payment system representing a unique subscriber.
 * @param {String} options.merchantSubscriptionId Specifies the identifier (ID) of the merchant’s subscription. This must be a unique value for each merchant subscription.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the details for the merchant subscription. The format of the returned object is specified by the value of the accept parameter in the call to the getSubscriptionDetails method.
 *
 *  <b>Example:</b>
 *
 * The following is an example of the getSubscriptionDetails method.
 *       getSubscriptionDetails({
 *           'consumerId' : <returns in response of Payment.getSubscriptionStatus>,
 *           'merchantSubscriptionId' : <returns in response of Payment.getSubscriptionStatus>,
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 */

/**
 * @method getNotification Retrieves a notification object that has been received in a prior notification message as a result of having the server listen for notification messages.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.notificationId Specifies the identifier (ID) of the notification that is retrieved.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the response. The format of the returned object is specified by the value of the accept parameter in the call to the getNotification method.
 *
 *  <b>Example:</b>
 *
 * The following is an example of the getNotification method.
 *
 *       getNotification({
 *           'notificationId' : 'ID of the notification that to be retrieved.'
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 */

/**
 * @method acknowledgeNotification Acknowledges the receipt of a notification and requests that further notifications be stopped for a specific NotificationID.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.notificationId Specifies the identifier (ID) of the notification to be retrieved.
 * @param {String} options.accept Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the response. The format of the returned object is specified by the value of the accept parameter in the call to the acknowledgeNotification method.
 *
 *  <b>Example:</b>
 *
 * The following is an example of the acknowleadgeNotification method.
 *
 *       acknowleadgeNotification({
 *           'notificationId' : 'ID of the notification that to be retrieved.'
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 */

/**
 * @class ATT.OAuth <b>Introduction:</b>
 *
 * The OAuth API provides access to the AT&T OAuth service. The OAuth service provides a safe and secure way for AT&T subscribers to access AT&T network resources through a third-party application without the risk of compromising security. The OAuth service ensures that secure, sensitive, and private AT&T subscription details are not exposed to the third-party application.
 * 
 * OAuth authorization management is an open standard recognized as providing a strong protection to clients and applications. The OAuth service provides developers with a security model that allows them to obtain an authorization code and an access token. By acquiring an authorization code, a resource owner has authorized an application to access a protected resource on their behalf. The receipt of an access token enables an application to access a protected resource on behalf of a resource owner via the AT&T network.
 * 
 * <b>Description:</b>
 * 
 * The OAuth API allows third party applications access to the private resources of subscribers without requiring the end-user to provide credentials (such as username and password) to the third party application. An application that is implemented with OAuth, provides a closer and more secure integration with the AT&T API Platform.
 * 
 * <b>The OAuth API provides the following methods:</b>
 *
 * 1) authorize
 *
 * 2) obtainEndUserAuthorization
 */
/**
 * @method obtainEndUserAuthorization
 * Obtains information required to obtain an OAuth access token. This method is the initial operation in the OAuth call flow. It is invoked by an application that requires subscriber authorization in order to obtain an OAuth access token. The application’s request for an OAuth access token is forwarded by redirecting the subscriber’s browser to the AT&T Gateway OAuth Server.
 * 
 * <b>Important</b> Currently, this method is necessary and supported only for applications attempting to consume the following APIs
 *
 * • Location API
 *
 * • IMMN API (Beta)
 *
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.clientId Specifies the API key that is assigned to the application when the developer creates an application account at [https://devconnect-api.att.com/.](https://devconnect-api.att.com/)
 * @param {String} options.scope Specifies the services that the application is requesting the end-user to provide consent for. The value of the scope parameter is a comma delimited list that contains one or more of the following values that represent the services that are in scope.
 * 
 * • Terminal Location – TL.
 *
 * • In-app Messaging from Mobile Number  – IMMN
 * @param {String} [options.redirectUrl] Specifies the URL where the subscriber’s browser is redirected following completion of the authorization process. If this parameter is not specified,the AT&T Gateway uses the value of the provided OAuth Redirect URL that is specified by the developer in the AT&T API Platform console when the application is registered.
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {String} Specifies a query parameter that is included with the redirect_uri parameter of the authorize method in the original OAuth request.
 *
 * <strong>Example:</strong>
 *
 * The following is an example of the obtainEndUserAuthorization method.
 *
 *      obtainEndUserAuthorization({
 *           'clientId' : 'API Key',
 *           'scope' : 'scope values',
 *          'redirectUrl' : 'redirect url'
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 */

/**
 * @method authorize
 *
 * Authorizes an application so that it may use specific AT&T APIs.
 *
 * This method must be called directly using the following ATT namespace: <b>Att.authorize('apiKey','secretKey','scope','grantType','oAuthCode')</b>
 *
 * When authorizing an application for use with the Location and IMMN APIs, the user must send their access key, secret key, scope, grantType, and OAuthCode.
 * 
 * <b>Syntax</b>
 * 
 * authorize( accessKey, secretKey, scope, grantType, oAuthCode )
 *
 * @param {String} accessKey Specifies the API key that is assigned to the application when the developer creates an application account at [https://devconnect-api.att.com/.](https://devconnect-api.att.com/)
 * @param {String} secretKey Specifies the secret key that is assigned to the application when the developer creates an application account at [https://devconnect-api.att.com/.](https://devconnect-api.att.com/)
 * @param {String} scope Specifies a comma separated list of authScopes enumeration values that specify which APIs the app requires access to.
 * @param {String} grantType Specifies the mechanism used to obtain the access token. The acceptable values for this parameter are:
 * 
 * authorization_code: Use when Web-server client applications use the authorization code to obtain the access token.
 * 
 * client_credentials: Use when autonomous client applications use the API key and its secret to obtain the access token.
 * 
 * refresh_token: Use when client applications of both types mentioned aboveuse refresh_token to obtain a new access_token after the initial access_token expires,
 * 
 * @param {String} oAuthCode Specifies the authorization code. The oAuthCode is returned in the response object of the obtainEndUserAuthorization method. Currently, this parameter is required only when using the Location and IMMN APIs.
 *    <b>Example:</b>
 *
 * The following is an example of the authorize method.
 *
 *              Att.authorize('apiKey','secretKey','scope','grantType','oAuthCode');
 */

/**
 * @class ATT.MMS <b>Introduction</b>
 *
 * The Multimedia Messaging Service (MMS) API enhances the power of your application’s communications by moving beyond the text-only capabilities of Short Message Service (SMS) messaging. Using the MMS API, you can send mobile terminated or receive mobile originated messages that include video, images and audio attachments as well as text.
 *
 * <b>Description</b>
 *
 *The MMS API enables applications to send Multimedia Messaging Services (MMS) messages and get the delivery status of the sent messages from the network. 
 * 
 * If your application hosts a service that complies with an AT&T specified callback, the application may also receive MMS messages. AT&T will deliver MMS messages to the application as soon as they arrive on the AT&T network.
 *
 * <b>The MMS API provides the following methods:</b>
 *
 *• getMMSDeliveryStatus
 *
 *• sendMMS
 */

/**
 * @method sendMMS
 *
 * Sends an MMS message to the specified recipient.
 *
 * The Multimedia Messaging Service allows for the delivery of different file types. For an updated list, see the[Developer Documentation](https://developer.att.com/developer/tierNpage.jsp?passedItemId=2400428).
 *
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {Object} options.body Specifies an object that contains all of the parameters that a user needs to send a message on an AT&T mobile device. The acceptable parameters are:
 * @param {String} options.body.address Specifies the MSISDN of the recipients.
 * @param {String} [options.body.subject] Specifies the subject of the message.
 * @param {String} [options.body.Priority="Default"] Acceptable values for this parameter are:
 * 
 *• "Default",
 * 
 *• "Low", 
 * 
 *• "Normal", 
 * 
 *• "High".
 *
 * The defaults value is: 'Default'
 * @param {Array} options.attachments Specifies an array of JSON objects that contain three key-value pairs:
 * 
 * • fileName:"Name of the file"
 * 
 * • fileType:"Type of the file"
 * 
 * • filePath:"Path of the file"
 *
 * The file may be an image, audio, video, or text file.
 * 
 * @param {String} options.contentType Specifies the format of the message content. Acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 *• application/x-www-form-urlencoded
 * @param {String} [options.accept="application/json"] Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the response. The format of the returned object is specified by the value of the accept parameter in the call to the sendMMS method.
 *
 * <strong>Examples:</strong>
 *
 * <b>Example 1: </b> The following example of the sendMMS method sends a JSON formatted messag with high priority.
 *
 *      sendMMS({
 *                  "body":{ "address" : "tel:xxxxxxxxxx,tel:xxxxxxxxxx", "subject" : "Test MMS", "priority" : "High" },
 *                  "contentType" : "application/json",
 *                  "accept" : "application/json",
 *                  "attachments" : [{'fileName' : "Name of the file",'fileType' : "type of the file like : image/png",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}, {'fileName' : "Name of the file",'fileType' : "fileType of the file like : image/jpeg",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}]
 *      },  function(data) {
 *
 *           //success Callback
 *
 *       }, function(error) {
 *
 *           //error Callback
 *
 *       });
 * 
 * Note: xxxxxxxxxx represents an AT&T wireless number.
 *
 * <b>Example 2.</b> The following example of the sendMMS method sends an XML formatted message with high priority.
 *
 *      sendMMS({
 *                  "body":"<outboundMessageRequest>" + "\n" + "<address>tel:xxxxxxxxxx</address>" + "\n" + "<subject>Test MMS</subject>" + "\n" + "<priority>High</priority>" + "\n" + "</outboundMessageRequest>",
 *                  "contentType" : "application/xml",
 *                  "accept" : "application/json",
 *                  "attachments" : [{'fileName' : "Name of the file",'fileType' : "type of the file like : image/png",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}, {'fileName' : "Name of the file",'fileType' : "type of the file like : image/jpeg",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}]
 *       },  function(data) {
 *
 *           //success Callback
 *
 *       }, function(error) {
 *
 *           //error Callback
 *
 *       });
 * 
 * Note: xxxxxxxxxx represents an AT&T wireless number.
 *
 * <b>Example 3.</b> The following example of the sendMMS method uses a contentType of 'application/x-www-form-urlencoded'.
 *
 *      sendMMS({
 *                  "body":"address=tel%3A%2B1xxxxxxxxxx&priority=High&subject=Test%20MMS",
 *                  "contentType" : "application/x-www-form-urlencoded",
 *                  "accept" : "application/json",
 *                 "attachments" : [{'fileName' : "Name of the file",'fileType' : "type of the file like : image/png",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}, {'fileName' : "Name of the file",'fileType' : "type of the file like : image/jpeg",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}]
 *      },  function(data) {
 *
 *          //success Callback
 *
 *      }, function(error) {
 *
 *          //error Callback
 *
 *      });
 *
 * <i>Note: xxxxxxxxxx represents an AT&T wireless number.</i>
 */

/**
 * @method getMMSDeliveryStatus
 *
 * Returns the status of a sent MMS message.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters,
 * @param {String} options.id Specifies the MMS request id that is returned by the sendMMS method.
 * @param {String} [options.accept="application/json"] Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *- application/json
 *
 *- application/xml
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the status information for the message. The format of the returned object is specified by the value of the accept parameter in the call to the getMMSDeliveryStatus method.
 *
 * <strong>Example</strong>
 *
 * The following example of the getMMSDeliveryStatus method returns a JSON object that contains information about the status of the specified message.
 *
 *      getMMSDeliveryStatus({
 *                  'id' : "MMS ID that you get in response of sendMMS.",
 *                  "accept" : "application/json",
 *       },  function(data) {
 *
 *            //success Callback
 *
 *       }, function(error) {
 *
 *            //error Callback
 *
 *       });
 */
/**
 * @class ATT.IMMN <b>Introduction:</b>
 *
 * The In-app Messaging from Mobile Number (IMMN) API enables your application to send and retrieve SMS and MMS messages on behalf of your customers’ regular AT&T mobile phone numbers.
 *
 * <b>Description:</b>
 *
 * The IMMN API allows applications to send and receive SMS and MMS messages on behalf of a subscriber if that subscriber has given consent. When sending a message on behalf of a subscriber,the subscriber's Mobile Subscriber Integrated Services Digital Network-Number (MSISDN) is retrieved from the subscriber’s consent information. The message is then forwarded to the recipients using the subscriber's MSISDN as the sender address. When your application sends a message for the subscriber,the AT&T system determines if the message is an SMS or MMS message, and then selects the correct transport mechanism accordingly.
 *  <br>Some major advantages of using the IMMN API are:
 * • When sending messages from your application, the Send Message method allows your customers to send text and picture messages to any U.S. mobile phone using their own AT&T phone number. Message recipients may immediately recognize who sent the message.
 * 
 * • When receiving messages in your application, the responses to the Send Message method may be received by the user’s mobile phone or by your application using the Get Message Headers and Get Message Content methods. Your application may display messages received by the user’s mobile phone, allowing the conversation to continue from within your application.
 * 
 * <b>The IMMN API provides the following methods:</b>
 *
 * • getMessageContent
 *
 * • getMessageHeaders
 *
 * • sendMessage
 */

/**
 * @method sendMessage
 * Sends messages on behalf of an AT&T subscriber. Each time the sendMessage method is invoked, a single message is sent to one or more destination devices. Messages are processed synchronously and then sent asynchronously to their destination.
 * 
 * This method sends messages as SMS or MMS as follows.
 * 
 * • For SMS: Text only, using the ISO-8859-1 format. The acceptable maximum length is 160 characters.
 * 
 * •  For MMS messages: Any attachment, or text with a length greater than 160 characters.
 *
 * This method can send messages with the following MMS content types.
 * 
 * • application/vnd.wap.multipart.mixed - without SMIL component.
 * 
 * • application/vnd.wap.multipart.related - without SMIL component.
 * 
 * • application/vnd.wap.multipart.related - with SMIL component.
 *
 * This method is limited to the following content types for MMS messages.
 * 
 * • ASCII
 * 
 * • UTF-8
 * 
 * • UTF-16
 * 
 * • ISO-8859-1
 * 
 * • JPEG
 * 
 * • GIF87a
 * 
 * • GIF89a
 * 
 * • 3GPP
 * 
 * • MP4
 * 
 * • AMR-NB
 * 
 * • SP-MIDI
 * 
 * • MP3
 * 
 * • AAC-LC
 * 
 * • V-card PIM
 * 
 * • PIM Support - vCalendar 1.0
 * 
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {Object} options.body Specifies a JSON object that contains properties for the following parameters:
 * @param {Array} options.body.Addresses Specifies one or more destination addresses. The acceptable format for this parameter is the protocol identifier (ID) followed by the URL-escaped AT&T mobile number,short code, or e-Mail address; such as tel%3A%2B16309700001, tel%3A16309700001,tel%3A6309700001, or short%3A309. The country code and preceding plus (+) symbol are optional.
 * 
 * Note: The maximum number of addresses is 10. If any address is duplicated, then the request is sent only once to that address.
 * @param {String} options.body.Subject Specifies the subject of the message.The maximum length for this parameter is 512 characters. The acceptable formats for this parameter are:
 * 
 * • ASCII
 * 
 * • UTF-16
 * 
 * • ISO-8859-1
 *
 * @param {String} options.body.Text Specifies the text portion of the message . The acceptable formats for the text for this parameter are:
 *
 *• ASCII
 *
 *• UTF-16
 *
 *• ISO-8859-1
 *
 *
 * Note: If the Attachments parameter is not specified, then this parameter must be specified..
 * @param {Array} options.attachments Specifies an Array of JSON objects that contain three-key pairs that contain the following values:
 * 
 * • fileName:"name of the file"
 * 
 * • fileType:"Type of the file"
 * 
 * • filePath:"Path of the file"
 * The file may be an image, audio, video, or text file.
 * 
 * @param {String} options.contentType Specifies the format of the message content. Acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 *• application/x-www-form-urlencoded

 * @param {String} options.accept Specifies the format of the body for the response object.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the response. The format of the returned object is specified by the value of the accept parameter in the request.
 *
 * <strong>Examples:</strong>
 *
 * <b>Example 1.</b> The following example of the sendMessage method sends JSON formatted “Hello world” message to a single device..
 *      sendMessage({
 *                   "body":{ "Addresses": ["mail@mail.mail","mail@mail.mail","tel:xxxxxxxxxx"], "Text": "Hello world", "Subject": "Hi" },
 *                   "contentType" : "application/json",
 *                   "accept" : "application/json",
 *                   "attachments" : [{'fileName' : "Name of the file",'fileType' : "type of the file like : image/png",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}, {'fileName' : "Name of the file",'fileType' : "type of the file like : image/jpeg",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}]
 *        }, function(data) {
 *
 *           success Callback
 *
 *       }, function(error) {
 *
 *           error Callback
 *
 *      });
 * 
 * <i>Note: xxxxxxxxxx represents an AT&T wireless number</i>
 *
 * <b>Example 2.</b> The following example of the sendMessage method sends XML formatted “Hello world” message to a multiple devices.
 *      sendMessage({
 *                    "body":"<MessageRequest>"+"\n"+"<Addresses>tel:xxxxxxxxxx,tel:xxxxxxxxxx,mail@mail.mail</Addresses>"+"\n"+"<Text>Hello world</Text>"+"\n"+"<Subject>Hi</Subject>"+"\n"+"</MessageRequest>",
 *                    "contentType" : "application/xml",
 *                    "accept" : "application/json",
 *                   "attachments" : [{'fileName' : "Name of the file",'fileType' : "type of the file like : image/png",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}, {'fileName' : "Name of the file",'fileType' : "type of the file like : image/jpeg",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}]
 *        }, function(data) {
 *
 *           success Callback
 *
 *       }, function(error) {
 *
 *           error Callback
 *
 *       });
 *
 *<i>Note: xxxxxxxxxx represents an AT&T wireless number</i>
 * 
 * <b>Example 3.</b> TThe following example of the sendMessage method sends a url-encoded “Hello world” message.
 *
 *      sendMessage({
 *                   "body":"Addresses=tel%3A%2Bxxxxxxxxxx&Addresses=tel%3A%2Bxxxxxxxxxx&Addresses=mail@mail.mail&Text=Hello World&Subject=Hi",
 *                   "contentType" : "application/x-www-form-urlencoded",
 *                   "accept" : "application/json",
 *                   "attachments" : [{'fileName' : "Name of the file",'fileType' : "type of the file like : image/png",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}, {'fileName' : "Name of the file",'fileType' : "type of the file like : image/jpeg",'filePath' :"Path of the file","fileObject":"base-64 encoded string of file object"}]
 *        }, function(data) {
 *
 *           success Callback
 *
 *       }, function(error) {
 *
 *           error Callback
 *
 *       });
 *
 * <i>Note: xxxxxxxxxx represents an AT&T wireless number</i>
 */

/**
 * @method getMessageHeaders
 * Retrieves metadata for one or more Subscriber Messages from the AT&T Messages environment.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.headerCount Specifies the number of headers to return:
 * 
 * The acceptable values for this parameter are 1 to 500.
 * 
 * The value of the headerCount parameter is relative to the most recent Subscriber message received.
 * 
 * If the indexCursor parameter is specified, headerCount starts with the first message defined by indexCursor.
 *
 * @param {String} [options.indexCursor] Specifies the index value for which the HeaderCount parameter begins the count.
 * 
 * The acceptable values for this parameter are:
 * 
 * • t:13313:
 * 
 * • S:558: 
 * 
 * • r:16993:
 * 
 * • I:286:
 * 
 * 
 * Each delimited value in the string represents a specific category. t:13313: Represents SMS, MMS, read and messages that were sent from the end user’s number. S:558: Represents MMS, read and messages that were sent from the end user’s number. I:286:Represents MMS, Unread and messages that were sent to the end-user’s number.r:16993: Represents SMS, Unread and messages that were sent to the end user’s number.
 * @param {String} [options.accept] Specifies the format of the body for the response object.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * 
 * @param {String} options.contentType Specifies the representation format for the request. The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 *• application/x-www-form-urlencoded
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the requested headers. The format of the returned object is specified by the value of the accept parameter in the request.
 *
 * <strong>Example:</strong>
 *
 * The following is an example of the Get Message Headers method that returns the metadata for 100 headers starting at an index of 123.
 *
 *          getMessageHeaders({
 *                  'accept' : 'application/json',
 *                  'headerCount' : 100  // Should be integer
 *                  'indexCursor' : '123' //Index value for which which HeaderCount starts.
 *           }, function(data) {
 *
 *               success Callback
 *
 *           }, function(error) {
 *
 *               error Callback
 *
 *           });
 */

/**
 * @method getMessageContent
 * Retrieves media for one or more subscriber messages from the AT&T Messages environment using information that is returned by the Get Message Headers method.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.messageId Specifies a message identifier that represents a Subscriber Message in the AT&T Messages environment.
 * @param {String} [options.partNumber] Specifies a content identifier that represents an attachment in the referenced Subscriber Message.
 * @param {String} [options.accept] Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 *
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the response to the request. The format of the returned object is specified by the value of the accept parameter in the call to the getMessageContent method.
 *
 * <strong>Example:</strong>
 *
 * The following is an example of the Get Message Content method that uses the message identifier returned by the Send Message method.
 *
 *      getMessageContent({
 *                  'messageId' : "IMMN message ID that you get in response of sendMessage",
 *                  'partNumber' : '1'
 *       }, function(data) {
 *
 *           success Callback
 *
 *       }, function(error) {
 *
 *           error Callback
 *
 *       });
 */

/**
 * @class ATT.Location <b>Introduction</b>
 *
 * The Location API enables you to provide your customers with location aware applications that may be crucial to the services that you offer. For example, adding location awareness to your application allows you to engage customers by enriching social interactions, supporting location aware advertising, providing location aware search, and giving directions.
 *
 *<b>Description</b>
 *
 * The Location API enables authorized applications to perform the following methods.
 * 
 * • getDeviceLocation
 */

/**
 * @method getDeviceLocation
 * Returns information about the location information of a device.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {Number} [options.requestedAccuracy] Specifies the accuracy of the device location in meters. The value of this parameter must be within the range of 0 to 20000. 
 * 
 * The default value for this parameter is: 100
 * 
 * This parameter value is also used by the API gateway to determine which location technology is used to locate the specified device.
 *
 *- 0 - 800  Assited GPS (A-GPS)
 *
 *- 801-9999 Enhanced Cell-Id (ECID)
 *
 *- 10000 - 20000 Cell-Id (CID)
 * 
 *
 * @param {String} [options.tolerance] Specifies if the response time of the method is changed due to the specified accuracy. When this parameter is used, it must be present in the body as a query parameter.
 *
 *  The acceptable values for this parameter are:
 *
 *- NoDelay - The response time is not changed.
 *
 *- LowDelay – The response time could have a minimum delay for better accuracy.
 *
 *- DelayTolerant - The response time could be significantly delayed for better accuracy.
 * 
 * The default value for this parameter is: 'LowDelay'
 *
 * @param {Number} [options.acceptableAccuracy] Specifies an acceptable limit of accuracy for the value of the requestedaccuracy parameter.
 * 
 * The default value for this parameter is: 10000
 *
 * If the Network is unable to return a response with an accuracy that is less than or greater than the value specified in the requestedaccuracy parameter, then the “Accuracy of location is not within acceptable limit” service error (SVC0200) is returned.
 * 
 * @param {String} [options.accept] Specifies the format of the body of the object returned by this method.
 *
 *The acceptable values for this parameter are:
 *
 *- application/json
 *
 *- application/xml
 * 
 * The default value for this parameter is: 'application/json'
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the location information for the device. The format of the returned object is specified by the value of the accept parameter in the call to the getDeviceLocation method.
 *
 * <strong>Example:</strong>
 *
 * The following example of the getDeviceLocation method returns a JSON object that contains location information using an accuracy of 10 meters with an acceptable accuracy of 100 meters.
 *
 *      getDeviceLocation({
 *           'requestedAccuracy' : 10,
 *           'tolerance' : 'LowDelay',
 *           'acceptableAccuracy' : 100
 *      },function(data) {
 *
 *         success Callback
 *
 *      }, function(error) {
 *
 *          error Callback
 *
 *      });
 *
 */

/**
 * @class ATT.Speech <b>Introduction:</b>
 *
 * The Speech API enables authorized applications to transcribe speech (audio) to text. In addition, your application may influence the results by passing a speech context in the speechToText method.
 *
 * <b>Description:</b>
 *
 * The Speech API is a service that accepts audio data and returns the text representation of that audio. The text output may then be processed by different speech contexts to produce text results that represent that speech context. Each speech context is tuned to produce results more suitable for a given scenario.
 * 
 * <b>The Speech API provides the following methods:</b>
 *
 *• speechToText
 */

/**
 * @method speechToText
 * Returns a text translation of the specified audio file using the specified speech context. The audio file must have one of the following sets of constraints: 16 bit PCM WAV, single channel, 8 kHz sampling, or AMR (narrowband), 12.2 kbit/s, 8 kHz sampling.
 *
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.filePath Specifies the path of the audio file that is translated to text. (For the Android platform, the path should be in the following format: /mnt/sdcard/.../fileName.fileExtension).
 * @param {String} [options.transferEncoding] Specifies how the audio file is encoded when transferred. This parameter is required only for a streaming request, and should be set to the following value:chunked
 * @param {String} [options.xSpeechContext="Generic"] Specifies the speech context that is applied to the transcribed text. Acceptable values for this property are:
 *
 *• Generic
 *
 *• TV
 *
 *• BusinessSearch
 *
 *• Websearch
 *
 *• SMS
 *
 *• Voicemail
 *
 *• QuestionAndAnswer
 *
 *• Gaming
 *
 *• SocialMedia
 *
 * If <b>TV</b> context is chosen, then the X-Arg parameter <b>Search</b> must be defined.
 * @param {String} options.contentType The content type of the request. Valid values are:
 *
 *• audio/wav (or audio/x-wav)
 *
 *• audio/amr
 *
 *• audio/amr-wb
 *
 *• audio/x-speex
 *
 *• audio/x-speex-with-header-byte;rate=16000
 *
 *• audio/x-speex-with-header-byte;rate=8000
 *
 *• audio/raw;coding=linear;rate=16000;byteorder=LE
 *
 *• audio/raw;coding=linear;rate=16000;byteorder=BE
 *
 *• audio/raw;coding=linear;rate=8000;byteorder=LE
 *
 *• audio/raw;coding=linear;rate=8000;byteorder=BE
 *
 *• audio/raw;coding=ulaw;rate=16000
 *
 *• audio/raw;coding=ulaw;rate=8000
 *
 *Note: the API Gateway shall not modify this header when passed onto the Speech enabler, except to remove white space in order to ensure consistent use of parameters.
 * @param {String} [options.accept="application/json"] Specifies the format of the body of the object returned by this method.
 * 
 * The acceptable values for this parameter are:
 *
 *• application/json
 *
 *• application/xml
 * @param {Number} options.contentLength <b>(IOS only).</b>This property is required only for a <b>non-streaming request.</b>
 * @param {String} [options.contentLanguage="en-US"] Only Specifies the language of the submitted audio.  Only used when xSpeechContext parameter is set to “Generic”.  Choose one of the following two choices:
 * 
 *• "en-US" (English - United States)
 * 
 *• "es-US" (Spanish - United States)
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON or XML formatted object that contains the transcribed text. The format of the returned object is specified by the value of the accept parameter in the call to the speechToText method.
 *
 * <b>Example:</b>
 *
 * The following is an example of the speechToText method.
 *
 *       speechToText({
 *           'filePath':'Path of audio file'//In android,Path should be like : /mnt/sdcard/.../example.wav.
 *           'xSpeechContext' : 'Generic',
 *           'contentType' : 'audio/wav',
 *           'contentLength' : length,//For IOS only.
 *           'accept':'application/json',
 *           'contentLanguage': 'es-US'
 *      },function(data) {
 *
 *         //success Callback
 *
 *      }, function(error) {
 *
 *          //error Callback
 *
 *      });
 */
 
 /**
 * @class ATT.Ads <b>Introduction:</b>
 *
 * The Ads API enables authorized applications to receive pay per click advertisements to be placed within their app.
 *
 * <b>Description:</b>
 *
 * The Advertisement API is a service that returns advertisements enabling the developer to insert the advertisements into their application.
 * 
 * <b>The Ads API provides the following methods:</b>
 *
 *• getAds
 */

/**
 * @method getAds
 * This operation returns an advertisement to the application.
 * 
 * All of the data is received through the callbacks.
 * The success callback will receive A JSON or XML formatted object that contains the content for the ad to be displayed.
 * The format of the returned object is specified by the value of the accept parameter in the call to the getAds method.
 *
 * <b>Example:</b>
 *
 * The following is an example of the getAds method.
 *
 *      getAds({
 *          accept: "application/json",
 *          udid: "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX",
 *          body: {
 *              Category: "auto",
 *          },
 *          accept: "application/json"
 *      },function(data) {
 *      
 *         //success Callback
 *      
 *      }, function(error) {
 *      
 *          //error Callback
 *      
 *      });
 *
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.udid Required field that specifies the Merchant ID associated with your app. The UDID Parameter is used for ad tracking purpose.
 * @param {String} [options.accept="application/json"] Specifies the format of the body of the response. Valid values are:
 *
 * • application/json
 *
 * • application/xml
 * @param {String/Object} options.body Specifies parameters for the ads request in either a either a flat object of a URI Encoded string
 * @param {String} options.body.Category Specifies the category for the advertisement
 * 
 * The acceptable values for this parameter are:
 * 
 * • auto
 * 
 * • business
 * 
 * • finance
 * 
 * • chat
 * 
 * • community
 * 
 * • social
 * 
 * • personals
 * 
 * • communication
 * 
 * • technology
 * 
 * • games
 * 
 * • health
 * 
 * • medical
 * 
 * • maps
 * 
 * • local
 * 
 * • entertainment
 * 
 * • movies
 * 
 * • tv
 * 
 * • music
 * 
 * • photos
 * 
 * • video
 * 
 * • news
 * 
 * • weather
 * 
 * • sports
 * 
 * • shopping
 * 
 * • tools
 * 
 * • travel
 * 
 * • other
 * @param {String} [options.body.Gender] The Gender of the demographic audience of the app.
 * For Male use “M” and for Female use “F”.  This would be an optional run time parameter
 * @param {Number} [options.body.ZipCode] Zip/Postal code of a user. For US only.
 * @param {Number} [options.body.Number] Area code of a user.  For US only.
 * @param {String} [options.body.City] The City (with state) of the device user. For US only.
 * @param {String} [options.body.Country] Country of visitor. Overrides the country detected by IP.
 * An ISO 3166 code to be used for specifying a country code.
 * @param {Number} [options.body.Longitude] User location latitude value (in degrees).
 * @param {Number} [options.body.Latitude] User location latitude value (in degrees).
 * @param {Number} [options.body.MaxHeight] Maximum height of the ad banner to be shown.
 * @param {Number} [options.body.MaxWidth] Maximum width of the ad banner to be shown.
 * @param {Number} [options.body.MinHeight] Minimum height of the ad banner to be shown.
 * @param {Number} [options.body.MinWidth] Minimum width of the ad banner to be shown.
 * @param {Number} [options.body.Type] Type of ads to be returned (1 – text, 2 – image, ).
 * You can set different combinations with these values. For example, 3 = 1 + 2 (text + image).
 * Only type =3 will be supported in this release . So the response could be either text or image Ad.
 * @param {String} [options.body.AgeGroup]
 * The age group of the demographic audience of the app. Choose one of the following:
 * 
 * • 1-13
 * 
 * • 14-25
 * 
 * • 26-35
 * 
 * • 36-55
 * 
 * • 55-100
 * @param {Number} [options.body.Over18] Filter ads by over 18 content: 0 or 1 — deny over 18 content, 2 — only over 18 content, 3 - allow all ads.
 * @param {String} [options.body.Keywords] Filter ads by keywords (delimited by keywords=music, commas).
 * @param {Boolean} [options.body.IsSizeRequired] If set true, the image size values (width and height) will be returned in html.
 * @param {Number} [options.body.Premium=0] Filter ads by premium status (0 – non- premium, 1 – premium only, 2 – both). Default value is 0.
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 */

/**
 * @class ATT.TTS <b>Introduction:</b>
 *
 * The Text to Speech API name speaks for itself, yes it converts the text what you send as a audio file.
 *
 * <b>Description:</b>
 *
 * The Text to Speech API is a service that accepts text and returns the audio content of what you sent as text. The Speech output may then be translated as per the language and the text sent will be generated as a audio file by the character's specified.
 * 
 * <b>The TTS API provides the following method:</b>
 *
 *• textToSpeech
 */

/**
 * @method textToSpeech
 * Returns a audio file in a particular voice and language for the text sent.
 * @param {Object} options Specifies a JSON object that contains properties for the following parameters:
 * @param {String} options.accept Specifies what is audio file type that has to be generated. These are following audio types that can be requested for audio generation audio/amr, audio/amr-wb and audio/x-wav.
 * @param {String} options.contentType Specifies the format in which the content is being transmitted in the body. Following are the Content Types that are supported text/plain and application/ssml+xml.
 * @param {String} options.body Specifies the text content that needs to be converted to audio file.
 * @param {String} options.filePath Specifies the path where the audio file has to be stored after the text translation. (For the Android platform, the path should be in the following format: /mnt/sdcard/.../fileName.fileExtension).
 * @param {String} [options.xarg="VoiceName=crystal"] This parameter exposes the properties, through which the audio file generation can have few variations like changing the audio voice character, Tempo of the audio file and volume of the audio file when generated. Acceptable values for this property are
 *
 *• VoiceName
 *
 *• Tempo
 *
 *• Volume
 *
 *• AppId
 *
 *• OrgId
 *
 *• ClientApp
 *
 *• Client Version
 *
 *• Client Screen
 *
 * If <b>VoiceName</b> to be specified in particular then this property has to be updated with the preset voice characters.
 *
 *• mike
 *
 *• crystal
 *
 *• rosa
 *
 *• alberto
 *
 * @param {String} [options.accept="application/json"] Specifies the format of the audio file that by this method. These are following audio types are :audio/amr, audio/amr-wb and audio/x-wav.
 * 
 * The acceptable values for this parameter are:
 *
 *• audio/wav
 *
 *• audio/amr
 *
 *• audio/amr-wb
 *
 * @param {Function} success The callback function that is called when the method returns success.
 * @param {Function} error The callback function that is called when the method returns an error.
 * @return {Object} A JSON formatted object that contains the path of the audio file. The filepath returned with file name and extension is specified by the value of the accept parameter in the call to the textToSpeech method.
 *
 * <b>Example:</b>
 *
 * The following is an example for textToSpeech request method.
 *
 *		textToSpeech({
 *			'accept' : 'audio/wav',
 *			'contentType' : text/plain,
 *			'body':text,
 *			"filePath" : filePath,
 *			'xarg' : "VoiceName=crystal"
 *
 *		}, function(data) {
 *			// success CallBack
 *		}, function(error) {
 *		    //failure Callback
 *		});
 *       
 */
