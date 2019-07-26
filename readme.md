###Scissors-Rock-Paper coding test game implementation

This project implements Scissors-Rock-Paper game for coding test purposes.

For more information about this game please refer to [Wikipedia page](https://en.wikipedia.org/wiki/Rock%E2%80%93paper%E2%80%93scissors)

####Prerequisites
* Java JRE 1.8
* Git
* Maven 3.0
* curl (optional)

#####Installing under Windows

* git clone https://github.com/greenomsk/codingtest.git
* cd condigtest
* mvn clean install

####Configuration

* cd %project_home%/target/scissors-rock-paper/config directory
* Open application.yaml file for editing
* Set game.strategy parameter (**Note**: game.strategy.markov.chain is set by default). Possible values are:
  * game.strategy.markov.chain - Markov Chain game strategy
  * game.strategy.random - Random game strategy
 
####Run
* cd %project_home%/target/scissors-rock-paper directory
* java -jar scissors-rock-paper.jar --logging.config=./config/logback.xml --spring.config.location=./config/application.yaml

####Game flow
* First of all game should be started.
* On next step any moves can be made by throwing gestures from list 'ROCK', 'PAPER', 'SCISSORS'.
Each gesture throwing will result to 'WIN', 'LOSE' or 'DRAW'.
* During game process user can request current score.
* Finish game.

#####All steps above could be done by using swagger web console
http://localhost:8080/swagger-ui.html

#####Or by using curl
* Start game: curl -X PUT "http://localhost:8080/1.0/scissors-rock-paper/start-new-game" -H "accept: application/json;charset=UTF-8"

* Make a move: curl -X PUT "http://localhost:8080/1.0/scissors-rock-paper/{game-id}/make-move" -H "accept: application/json;charset=UTF-8" -H "Content-Type: application/json" -d "{ \"player-gesture\": \"{gesture}\"}"
  * Were **game-id** game identifier got after start game call
  * **gesture** is one of the possible gestures 

* Get total score: curl -X GET "http://localhost:8080/1.0/scissors-rock-paper/{game-id}/total-score" -H "accept: application/json;charset=UTF-8"

* Finish game: curl -X POST "http://localhost:8080/1.0/scissors-rock-paper/{game-id}/finish-game" -H "accept: application/json;charset=UTF-8"

######Examples:
curl -X PUT "http://localhost:8080/1.0/scissors-rock-paper/start-new-game" -H "accept: application/json;charset=UTF-8"

curl -X PUT "http://localhost:8080/1.0/scissors-rock-paper/1/make-move" -H "accept: application/json;charset=UTF-8" -H "Content-Type: application/json" -d "{ \"player-gesture\": \"ROCK\"}"

curl -X GET "http://localhost:8080/1.0/scissors-rock-paper/1/total-score" -H "accept: application/json;charset=UTF-8"

curl -X POST "http://localhost:8080/1.0/scissors-rock-paper/1/finish-game" -H "accept: application/json;charset=UTF-8"

