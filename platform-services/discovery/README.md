## DISCOVERY-SERVICE Eureka server
- - -
### Provides automatic detecting devices and services on a network.</br>This allows services to find and communicate with each other without hard-coding the hostname and port. The only 'fixed point' in such an architecture is the service registry, with which each service has to register. </br>
- - -
#### With Netflix Eureka, each client can simultaneously act as a server to replicate its status to a connected peer. </br> In other words, a client retrieves a list of all connected peers in a service registry, and makes all further requests to other services through a load-balancing algorithm. </br> To be informed about the presence of a client, they have to send a heartbeat signal to the registry. </br>
- - -