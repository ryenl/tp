package seedu.address.model.order;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;

/**
 * Class representing a customer order.
 */
public class CustomerOrder extends Order {
    public CustomerOrder(Person person, List<Product> items, OrderStatus status, Remark remark) {
        super(person, items, status, remark);
    }

    public CustomerOrder(Person person, List<Product> items, OrderStatus status, Remark remark, LocalDateTime orderDate) {
        super(person, items, status, remark, orderDate);
    }

    @Override
    public String getOrderType() {
        return "Customer Order";
    }
}
