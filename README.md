# Installation and Running Guide for Laboratory Reporting Application

This document provides step-by-step instructions for installing and running the laboratory reporting application
developed using Spring Boot and React. Here are the detailed steps:

## Requirements

- Java Development Kit (JDK) 17 or newer
- Node.js and npm (Node Package Manager)
- Git (optional)

## Step 1: Download the Project

You can download the project to your computer by cloning the GitHub repository. If you don't use Git, you can also
download it from the project's GitHub page by selecting the "Download ZIP" option.

```bash
git clone https://github.com/halil-akgun/laboratory-reporting-application.git
```

## Step 2: Installation and Running of the Server (Spring Boot) Part

1. Navigate to the `laboratory-reporting-application` folder:
   ```bash
   cd laboratory-reporting-application
   ```
2. Start the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Step 3: Installation and Running of the Client (React) Part

1. Navigate to the `laboratory-reporting-frontend` folder:
   ```bash
   cd laboratory-reporting-frontend
   ```
2. Install dependencies by running the following command:
   ```bash
   npm install
   ```
3. Start the React application:
   ```bash
   npm start
   ```

This command will start the React application, and it will be accessible in your browser at the default
address `http://localhost:3000`.

## Using the Application

You have successfully launched the application. You can access and start using the application by going
to `http://localhost:3000` in your browser.

## Conclusion

This guide covers the installation and running steps for the laboratory reporting application developed with Spring Boot
and React. After successfully running the project, you can continue to enhance the application.

Note: Project dependencies and system requirements may change, so be sure to make adjustments according to the latest
documentation and project requirements.
