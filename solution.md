# Crypto Conversion Web Application

This is a Java web application that allows users to fetch the current localized price of a cryptocurrency based on their IP address. The application provides a user interface where users can select a cryptocurrency and enter an optional IP address to retrieve the price in their local currency.

## Features

-  login functionality
- Landing page with a dropdown for selecting a cryptocurrency and an input field for the IP address
- Automatic detection of user's IP address if not provided
- Display of the current price of the selected cryptocurrency in the user's local currency
- Conversion history tracking for each user
- CI/CD integration for automated build and deployment

## Technologies Used

- Java
- Spring Boot
- Thymeleaf (for server-side rendering of HTML templates)
- RESTful API integration with external services (Coingecko API, ipapi)
- Maven (for dependency management)
- Docker (for containerization)
- GitHub Actions (for CI/CD)

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- Docker

### Installation

1. Clone the repository:

git clone https://github.com/rohit290992/crypto-conversion.git
cd your-repo

2. Build the application:
   mvn clean install

### Usage

1. Start the Docker container:
   docker run -p 8080:8080 crypto-conversion-app
2. Open a web browser and access the application at http://localhost:8080
3. Register a new user or log in with an existing account.
4. On the home page, select a cryptocurrency from the dropdown and optionally enter an IP address. Click the "Convert" button to fetch and display the current price in the user's local currency. 
5. The conversion history will be displayed below the conversion form.
