# Rock, Paper, Scissors

Example of extensible game

### How to run

You can build the project and then execute it, single command:
```shell
 mvn clean package && java -jar target/rock-paper-scissors-1.0-SNAPSHOT.jar
```
Or you can just execute already packaged .jar:
```shell
java -jar rock-paper-scissors-1.0-SNAPSHOT.jar
```

### Dependencies

<b> junit-jupiter, mockito-junit-jupiter</b> - Standard testing libraries 
<br>
<b> text-io </b> - library for safe and valid command line input
<br>
<b> jansi </b> - just fun colors for the CLI, so the reviewer wouldn't be bored (could be thrown away)
<br>
<b> reflections </b> - convenient library to work with reflections (see ComputerPlayer)

### Thoughts about the solution

1. I made an arg of N rounds to be prompted for user input, not as a launch argument. It seemed to be more concise with other parameters.
2. Human-player input is masked to make "human vs. human" fun
3. In the sake of simplicity I made an assumption that only 2 players could play this game. However, it’s not that hard to refactor logic to support N players.
4. There could be a test for UI, made as Snapshot test , but it was an overkill for this assignment
5. There are several lines not covered with tests (reflection stuff, I suppose it’s just cannot be tested)

