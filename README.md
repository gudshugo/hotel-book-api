# hotel-book-api
A REST API developed in Java (using Spring Boot) to function as a booking system for hotels.\
This project is a technical test part of the Alten Group recruitment process.

## Problem description:

Post-Covid scenario:
People are now free to travel everywhere but because of the pandemic, a lot of hotels went bankrupt. Some former famous travel places are left with only one hotel.
You’ve been given the responsibility to develop a booking API for the very last hotel in Cancun.

The requirements are:
- API will be maintained by the hotel’s IT department.
- As it’s the very last hotel, the quality of service must be 99.99 to 100% => no downtime
- For the purpose of the test, we assume the hotel has only one room available
- To give a chance to everyone to book the room, the stay can’t be longer than 3 days and can’t be reserved more than 30 days in advance.
- All reservations start at least the next day of booking,
- To simplify the use case, a “DAY’ in the hotel room starts from 00:00 to 23:59:59.
- Every end-user can check the room availability, place a reservation, cancel it or modify it.
- To simplify the API is insecure.


## Requirements:

```Maven``` 
```Spring Boot``` 
```PostgreSQL``` 
```Java V.11+```
```Docker```

## Running the project:

To run the container in the docker with the project, execute: 

1 - On the project root, run:

`$ mvn clean install`

2 - After mvn build, run:

`$ docker-compose -f [PROJECT_ROOT_PATH]\docker-compose.yml up -d`

3 - After that, wait a few second and the API endpoints will already be available for calling at `http://localhost:8080`.

**Note: If you want to run the project locally, without using the docker environment, change the application.properties configuration to the "dev" profile, as in the image below.**

![image](https://user-images.githubusercontent.com/13324028/142736124-9a2370e1-f728-4489-8659-ff8dd5a2fa04.png)

# API Endpoins:

1 - **Create reservation endpoint:**

**Description**: API endpoint that creates a new reservation.\
**URI Syntax**: `POST http://localhost:8080/api/v1/reservation`\
**Input**:
- A JSON body, such as:
```json
{
    "room_id": 1, 
    "check_in": "2021-12-10",
    "check_out": "2021-12-12"
}
```
**Output**: 
- A JSON body with a created reservation (indicated by "reserved: true"), such as:
```json
{
    "id": 1,
    "checkIn": "2021-12-10",
    "checkOut": "2021-12-12",
    "reserved": true
}
```

2 - **Cancel reservation endpoint:**

**Description**: API endpoint that cancel a reservation.\
**URI Syntax**: `PATCH http://localhost:8080/api/v1/reservation/{id}/cancel`\
**Input**: 
- {id} refers to a reservation id.

**Output**: 
- A JSON body with a canceled reservation (indicated by "reserved: false"), such as:
```json
{
    "id": 1,
    "checkIn": "2021-12-18",
    "checkOut": "2021-12-20",
    "reserved": false
}
```

3 - **Modify reservation endpoint:**

**Description**: API endpoint that modifies a reservation by id and a given range of dates.\
**URI Syntax**: `PATCH http://localhost:8080/api/v1/reservation/{id}/modify`\
**Input**: 
- {id} refers to a reservation id.
- A JSON body with a range of dates, such as:
   ```json
    {
        "check_in": "2021-12-15",
        "check_out": "2021-12-17"
    }
   ```
**Output**: 
- A JSON body with a modified reservation, such as:
   ```json
    {
        "id": 1,
        "checkIn": "2021-12-15",
        "checkOut": "2021-12-17",
        "reserved": true
    }
   ```

4 - **Get room availability endpoint:**

**Description**: API endpoint that get room availability by id and a given range of dates.\
**URI Syntax**: `GET http://localhost:8080/api/v1/room/{id}/availability?check_in={check_in}&check_out={check_out}`\
**Input**: 
- {id} refers to a room id.
- {check_in} refers to the customer check in informed date.
- {check_out} refers to the customer check out informed date.

**Output**: 
- A JSON body with room id and the available dates, such as:
    ```json
    {
        "room_id": 1,
        "available_dates": [
            "2021-12-12",
            "2021-12-11",
            "2021-12-10"
        ]
    }
   ```

In order to access the complete requisition documentation, check this link below:

``` 
 http://localhost:8080/swagger-ui/
```   

## Tests

For this project, unit tests were created for the service and utility packages.

Unit test coverage can be checked after running the command:

`$ mvn clean install` 

And the jacoco report is available in the project's target/site/jacoco/index.html file, as in the image below:

![image](https://user-images.githubusercontent.com/13324028/142735916-b8e828f2-c4da-45e3-bd45-8f3f83fc8cf3.png)

For more information, contact: gudshugo@gmail.com.
