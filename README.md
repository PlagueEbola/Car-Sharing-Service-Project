# Car-Sharing-Service-Project
<img src="https://st4.depositphotos.com/3275449/41883/v/450/depositphotos_418834060-stock-illustration-online-ordering-taxi-car-rent.jpg">
<h1>Car Sharing</h1>
This project is simulating of car sharing where we can add, delete cars, users, and assign user to 
car and realised and login logic which don`t allow unregistered users change or see some details.
<h2>Features</h2>
<ul>
<li>Authentication</li>
<li>Create and delete users</li>
<li>Create and delete cars</li>
<li>Create and cancelling payment</li>
<li>Add and return rental</li>
<li>Set rental for user</li>
<li>Change user role</li>
</ul>
<h2>Getting Started</h2>
<ul>
<li>Firstly you should copy repository to your local machine</li>
<li>Replace values in application.properties and liquibase.properties to your data</li>
<li>Then run Mysql server</li>
<li>Run the priject on your machine</li>
</ul>
<h2>Structure</h2>
<ul>
<li>controller - Rest controllers</li>
<li>dto - Data transfer objects for request and response</li>
<li>exception - Classes which contains some costom exceptions</li>
<li>lib - Classes whish contains validation of User</li>
<li>model - Classes which describe objects as Car, Payment, Rental, User, UserRole</li>
<li>repository - Classes which contains methods which work whith repository</li>
<li>security - Contains JWT Token and AuthenticationSeervice</li>
<li>service - Provides business logic, includes mapper-s for dto</li>
<li>resources - Contains scripts, database changelog and properties</li>
</ul>
<h2>Used Technologies</h2>
<ul>
<li>Java v.17.0.5</li>
<li>Maven-CheckStyle v.3.1.1</li>
<li>Maven v.3.10.1</li>
<li>Spring-Boot v.3.1.0</li>
<li>Liquibase v.4.2.2</li>
<li>Telegram bot v.6.5.0</li>
<li>Stripe API v.22.21.0</li>
<li>JSON Web Token (JWT) v.0.9.1</li>
<li>Lombok</li>
</ul>
<h2>Author</h2>
