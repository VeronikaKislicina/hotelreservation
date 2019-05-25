# Hotel Reservation Application

## Story

Develop a small hotel reservation system. Application will use two types of users – hotel employees and customers.

Hotel administrators should be able to:
• Manage rooms – view / add / remove / edit
• See schedule for each room
• See hotel availability statistics (how many rooms are free/busy) for specified period

Customers should be able to
• Check availability on specified period
• Make a reservation

## API Documentation

**View rooms**

```GET http://localhost:8080/admins/rooms```



**Add room**

```POST http://localhost:8080/admins/rooms```

Response body:
```json
{
"available": true,
"roomNumber": 0
}
```


**Remove room**

```DELETE http://localhost:8080/admins/rooms/{id}```



**Update room**

```PUT http://localhost:8080/admins/rooms/{id}```



**See schedule for each room**

```GET http://localhost:8080/admins/schedule/{roomNumber}```



**See hotel availability statistics (how many rooms are free/busy) for specified period**

```POST http://localhost:8080/admins/availability```

Response body:
```json
{
"startDate": "2019-05-05 10:00",
"endDate": "2019-05-06 12:00"
}
```


**Check availability on specified period**

```POST http://localhost:8080/clients/availability```

Response body:
```json
{
"startDate": "2019-05-05 10:00",
"endDate": "2019-05-06 12:00"
}
```


**Make a reservation**

```POST http://localhost:8080/clients/bookings```

Response body:
```json
{
"startDate": "2019-05-05 10:00",
"endDate": "2019-05-06 12:00",
"clientName": "Tom"
}
```

