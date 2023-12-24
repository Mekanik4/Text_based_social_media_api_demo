# **Text based social media api demo**

The goal of this project is to create a text-based social media API that allows
users to register, login, post text, comment on posts, follow other users, and view different
types of posts and comments. The API will be used by a front-end application, such as a
single-page app.

The API is expected to have up to 500 active users and will have two types of users: Free and
Premium. During registration, users can select their plan type, and the username will be
their email address.


## **Implemented features**

### Create role
You can create a new role by providing the role's name.
> Endpoint: "/role"
> 
> No authentication needed
> 
> Request body: {"name": String}


### Register user
You can create a new user by providing an email, a password, a role and the type of the user, free (false) or premium (true).
> Endpoint: "/auth/signup"
> 
> No authentication needed
> 
> Request body: {"email": String, "password": String,  "premium": Boolean, "role": String}


### Login with user
You can login with an existing user by providing the user's email and password.
> Endpoint: "/auth/login"
> 
> No authentication needed
> 
> Request body: {"email": String, "password": String}


### View user's details
You can view the user's details after you have logged in with this user.
> Endpoint: "/users/me"
> 
> Authentication needed


### Create a post
Users can post a small text with a limit of 1000 characters.
> Endpoint: "/users/me/posts/new"
> 
> Authentication needed
> 
> Request body: {"context": String}



### Create a comment
Users can comment at most 10 times per post.
> Endpoint: "/users/me/posts/{post_id}/comments/new"
> 
> Authentication needed
> 
> Request body: {"context": String}


### Follow a user
Users can follow other users.
> Endpoint: "/users/me/follow"
> 
> Authentication needed
> 
> Request body: {"followingName": String}


### Stop following a user
Users can delete other users from their following list.
> Endpoint: "/users/me/followers/delete"
> 
> Authentication needed
> 
> Request body: {"followingName": String}


### View posts
Users can view all posts posted by users they follow, ordered by reverse chronological order.
> Endpoint: "/users/me/following/posts"
> 
> Authentication needed


Users can view their own posts, including the latest 100 comments, sorted by reverse chronological order.
> Endpoint: "/users/me/posts"
> 
> Authentication needed


### View comments
Users can view the latest comment they posted on each post by them or by the users they follow.
> Endpoint: "/users/me/comments/latest"
> 
> Authentication needed


Users can view the latest comment on all posts by them or by the users they follow.
> Endpoint: "/users/me/posts/comments/latest"
> 
> Authentication needed

### Retrieve follows
Users can retrieve a list of all the users they follow.
> Endpoint: "/users/me/following"
> 
> Authentication needed


Users can retrieve a list of all the users who follow them.
> Endpoint: "/users/me/followers"
> 
> Authentication needed


## Other features

An automated schema generation has been implemented.

The project includes a testing folder with implemented tests.

## Tech stack

The project uses:
> Java 17
> 
> Maven as build tool
> 
> Java Spring Boot
> 
> PostgeSQL database


## Usage instructions

1) Download the project and open it in a Java IDE, like IntelliJ. 

2) Create a PostgreSQL database. Use the database specifications stated in application.yml file.

3) Build and run the project.

4) For more information about the endpoints you can see the swagger documentation in http://localhost:8081/swagger-ui/index.html#/ while the project is running. 



