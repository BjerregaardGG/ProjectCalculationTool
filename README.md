

![Project Calculation Tool](https://static1.makeuseofimages.com/wordpress/wp-content/uploads/2023/08/using-thymeleaf-in-spring-boot-2.jpg)
# Project Calculation Tool

This Program is a web based application, made to make creating and managing projects an easy task. 
you will be able to create projects, and break them into smaller subprojects and those into task. it will allow you to add, update and delete where ever you like in your project. 

## Built With
- **Spring Boot** - Backend framework.
- **Thymeleaf** - Template engine for dynamic HTML generation.
- **H2 Database** For  local database.
- **Mysql Database** For deployment.
- **Maven** - Dependency management.

## Getting Started

To run the application locally, follow these steps:

## Prerequisites
- Java 17 or later
- Maven
- An IDE (e.g., IntelliJ IDEA or Eclipse)

## Installation and Running
1. Clone the repository:
   ```bash
   git clone https://github.com/BjerregaardGG/ProjectCalculationTool.git
   cd ProjectCalculationTool ```

2. Run the program through your IDE.

3. Access the application in your browser at http://localhost:8080/login

## Usage
    1. Open the application in your browser.
    2. Create a new project by clicking "Create Project" 
    3. Add subprojects and tasks to the project.
    4. View the calculated cost and work per day in hours for the entire project.

## Configuration
- By default, the application uses an embedded H2 database.
- To connect to another database, update the `application.properties` file:
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/your-database
  spring.datasource.username=your-username
  spring.datasource.password=your-password

## Contributing
Contributions are welcome! We would love feedback \
Please follow these steps:

    1. Fork the repository.
    2. Create a feature branch: `git checkout -b feature/new-feature`.
    3. Commit your changes: `git commit -m "Add new feature"`.
    4. Push to the branch: `git push origin feature/new-feature`.
    5. Submit a pull request.

## Authors 

 * [BjerregaardGG](https://github.com/BjerregaardGG)
 * [NikolajPirum](https://github.com/NikolajPirum)
 * [Jhockinn](https://github.com/Jhockinn)
 * [Telity](https://github.com/Telity)
