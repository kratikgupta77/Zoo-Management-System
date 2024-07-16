# Zoo Management System (Java)

## Steps to RUN

- mvn clean
- mvn compile
- mvn package
- java -jar .\target\Untitled-1.0-SNAPSHOT.jar

## Features

### For Admins

- Add, remove, or modify attractions in the zoo.
- Manage a list of animals and their information.
- Set discounts for specific visitor categories.
- Schedule events at attractions.
- View feedback and suggestions from visitors.

### For Visitors

- Purchase zoo tickets based on their chosen experience level (Basic, Premium).
- Buy memberships to avail discounts and special deals.
- Explore the zoo's attractions.
- View information about animals and attractions.
- Leave feedback and suggestions to improve the zoo experience.

## Code Structure

Zoo: Represents the main class for managing the zoo's operations. It contains lists for attractions, animals, visitors, discounts, and feedback. It provides methods for managing these entities.

Attraction: Represents an attraction within the zoo, with attributes like ID, name, information, opening time, closing time, ticket price, and visitor count.

Animal(interface): Represents an animal in the zoo, with attributes like name, and information.it is extended by 3 subclasses mammals reptiles amphibians.

Visitor: Represents a visitor, with attributes including name, age, phone number, balance, username, and membership type. This class contains visitor-specific functionalities.

Admin: Extends the User class and is responsible for admin-specific functionalities

User: Represents a user with username and password.

Other data structure are used like list,hashmap for discounts, SpecialDeals, and Feedback.

## Error Handling

To handle errors in the code like null pointer exceptions I have instantiated few object variables in the main code.

## Authors

- Kratik Gupta

## Acknowledgments

This project was inspired by the need for an efficient zoo management system and was created to demonstrate good coding practices in Java.
