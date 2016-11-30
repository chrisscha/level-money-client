# level-money-client
Java client for communicating with Level Money service and generating useful or readable data.

## Functionality
Retrieves all transactions for the user groups and summarizes them by month. An overall average is calculated. This average ignores months with no activities. It also doesn't make any attempt to handle floating point errors. Unusually large currencies like Zimbabwe Dollars can cause overflows, although all of the math is done with 64-bit longs and should be fine for amounts approaching 1 Quadrillion USD.

The ignoreDonuts flag ingores donut transactions.

The crystalBall flag predicts the future (maybe) by showing what you are likely to spend for the current month. It will also ignore donut transactions if the ignoreDonuts flag is set.

The ignore cc payments functionality is not implemented.

## Technologies Used
- Java8 - Language. 8 is great.
- Gradle - Cleans, builds, and manages dependencies.
- ShadowJar - Wraps all of the jars into one uber/shadow/shade jar.
- Apache HttpClient - Useful for calling services over http. Lightweight.
- Apache Commons CLI - Handles command arguments and displays pretty error messages.
- Jackson fasterXML - Converts from over-the-wire json into POJOs and back into json. Fast but not the easiest thing out there.
- JUnit - Unit tests.
- Mockito - Mocks. Eventually I would add in PowerMock too if I need to do something like mock a private static function.

## Checking it out
Go to where you like your projects to go and run:
```
git clone https://github.com/chrisscha/level-money-client.git
```

## Running it
The project has the latest jar checked in to the root project directory. If you have the 1.8 Java runtime installed you should be able to run it just fine. Otherwise, install Java 1.8 first.
```
java -jar LevelMoneyClient-1.0-SNAPSHOT-all.jar -password password -email email@email.com -crystalBall -ignoreDonuts
```
If you leave off the correct and required arguments you should get a message telling you what you did wrong.

I have tested running the program on both Linux and Windows.

## Building it
Personally, I use Intellij. And I would suspect most modern IDEs handle some of the installation bits magically. Specifically, gradle generates many files on project creation, and I like to try to reduce the number of checked in auto-generated files to a minimum. Especially, when Intellij will just help you out and fill in the missing pieces. Running the program from the command line is a bit more difficult. The following instructions will be for getting the program to run from scratch for Ubuntu and will need to be modified for other flavors of linux like Apple.

1. Install gradle. 

Gradle isn't available from the normal apt-get repositories so you'll first need to update those:
```
sudo add-apt-repository ppa:cwchien/gradle
sudo apt-get update
sudo apt-get install gradle
```

Navigate to the place in the project directory where the build.gradle file is located. All commands will be run from this location from now on....

2. Generate gradle wrapper
```
gradle wrapper
```

3. Build it and Run it
Gradlew is a gradle wrapper and makes sure that you run a compatible version of gradle. You'll notice that it now installs gradle 2.0, although you probably just installed 3.2.1. The shadowJar is just a bundled uberJar that contains all of the dependencies.
```
./gradlew clean shadowJar
java -jar build/libs/LevelMoneyClient-1.0-SNAPSHOT-all.jar -password password -email email@email.com
```
See the Running it section for more
