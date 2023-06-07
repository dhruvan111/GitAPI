## Project Title

REST API with Spring Boot, MongoDB, and JWT Authentication

## Description

This project is a REST API implementation using Spring Boot, MongoDB, and JWT authentication. It provides endpoints to interact with the GitHub API, retrieve repository data based on a Git username, store the data in a MongoDB database, and publish repositories on GitHub using the GitHub API.

## Features

- User registration and login
- JWT authentication for secure access to API endpoints
- Retrieve repository data from the GitHub API based on Git username
- Store repository data in MongoDB
- Publish repositories on GitHub using the GitHub API

## Technologies Used

- Java
- Spring Boot
- Spring Security
- MongoDB
- GitHub API

## Installation and Setup

1. Clone the repository: `git clone <repository_url>`
2. Navigate to the project directory: `cd <project_directory>`
3. Configure the MongoDB connection details in `application.properties` file.
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run`

## API Endpoints

- `/api/signup` - Register a new user
- `/api/signin` - User login to obtain JWT token
- `/api/test/all` - Public api, no roles needed
- `/api/test/user` - Allowed roles -> ( USER, MODERATOR, ADMIN )
- `/api/test/mod` - Allowed roles -> (MODERATOR, ADMIN )
- `/api/test/admin` - Allowed roles -> (ADMIN)
- `/api/repo/git/{owner}` - Retrieve repository data for a given Git username
- `/api/repo/git/publish` - Publish a repository on GitHub
and so on... 

## Usage

1. Register a new user using the `/api/signup` endpoint.
2. Login with the registered user credentials using the `/api/signin` endpoint to obtain a JWT token.
3. Use the obtained JWT token to authenticate subsequent API requests by including it in the Authorization header.
4. Retrieve repository data for a Git username using the `/api/repo` endpoint.
5. Publish a repository on GitHub using the `/api/repo/publish` endpoint.

## Contributing

Contributions are welcome! If you would like to contribute to this project, please follow these steps:

1. Fork the repository
2. Create a new branch
3. Make your changes
4. Submit a pull request
