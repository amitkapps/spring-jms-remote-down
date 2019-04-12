# Remote JMS Server

## Overview
1. You have a local jms publisher/listener(Spring's `DefaultMessageListenerContainer`) client
1. A remote JMS server (in this case JBoss)
1. Application should continue to receive messages successfully:
    1. If remote jms server is down on startup
    1. If remote jms server goes down temporarily or if there is a temporary network outage
    
## Setup
1. locally running jboss with a couple of queues
1. queue configurations mentioned in jboss/messaging-services

## Key configurations
1. For the remote connection factory lookup `JndiObjectFactoryBean`, property `lookupOnStartup` should be set to `true`
1. Need to use a `CachingConnectionFactory` to allow re-connection on errors