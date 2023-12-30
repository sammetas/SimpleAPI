This Simple API just will take the request and do a simple validation of gameId and game.
If these are not null it will return the same payload as response.

This API is used to test the Round Robin logic
#To start normal instance

java -Dserver.port=8084   -jar target/SimpleAPI-0.0.1-SNAPSHOT.jar

#To start an instance with given delay response

java -Dserver.port=8081  -Dround.latency=100 -jar target/SimpleAPI-0.0.1-SNAPSHOT.jar 

#to start 4 instances with no delay 

java -Dserver.port=8081   -jar target/SimpleAPI-0.0.1-SNAPSHOT.jar
java -Dserver.port=8082   -jar target/SimpleAPI-0.0.1-SNAPSHOT.jar
java -Dserver.port=8083   -jar target/SimpleAPI-0.0.1-SNAPSHOT.jar
java -Dserver.port=8084   -jar target/SimpleAPI-0.0.1-SNAPSHOT.jar
