# Golf Club Membership & Tournament API

QAP4 project for the Spring Boot / Docker unit. This is a REST API for a
golf club that lets members register and tracks who's signed up for which tournaments.

## What it does

- Add and look up members
- Add and look up tournaments
- Register a member to a tournament
- Search members by name, membership type, phone number, or by which tournament (start date)
  they're registered for
- Search tournaments by start date or location

## Stack

- Java 17
- Spring Boot 3 (Web, Data JPA, Validation)
- MySQL 8
- Docker / Docker Compose
- Lombok

## Running it

You need Docker Desktop running. From the project root:

docker compose up --build

That spins up MySQL and the API together, no extra setup needed. Give it a few seconds to come
up (Compose waits for MySQL's healthcheck before starting the app).

The API is up on **port 2026** (I remapped it off 8080 to dodge a port conflict with another
container I had running — see host port in `docker-compose.yml` if you need to change it back).

Test it's alive:
http://localhost:2026/api/members
Should return `[]` on a fresh DB.

To stop it:
docker compose down
(add `-v` on the end if you actually want to wipe the DB volume too, otherwise your data
survives between restarts)

## Endpoints

### Members
- `POST /api/members` — add a member (JSON body: memberName, memberAddress,
  memberEmailAddress, memberPhoneNumber, membershipStartDate, membershipType)
- `GET /api/members` — get all members
- `GET /api/members/{id}` — get one member

### Tournaments
- `POST /api/tournaments` — add a tournament (JSON body: startDate, endDate, location,
  entryFee, cashPrizeAmount)
- `GET /api/tournaments` — get all tournaments
- `GET /api/tournaments/{id}` — get one tournament
- `POST /api/tournaments/{tournamentId}/register/{memberId}` — registers that member to that
  tournament, no body needed, both ids are in the url

### Search endpoints

Members:
- `GET /api/members/search/name?name=John` — partial match, not case sensitive
- `GET /api/members/search/membership-type?type=ANNUAL` — has to be ANNUAL / MONTHLY /
  LIFETIME, all caps, it's an enum so it's picky about that
- `GET /api/members/search/phone?phoneNumber=555-1234` — exact match
- `GET /api/members/search/tournament-start-date?startDate=2026-08-01` — gives you every
  member registered to a tournament that starts on that date. dates need to be
  `YYYY-MM-DD` format

Tournaments:
- `GET /api/tournaments/search/start-date?startDate=2026-08-01`
- `GET /api/tournaments/search/location?location=Pine` — also partial match / case
  insensitive

All tested and screenshotted in Postman, in the submission.

## Docker

Screenshot of it running is in the submission. Everything's in the `Dockerfile` (multistage
build so the final image isn't lugging around the whole Maven build) and
`docker-compose.yml` (spins up MySQL + the app together with a healthcheck so the app doesn't
try to connect before the DB is ready).

## RDS / AWS

Didn't do this part. Had issues with creating a AWS account and after 3 trys I made the call to skip it
instead of doing something half-baked.

I did still build for it though — there's an `application-rds.properties` file with an `rds`
Spring profile that's ready to go, it just needs these env vars set:
- `RDS_ENDPOINT`
- `RDS_DB_NAME`
- `RDS_USERNAME`
- `RDS_PASSWORD`

and you'd run it with `--spring.profiles.active=rds`. No code changes needed, it's all
externalized through env vars already, I just never had an actual RDS instance to point it at.

## Stuff I got stuck on and sorta had trouble with 

- Spent way too long chasing a `ECONNRESET` / empty reply from Postman after I remapped the
  app's port to avoid a conflict with another container. Turns out I'd changed `server.port`
  in `application.properties` instead of just remapping the host side in
  `docker-compose.yml` — so the app was listening on a totally different port inside the
  container than what Compose was forwarding to. i Fixed it by keeping `server.port=8080`
  fixed inside the container and only ever touching the left-hand side of the `HOST:CONTAINER`
  mapping in compose.
- Got a `port already allocated` error on 3306 because I had another project's MySQL
  container already using it. Remapped this project's db to `3307:3306` on the host side to
  fix it, no changes needed inside the container itself since the app talks to `db:3306` over
  the internal Docker network anyway.
- Had a stretch where POST kept 400ing with "Required request body is missing" — turned out
  I just hadn't actually set the body in Postman to raw/JSON, so nothing was being sent.
  Rookie mistake but wasted some time on it.
- Also broke the docker-compose YAML a couple of times just from indentation when editing it by
  hand (the `volumes:` block especially, it has to line up at the very top level not nested
  under `services:`).
- Finally, as mentioned above I never got to the AWS part since I had issues with creating an account and after a 
few trying I opted to submit this without the RDS to AWS