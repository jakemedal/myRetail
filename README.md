# myRetail

myRetail is a RESTful web service that can retrieve product details by product ID, and update product price information. Product title information is retrieved from an external REST API, and product price information is stored locally in a NoSQL document store.

### API Reference
----

**Request**
  
  GET /myRetail/api/products/{productId}
  
**URL Params**

  *Required:*
  `producId=[integer]`

  *Optional:*
  None

**Data Params**

  None
  
**Header Params**

  None

**Success Response:**

  *Code:* 200 <br />
  *Content:*
    
  ```json
  {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
  ```
**Error Responses:**

  *Code:* 404 <br />
  *Description:* No data was found for the provided `productId`

  *Code:* 406 <br />
  *Description:* Invalid accept type header
  
  *Code:* 500 <br />
  *Description:* Server side error

**Sample Call:**

  `curl localhost:8080/myRetail/api/products/16696656`
