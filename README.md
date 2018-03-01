# myRetail

myRetail is a RESTful web service that can retrieve product details by product ID, and update product price information. Product title information is retrieved from an external REST API, and product price information is stored locally in a NoSQL document store.

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

 `# TODO`
  
**MongoDB**

  Follow the [installation instructions](https://docs.mongodb.com/manual/administration/install-on-linux/) according to your operating system.
  
  






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
