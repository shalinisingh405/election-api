## Description

If you are not familiar with how elections work in the UK, please see this short BBC video https://www.youtube.com/watch?v=cRxUhGetEPQ

The results API presents a simple elections result service.

### Domain
The domain for the election represents some key concepts:
- _**constituencyId**_ a unique integer id to identify a location. E.g "Brent Central" is 90
- _**party**_ is a short 3, or 4, letter code for a party for instance LAB = Labour, CON = Conservative etc.
- _**votes**_ the number of votes gained by a party in a constituency
- _**share**_ the % share of the total votes the party received

### API
The API has 3 endpoints:
- GET `/result/{id}` to get an elections result for a given id.
- POST `/result` to add a new result
- GET `/scoreboard` to get the running totals. This is unimplemented.

### Task

During your assessment we will ask you to work though the task in `tasks.md` with a pair. Please do not work on or complete these prior to the assessment.

__Warning:__  If you make any changes to the code, please ensure you return it to its initial (HEAD) state before your assessment.

## Prerequisites
- Java 11

### Please ensure that the project builds without error within your choice of IDE. This should require no changes to the files in this repository.
### To Build
macOS / 'nix

`./gradlew build` or `./mvnw install`

Windows:

`gradlew.bat build` or `./mvnw.cmd install`

### To Run
macOS / 'nix

`./gradlew bootRun` or `./mvnw spring-boot:run`

Windows:

`gradlew.bat bootRun` or `./mvnw.cmd spring-boot:run`

### To Test
macOS / 'nix

`./gradlew test` or `./mvnw test`

Windows:

`gradlew.bat test` or `./mvnw.cmd test`
# election-api

Follow these 

```declarative
…or create a new repository on the command line
echo "# election-api" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:shalinisingh405/election-api.git
git push -u origin main
```
if any error while pushing to git for ex> 

''' error: failed to push some refs to 'github.com:shalinisingh405/election-api.git''

do these - 
```declarative
git reset --mixed origin/main
git add .
git commit -m "This is a new commit for what I originally planned to be amended"
git push origin main
```

