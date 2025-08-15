# Cocktails REST API

**<span style="font-size: larger;">This is the Backend app consumed by Frontend Angular project [Cocktails Angular App](https://github.com/aleksandra-mileva/cocktails-angular-app)</span>**

##  How to run the Cocktails REST API with Docker
- <span>If you use docker you don't have to clone the REST API Repository. You only need the docker-compose file: [docker-compose file](https://github.com/aleksandra-mileva/cocktails-rest-api/blob/main/local/docker-compose.yaml)</span>
- You must have docker and docker-compose installed on your computer.
- Use docker compose to run the application.
- The docker compose file contains images for the Backend application and the database.
- Following command should be executed in the directory of the docker-compose.yaml file with already installed docker and docker-compose: `docker-compose up` or `docker-compose -f docker-compose.yaml up`

##  How to run the Cocktails REST API without Docker
- <span>Clone the Rest Api: [Cocktails REST API](https://github.com/aleksandra-mileva/cocktails-rest-api)</span>
- Make sure you have a MySQL server running.
- Make sure you have Java 21 installed.
- Option A: Run the project using IntelliJ or another IDE.
- Option B (no ide):
  `./gradlew build`
  `java -jar build/libs/<your-app-name>-<version>.jar`

##  Functionality
**<span style="font-size: larger;">For all users</span>**
- List all cocktails or Search cocktails by criteria: `GET` `/api/cocktails/search`
- View single cocktail details: `GET` `/api/cocktails/{cocktailId}`
- View posted comments for a cocktail: `GET` `/api/cocktails/{cocktailId}/comments`

**<span style="font-size: larger;">For not Authenticated users</span>**
- Register: `POST` `/api/auth/register`
- Login: `POST` `/api/auth/login`

**<span style="font-size: larger;">For Authenticated users</span>**
- Add Cocktail: `POST` `/api/cocktails`
- Update Cocktail allowed only for the Owner of the cocktail and the Admin: `PUT` `/api/cocktails/{cocktailId}`
- Delete Cocktail allowed only for the Owner of the cocktail and the Admin: `DELETE` `/api/cocktails/{cocktailId}`
- Add Comment for Cocktail: `POST` `/api/cocktails/{cocktailId}/comments`
- Delete a Comment allowed only for the Owner of the comment and the Admin: `DELETE` `/api/cocktails/{cocktailId}/comments/{commentId}`
- Add or remove a Cocktail from favorite cocktails: `POST` `/api/users/favourites/{cocktailId}`
- View profile details: `GET` `/api/users/{userId}`
- Edit profile details: `PUT` `/api/users/{userId}`
- List all Cocktails uploaded by a user, allowed only for the user: `GET` `/api/users/{userId}/cocktails`
- List users' favourite Cocktails, allowed only for the user: `GET` `/api/users/{userId}/favorites`

**<span style="font-size: larger;">For Admins</span>**
- View statistics: `GET` `/api/statistics`

**<span style="font-size: larger;">Common</span>**
- Home page: `GET` `/api/`
- Options for searching the Cocktail (flavours, spirits, types): `GET` `/api/cocktails/options`

##  Built with
- Java 21
- Spring Boot 3.4.7
- Spring Security with JWT
- Gradle
- MapStruct
- Validation and Error Handling
- Interceptors
- Schedulers
- Cloudinary
- CriteriaBuilder API
- JavaMailSender
- **Liquibase**
- **Docker**