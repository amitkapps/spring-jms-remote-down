# Remote JMS Server

## Overview
1. You have a local message publisher/listener client
1. A remote JMS server (in this case JBoss)
1. Application should continue to receive messages successfully:
    1. If remote jms server is down on startup
    1. If remote jms server goes down temporarily or if there is a temporary network outage