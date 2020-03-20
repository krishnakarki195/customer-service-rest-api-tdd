# customer-service-rest-api-tdd
Description
Build an API to request and administer service requests by our customer.

The application should receive requests for service by our client companies, and allow updates to the status of those requests.

Requirements
System shal store requests in a MySql database
Endpoint Specification
Create a new request
Endpoint: /api/service Type: POST Request Body:

{
    "customerName": "Some Customer",
    "customerAddress": "123 Any Street, SomeCity, ST, 99999",
    "phoneNumber": "111-222-3333",
    "description": "it's broke and I need it fixed!"
}
Response Body:

{
    "requestNumber": "123",
    "customerName": "Some Customer",
    "customerAddress": "123 Any Street, SomeCity, ST, 99999",
    "phoneNumber": "111-222-3333",
    "description": "it's broke and I need it fixed!"
    "technician": "UNASSIGNED"
}
Get all requests
Endpoint: /api/service Type: GET Request Body: NA Response Body:

Requests[
    {
        "requestNumber": "123",
        "requestDateTime": "01/20/2020 08:30AM"
        "customerName": "Some Customer",
        "customerAddress": "123 Any Street, SomeCity, ST, 99999",
        "phoneNumber": "111-222-3333",
        "description": "it's broke and I need it fixed!",
        "status": "PENDING"
    },
    {
        "requestNumber": "124",
        "requestDateTime": "01/20/2020 08:30AM"
        "customerName": "Another Customer",
        "customerAddress": "123 Some Other St, SomeCity, AK, 99999",
        "phoneNumber": "111-222-3333",
        "description": "it's broke and I need it fixed!",
        "technician": "Johnny Fixer",
        "appointmentDateTime": "11/11/2019",
        "status": "RESOLVED"
    },
    ...
}
Get one request
Endpoint: /api/service/{requestNumber} Type: GET Request Body: NA Response Body:

{
    "requestNumber": "124",
    "requestDateTime": "10/20/2010 08:30AM"
    "customerName": "Another Customer",
    "customerAddress": "123 Some Other St, SomeCity, AK, 99999",
    "phoneNumber": "111-222-3333",
    "description": "it's broke and I need it fixed!",
    "technician": "Johnny Fixer",
    "appointmentDateTime": "11/11/2019",
    "status": "RESOLVED"
    "Notes": [
        {
            "dateTime": "10/20/2020 12:30PM",
            "note": "Customer did not answer"
        },
        {
            "dateTime": "10/21/2020 08:30AM",
            "note": "Spoke with customer.  Was in car and will check if it's plugged in when he gits home"
        },
        {
            "dateTime": "10/21/2020 03:00PM",
            "note": "Customer called to say that the unit was not plugged in.  He plugged it in and now it works - setting request to RESOLVED status"
        }

    ]
}
Assign request
Endpoint: /api/service/{requestNumber} Type: PUT Request Body:

{
    "technician": "Bob Builder",
    "appointmentDate": "01/20/2020",
    "appointmentTime": "08:30AM"
}
Response Body:

{
    "requestNumber": "123",
    "customerName": "Some Customer",
    "customerAddress": "123 Any Street, SomeCity, ST, 99999",
    "phoneNumber": "111-222-3333",
    "description": "it's broke and I need it fixed!",
    "status": "ASSIGNED"
}
Update request
Endpoint: /api/service/{requestNumber}/status Type: PUT Notes: Request Body: All attributes are optional, but must have at least one

{
    "technician": "Bob Builder",
    "appointmentDate": "01/20/2020",
    "appointmentTime": "08:30AM",
    "status": "RESOLVED"
    "note": "Customer called to say that the unit was not plugged in.  He plugged it in and now it works"
}   
