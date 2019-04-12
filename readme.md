# Remote JMS Server

## Overview
1. You have a local jms publisher/listener(Spring's `DefaultMessageListenerContainer`) client
1. A remote JMS server (in this case JBoss)
1. Message Consumer: 
    1. Application should continue to receive messages successfully:
    1. If remote jms server is down on startup
    1. If remote jms server goes down temporarily or if there is a temporary network outage
1. Message Publisher
    1. The message publisher should hold back message publishes with built in re-try in case the remote JMS server is down
    1. utilize Spring Retry which uses AOP to keep retrying publishes with different back-off policies. Ref: 
    
## Setup
1. locally running jboss with a couple of queues
1. queue configurations mentioned in jboss/messaging-services

## Key configurations
1. For the remote connection factory lookup `JndiObjectFactoryBean`, property `lookupOnStartup` should be set to `true`
1. Need to use a `CachingConnectionFactory` to allow re-connection on errors

## References
1. [JBoss jms connection factory lookup](https://stackoverflow.com/questions/1323489/how-to-initialize-connectionfactory-for-remote-jms-queue-when-remote-machine-is)
1. [JBoss jnp naming context factory doc](https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Web_Platform/5/html/Administration_And_Configuration_Guide/Naming_on_JBoss-The_Naming_InitialContext_Factories.html)
1. [Tuning Spring jms message listener message consumption](https://uberconf.com/blog/bruce_snyder/2011/08/tuning_jms_message_consumption_in_spring)
1. [JBoss 5 Connection Factory custom configuration](https://access.redhat.com/documentation/en-us/jboss_enterprise_application_platform/5/html/messaging_user_guide/conf.connectionfactory)

## Failure and recovery logs
### Remote jms server is down, jms client app is brought up
```

INFO : [11:39:58:058 MST] main amitk.poc.jms.remote.down.OrderMessageListenerTest - Context loaded
INFO : [11:40:03:003 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - JMS message listener invoker needs to establish shared Connection
INFO : [11:40:03:003 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - JMS message listener invoker needs to establish shared Connection
ERROR: [11:40:03:003 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.CommunicationException: Could not obtain connection to any of these urls: localhost:1099 [Root exception is javax.naming.CommunicationException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is javax.naming.ServiceUnavailableException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is java.net.ConnectException: Connection refused (Connection refused)]]]
ERROR: [11:40:03:003 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.CommunicationException: Could not obtain connection to any of these urls: localhost:1099 [Root exception is javax.naming.CommunicationException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is javax.naming.ServiceUnavailableException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is java.net.ConnectException: Connection refused (Connection refused)]]]
ERROR: [11:40:08:008 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.CommunicationException: Could not obtain connection to any of these urls: localhost:1099 [Root exception is javax.naming.CommunicationException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is javax.naming.ServiceUnavailableException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is java.net.ConnectException: Connection refused (Connection refused)]]]
ERROR: [11:40:08:008 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.CommunicationException: Could not obtain connection to any of these urls: localhost:1099 [Root exception is javax.naming.CommunicationException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is javax.naming.ServiceUnavailableException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is java.net.ConnectException: Connection refused (Connection refused)]]]
ERROR: [11:40:14:014 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
ERROR: [11:40:14:014 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
ERROR: [11:40:19:019 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
ERROR: [11:40:19:019 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
ERROR: [11:40:24:024 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
ERROR: [11:40:24:024 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
ERROR: [11:40:29:029 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
ERROR: [11:40:29:029 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
```
### Remote jms server is brought up for the first time since the jms client started 
```
INFO : [11:40:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.connection.CachingConnectionFactory - Established shared JMS Connection: JBossConnection->ConnectionDelegate[1837016167, ID=5c-j7p2feuj-1-p862feuj-biizts-m10a, SID=1]
INFO : [11:40:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.connection.CachingConnectionFactory - Established shared JMS Connection: JBossConnection->ConnectionDelegate[1837016167, ID=5c-j7p2feuj-1-p862feuj-biizts-m10a, SID=1]
INFO : [11:40:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Successfully refreshed JMS Connection
INFO : [11:40:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-1 org.springframework.jms.listener.DefaultMessageListenerContainer - Successfully refreshed JMS Connection

...

 
INFO : [11:41:28:028 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 amitk.poc.jms.remote.down.OrderMessageListener - Received Order: Message 40
INFO : [11:41:31:031 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 amitk.poc.jms.remote.down.OrderMessageListener - Received Order: Message 41

```
### Remote jms server is brought down, jms client app is still up
```
WARN : [11:41:34:034 MST] Timer-1 org.jboss.remoting.LeasePinger - org.jboss.remoting.LeasePinger$LeaseTimerTask@523b38bb failed to ping to server: Can not get connection to server. Problem establishing socket connection for InvokerLocator [bisocket://amitkapps:4457/?JBM_clientMaxPoolSize=200&clientLeasePeriod=10000&clientSocketClass=org.jboss.jms.client.remoting.ClientSocketWrapper&dataType=jms&enableTcpNoDelay=true&failureDisconnectTimeout=0&generalizeSocketException=true&marshaller=org.jboss.jms.wireformat.JMSWireFormat&numberOfCallRetries=2&pingFrequency=30000&pingWindowFactor=10&socket.check_connection=false&stopLeaseOnFailure=true&timeout=30000&unmarshaller=org.jboss.jms.wireformat.JMSWireFormat&useClientConnectionIdentity=true&validatorPingPeriod=10000&validatorPingTimeout=5000&writeTimeout=30000]
WARN : [11:41:34:034 MST] Thread-11 org.springframework.jms.connection.CachingConnectionFactory - Encountered a JMSException - resetting the underlying JMS Connection
javax.jms.JMSException: Failure on underlying remoting connection
	at org.jboss.jms.client.remoting.ConsolidatedRemotingConnectionListener.handleConnectionException(ConsolidatedRemotingConnectionListener.java:117)
	at org.jboss.remoting.ConnectionValidator$3.run(ConnectionValidator.java:524)
WARN : [11:41:34:034 MST] Thread-11 org.springframework.jms.connection.CachingConnectionFactory - Encountered a JMSException - resetting the underlying JMS Connection
javax.jms.JMSException: Failure on underlying remoting connection
	at org.jboss.jms.client.remoting.ConsolidatedRemotingConnectionListener.handleConnectionException(ConsolidatedRemotingConnectionListener.java:117)
	at org.jboss.remoting.ConnectionValidator$3.run(ConnectionValidator.java:524)
ERROR: [11:41:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.jboss.jms.client.container.ClosedInterceptor - ClosedInterceptor.ClientSessionDelegate[6c-y8p2feuj-1-p862feuj-biizts-m10a]: method getTransacted() did not go through, the interceptor is CLOSED
WARN : [11:41:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Execution of JMS message listener failed, and no ErrorHandler has been set.
javax.jms.IllegalStateException: The object is closed
	at org.jboss.jms.client.container.ClosedInterceptor.invoke(ClosedInterceptor.java:159)
	at org.jboss.aop.advice.PerInstanceInterceptor.invoke(PerInstanceInterceptor.java:86)
	at org.jboss.aop.joinpoint.MethodInvocation.invokeNext(MethodInvocation.java:102)
	at org.jboss.jms.client.delegate.ClientSessionDelegate.getTransacted(ClientSessionDelegate.java)
	at org.jboss.jms.client.JBossSession.getTransacted(JBossSession.java:154)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.jms.connection.CachingConnectionFactory$CachedSessionInvocationHandler.invoke(CachingConnectionFactory.java:355)
	at com.sun.proxy.$Proxy12.getTransacted(Unknown Source)
	at org.springframework.jms.listener.AbstractMessageListenerContainer.commitIfNecessary(AbstractMessageListenerContainer.java:591)
	at org.springframework.jms.listener.AbstractMessageListenerContainer.doExecuteListener(AbstractMessageListenerContainer.java:499)
	at org.springframework.jms.listener.AbstractPollingMessageListenerContainer.doReceiveAndExecute(AbstractPollingMessageListenerContainer.java:325)
	at org.springframework.jms.listener.AbstractPollingMessageListenerContainer.receiveAndExecute(AbstractPollingMessageListenerContainer.java:263)
	at org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.invokeListener(DefaultMessageListenerContainer.java:1103)
	at org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.executeOngoingLoop(DefaultMessageListenerContainer.java:1095)
	at org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.run(DefaultMessageListenerContainer.java:992)
	at java.lang.Thread.run(Thread.java:748)
WARN : [11:41:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Execution of JMS message listener failed, and no ErrorHandler has been set.
javax.jms.IllegalStateException: The object is closed
	at org.jboss.jms.client.container.ClosedInterceptor.invoke(ClosedInterceptor.java:159)
	at org.jboss.aop.advice.PerInstanceInterceptor.invoke(PerInstanceInterceptor.java:86)
	at org.jboss.aop.joinpoint.MethodInvocation.invokeNext(MethodInvocation.java:102)
	at org.jboss.jms.client.delegate.ClientSessionDelegate.getTransacted(ClientSessionDelegate.java)
	at org.jboss.jms.client.JBossSession.getTransacted(JBossSession.java:154)
	at sun.reflect.GeneratedMethodAccessor4.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.jms.connection.CachingConnectionFactory$CachedSessionInvocationHandler.invoke(CachingConnectionFactory.java:355)
	at com.sun.proxy.$Proxy12.getTransacted(Unknown Source)
	at org.springframework.jms.listener.AbstractMessageListenerContainer.commitIfNecessary(AbstractMessageListenerContainer.java:591)
	at org.springframework.jms.listener.AbstractMessageListenerContainer.doExecuteListener(AbstractMessageListenerContainer.java:499)
	at org.springframework.jms.listener.AbstractPollingMessageListenerContainer.doReceiveAndExecute(AbstractPollingMessageListenerContainer.java:325)
	at org.springframework.jms.listener.AbstractPollingMessageListenerContainer.receiveAndExecute(AbstractPollingMessageListenerContainer.java:263)
	at org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.invokeListener(DefaultMessageListenerContainer.java:1103)
	at org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.executeOngoingLoop(DefaultMessageListenerContainer.java:1095)
	at org.springframework.jms.listener.DefaultMessageListenerContainer$AsyncMessageListenerInvoker.run(DefaultMessageListenerContainer.java:992)
	at java.lang.Thread.run(Thread.java:748)
ERROR: [11:41:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.jboss.jms.client.container.ClosedInterceptor - ClosedInterceptor.ClientSessionDelegate[6c-y8p2feuj-1-p862feuj-biizts-m10a]: method getTransacted() did not go through, the interceptor is CLOSED
WARN : [11:41:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Setup of JMS message listener invoker failed for destination 'jms/queue/orders' - trying to recover. Cause: The object is closed
WARN : [11:41:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Setup of JMS message listener invoker failed for destination 'jms/queue/orders' - trying to recover. Cause: The object is closed
ERROR: [11:41:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.CommunicationException [Root exception is java.rmi.ConnectException: Connection refused to host: amitkapps; nested exception is: 
	java.net.ConnectException: Connection refused (Connection refused)]
ERROR: [11:41:34:034 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.CommunicationException [Root exception is java.rmi.ConnectException: Connection refused to host: amitkapps; nested exception is: 
	java.net.ConnectException: Connection refused (Connection refused)]
ERROR: [11:41:39:039 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.CommunicationException: Could not obtain connection to any of these urls: localhost:1099 [Root exception is javax.naming.CommunicationException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is javax.naming.ServiceUnavailableException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is java.net.ConnectException: Connection refused (Connection refused)]]]
ERROR: [11:41:39:039 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.CommunicationException: Could not obtain connection to any of these urls: localhost:1099 [Root exception is javax.naming.CommunicationException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is javax.naming.ServiceUnavailableException: Failed to connect to server localhost/127.0.0.1:1099 [Root exception is java.net.ConnectException: Connection refused (Connection refused)]]]
...
```
### Remote jms server is brought back up again, jms client app is still up and running
```
ERROR: [11:50:21:021 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
ERROR: [11:50:21:021 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Could not refresh JMS Connection for destination 'jms/queue/orders' - retrying in 5000 ms. Cause: JndiObjectTargetSource failed to obtain new target object; nested exception is javax.naming.NameNotFoundException: ConnectionFactory not bound
INFO : [11:50:26:026 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.connection.CachingConnectionFactory - Established shared JMS Connection: JBossConnection->ConnectionDelegate[305427533, ID=5c-rzdffeuj-1-1dwefeuj-dybjoc-m10a, SID=1]
INFO : [11:50:26:026 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.connection.CachingConnectionFactory - Established shared JMS Connection: JBossConnection->ConnectionDelegate[305427533, ID=5c-rzdffeuj-1-1dwefeuj-dybjoc-m10a, SID=1]
INFO : [11:50:26:026 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Successfully refreshed JMS Connection
INFO : [11:50:26:026 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-2 org.springframework.jms.listener.DefaultMessageListenerContainer - Successfully refreshed JMS Connection
INFO : [11:50:26:026 MST] org.springframework.jms.listener.DefaultMessageListenerContainer#0-3 amitk.poc.jms.remote.down.OrderMessageListener - Received Order: Message 42
```