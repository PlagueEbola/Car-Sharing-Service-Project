# Car-Sharing-Service-Project
<img src="https://cdn.dribbble.com/users/508588/screenshots/14845034/media/18078f287ce75878d1858ab43d7607e2.jpg?compress=1&resize=400x300">
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
<h2>Endpoints</h2>
<ul>
<li>Authentication Controller</li>
<ul>
<li>POST /register - register new user (non-auth access);</li>
<li>POST /login - login user;</li>
</ul>
<li>Car Controller</li>
<ul>
<li>POST: /cars - add new car;</li>
<li>GET: /cars - get list of cars;</li>
<li>GET: /cars/{id} - get var by id;</li>
<li>PUT: /cars/{id} - update car by id;</li>
<li>DELETE: /cars/{id} - delete car by id;</li>
</ul>
<li>Payment Controller</li>
<ul>
<li>POST: /payments - create payment;</li>
<li>GET: /payments/success/{id} - set status PAID;</li>
<li>GET: /payments/cancell/{id} - cancell payment;</li>
</ul>
<li>Rental Controller</li>
<ul>
<li>POST: /rentals - add a new rental;</li>
<li>GET: /rentals - get list of user rentals;</li>
<li>GET: /rentals/{id} - get rental by id;</li>
<li>POST: /rentals/{id}/return - end of rental;</li>
</ul>
<li>User Controller</li>
<ul>
<li>GET: /users/me - get my profile info;</li>
<li>PUT: /users/me - update my profile info;</li>
<li>PUT: /users/{id}/role - update role of user;</li>
</ul>
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
