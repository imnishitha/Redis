
# Redis to flat file Sync.

Aim- To develop an utility to fetch the data from redis from the specified partition, store the data in json format in a specified .json file and also to the dump back the data onto to specified partition in redis from the .json file after modifying the data fetched as per the requirements.

## Redis

Redis, RE-dis is an open-source in-memory data structure project implementing a distributed, in-memory key-value database with optional durability. Redis supports different kinds of abstract data structures, such as strings, lists, maps, sets, sorted sets, hyperloglogs, bitmaps, streams and spatial indexes.

Download and install redis from [here] (https://redis.io).

## JSON
JSON: JavaScript Object Notation.
JSON is a syntax for storing and exchanging data.
JSON is text, written with JavaScript object notation.

When exchanging data between a browser and a server, the data can only be text.
JSON is text, and we can convert any JavaScript object into JSON, and send JSON to the server.
We can also convert any JSON received from the server into JavaScript objects.
This way we can work with the data as JavaScript objects, with no complicated parsing and translations.
 
###Steps to execute the code.
Download the .jar file and execute it on command prompt as described below.

Steps to execute the file:

Create a .json file in the required file location.
Run the command java -jar final.jar with the necessary arguments on the same line on command prompt.

command:
java -jar final.jar argument_1 argument_2 argument_3 argument_4 

argument_1- file path
argument_2- port number
argument_3- partition number
argument_4- task ( f - for fetching the data from redis
                                 r - for dumping the data to redis )

Example: 

java -jar final.jar d:\\test.json 6379 1 f
This command is to fetch ( f ) the data from partition 1 , through port 6379 and store the data in  d:\\test.json file. 

java -jar final.jar d:\\test.json 6379 2 r
This command is to dump ( r ) the data to partition 2 , through port 6379 from the data in  d:\\test.json file.



