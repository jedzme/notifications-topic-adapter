package com.att.eg.policy.notifications.topic_adapter.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PolicyTopicRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		log.info("Creating Route: Policy LABC JMS to OV Kafka");

		from("policyjmscomponent:topic:{{policynotification.jms.topic}}").routeId("ConsumerRoute")
				.to("kafka:{{policynotification.kafka.topic}}?brokers={{policynotification.kafka.brokers}}"
						+ "&securityProtocol={{kafka.security.protocol}}"
						+ "&sslTruststoreLocation={{kafka.ssl.trust-store.location}}"
						+ "&sslTruststoreType={{kafka.ssl.trust-store-type}}"
						+ "&sslTruststorePassword={{kafka.ssl.trust-store-password}}"
						+ "&saslKerberosServiceName={{kafka.sasl.kerberos.svcname}}"
						+ "&key={{policynotification.kafka.default.key}}")
				.log("Message received from JMS : ${body}").log("Headers: ${headers}");

	}
	
}
