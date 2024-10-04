# Getting Started Book Store

This is a Spring boot based Book Store application,
that allows user to add books, browse books, add them to the cart,
and place orders.

It features user registration, login functionality also.

### Features
* Add books
* Browse Books
* Shopping Cart
* Order placement
* User registration and login

### Prerequisites

- [Java 17](https://adoptopenjdk.net/)
- [Maven 3.x](https://maven.apache.org/)
- [Postman](https://www.postman.com/downloads/) to try the APIs. 
(soon adding postman_collection.json file to root folder, to import and use the API collection)

### How to start application
1. Clone the repository:
```
$ git clone https://github.com/manimuttu/bookstore-springboot.git

$ cd bookstore-springboot
```
2. Install dependencies and build the application:
```
$ mvn clean install
```

3. Run the application:

```
mvn spring-boot:run
```
4. The application will be available at http://localhost:8080


### Swagger
Access swagger from below link
```
http://localhost:8080/swagger-ui/index.html
```

### H2 link
Access H2 in-memory database from below url     

username:sa     
password:password
```
http://localhost:8080/h2-console/
```


### Running Tests
To run the unit tests:
```
mvn test
```

### Steps to test the APIs

- `1. Register yourself with username and password`  
   This will register you.


- `2. Login with the registered username and password`  
   Auth: Basic Auth
   
   This will takes credentials and generate **jwt** token for subsequent calls.


- `3. List all the books`  
  Auth: Bearer Token
  Lists books.


- `4. Add Books to the cart`  
  Auth: Bearer Token
  Add books to cart.


- `5. Checkout/Order from cart`
  Auth: Bearer Token
  Checkout the cart.


- `6. Cancel order`  
  Auth: Bearer Token
  This will cancel the order.
 
### Available APIs

Here are the available APIs along with their request details and sample responses.
As Authentication and Authorization has been added very last momement,
We are taking username manually in all the endpoints.
This will be integrated with Authenticated user very soon.

#### 1. Register User

**Endpoint:** `/bookstore/user/register`  
**Method:** `POST`  
**Authorization:** None   
**Request Body:**

```json
{
    "username": "bob",
    "password": "secret"
}
```
**Sample Response:**    
Response code: 201 Created
```
User registered
```

#### 2. Login User

**Endpoint:** `/bookstore/user/login`  
**Method:** `GET`  
**Authorization:** Basic Ym9iOnNlY3JldA==   
**Request Body:** None  

**Sample Response:**    
Response code: 201 Created
```
User registered
```


#### 2. Add Books To The Database

**Endpoint:** `/bookstore/books/add`  
**Method:** `POST`  
**Authorization:** Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJpYXQiOjE3MjgwNDc0OTMsImV4cCI6MTczNTk5NjI5M30.x6bjbMz4OHY3QBdilT0z2vnWKI62dE4Y5zpBjj69sTA     

**Request Body:**

```json
[
  {
    "isbn": 1234,
    "title": "freedom of speech",
    "author": "fighter",
    "price": 200.00,
    "stock": 20
  },
  {
    "isbn": 12345,
    "title": "freedom of speech",
    "author": "fighter",
    "price": 200.00,
    "stock": 25
  }
]
```
**Sample Response:** None    
Response code: 201 Created


#### 3. List Books Collections

**Endpoint:** `/bookstore/books/list`  
**Method:** `GET`  
**Authorization:** Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJpYXQiOjE3MjgwNDc0OTMsImV4cCI6MTczNTk5NjI5M30.x6bjbMz4OHY3QBdilT0z2vnWKI62dE4Y5zpBjj69sTA

**Request Body:** None

**Sample Response:**    
Response code: 200 OK
```json
[
  {
    "isbn": "978-0134685991",
    "title": "Effective Java",
    "author": "Joshua Bloch",
    "price": 45.0,
    "stock": 10
  },
  {
    "isbn": "978-0134757599",
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "price": 40.0,
    "stock": 5
  }
]
```



#### 4. Add Books To The  Cart

**Endpoint:** `/bookstore/cart/add?username=bob`    
- `username`: The username of the user, To whose cart books are to be added.

**Method:** `POST`  
**Authorization:** Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJpYXQiOjE3MjgwNDc0OTMsImV4cCI6MTczNTk5NjI5M30.x6bjbMz4OHY3QBdilT0z2vnWKI62dE4Y5zpBjj69sTA

**Request Body:** 
```json
[
  {
    "bookId": "978-0134685991",
    "quantity": 8
  },
  {
    "bookId": "978-0135166307",
    "quantity": 3
  }
]
```
**Sample Response:**    
Response code: 200 OK
```json
{
  "username": "bob",
  "cartItems": [
    {
      "bookId": "978-0134685991",
      "bookTitle": "Effective Java",
      "price": 45.0,
      "quantity": 8
    },
    {
      "bookId": "978-0135166307",
      "bookTitle": "Spring in Action",
      "price": 39.99,
      "quantity": 3
    }
  ]
}
```


#### 5. List Users Cart Items

**Endpoint:** `/bookstore/cart/items?username=bob`  
- `username`: The username of the user, whose cart items to list.

**Method:** `GET`  
**Authorization:** Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJpYXQiOjE3MjgwNDc0OTMsImV4cCI6MTczNTk5NjI5M30.x6bjbMz4OHY3QBdilT0z2vnWKI62dE4Y5zpBjj69sTA

**Request Body:** None

**Sample Response:**    
Response code: 200 OK
```json
{
  "username": "bob",
  "cartItems": [
    {
      "bookId": "978-0134685991",
      "bookTitle": "Effective Java",
      "price": 45.0,
      "quantity": 8
    },
    {
      "bookId": "978-0135166307",
      "bookTitle": "Spring in Action",
      "price": 39.99,
      "quantity": 3
    }
  ]
}
```



#### 6. Cart Checkout

**Endpoint:** `/bookstore/orders/checkout?username=bob`  
- `username`: The username of the user, making checkout.

**Method:** `POST`  
**Authorization:** Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJpYXQiOjE3MjgwNDc0OTMsImV4cCI6MTczNTk5NjI5M30.x6bjbMz4OHY3QBdilT0z2vnWKI62dE4Y5zpBjj69sTA

**Request Body:** None

**Sample Response:**    
Response code: 200 OK
```json
{
  "orderNumber": 2,
  "totalAmount": 479.97,
  "username": "bob",
  "orderItems": [
    {
      "bookIsbn": "978-0134685991",
      "bookTitle": "Effective Java",
      "price": 45.0,
      "quantity": 8
    },
    {
      "bookIsbn": "978-0135166307",
      "bookTitle": "Spring in Action",
      "price": 39.99,
      "quantity": 3
    }
  ]
}
```


#### 7. List User Orders

**Endpoint:** `/bookstore/orders/userOrders?username=bob`  
- `username`: The username of the user requesting the orders.

**Method:** `GET`  
**Authorization:** Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJpYXQiOjE3MjgwNDc0OTMsImV4cCI6MTczNTk5NjI5M30.x6bjbMz4OHY3QBdilT0z2vnWKI62dE4Y5zpBjj69sTA

**Request Body:** None

**Sample Response:**    
Response code: 200 OK
```json
{
  "orderNumber": 2,
  "totalAmount": 479.97,
  "username": "bob",
  "orderItems": [
    {
      "bookIsbn": "978-0134685991",
      "bookTitle": "Effective Java",
      "price": 45.0,
      "quantity": 8
    },
    {
      "bookIsbn": "978-0135166307",
      "bookTitle": "Spring in Action",
      "price": 39.99,
      "quantity": 3
    }
  ]
}
```


#### 8. Cancel Order

**Endpoint:** `/bookstore/orders/cancel/{order_number}?username=bob`  
- `username`: The username of the user requesting the cancellation.
- `order_number`: The Order number that needs to be cancelled.

**Method:** `DELETE`  
**Authorization:** Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJpYXQiOjE3MjgwNDc0OTMsImV4cCI6MTczNTk5NjI5M30.x6bjbMz4OHY3QBdilT0z2vnWKI62dE4Y5zpBjj69sTA

**Request Body:** None

**Sample Response:**    
Response code: 200 OK
```
Order Cancelled
```