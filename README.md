# myRetail

myRetail is a RESTful web service that can retrieve product details by product ID, and update product price information. Product title information is retrieved from an external REST API, and product price information is stored in a database maintained by the application. 

The code is written in Java, built using Gradle, tested using the Spock Framework (Groovy), deployed to a Tomcat server, and stores data in a MongoDB document store.

### Setup

To build and run this project, you will need to have installed the following:
  * Java
  * Gradle
  * Groovy
  * Tomcat
  * MongoDB
  
  *SDKMAN!* is a software development kit manager **for unix based systems** that can be used to install the necessary items listed above. To install *SDKMAN!*, you can run the following curl command:
  
  `$ curl -s "https://get.sdkman.io" | bash`
  
Follow the on screen instructions to complete the setup. Once the setup is complete, you will be able to use `sdk` commands as needed.

**Java** 
  
  `$ sdk install java`
  
**Gradle**

  `$ sdk install gradle 4.6`
  
**Groovy**

  `$ sdk install groovy`
  
**Tomcat**

 Tomcat can be downloaded from the [Apache Tomcat website](https://tomcat.apache.org/index.html). If you are unfamiliar with Tomcat, you can follow the official [Tomcat 9 documentation](https://tomcat.apache.org/tomcat-9.0-doc/index.html).
 
 You must update myRetail's [deploy.sh](./deploy.sh) script to point CATALINA_HOME to your installation of tomcat.
  
**MongoDB**

  Follow the [installation instructions](https://docs.mongodb.com/manual/administration/install-on-linux/) according to your operating system.
  
  
### Build

To build the war file that will live on the Tomcat server:

`$ ./gradlew clean build`

### Deploy

If you haven't already, update CATALINA_HOME to point to your Tomcat installation.

To deploy the war you just built:

`$ ./deploy.sh`

### Start MongoDB

To start MongoDB:

`$ mongod`


### Test

To run the integration tests against your newly deployed app:

`$ ./gradlew clean integrationTest`



<!---
### API Reference
----

* **Request**
  
  `GET /myRetail/api/products/{productId}`
  
* **URL Params**

  *Required:*
  `productId=[integer]`

  *Optional:*
  None

* **Data Params**

  None
  
* **Header Params**

  None

* **Success Response:**

  *Code:* 200
  
  *Content:*
  
  ```json
  {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
  ```
* **Error Responses:**

  *Code:* 404 <br />
  *Description:* No data was found for the provided `productId`

  *Code:* 406 <br />
  *Description:* Invalid accept type header
  
  *Code:* 500 <br />
  *Description:* Server side error

* **Sample Call:**

  `curl localhost:8080/myRetail/api/products/16696656`
  
--->
