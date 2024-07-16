    package example;
import java.util.*;
class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean correctLogin(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
}

class Visitor extends User {
    private String name;
    private int age;
    private String phoneNumber;
    private double balance;
    private String username;
    public String membershipType;

    public Visitor(String username, String password, String name, int age, String phoneNumber, double balance) {
        super(username, password);
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.username = username;
        this.membershipType="basic";
    }
    public String getName(){
        return name ;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean login(String enteredUsername, String enteredPassword) {
        if (super.correctLogin(enteredUsername, enteredPassword)) {
            System.out.println("visitor logged in successfully.");
            return true;
        } else {
            System.out.println("Wrong username or password. Please try again.");
            return false;
        }
    }

    public void register(Zoo zoo) {
        zoo.addVisitor(this);
    }

    private void exploreTheZoo(Zoo zoo, Scanner scanner) {
        while (true) {
            System.out.println("explore the zoo:");
            System.out.println("1. View Attraction");
            System.out.println("2. View Animals");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                zoo.viewAttractions();
            } else if (choice == 2) {
                zoo.viewAnimals();
            } else if (choice == 3) {
                System.out.println("Exiting visitor Menu.");
                break;
            } else {
                System.out.println("entered invalid");
            }
        }
    }

    private void buyMembership(Scanner scanner, Visitor visitor, Zoo zoo) {
        while (true) {
            System.out.println("Buy Membership:");
            System.out.println("Select a Membership Type:");
            System.out.println("1. Premium Membership (₹2000)");
            System.out.println("2. Basic Membership (₹1000)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice. Please select a valid membership type or exit.");
                continue;
            }

            if (choice == 3) {
                break;
            }

            double membershipPrice = (choice == 1) ? 2000 : 1000; // Set the price based on choice
            if (choice==1){
            this.membershipType="premium";}
            System.out.print("Enter a discount code (or write na for no discount): ");
            String discountCode = scanner.next();

            applyDiscount(zoo, (choice == 1) ? "premium" : "basic", discountCode, membershipPrice, visitor);
        }
    }

    public void applyDiscount(Zoo zoo, String membershipType, String discountCode, double membershipPrice, Visitor visitor) {
        if (discountCode.equalsIgnoreCase("na")) {
            // No discount code provided
            if (visitor.getBalance() < membershipPrice) {
                System.out.println("Insufficient balance. Your balance is ₹" + visitor.getBalance() + " but the membership price is ₹" + membershipPrice);
            } else {
                visitor.setBalance(visitor.getBalance() - membershipPrice);
                System.out.println("Membership purchased successfully. Your balance is now ₹" + visitor.getBalance());
            }
        } else if (zoo.hasDiscount(discountCode)) {
            double discountPercentage = zoo.getDiscountPercentage(discountCode);
            double discountedPrice = membershipPrice * (1 - discountPercentage / 100);

            if (visitor.getBalance() < discountedPrice) {
                System.out.println("Insufficient balance. Your balance is ₹" + visitor.getBalance() + " but the discounted price is ₹" + discountedPrice);
            } else {
                visitor.setBalance(visitor.getBalance() - discountedPrice);
                System.out.println("Membership purchased successfully with a discount. Your balance is now ₹" + visitor.getBalance());
            }
        } else {
            System.out.println("Invalid discount code. Your price is: ₹" + membershipPrice);
        }
    }

    public void visitAnimal(Animal animal, Scanner scanner) {
        System.out.println("Visitor " + this.name + " is visiting the " + animal.getName());
        System.out.println("Category: " + animal.getCategory());


        if (animal instanceof Mammal mammal) {
            mammal.makeNoise();
        } else if (animal instanceof Amphibian amphibian) {
            amphibian.makeNoise();
        } else if (animal instanceof Reptile reptile) {
            reptile.makeNoise();
        } else {
            System.out.println("This animal type is not supported for interaction.");
        }

        System.out.println("What would you like to do?");
        System.out.println("1. Feed the " + animal.getName());
        System.out.println("2. Read about the " + animal.getName());
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                animal.feed();
                break;
            case 2:
                animal.readAbout();
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }


    public void visitorMenu(Zoo zoo) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Visitor Menu:");
            System.out.println("1. Explore the Zoo");
            System.out.println("2. Buy Membership");
            System.out.println("3. Buy Tickets");
            System.out.println("4. View Discounts");
            System.out.println("5. View Special Deals");
            System.out.println("6. Visit Animals");
            System.out.println("7. Visit Attractions");
            System.out.println("8. Leave Feedback");
            System.out.println("9. Log Out");
            System.out.print("Enter your choice: ");
            int visitorChoice = scanner.nextInt();

            switch (visitorChoice) {
                case 1:
                    exploreTheZoo(zoo, scanner);
                    break;
                case 2:
                    buyMembership(scanner, this, zoo);
                    break;
                case 3:
                    zoo.buyTicket(scanner, this);
                    break;
                case 4:
                    zoo.viewDiscounts();
                    break;
                case 5:
                    zoo.viewSpecialDeals();
                    break;
                case 6:
                    zoo.viewAnimals();
                    System.out.print("Enter the name of the animal you want to visit: ");
                    String animalName = scanner.next();
                    Animal selectedAnimal = zoo.findAnimalByName(animalName);

                    if (selectedAnimal != null) {
                        this.visitAnimal(selectedAnimal, scanner);
                    } else {
                        System.out.println("Animal not found in the zoo.");
                    }


                    break;
                case 7:
                    zoo.viewAttractions();
                    System.out.print("Enter the id of the attraction you want to visit: ");
                    int id = scanner.nextInt();
                    Attraction selectedAttraction = zoo.findAttractionByID(id);

                    if (selectedAttraction != null) {
                        zoo.visitAttractions(scanner,this);
                    } else {
                        System.out.println("Attraction not found in the zoo.");
                    }

                    break;
                case 8:
                    System.out.println("provide feedback about zoo experiance");
                    String feed = scanner.next();
                    zoo.submitFeedback(feed);
                    scanner.nextLine();
                    break;
                case 9:
                    System.out.println("Logging out.");
                    return;


                    default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


}


    interface Animal {
        String getName();
        String getCategory();
        String getInformation();
        void setInformation(String information);
        void feed() ;
        void readAbout() ;
    }
