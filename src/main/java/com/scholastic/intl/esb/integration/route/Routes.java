package com.scholastic.intl.esb.integration.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.scholastic.intl.esb.integration.resource.AddParentCustRvaSucResponse;
import com.scholastic.intl.esb.integration.resource.AddParentCustSnippResponse;

@Component
public class Routes extends RouteBuilder {

	private final Logger logger = LoggerFactory.getLogger(Routes.class);

	@Override
	public void configure() throws Exception {
		
		from("direct:ParentCustomerAdd").streamCaching()
		.to("log:incomingNetsuiteReq_CompleteLog?level=INFO&showAll=true&multiline=true").choice()
		/*.when(header("Interface_Name").isEqualTo("anz-customer-interface-parentCustSnipp"))
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.setHeader("Authorization",
				constant("{{SnippAuthToken}}"))
		.to("log:JustBeforeHit_CompleteLog?level=INFO&showAll=true&multiline=true")
		.to("{{SnippEndpoint}}")
		.to("log:snippResponse_CompleteLog?level=INFO&showAll=true&multiline=true")
		.choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Snipp add....\n ${body}")
		.unmarshal()
		//.string()
		.json(JsonLibrary.Jackson, AddParentCustSnippResponse.class)
		.endChoice()
		.otherwise()
		.log("Error occured while adding to Snipp : ${body}").endChoice().endChoice()*/
		.when(header("Interface_Name").isEqualTo("anz-customer-interface-parentCustRva"))
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.setHeader("X-API-Version", constant("v1"))
		.setHeader("Authorization",constant("{{RvaAuthToken}}"))
		.to("log:JustBeforeHit_CompleteLog?level=INFO&showAll=true&multiline=true")
		.to("{{RvaEndpoint}}")
		.to("log:rvaResponse_CompleteLog?level=INFO&showAll=true&multiline=true")
		.choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Rva add....\n ${body}")
		.unmarshal()
		//.string()
		.json(JsonLibrary.Jackson, AddParentCustRvaSucResponse.class)
		.endChoice()
		.otherwise()
		.log("Error occured while adding to Rva : ${body}").endChoice().endChoice()
		.when(header("Interface_Name").isEqualTo("anz-customer-interface-parentCustJade"))
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("xml"))
		.setHeader("SOAPAction", constant("addParent"))
		.to("{{JadeEndpoint}}")
		.to("log:jadeResponse_CompleteLog?level=INFO&showAll=true&multiline=true")
		.choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Jade add....\n ${body}")
		.unmarshal()
		.string()
		.endChoice()
		.otherwise()
		.log("Error occured while adding to Jade : ${body}").endChoice().endChoice()
		.when(header("Interface_Name").isEqualTo("anz-customer-interface-parentCustJadeAus"))
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("xml"))
		.setHeader("SOAPAction", constant("addParent"))
		.to("{{JadeEndpointAus}}")
		.to("log:jadeResponse_CompleteLog?level=INFO&showAll=true&multiline=true")
		.choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Jade add....\n ${body}")
		.unmarshal()
		.string()
		.endChoice()
		.otherwise()
		.log("Error occured while adding to Jade : ${body}").endChoice().endChoice()
		.end();
		
		/*from("direct:callSnippForUpdate").streamCaching()
		.to("log:incomingNetsuiteReq_CompleteLog?level=INFO&showAll=true&multiline=true")
		.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.setHeader("Authorization", simple("${header.Authorization}"))
		.to("log:JustBeforeHit_CompleteLog?level=INFO&showAll=true&multiline=true")
		//.toD("http://uat.snipp.ie/scholasticmigrationtest/member/${header.MemberId}")
		.toD("${header.DynamicUrlSnipp}")
		//.to("http://uat.snipp.ie/scholasticmigrationtest/member/2203439")
		.to("log:snippResponse_CompleteLog?level=INFO&showAll=true&multiline=true")
		.choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Snipp update....\n ${body}")
		.unmarshal()
		//.string()
		.json(JsonLibrary.Jackson, AddParentCustSnippResponse.class)
		.endChoice()
		.otherwise()
		.log("Error occured while updating Snipp : ${body}").endChoice()
		.end();*/
		
		from("direct:callRvaForUpdate").streamCaching()
		.to("log:incomingRvaUpdateReq_CompleteLog?level=INFO&showAll=true&multiline=true")
		.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
		.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.setHeader("X-API-Version", constant("v1"))
		.setHeader("Authorization",constant("{{RvaAuthToken}}"))
		.to("log:JustBeforeHit_CompleteLog?level=INFO&showAll=true&multiline=true")
		//.toD("http://uat.snipp.ie/scholasticmigrationtest/member/${header.MemberId}")
		//actual one
		//.toD("${header.DynamicUrlSnipp}")
		//.to("http://uat.snipp.ie/scholasticmigrationtest/member/2203439")
		.to("{{RvaEndpoint}}")
		.to("log:rvaUpdateResponse_CompleteLog?level=INFO&showAll=true&multiline=true")
		.choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Rva update....\n ${body}")
		.unmarshal()
		//.string()
		.json(JsonLibrary.Jackson, AddParentCustRvaSucResponse.class)
		.endChoice()
		.otherwise()
		.log("Error occured while updating Rva : ${body}").endChoice()
		.end();
		
		from("direct:callJadeForUpdate").streamCaching()
		.to("log:incomingNetsuiteReq_CompleteLog?level=INFO&showAll=true&multiline=true")
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("xml"))
		.setHeader("SOAPAction", constant("updateParent"))
		.to("{{JadeEndpoint}}")
		.to("log:jadeResponse_CompleteLog?level=INFO&showAll=true&multiline=true")
		.choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Jade update....\n ${body}")
		.unmarshal()
		.string()
		.endChoice()
		.otherwise()
		.log("Error occured while updating Jade : ${body}").endChoice()
		.end();
		
		from("direct:callJadeForUpdateAus").streamCaching()
		.to("log:incomingNetsuiteReq_CompleteLog?level=INFO&showAll=true&multiline=true")
		.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		.setHeader(Exchange.CONTENT_TYPE, constant("xml"))
		.setHeader("SOAPAction", constant("updateParent"))
		.to("{{JadeEndpointAus}}")
		.to("log:jadeResponse_CompleteLog?level=INFO&showAll=true&multiline=true")
		.choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Jade update....\n ${body}")
		.unmarshal()
		.string()
		.endChoice()
		.otherwise()
		.log("Error occured while updating Jade : ${body}").endChoice()
		.end();
		
		from("direct:addToQueueForInsertChildCust")
		.streamCaching()
		.to("log:incomingInsertChildtoNS_CompleteLog?level=INFO&showAll=true&multiline=true")
				.setHeader("INTERFACE_NAME", constant("anz-customer-interface-insertChild"))
				.to("log:incomingInsertChildtoNS_CompleteLog2?level=INFO&showAll=true&multiline=true")
				.to("jms:{{Qendpoint.nzQEndpoint}}").setBody(constant("InsertChildCustomer has been inserted to Queue Successfully"))
		.end();
		
		from("direct:addToQueueForUpdateChildCust")
		.streamCaching()
		.to("log:incomingUpdateChildtoNS_CompleteLog?level=INFO&showAll=true&multiline=true")
				.setHeader("INTERFACE_NAME", constant("anz-customer-interface-updateChild"))
				.to("log:incomingUpdateChildtoNS_CompleteLog2?level=INFO&showAll=true&multiline=true")
				.to("jms:{{Qendpoint.nzQEndpoint}}").setBody(constant("UpdateChildCustomer has been inserted to Queue Successfully"))
		.end();
		
		from("direct:updateBackToPalm").streamCaching()
		.to("log:incomingUpdateRequest_CompleteLog?level=INFO&showAll=true&multiline=true").choice()
		.when(header("Interface_Name").isEqualTo("anz-customer-interface-insertChild"))
		.setHeader(Exchange.HTTP_METHOD, constant("POST")).setHeader(Exchange.CONTENT_TYPE, constant("xml"))
		.setHeader("SOAPAction", constant("updateClient")).to("{{InsertCustomerUpdateEndpoint}}").choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Palm update....\n ${body}").unmarshal().string().endChoice()
		.otherwise()
		// HTTP status >= 300 : throws an exception when
		// "throwExceptionOnFailure=true" (by default)
		.log("Error occured while updating Palm : ${body}").endChoice()
		.when(header("Interface_Name").isEqualTo("anz-customer-interface-insertChildAus"))
		.setHeader(Exchange.HTTP_METHOD, constant("POST")).setHeader(Exchange.CONTENT_TYPE, constant("xml"))
		.setHeader("SOAPAction", constant("updateClient")).to("{{JadeEndpointAus}}").choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Palm update....\n ${body}").unmarshal().string().endChoice()
		.otherwise()
		// HTTP status >= 300 : throws an exception when
		// "throwExceptionOnFailure=true" (by default)
		.log("Error occured while updating Palm : ${body}").endChoice()
		.when(header("Interface_Name").isEqualTo("anz-customer-interface-updateChild"))
		.setHeader(Exchange.HTTP_METHOD, constant("POST")).setHeader(Exchange.CONTENT_TYPE, constant("xml"))
		.setHeader("SOAPAction", constant("updateClient")).to("{{InsertCustomerUpdateEndpoint}}").choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Palm update....\n ${body}").unmarshal().string().endChoice()
		.otherwise()
		// HTTP status >= 300 : throws an exception when
		// "throwExceptionOnFailure=true" (by default)
		.log("Error occured while updating Palm : ${body}").endChoice()
		.when(header("Interface_Name").isEqualTo("anz-customer-interface-updateChildAus"))
		.setHeader(Exchange.HTTP_METHOD, constant("POST")).setHeader(Exchange.CONTENT_TYPE, constant("xml"))
		.setHeader("SOAPAction", constant("updateClient")).to("{{JadeEndpointAus}}").choice()
		.when(header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300))
		.log("Response Received after Palm update....\n ${body}").unmarshal().string().endChoice()
		.otherwise()
		// HTTP status >= 300 : throws an exception when
		// "throwExceptionOnFailure=true" (by default)
		.log("Error occured while updating Palm : ${body}").endChoice()
		.end();
		
		from("direct:addToQueueFailureQueue").streamCaching()
		.to("log:incoming_failure_CompleteLog?level=INFO&showAll=true&multiline=true")
		.setHeader("INTERFACE_NAME", constant("anz-customer-interface"))
		.to("log:incoming_failure_CompleteLog2?level=INFO&showAll=true&multiline=true")
		.to("jms:{{Qendpoint.anzFailureQEndpoint}}")
		.log("Customer failed request has been added to reprocessing queue successfully")
		.setBody(constant("Customer has been inserted to Queue Successfully"))
		.end();
		
	}
	
}
