package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;

/**
 * Abstract class representing a general order in the system.
 * Each order has an associated customer, list of items, order date, and status.
 */
public abstract class Order {
    private final LocalDateTime orderDate;
    private final List<? extends Product> items;
    private OrderStatus status;
    private Person person;
    private Person originalPerson;
    private final Remark remark;

    /**
     * Constructs an {@code Order} with the specified customer, list of items, and initial status.
     * The order date is set to the current date and time.
     *
     * @param person The customer associated with this order.
     * @param items The list of products in this order.
     * @param status The initial status of the order.
     */
    public Order(Person person, List<? extends Product> items, OrderStatus status, Remark remark) {
        requireAllNonNull(person, items, status, remark);
        this.orderDate = LocalDateTime.now();
        this.items = items;
        this.status = status;
        this.person = new Person(person);
        this.originalPerson = person;
        this.remark = remark;
    }

    /**
     * Constructs an {@code Order} with the specified customer, list of items, initial status, remark, and order date.
     *
     * @param person The customer associated with this order.
     * @param items The list of products in this order.
     * @param status The initial status of the order.
     * @param remark The remark associated with this order.
     * @param orderDate The date and time when the order was placed.
     */
    public Order(Person person, List<? extends Product> items, OrderStatus status, Remark remark, LocalDateTime orderDate) {
        requireAllNonNull(person, items, status, remark, orderDate);
        this.orderDate = orderDate;
        this.items = items;
        this.status = status;
        this.person = new Person(person);
        this.originalPerson = person;
        this.remark = remark;
    }

    /**
     * Returns the customer associated with this order.
     *
     * @return The {@code Person} object representing the customer.
     */
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getOriginalPerson() {
        return originalPerson;
    }

    public void setOriginalPerson(Person person) {
        this.originalPerson = person;
    }

    public LocalDateTime getLocalDateTime() {
        return orderDate;
    }
    /**
     * Returns the order date in a formatted string.
     *
     * @return A string representation of the order date in the format "dd-MM-yyyy".
     */
    public String getOrderDate() {
        return orderDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * Returns the list of items in this order.
     *
     * @return A list of {@code Product} objects representing the items in the order.
     */
    public List<? extends Product> getItems() {
        return items;
    }

    /**
     * Returns the current status of this order.
     *
     * @return The {@code OrderStatus} of the order.
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Returns the remark of this order.
     *
     * @return The {@code Remark} of the order.
     */
    public Remark getRemark() {
        return remark;
    }

    /**
     * Sets the status of this order.
     *
     * @param status The new status to be set for the order.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Returns a string indicating the type of order. Must be implemented by subclasses.
     *
     * @return A string representing the specific order type.
     */
    public abstract String getOrderType();

    /**
     * Returns a string representation of the order, including the type, date, status, and list of items.
     *
     * @return A formatted string with order details.
     */
    @Override
    public String toString() {
        return "Order Type: " + getOrderType() + "\n"
                + "Order Date: " + getOrderDate() + "\n"
                + "Status: " + status + "\n"
                + "Remark: " + remark + "\n"
                + "Items: " + "\n"
                + viewOrder();
    }

    /**
     * Returns a string listing all items in the order. Each item is displayed on a new line.
     *
     * @return A string representation of the items in the order.
     */
    public String viewOrder() {
        StringBuilder sb = new StringBuilder();
        for (Product entry: items) {
            sb.append(entry.viewProduct());
            sb.append("\n");
        }
        return sb.toString();
    }
}