class Mammal implements Animal {
    private String name;
    private String information;
    public void makeNoise() {
        System.out.println("The " + getName() + " roars.");
    }
    public void readAbout() {
        // Implement reading about this animal
        System.out.println("Reading about the " + name);
        System.out.println("Description: " + information);
    }
    public void feed() {
        // Implement the feeding behavior for this animal
        System.out.println("Feeding the " + name);
        makeNoise(); // Call the makeNoise method when feeding
    }


    public Mammal(String name, String information) {
        this.name = name;
        this.information = information;
    }

    public String getCategory() {
        return "Mammals";
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
class Reptile implements Animal {
    private String name;
    private String information;
    public void makeNoise() {
        System.out.println("The " + getName() + " makes a reptilian sound.");
    }
    public void feed() {
        System.out.println("Feeding the " + name);
        makeNoise();
    }


    public Reptile(String name, String information) {
        this.name = name;
        this.information = information;
    }
    public void readAbout() {
        System.out.println("Reading about the " + name);
        System.out.println("Description: " + information);
    }

    public String getCategory() {
        return "Reptiles";
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
class Amphibian implements Animal {
    private String name;
    private String information;
    public void makeNoise() {
        System.out.println("The " + getName() + " makes a amphibian  sound.");
    }


    public Amphibian(String name, String information) {
        this.name = name;
        this.information = information;
    }
    public void readAbout() {
        System.out.println("Reading about the " + name);
        System.out.println("Description: " + information);
    }
    public void feed() {
        System.out.println("Feeding the " + name);
        makeNoise();
    }


    public String getCategory() {
        return "Amphibians";
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}


class Admin extends User {
        private static final String ADMIN_USERNAME = "admin";
        private static final String ADMIN_PASSWORD = "admin123";

        public Admin(String username, String password) {
            super(username, password);
        }

        public boolean login(String enteredUsername, String enteredPassword) {
            if (super.correctLogin(enteredUsername, enteredPassword)) {
                System.out.println("Admin logged in successfully.");
                return true;
            } else {
                System.out.println("Wrong username/password. try again.");
                return false;
            }
        }

        public void adminMenu(Zoo zoo) {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Admin Menu:");
                System.out.println("1. Manage Attractions");
                System.out.println("2. Manage Animals");
                System.out.println("3. Schedule Events");
                System.out.println("4. Set Discounts");
                System.out.println("5. Set Special Deal");
                System.out.println("6. View Visitor Stats");
                System.out.println("7. View Feedback");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                int adminChoice = scanner.nextInt();

                if (adminChoice == 1) {
                    manageAttractions(zoo, scanner);
                } else if (adminChoice == 2) {
                    manageAnimals(zoo, scanner);
                } else if (adminChoice == 3) {
                    manageschedules(zoo, scanner);
                } else if (adminChoice == 4) {
                    setDiscounts(zoo, scanner);
                } else if (adminChoice == 5) {
                    setSpecialDeals(zoo, scanner);
                } else if (adminChoice == 6) {
                    viewVisitorStats(zoo);
                } else if (adminChoice == 7) {
                    zoo.viewFeedback();
                } else if (adminChoice == 8) {
                    System.out.println("Exiting Admin Menu.");
                    break;
                }
            }
            //scanner.close();
        }

        private void manageAttractions(Zoo zoo, Scanner scanner) {
            while (true) {
                System.out.println("Manage Attractions/Events:");
                System.out.println("1. Add Attraction");
                System.out.println("2. View Attractions");
                System.out.println("3. Modify Attraction");
                System.out.println("4. Remove Attraction");

                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter Attraction Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Attraction Description: ");
                    String description = scanner.next();
                    System.out.print("Enter Attraction ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter Attraction ticket price: ");
                    double price = scanner.nextDouble();
                    zoo.addAttraction(new Attraction(id, description, name,price));
                    System.out.println("Attraction added successfully.");
                } else if (choice == 2) {
                    zoo.viewAttractions();
                } else if (choice == 3) {
                    System.out.print("Enter Attraction ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter Attraction new Description: ");
                    String description = scanner.next();
                    System.out.print("Enter Attraction NEW Name: ");
                    String name = scanner.next();
                    zoo.modifyAttraction(id, description, name);
                } else if (choice == 4) {
                    System.out.print("Enter Attraction ID to remove: ");
                    int id = scanner.nextInt();
                    zoo.removeAttraction(id);
                } else if (choice == 5) {
                    System.out.println("Exiting Manage Attractions.");
                    break;
                }
            }
        }

        private void manageAnimals(Zoo zoo, Scanner scanner) {
            while (true) {
                System.out.println("Manage Animals:");
                System.out.println("1. Add Animal to zoo list");
                System.out.println("2. Update Animal Details");
                System.out.println("3. Remove Animal");
                System.out.println("4. add animal to attraction");

                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter Animal Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Animal Category (Mammals/Amphibians/Reptiles): ");
                    String category = scanner.next();
                    System.out.print("Enter Animal Description: ");
                    String description = scanner.next();
                    Animal animal;
                    if (category.equalsIgnoreCase("Mammals")) {
                        animal = new Mammal(name, description);
                    } else if (category.equalsIgnoreCase("Amphibians")) {
                        animal = new Amphibian(name, description);
                    } else if (category.equalsIgnoreCase("Reptiles")) {
                        animal = new Reptile(name, description);
                    } else {
                        System.out.println("Invalid animal category. Please choose Mammals, Amphibians, or Reptiles.");
                        scanner.nextLine();
                        continue;
                    }
                    zoo.addAnimal(animal);
                    System.out.println("Animal added successfully.");
            } else if (choice == 2) {
                    System.out.print("Enter Animal name to update details: ");
                    String name = scanner.next();
                    System.out.print("Enter Animal info to be updated: ");
                    String info = scanner.next();
                    zoo.updateAnimaldetails(name, info);
                } else if (choice == 3) {
                    System.out.print("Enter Animal name to remove: ");
                    String name = scanner.next();
                    zoo.removeAnimal(name);
                } else if (choice == 4) {
                    System.out.println("enter attraction id to add to:");
                    int id = scanner.nextInt();
                    System.out.print("Enter Animal name to add: ");
                    String name = scanner.next();
                    zoo.findAttractionByID(id).addAnimal(zoo.findAnimalByName(name));
                } else if (choice == 5) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }

        private void manageschedules(Zoo zoo, Scanner scanner) {
            while (true) {
                System.out.println("manage schedules:");
                System.out.println("1. view schedules");
                System.out.println("2. change schedules of attractions  ");
                System.out.println("3. set ticket prices");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter Attraction ID: ");
                    int id = scanner.nextInt();
                    zoo.viewScheduleTimings(id);
                } else if (choice == 2) {
                    System.out.print("Enter Attraction ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter new start time of event  ");
                    String etime = scanner.next();
                    System.out.print("Enter new closing time of event ");
                    String otime = scanner.next();
                    zoo.scheduleEvent(id, etime, otime);
                } else if (choice == 3) {
                    System.out.println("enter attraction id for setting ticket prices:");
                    int id = scanner.nextInt();
                    System.out.println("enter new price (in double):");
                    double price = scanner.nextInt();
                    zoo.findAttractionByID(id).setticketprice(price);
                } else if (choice == 4) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }

        private void setDiscounts(Zoo zoo, Scanner scanner) {
            while (true) {
                System.out.println("Set Discounts:");
                System.out.println("1. Add Discount");
                System.out.println("2. Remove Discount");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter visitor category (Minor, Senior): ");
                    String category = scanner.next();
                    System.out.print("Enter discount percentage: ");
                    double discountPercentage = scanner.nextDouble();
                    zoo.setDiscount(category, discountPercentage);
                    System.out.println("Discount added successfully.");
                } else if (choice == 2) {
                    System.out.print("Enter visitor category to remove: ");
                    String category = scanner.next();
                    zoo.removeDiscount(category);
                    System.out.println("Discount removed successfully.");
                } else if (choice == 3) {
                    System.out.println("Exiting Set Discounts.");
                    break;
                }
            }
        }

        private void setSpecialDeals(Zoo zoo, Scanner scanner) {
            while (true) {
                System.out.println("Set Special Deals:");
                System.out.println("1. Add Special Deal");
                System.out.println("2. Remove Special Deal");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter minimum attractions for the deal: ");
                    int minAttractions = scanner.nextInt();
                    System.out.print("Enter discount percentage for the deal: ");
                    double discountPercentage = scanner.nextDouble();
                    zoo.addSpecialDeal(minAttractions, discountPercentage);
                    System.out.println("Special deal added successfully.");
                } else if (choice == 2) {
                    System.out.print("Enter minimum attractions to remove: ");
                    int minAttractions = scanner.nextInt();
                    zoo.removeSpecialDeal(minAttractions);
                    System.out.println("Special deal removed successfully.");
                } else if (choice == 3) {
                    System.out.println("Exiting Set Special Deals.");
                    break;
                }
            }
        }

        private void viewVisitorStats(Zoo zoo) {
            zoo.viewVisitorStats();
        }


    }

