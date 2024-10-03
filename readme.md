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


### Running Tests
To run the unit tests:
```
mvn test
```

### Available APIs

Here are the available APIs along with their request details and sample responses.

#### 1. Register User

**Endpoint:** `/bookstore/user/register`  
**Method:** `POST`  
**Request Body:**

```json
{
    "username": "ganesh",
    "password": "secret"
}
```
**Sample Response:**    
Response code: 201 Created
```json
{
  "id": 5,
  "username": "ganesh",
  "password": "secret"
}
```


#### 2. Add Books To The Database

**Endpoint:** `/bookstore/books/add`  
**Method:** `POST`  
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
**Request Body:** None

**Sample Response:**    
Response code: 200 OK
```
Order Cancelled
```