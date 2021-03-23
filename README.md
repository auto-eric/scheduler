# scheduler

## Task

A timer service needs to be implemented in Java.

* Clients can create timers in the timer service via REST.
* A Timer has a 24 hour format (hh:mm) and a message.
* The timer service is running a “clock” representing the current time. Every time the clock has the same time (current
  time) as the time defined in a timer the service is executing a REST request.
* The REST request is send to a second simple text service and contains the message stored with the timer defined by the
  client. The simple test service is just printing the message into the logs when it receives the rest request.

### Additional hints/requirements

* Timer Service should be based on spring boot
* Client can register timers via REST at the timer service
* A Timer contains a time in the format hh:mm and a message
* The timer database object needs to be defined and should be stored in a Mongo DB
* Timer service runs a scheduler that executes a REST request when every the current time meets the time defined in the
  timer
* The REST request is executed against the Text Service containing the message
* The Text service is really simple and just prints the message into the Log received via REST request
* It is important that the timer service is able to run in multi instances and can handle a huge amount of timers
  registered
* Time zone does not need to be taken into account, the service is running with the local system time

## Solution

* two spring-boot service
    * logger: one POST endpoint to log
    * timer
        * REST
            * POST endpoint for creation with validation on `time` and `message`
            * GET endpoint
        * scheduler
          1. search for all current not running
          1. on each: findAndModify 
          1. call logger

## TODO

* DB index
* pagination
* async (?)
* ~~in-memory DB for tests~~
* DELETE endpoint