    class Attraction {
        private int ID;

        private String name;
        private String information;
        private String openingTime;
        private String closingTime;
        private double ticketPrice;
        private int visitorCount;
        private List<Animal> animals;

        public Attraction(int ID, String name, String information,double price) {
            this.ID = ID;
            this.name = name;
            this.information = information;
            this.openingTime = "09:00 AM"; // Default opening time
            this.closingTime = "05:00 PM"; // Default closing time
            this.ticketPrice = price;
            this.visitorCount = 0;
            this.animals = new ArrayList<>();
        }

        public  String displayAnimalList() {
            for (Animal animal : animals) {
                System.out.println(animal.getName()); // Assuming Animal has a getName() method
            }
            return "end";
        }

        public void setticketprice(double i) {
            this.ticketPrice = i;
        }


        public void addAnimal(Animal animal) {
            animals.add(animal);
        }


        public void removeAnimal(Animal animal) {
            animals.remove(animal);
        }

        public String getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(String openingTime) {
            this.openingTime = openingTime;
        }

        public String getClosingTime() {
            return closingTime;
        }

        public void setClosingTime(String closingTime) {
            this.closingTime = closingTime;
        }

        public double getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(double ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public int getVisitorCount() {
            return visitorCount;
        }

        public void incrementVisitorCount() {
            visitorCount++;
        }

        public String getInformation() {
            return information;
        }

        public String getName() {
            return name;
        }

        public int getID() {
            return ID;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Zoo {
        private List<Attraction> attractionList;
        private Map<String, Double> discounts;
        private Map<Integer, Double> specialDeals;
        private List<Visitor> visitorList;
        private List<String> visitorFeedbackList;

        private List<Animal> animalList;

        public Zoo(List<Visitor> visitorList,List<Animal> animalList,List<Attraction> attractionList,Map<String, Double> discounts) {
            visitorFeedbackList = new ArrayList<>();
            this.attractionList = attractionList;
            this.animalList = animalList;
            this.visitorList = visitorList;
            this.discounts = discounts;
            specialDeals = new HashMap<>();
        }

        public void viewDiscounts() {
            System.out.println("Available Discounts:");
            discounts.forEach((category, percentage) -> {
                System.out.println(category + ": " + percentage + "%");
            });
        }

        public double getDiscountPercentage(String discountCategory) {
            if (hasDiscount(discountCategory)) {
                return discounts.get(discountCategory);
            } else {
                                // return 0.0 if the discount category is not found.
                return 0.0;
            }
        }

        public void addVisitor(Visitor visitor) {
            visitorList.add(visitor);
        }

        public boolean hasDiscount(String discountCategory) {
            for (String category : discounts.keySet()) {
                if (category.equalsIgnoreCase(discountCategory)) {
                    return true;
                }
            }
            return false;
        }

        public void addAttraction(Attraction attraction) {
            attractionList.add(attraction);
        }

        public void addAnimal(Animal animal) {
            animalList.add(animal);
        }

        public void setDiscount(String category, double discountPercentage) {
            discounts.put(category, discountPercentage);
        }

        public void removeDiscount(String category) {
            discounts.remove(category);
        }

        public void addSpecialDeal(int minAttractions, double discountPercentage) {
            specialDeals.put(minAttractions, discountPercentage);
        }

        public void removeSpecialDeal(int minAttractions) {
            specialDeals.remove(minAttractions);
        }

        public void viewSpecialDeals() {
            System.out.println("Available Special Deals:");
            specialDeals.forEach((attractionID, dealPercentage) -> {
                System.out.println("Attraction ID: " + attractionID + ", Discount: " + dealPercentage + "%");
            });
        }

        public void viewVisitorStats() {
            for (Attraction attraction : attractionList) {
                System.out.println("the attraction " + attraction.getName() + " has recorded " + attraction.getVisitorCount() + " visitors.");
            }
        }

        public Attraction findAttractionByID(int attractionID) {
            for (Attraction attraction : attractionList) {
                if (attraction.getID() == attractionID) {
                    return attraction;
                }
            }
            return null; // Attraction with the specified ID not found
        }

        public Animal findAnimalByName(String name) {
            for (Animal animal : animalList) {
                if (animal.getName().equals(name)) {
                    return animal;
                }
            }
            return null; // Animal with the specified name not found
        }

        public void viewAttractions() {
            if (attractionList.isEmpty()) {
                System.out.println("There are no attractions to view.");
            } else {
                System.out.println("Attractions:");
                for (Attraction attraction : attractionList) {
                    System.out.println("Attraction ID: " + attraction.getID());
                    System.out.println("Name: " + attraction.getName());
                    System.out.println("Information: " + attraction.getInformation());
                    System.out.println("Ticket Price: ₹" + attraction.getTicketPrice());
                    System.out.println("animals attraction are"+attraction.displayAnimalList());
                    System.out.println();
                }
            }
        }

        public void viewAnimals() {
            if (animalList.isEmpty()) {
                System.out.println("There are no animals to view.");
            } else {
                System.out.println("Animals:");
                for (Animal animal : animalList) {
                    System.out.println("Name: " + animal.getName());
                    System.out.println("Category: " + animal.getCategory());
                    System.out.println("Description: " + animal.getInformation());
                    System.out.println();
                }
            }
        }

        public void removeAnimal(String name) {
            Animal animal = findAnimalByName(name);
            if (animal != null) {
                animalList.remove(animal);
                System.out.println("Animal with name " + name + " has been removed.");
            } else {
                System.out.println("Animal with name " + name + " not found in the list.");
            }
        }

        public void modifyAttraction(int attractionID, String newInformation, String newName) {
            Attraction attraction = findAttractionByID(attractionID);
            if (attraction != null) {
                attraction.setInformation(newInformation);
                attraction.setName(newName);
                System.out.println("Attraction with ID " + attractionID + " has been modified.");
            } else {
                System.out.println("Attraction with ID " + attractionID + " not found in the list.");
            }
        }

        public void updateAnimaldetails(String name, String newInfo) {
            Animal animal = findAnimalByName(name);
            if (animal != null) {
                animal.setInformation(newInfo);
                System.out.println("Animal " + animal.getName() + " has been modified.");
            } else {
                System.out.println("Animal not found with name " + name);
            }

        }

        public void removeAttraction(int attractionID) {
            Attraction attraction = findAttractionByID(attractionID);
            if (attraction != null) {
                attractionList.remove(attraction);
                System.out.println("Attraction with ID " + attractionID + " has been removed.");
            } else {
                System.out.println("Attraction with ID " + attractionID + " not found in the list.");
            }
        }


        public void scheduleEvent(int attractionID, String eventTime, String endtime) {
            Attraction selectedAttraction = findAttractionByID(attractionID);

            if (selectedAttraction != null) {
                System.out.println("Previous event timings " + selectedAttraction.getOpeningTime() + " to " + selectedAttraction.getClosingTime());
                selectedAttraction.setOpeningTime(eventTime);
                selectedAttraction.setClosingTime(endtime);
                System.out.println("Event '" + selectedAttraction.getName() + "' scheduled for " + selectedAttraction.getName() + " at " + eventTime + " to " + endtime);
            } else {
                System.out.println("Attraction with ID " + attractionID + " not found in the zoo.");
            }
        }

        public void viewScheduleTimings(int attractionID) {
            Attraction selectedAttraction = findAttractionByID(attractionID);

            if (selectedAttraction != null) {
                System.out.println("Scheduled Event Timings for Attraction " + selectedAttraction.getName() + " (ID: " + selectedAttraction.getID() + "):");
                System.out.println("Opening Time: " + selectedAttraction.getOpeningTime());
                System.out.println("Closing Time: " + selectedAttraction.getClosingTime());
            } else {
                System.out.println("Attraction with ID " + attractionID + " not found in the zoo.");
            }
        }
        /*private boolean isValidAnimalCategory(Animal animal) {
            String category = animal.getCategory();
            return category.equals("Mammals") || category.equals("Amphibians") || category.equals("Reptiles");
        }*/
        public void buyTicket(Scanner scanner, Visitor visitor) {
            while (true) {
                System.out.println("Buy Tickets:");
                System.out.println("Select an Attraction to Buy a Ticket:");

                // Display the list of attractions and their prices
                int attractionIndex = 1;
                for (Attraction attraction : attractionList) {
                    System.out.println(attractionIndex + ". " + attraction.getName() + " (₹" + attraction.getTicketPrice() + ")");
                    attractionIndex++;
                }

                System.out.println(attractionIndex + ". Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice < 1 || choice > attractionList.size() + 1) {
                    System.out.println("Invalid choice. Please select a valid attraction or exit.");
                    continue;
                }

                if (choice == attractionList.size() + 1) {
                    break;
                }

                Attraction selectedAttraction = attractionList.get(choice - 1);
                double ticketPrice = selectedAttraction.getTicketPrice();

                if (visitor.getBalance() < ticketPrice) {
                    System.out.println("Insufficient balance. Your balance is ₹" + visitor.getBalance() + " but the ticket price is ₹" + ticketPrice);
                    continue;
                }
                visitor.setBalance(visitor.getBalance() - ticketPrice);
                System.out.println("The ticket for " + selectedAttraction.getName() + " was purchased successfully. Your balance is now ₹" + visitor.getBalance());
                selectedAttraction.incrementVisitorCount();
            }
        }

        public void submitFeedback(String feedback) {
            visitorFeedbackList.add(feedback);
            System.out.println("Thank you for your feedback!");
        }

        public void viewFeedback() {
            if (visitorFeedbackList.isEmpty()) {
                System.out.println("There is no feedback available.");
            } else {
                System.out.println("Visitor Feedback:");
                for (String feedback : visitorFeedbackList) {
                    System.out.println(feedback);
                }
            }
        }
        public void visitAttractions(Scanner scanner, Visitor visitor) {
            System.out.println("Select an Attraction to Visit:");

            // Display the list of attractions and their prices
            int attractionIndex = 1;
            for (Attraction attraction : this.attractionList) {
                System.out.println(attractionIndex + ". " + attraction.getName() + " (₹" + attraction.getTicketPrice() + ")");
                attractionIndex++;
            }

            System.out.println(attractionIndex + ". Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice < 1 || choice > this.attractionList.size() + 1) {
                System.out.println("Invalid choice. Please select a valid attraction or exit.");
            } else if (choice == this.attractionList.size() + 1) {
                System.out.println("Exiting the Visit Attractions menu.");
            } else {
                Attraction selectedAttraction = this.attractionList.get(choice - 1);

                // Check if the visitor is a basic member
                if (visitor.membershipType.equalsIgnoreCase("basic")) {
                    System.out.println("Ticket not available. Basic Members need to buy separate tickets for the attractions.");
                } else {
                    double ticketPrice = selectedAttraction.getTicketPrice();
                    if (visitor.getBalance() >= ticketPrice) {
                        visitor.setBalance(ticketPrice);
                        System.out.println("You have successfully visited " + selectedAttraction.getName() + ". Your balance is now ₹" + visitor.getBalance());
                        selectedAttraction.incrementVisitorCount();
                    } else {
                        System.out.println("Insufficient balance. Your balance is ₹" + visitor.getBalance()+ " but the ticket price is ₹" + ticketPrice);
                    }
                }
            }
        }


    }
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Visitor> visitors = new ArrayList<>();
        visitors.add(new Visitor("visitor1", "password1", "John Doe", 25, "1234567890", 1000.0));
        visitors.add(new Visitor("visitor2", "password2", "Jane Smith", 30, "9876543210", 3000.0));
        List<Attraction> attraction1 = new ArrayList<>();
        attraction1.add(new Attraction(1,"safari","thrilling adventure in the jungle",10));
        attraction1.add(new Attraction(2,"garden","peaceful garden",20));
        attraction1.add(new Attraction(3,"dinausor","experiance the past",50));
        List<Animal> animal1 = new ArrayList<>();
        animal1.add(new Mammal("lion","he roars"));
        animal1.add(new Amphibian("fish","fish swims"));
        Map<String, Double> discounts = new HashMap<>();
        discounts.put("Minor", 10.0); // 10% discount for Minor visitors
        discounts.put("Senior", 15.0); // 15% discount for Senior visitors
        Zoo zoo = new Zoo(visitors,animal1,attraction1,discounts);
        Visitor currentVisitor = null;
        while (true) {
            System.out.println("Welcome to the Zoo Management System");
            System.out.println("1. Admin Login");
            System.out.println("2. Visitor Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter admin username: ");
                String adminUsername = scanner.next();
                System.out.print("Enter admin password: ");
                String adminPassword = scanner.next();

                Admin admin = new Admin("admin", "admin123"); // You can change the admin credentials

                if (admin.login(adminUsername, adminPassword)) {
                    System.out.println("logged in");
                    admin.adminMenu(zoo);
                } else {
                    System.out.println("Admin login failed.");
                }
            } else if (choice == 2) {
                while (true) {
                    System.out.println("Visitor Menu:");
                    System.out.println("1. Log In");
                    System.out.println("2. Register");
                    System.out.println("3. Exit");
                    System.out.print("Enter your choice: ");
                    int k = scanner.nextInt();
                    if (k == 1) {
                        System.out.print("Username: ");
                        String username = scanner.next();
                        System.out.print("Password: ");
                        String password = scanner.next();
                        Visitor loggedInVisitor = null;
                        for (Visitor visitor : visitors) {
                            if (visitor.correctLogin(username, password)) {
                                loggedInVisitor = visitor;
                                break;
                            }
                        }
                        if (loggedInVisitor != null) {
                            System.out.println("Logged in as " + loggedInVisitor.getName());
                            loggedInVisitor.visitorMenu(zoo);

                        } else {
                            System.out.println("Invalid username or password. Please try again.");
                        }
                    }
                    else if (k == 2) {
                        System.out.print("Enter your name: ");
                        String name = scanner.next();
                        System.out.print("Enter your age: ");
                        int age = scanner.nextInt();
                        System.out.print("Enter your phone number: ");
                        String phoneNumber = scanner.next();
                        System.out.print("Create a username: ");
                        String newUsername = scanner.next();
                        System.out.print("Create a password: ");
                        String newPassword = scanner.next();
                        System.out.print("Enter your balance: ");
                        int balance = scanner.nextInt();
                        Visitor newVisitor = new Visitor(newUsername, newPassword, name, age, phoneNumber, balance);
                        visitors.add(newVisitor);
                        System.out.println("Registration successful. You can now log in.");
                    }
                    else if(k==3){
                        break;
                    }
                }
            }else if (choice == 3) {
                System.out.println("Exiting. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}