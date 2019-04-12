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
