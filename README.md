This project represents oversimplified/toy order managment system (OOMS).

It is designed to allow customers to buy assets (ISINs), save orders and update customer balance.  

**Architecture:**
- System consists of 3 microservices ( spring-boot apps ) working together 
    - *Price-Handler* provides prices of any ISIN via REST-API  
    - *Customer-service*, *Order-Service* communicate via kafka
- *Customer-Service*
    - has a database to read customer id and name 
    - is responsible for generating purchase transactions ( *order.request* kafka topic )
- *Order-Service* 
    - is reponsible for ISIN "purchase".
    - is reponsible for onboarding new customers ( *onboard.customer* topic ) 
    - has a database to store customers accounts (balance) and orders 

![](schema.png?raw=true "Title")
 
