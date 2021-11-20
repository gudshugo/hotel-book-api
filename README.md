# hotel-book-api
A REST API developed in Java (using Spring Boot) to function as a booking system for hotels.
This project is a technical test part of the Alten Group recruitment process.

## Requirements:

```Maven``` 
```Java V.11+```
```Docker```

## Running the project:

To run the container in the docker with the project, execute: 

1 - On the project root, run:

`mvn clean install`

2 - After mvn build, run:

`docker-compose -f [PROJECT_ROOT_PATH]\docker-compose.yml up -d`

3 - After that, the API endpoints will already be available for calling at `http://localhost:8080`.

# API Endpoins:

1 - Create reservation endpoint -> API endpoint that creates a new reservation.

**Syntax**: `http://localhost:8080/api/v1/reservation`
**Parameters**: A json body, as in the example:
```json
{
    "room_id": 1, 
    "check_in": "2021-12-10",
    "check_out": "2021-12-12"
}
```
