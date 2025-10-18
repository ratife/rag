# RAG (Retrieval-Augmented Generation) Demo Project

This project demonstrates a Retrieval-Augmented Generation (RAG) system built with Spring Boot and Angular, leveraging Elasticsearch for document retrieval and ZhipuAI for generation capabilities.

## Project Overview

RAG is a technique that enhances Large Language Models (LLMs) by retrieving relevant information from a knowledge base before generating responses. This approach improves the accuracy and relevance of AI-generated content by grounding it in specific data.

## Architecture

The project follows a modular, clean architecture approach:

- **Domain**: Contains business logic, use cases, and domain entities
- **Infrastructure**: Handles data persistence, external services integration
- **Interface**: Provides REST API endpoints and web controllers
- **Front**: Angular-based frontend application

## Technologies

- **Backend**:
  - Java 23
  - Spring Boot 3.5.6
  - Spring AI 1.1.0-M2
  - Elasticsearch 8.10.4
  - H2 Database
  - MapStruct for object mapping

- **Frontend**:
  - Angular
  - TypeScript

- **DevOps**:
  - Docker & Docker Compose
  - Maven 3.9.11

## Setup and Installation

### Prerequisites
- Docker and Docker Compose
- Java 23 (for local development)
- Maven (for local development)
- Node.js and npm (for frontend development)

### Running with Docker

1. Clone the repository
2. Set the required environment variables:
   ```
   export ZHIPUAI_API_KEY=your_zhipuai_api_key
   ```
3. Start the application:
   ```
   docker-compose up -d
   ```

The application will be available at http://localhost:8080

### Development Setup

1. Clone the repository
2. Build the project:
   ```
   mvn clean install
   ```
3. Run the backend:
   ```
   cd interface
   mvn spring-boot:run
   ```
4. Run the frontend:
   ```
   cd front
   npm install
   ng serve
   ```

## Features

- Document upload and management
- Conversation-based interaction with documents
- Retrieval of relevant document sections based on queries
- AI-powered responses using ZhipuAI

## API Endpoints

- `/api/documents`: Document management
- `/api/conversations`: Conversation management
- `/api/messages`: Message handling

## License

[Add license information here]