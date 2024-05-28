# IronHack Lab-8

![Captura de pantalla 2024-05-28 a la(s) 3.42.22 p.m..png](..%2F..%2FDocuments%2FCapturas%20de%20pantalla%20y%20video%2FCaptura%20de%20pantalla%202024-05-28%20a%20la%28s%29%203.42.22%E2%80%AFp.m..png)

![Captura de pantalla 2024-05-28 a la(s) 3.43.09 p.m..png](..%2F..%2FDocuments%2FCapturas%20de%20pantalla%20y%20video%2FCaptura%20de%20pantalla%202024-05-28%20a%20la%28s%29%203.43.09%E2%80%AFp.m..png)
```yaml
openapi: 3.0.0
info:
  description: API for IronHack Lab 8
  version: 1.0.0
  title: IronHackLab API
servers:
  - url: https://api.ironhacklab.com/v1
paths:
  /users:
    post:
      summary: Create a new user
      description: Create a new user in the IronHackLab system
      operationId: createUser
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/User'
      responses:
        '201':
          description: User created successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
        '400':
          description: Invalid input
        '409':
          description: User already exists
  /users/{userId}:
    get:
      summary: Get user by Id
      description: Returns a user
      operationId: getUserById
      parameters:
        - name: userId
          in: path
          required: true
          description: Id of user to return
          schema:
            type: string
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/User'
        '400':
          description: Invalid ID supplied
        '404':
          description: User not found
  /orders:
    post:
      summary: Create a new order
      description: Create a new order in the IronHackLab system
      operationId: createOrder
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Order'
      responses:
        '201':
          description: Order created successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Order'
        '400':
          description: Invalid input
  /orders/{orderId}:
    get:
      summary: Get order by Id
      description: Returns a single order
      operationId: getOrderById
      parameters:
        - name: orderId
          in: path
          required: true
          description: ID of order to return
          schema:
            type: string
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Order'
        '400':
          description: Invalid ID supplied
        '404':
          description: Order not found
  /customer-tickets:
    post:
      summary: Create a new customer service ticket
      description: Create a new customer service ticket in the IronHackLab system
      operationId: createCustomerTicket
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/CustomerTicket'
      responses:
        '201':
          description: Ticket created successfully
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/CustomerTicket'
        '400':
          description: Invalid input
  /customer-tickets/{ticketId}:
    get:
      summary: Get customer service ticket by Id
      description: Returns a customer service ticket
      operationId: getCustomerTicketById
      parameters:
        - name: ticketId
          in: path
          required: true
          description: Id of ticket to return
          schema:
            type: string
      responses:
        '200':
          description: Successful operation
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/CustomerTicket'
        '400':
          description: Invalid ID supplied
        '404':
          description: Ticket not found
components:
  schemas:
    User:
      type: object
      required:
        - username
        - email
        - password
      properties:
        id:
          type: string
          example: "12345"
        username:
          type: string
          example: "heri"
        email:
          type: string
          example: "heri.espinosae@ironhack.com"
        password:
          type: string
          example: "password123"
        firstName:
          type: string
          example: "Heri"
        lastName:
          type: string
          example: "Espinosa"
        createdAt:
          type: string
          format: date-time
          example: "2024-05-27T01:00:00Z"
        updatedAt:
          type: string
          format: date-time
          example: "2024-05-27T01:02:00Z"
    Order:
      type: object
      required:
        - userId
        - items
      properties:
        id:
          type: string
          example: "67890"
        userId:
          type: string
          example: "12345"
        items:
          type: array
          items:
            $ref: '#/components/schemas/OrderItem'
        totalAmount:
          type: number
          format: double
          example: 50.50
        status:
          type: string
          example: "processing"
        createdAt:
          type: string
          format: date-time
          example: "2024-05-27T01:15:30Z"
        updatedAt:
          type: string
          format: date-time
          example: "2024-05-27T012:15:30Z"
    OrderItem:
      type: object
      required:
        - productId
        - quantity
        - price
      properties:
        productId:
          type: string
          example: "54321"
        quantity:
          type: integer
          example: 2
        price:
          type: number
          format: double
          example: 55.00
    CustomerTicket:
      type: object
      required:
        - userId
        - issue
      properties:
        id:
          type: string
          example: "11223"
        userId:
          type: string
          example: "12345"
        issue:
          type: string
          example: "Unable to log in to the account"
        status:
          type: string
          example: "open"
        createdAt:
          type: string
          format: date-time
          example: "2024-05-27T01:15:30Z"
        updatedAt:
          type: string
          format: date-time
          example: "2024-05-27T01:15:30Z"


```


