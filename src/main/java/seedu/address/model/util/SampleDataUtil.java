package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Information;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Ingredients;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.product.Product;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("");
    // Static collections for ingredients and pastries to avoid multiple initializations
    private static final Map<Integer, Ingredient> defaultIngredients = new HashMap<>();
    private static final List<Pastry> defaultPastries = new ArrayList<>();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private static Customer sampleCustomer() {
        Customer c = new Customer(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Information("I eat chocolate"),
                new Remark("I enjoy movies"), getTagSet("Customer"));
        return c;
    }

    private static Supplier sampleSupplier() {
        Supplier s = new Supplier(new Name("Alice Tan"),
                new Phone("91234567"),
                new Email("alice.tan@example.com"),
                new Address("Blk 123 Clementi Ave 3, #12-34"),
                new Ingredients(List.of(
                        new Ingredient(1, "Flour", 1.50),
                        new Ingredient(2, "Sugar", 0.80)
                )),
                new Remark("Reliable supplier"),
                getTagSet("Supplier")
        );
        return s;
    }

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Remark("I love cakes"), getTagSet("Customer")),
            sampleCustomer(),
            sampleSupplier(),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Remark("I watch football"), getTagSet("Customer")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Remark("Ingredients are cool"), getTagSet("Supplier")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Remark("I do not like sweet stuff"), getTagSet("Customer")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Remark("I want to invest in your bakery business"), getTagSet("Supplier")),
            new Person(new Name("Joel Tio"), new Phone("98102923"), new Email("joel.tio@gmail.com"),
                    new Address("548 Bukit Timah Road"),
                    new Remark("I am lactose intolerant"), getTagSet("Customer", "Friend"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static CustomerOrderList getSampleCustomerOrderList() {
        Ingredient flour = new Ingredient(1, "Flour", 1.50);
        Ingredient sugar = new Ingredient(2, "Sugar", 0.80);
        Ingredient strawberry = new Ingredient(3, "Strawberry", 3.00);
        Ingredient chocolate = new Ingredient(4, "Chocolate", 2.50);
        Pastry strawberryWaffle = new Pastry(1, "Strawberry Waffle", 5.50,
                new ArrayList<>(List.of(strawberry, flour, sugar)));
        Pastry chocolatedonut = new Pastry(2, "Chocolate Donut", 4.00,
                new ArrayList<>(List.of(chocolate, flour, sugar)));
        List<Product> pastryList = List.of(strawberryWaffle, chocolatedonut);
        CustomerOrderList customerOrderList = new CustomerOrderList();
        LocalDateTime orderDate = LocalDateTime.parse("05-11-2024 11:45:00", DATE_TIME_FORMATTER);
        customerOrderList.addOrder(new CustomerOrder(sampleCustomer(), pastryList, OrderStatus.PENDING, EMPTY_REMARK, orderDate));
        return customerOrderList;
    }

    public static SupplyOrderList getSampleSupplyOrderList() {
        Ingredient flour = new Ingredient(1, "Flour", 1.50);
        Ingredient sugar = new Ingredient(2, "Sugar", 0.80);
        List<Product> ingredientList = List.of(flour, sugar);
        SupplyOrderList supplyOrderList = new SupplyOrderList();
        LocalDateTime orderDate = LocalDateTime.parse("07-11-2024 13:56:00", DATE_TIME_FORMATTER);
        supplyOrderList.addOrder(new SupplyOrder(sampleSupplier(), ingredientList, OrderStatus.PENDING, EMPTY_REMARK, orderDate));
        return supplyOrderList;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    static {
        // Initialize default ingredients
        Ingredient flour = new Ingredient(1, "Flour", 1.50);
        Ingredient sugar = new Ingredient(2, "Sugar", 0.80);
        Ingredient strawberry = new Ingredient(3, "Strawberry", 3.00);
        Ingredient chocolate = new Ingredient(4, "Chocolate", 2.50);
        Ingredient cheese = new Ingredient(5, "Cheese", 4.00);
        Ingredient cream = new Ingredient(6, "Cream", 2.00);

        // Add ingredients to map with unique IDs
        defaultIngredients.put(flour.getProductId(), flour);
        defaultIngredients.put(sugar.getProductId(), sugar);
        defaultIngredients.put(strawberry.getProductId(), strawberry);
        defaultIngredients.put(chocolate.getProductId(), chocolate);
        defaultIngredients.put(cheese.getProductId(), cheese);
        defaultIngredients.put(cream.getProductId(), cream);

        // Initialize default pastries using the ingredients
        defaultPastries.add(new Pastry(1, "Strawberry Waffle", 5.50,
                new ArrayList<>(List.of(strawberry, flour, sugar))));
        defaultPastries.add(new Pastry(2, "Chocolate Donut", 4.00,
                new ArrayList<>(List.of(chocolate, flour, sugar))));
        defaultPastries.add(new Pastry(3, "Cheesecake", 6.50,
                new ArrayList<>(List.of(cheese, cream, sugar))));
    }

    // Method to retrieve default ingredients
    public static Map<Integer, Ingredient> getDefaultIngredients() {
        return new HashMap<>(defaultIngredients); // Defensive copy to prevent modification
    }

    // Method to retrieve default pastries
    public static List<Pastry> getDefaultPastries() {
        return new ArrayList<>(defaultPastries); // Defensive copy to prevent modification
    }

    // Method to retrieve a sample IngredientCatalogue with default ingredients
    public static IngredientCatalogue getSampleIngredientCatalogue() {
        IngredientCatalogue ingredientCatalogue = IngredientCatalogue.getInstance();
        for (Ingredient ingredient : defaultIngredients.values()) {
            ingredientCatalogue.addIngredient(ingredient);
        }
        return ingredientCatalogue;
    }

    // Method to retrieve a sample PastryCatalogue with default pastries
    public static PastryCatalogue getSamplePastryCatalogue() {
        PastryCatalogue pastryCatalogue = new PastryCatalogue();
        for (Pastry pastry : defaultPastries) {
            pastryCatalogue.addPastry(pastry.getName(), pastry.getCost(), pastry.getIngredients());
        }
        return pastryCatalogue;
    }

}
