package com.att.eg.policy.notifications.topic_adapter.camel.component;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

@Component
public class PolicyJmsComponent extends JmsComponent {

	@Autowired
	private CamelContext camelContext;

	@Value("${policynotification.jms.weblogic.url}")
	private String webLogicUrl;

	@Value("${policynotification.jms.context.factory}")
	private String jmsContextFactory;

	@Value("${policynotification.jms.connection.factory}")
	private String jmsConnectionFactory;

	@Value("${policynotification.jms.topic}")
	private String jmsTopic;

	@Value("${policynotification.jms.topic.clientid}")
	private String jmsClientId;

	@Value("${policynotification.jms.topic.subscriptionname}")
	private String jmsSubscriptionName;

	@Value("${policynotification.jms.topic.subscriptiondurable.enabled}")
	private boolean isSubscritionDurable;

	@Value("${kafka.sasl.jaas.config.path}")
	private String jaasConfigPath;

	@Value("${krb5.debug.enabled}")
	private boolean isKrb5DebugModeEnabled;

	@Value("${kafka.sasl.krb.config.path}")
	private String krbConfigPath;

	@PostConstruct
	private void init() throws NamingException {

		System.setProperty("java.security.auth.login.config", jaasConfigPath);
		System.setProperty("sun.security.krb5.debug", Boolean.toString(isKrb5DebugModeEnabled));
		System.setProperty("java.security.krb5.conf", krbConfigPath);

		JmsConfiguration jmsConfig = new JmsConfiguration();

		Properties environmentProps = new Properties();
		environmentProps.put(Context.INITIAL_CONTEXT_FACTORY, jmsContextFactory);
		environmentProps.put(Context.PROVIDER_URL, webLogicUrl);
		environmentProps.put(Context.SECURITY_PRINCIPAL, "");
		environmentProps.put(Context.SECURITY_CREDENTIALS, "");

		JndiTemplate jndiTemplate = new JndiTemplate();
		jndiTemplate.setEnvironment(environmentProps);

		JndiDestinationResolver destinationResolver = new JndiDestinationResolver();
		destinationResolver.setJndiTemplate(jndiTemplate);

		TopicConnectionFactory topiConnectionFactory = (TopicConnectionFactory) jndiTemplate
				.lookup(jmsConnectionFactory);

		jmsConfig.setConnectionFactory(topiConnectionFactory);
		jmsConfig.setDestinationResolver(destinationResolver);

		if (isSubscritionDurable) {
			jmsConfig.setSubscriptionDurable(isSubscritionDurable);
			jmsConfig.setClientId(jmsClientId);
			jmsConfig.setSubscriptionName(jmsSubscriptionName);
		}

		jmsConfig.setAcknowledgementMode(Session.AUTO_ACKNOWLEDGE);

		setConfiguration(jmsConfig);

		camelContext.addComponent("policyjmscomponent", this);
		camelContext.disableJMX();

	}

	public void publishMessage(String message) throws NamingException, JMSException {
		Properties environmentProps = new Properties();
		environmentProps.put(Context.INITIAL_CONTEXT_FACTORY, jmsContextFactory);
		environmentProps.put(Context.PROVIDER_URL, webLogicUrl);
		environmentProps.put(Context.SECURITY_PRINCIPAL, "");
		environmentProps.put(Context.SECURITY_CREDENTIALS, "");

		JndiTemplate jndiTemplate = new JndiTemplate();
		jndiTemplate.setEnvironment(environmentProps);
		
		Topic topic = (Topic) jndiTemplate.lookup(jmsTopic);
		TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) jndiTemplate
				.lookup(jmsConnectionFactory);
		TopicConnection topicConn = topicConnectionFactory.createTopicConnection();
		TopicSession topicSession = topicConn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicPublisher topicPublisher = topicSession.createPublisher(topic);
		topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);
		
		TextMessage txtMessage = topicSession.createTextMessage();
		txtMessage.setText(message);
		
		topicPublisher.publish(txtMessage);
		
		topicPublisher.close();
		topicSession.close();
		topicConn.close();

	}

}
