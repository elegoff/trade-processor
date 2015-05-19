# trade-processor

Architecture
==============

This is a rather traditional architecture with :

* Tomcat as a web-container 

A servlet is dedicted to handling POST'd Json messages   
For the sake of simplicity and timing constraints, the same instance is used for the rendering phase (JSP)
In a production mode, the JSP rendering should better be done  by a separate Tomcat instance.

* MongoDB as a database

It allows to stores two collections Trades and Processed
Trades collects every posted documents as is.
 
Also for simplicity , The database is configured with a single node, and the mongod process runs on same machine as the Tomcat instance. 
Better perfomance is expected if mongo is started in a separate box and configured with a cluster.


Framework and Tools
====================
* [Tomcat] [1] For handling the endpoint and for rendering teh results as JSP
* [Junit] [2] Unit testing the message consumption, simulating some fake messages
* [Gson] [3] Converting Json to java objects, and vice-versa
* [Jfreechart] [4] Rendering statistics
* [MongoDb] [5] Storing incoming message, and calculation results
* [Rest-assured] [6] In use in conjunction with Junit to perform some http requests simulation
* [Jquery] [7] when rendering the results, use ajax to requests fresh data
* [Bootstrap] [8] For CSS styling the result page


[1]: http://tomcat.apache.org "Tomcat"
[2]: http://junit.org "JUnit"
[3]: https://code.google.com/p/google-gson/ "Google-gson"
[4]: http://www.jfree.org/jfreechart/ "JFreeChart"
[5]: https://www.mongodb.org/ "MongoDb" 
[6]: https://code.google.com/p/rest-assured/ "rest-assured"
[7]: https://jquery.com/ "Jquery"
[8]: http://getbootstrap.com/css/ "Bootstrap"

How it works
============

Consuming Json
--------------
A servlet uses its doPost() method to read the incoming Json message
The message is converted (Gson) into a java bean which allows sanitazing (valididating) its content
Once the bean is validated, an executor service launch asynchronous tasks :
1. inserting the Trade message into mongodb

Trades are documents like

      >db.Trades.findOne()
      {
	"_id" : ObjectId("5557d36ae4b033beb91f6e40"),
	"userId" : 123456,
	"currencyFrom" : "EUR",
	"currencyTo" : "USD",
	"amountSell" : 100.00,
	"amountBuy" : 100.00,
	"rate" : 1.00,
	"timePlaced" : "24-JAN-15 10:27:44",
	"originatingCountry" : "FR"
	}

2. forwarding the Trade message to the Processing task

The asynchronicty of those tasks and then inherent Threadpool are meant to allow the servlet to handle more messages.


Processing a message
--------------------
The trade message is used to gather information related to a given currency pair (e.g "USD->CHF" )
We decided to :
- aggregate the count of messages for a given currency pair
- store the max and min rate observed for this currency pair
- keep the repartition of bought amounts by day and also by country
Each document of the Processed collection looks like :

	  > db.Processed.findOne()
	  {
	"_id" : ObjectId("5557d6cee4b03caef11d5d13"),
	"currencyPair" : "EUR->GBP",
	"minRate" : 0.8899999856948853,
	"maxRate" : 1.8899999856948853,
	"count" : 3,
	"volumeByDay" : {
		"25-JAN-15" : 120,
		"23-JAN-15" : 33
	},
	"volumeByCountry" : {
		"UK" : 120,
		"DE" : 33
	}
  }


Rendering the processed data
----------------------------
A JSP page is refreshed (every 5 s) to reflect latests database updates
It is populated after a global request on the Processed collection (ordered from biggest count to lowest count)
(Further improvements could include some restricting criteria like a date range / results for a given currency pair ...)
Repartitions data are rendered as png charts


