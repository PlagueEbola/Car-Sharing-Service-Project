# Car-Sharing-Service-Project
<img src="https://cdn.dribbble.com/users/508588/screenshots/14845034/media/18078f287ce75878d1858ab43d7607e2.jpg?compress=1&resize=400x300">
Car Sharing
A simulation of car sharing where we can add and delete cars that can be assigned to a user. It features an authentication and authorization system to allow or deny access to different features to users with appropriate rights.  And with the help of Stripe, payments are made more accessible. It also utilizes Telegram bots to send notifications to users.

# Features

* `Authentication`
* `Create and delete users`
* `Create and delete cars`
* `Create and cancelling payment`
* `Add and return rental`
* `Set rental for user`
* `Change user role`

# Getting Started

* Firstly you should copy the repository to your local machine.
* Replace values in in `.env` file.
* Build the project using the command: `mvn clean package`.
* Launch the `Docker` on your device. 
* Run the command: `docker-compose up`.
* Ther will be an account injected by default with credentials
  - login: `bob@gmail.com`, password: `123`
  - login: `alice@gmail.com`, password: `12345`
 
# How to receive notifications on Telegram bot? 
* Create your own bot with the `BotFather`.
* Write your bot's `token` and `name` to the `.env` file.
* Run the app.
* Go to the bot's Telegram and write `/start`.
* Follow the instructions you receive.
* For clarity, notifications will be sent `every 10 seconds`.

# Endpoints

* `Authentication Controller`
  - `POST`: `/register` - register a new user (non-auth access);
  - `POST`: `/login` - login user;

* `Car Controller`
  - `POST`: `/cars` - add new car;
  - `GET`: `/cars` - get a list of cars;
  - `GET`: `/cars/{id}` - get var by id;
  - `PUT`: `/cars/{id}` - update car by id;
  - `DELETE`: `/cars/{id}` - delete car by id;

* `Payment Controller`
  - `POST`: `/payments` - create payment;
  - `GET`: `/payments/success/{id}` - set status PAID;
  - `GET`: `/payments/cancel/{id}` - cancel payment;

* `Rental Controller`
  - `POST`: `/rentals` - add a new rental;
  - `GET`: `/rentals` - get a list of user rentals;
  - `GET`: `/rentals/{id}` - get rental by id;
  - `POST`: `/rentals/{id}/return` - end of rental;

* `User Controller`
  - `GET`: `/users/me` - get my profile info;</li>
  - `PUT`: `/users/me` - update my profile info;</li>
  - `PUT`: `/users/{id}/role` - update role of user;</li>


# Structure

* `controller` - Rest controllers
* `dto` - Data transfer objects for request and response
* `exception` - Classes that contain some custom exceptions
* `lib` - Classes that contain validation of User
* `model` - Classes which describe objects as `Car`, `Payment`, `Rental`, `User`, `UserRole`
* `repository` - Classes that contain methods that work with the repository
* `security` - Contains `JWT Token` and `AuthenticationSeervice`
* `service` - Provides business logic, includes mapper-s for dto
* `resources` - Contains scripts, database changelog, and properties

# Used Technologies

* Java `v.17.0.5`
* Maven-CheckStyle `v.3.1.1`
* Maven `v.3.10.1`
* Spring-Boot `v.3.1.0`
* Liquibase `v.4.2.2`
* Telegram bot `v.6.5.0`
* Stripe API `v.22.21.0`
* JSON Web Token (JWT) `v.0.9.1`
* Lombok

# Authors
* [Sushko Roman](https://github.com/PlagueEbola)
* [Budkevych Mykyta](https://github.com/De1eF)
* [Andrew Kluchnik](https://github.com/AndrewkeY-hub)
* [Denys Magola](https://github.com/denys-mg)
* [Pasha Ditkovskyi](https://github.com/Deschna)
