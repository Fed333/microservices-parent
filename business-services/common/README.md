## Archaius dynamic properties source demo.
- - -
The project is a demo of archaius dynamic properties with RoutingDataSource.</br>
- - -
It consists from next dependency modules:
- spring-boot; 
- spring-web; 
- spring-data-jpa; 
- spring-data-rest.

And two PostgreSQL database:
- "tenant1";
- "tenant2".
- - -
The database to use is determined at runtime using archaius dynamic properties.</br>
After modifying the value of property "_active.database_" in "_classpath:routing-datasource.properties_" </br>
the program switches between **tenant1** database and **tenant2** when values are "**tenant1**" and "**tenant2**" accordingly. </br>
- --
Databases initializing scripts are placed into `common/support/tenant/` directory.
To make it initialize inside docker paste this folder under `~/PostgreSQL/` next directory.
- - -