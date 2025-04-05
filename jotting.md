project name: moviebooking (microservice)
technology: java/Rest
java version: 11
java framework: spring boot
development approach : TDD


Classes required
- create a class that loads MovieJsonData into memory
- create a movie class to hold the movie json data
- create dataStorage class to store the movie data in memory
condition - data storage class must load the movie into memory at application start up
- create a moviebooking class
- create a movieBookingService class that will perform the business logic for the application
- create a moviebookingController REST class that will contain the movie booking rest api
- create class movieBookingRequest that contains the movie booking request data
- create a class movieBookingResponse  that contains the movie booking response
- create a movieReportJob class to perform the reporting

- create a bookingException, bookingExceptionDetail and bookingExceptionHandler class  


Class responsibilities
- movie class - to hold the movie data we have in Json format
- movieJsonData class - it converts and loads Json object into Java object
- dataStorage class - trigger loading of movies and store the movies at start up
- movieBooking class - to hold the booking data
- movieBookingService - contains the logic for the application
- movieBookingController - exposes the app api / accept request and return response
- movieBookingRequest - This class will be used to receive data from the client when a user makes a booking request
- movieBookingResponse - This class will be used to send data back to the client after processing the booking request.
- bookingException - this class with contain the exceptions that could happen during booking

