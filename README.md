Ecommerce Solution
Please follow below instructions to run this project:

Technology stack used for this application:

1. Java - 17
2. Spring Boot: 3.2.2
3. Spring Data JPA
3. Maven
4. Mysql
5. Flyway
6. Swagger
7. lombok

Run Commands:

``````
mvn clean
mvn install
mvn spring-boot:run

Generate and run jar file:
mvn clean install

Move to the target folder
Run Jar File:
java -jar ecommerce-api.jar
``````

###Browse swagger:

```
1. After running code please check API documentation swagger ui
2. Swagger UI: http://localhost:8080/ecommerce/swagger-ui/index.html
```

Required Maven dependencies in pom.xml
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>7.0.4.Final</version>
    </dependency>

    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.3.0</version>
    </dependency>

    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-mysql</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.20</version>
        <optional>true</optional>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.6</version>
    </dependency>
   <dependency>
      <groupId>org.flywaydb</groupId>
      <artifactId>flyway-core</artifactId>
   </dependency>
```

```
Flyway: To run scripts.
Create a folder db.migration under resources folder and then create SQL scripts under this folder. 
Flyway adheres to the following naming convention for migration scripts:
```

```
Script file format:
<Prefix><Version>__<Description>.sql
For example: V1_1__CREATE_TABLE_SCRIPT.sql
```

```
   Required APIs:
- Create an API to return the wish list of a customer: localhost:8080/ecommerce/wish/items/customer/id
- Create an API to return the total sale amount of the current day: localhost:8080/ecommerce/sales/current-date-total-sales-amount
- Create an API to return the max sale day of a certain time range: localhost:8080/ecommerce/sales/max-sales-day?startDate=2024-05-01&endDate=2024-05-03
- Create an API to return top 5 selling items of all time (based on total sale amount): localhost:8080/ecommerce/sales/amount-wise-top-five-items
- Create an API to return top 5 selling items of the last month (based on number of sales): localhost:8080/ecommerce/sales/sales-wise-top-five-items
```

```
Customer API Endpoints:
POST: localhost:8080/ecommerce/customers
PUT: localhost:8080/ecommerce/customers/id
GET: localhost:8080/ecommerce/customers
GET: localhost:8080/ecommerce/customers/id
DELETE: localhost:8080/ecommerce/customers/id
```

Customer Save and Update API Payload:

``` 
POST: localhost:8080/ecommerce/customers
{
    "name" : "Isac",
    "phone": "13714150321"

}
PUT: localhost:8080/ecommerce/customers/id
{
    "name" : "Amin",
    "phone": "01777741822"

}
```

``` 
ITEM API Endpoints:
POST: localhost:8080/ecommerce/items
PUT: localhost:8080/ecommerce/items/1
GET: localhost:8080/ecommerce/items
GET: localhost:8080/ecommerce/items/1
DELETE: localhost:8080/ecommerce/items/1
```

ITEM Save and Update API Payload:

``` 
POST: localhost:8080/ecommerce/items
{
    "name" : "Butter",
    "description": "Butter",
    "sku": "skbutter01",
    "unitPrice": 20
}
PUT: localhost:8080/ecommerce/items
{
    "name" : "Soap",
    "description": "Soap",
    "sku": "sksoap001",
    "unitPrice": 60
}
```

Sales API Endpoints:

``` 
POST: localhost:8080/ecommerce/sales
PUT: localhost:8080/ecommerce/sales/id
GET: localhost:8080/ecommerce/sales
GET: localhost:8080/ecommerce/sales/id
GET: localhost:8080/ecommerce/sales/current-date-total-sales-amount
GET: localhost:8080/ecommerce/sales/max-sales-day?startDate=2024-05-01&endDate=2024-05-03
GET: localhost:8080/ecommerce/sales/sales-wise-top-five-items
GET: localhost:8080/ecommerce/sales/amount-wise-top-five-items
DELETE: localhost:8080/ecommerce/sales/id
```

Sales Save and Update API Payload:

``` 
{
    "customerId" : 1,
    "itemDTOS": [
        {"itemId":2, "quantity": 1 }, {"itemId":2, "quantity": 1 },
        {"itemId":3, "quantity": 5 },{"itemId":4, "quantity": 3 },
        {"itemId":5, "quantity": 1 },{"itemId":6, "quantity": 2 }, 
         {"itemId":7, "quantity": 2 },{"itemId":8, "quantity": 1 } 
    ]
}

PUT: localhost:8080/ecommerce/sales/id
{
    "customerId" : 3,
    "itemDTOS": [
        {"itemId":4, "quantity": 2 },
        {"itemId":2, "quantity": 5 },
        {"itemId":10, "quantity": 7 },
        {"itemId":4, "quantity": 5 }  
    ]
}
```

Customer Wish List API Endpoints:

``` 
POST: localhost:8080/ecommerce/wish/items
GET: localhost:8080/ecommerce/wish/items/customer/id
GET: localhost:8080/ecommerce/wish/items/1id
DELETE: localhost:8080/ecommerce/wish/items/1
```

Customer Wish Save API Payload:

``` 
{
    "customer" : 
    {
        "id": 4
    },
    "item": {
        "id": 5
    }
}
```

Command to run Docker Container:

```
docker container run --name ecommerce-api -p 8080:8080 -d ecommerce-api.jar

```
