package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.person.Person;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;

class JsonAdaptedCustomerOrder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CustomerOrder's %s field is missing!";
    public static final String INVALID_STATUS_MESSAGE = "Invalid order status: %s. Valid statuses are: %s";
    public static final String EMPTY_ORDER_MESSAGE = "CustomerOrder must contain at least one pastry item";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final JsonAdaptedPerson person;
    private final List<JsonAdaptedPastry> pastrys;
    private final String status;
    private final String remark;
    private final String orderDate;

    /**
     * Constructs a {@code JsonAdaptedCustomerOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedCustomerOrder(@JsonProperty("person") JsonAdaptedPerson person,
                                    @JsonProperty("pastrys") List<JsonAdaptedPastry> pastrys,
                                    @JsonProperty("status") String status,
                                    @JsonProperty("remark") String remark,
                                    @JsonProperty("orderDate") String orderDate) {
        this.person = person;
        if (pastrys == null) {
            this.pastrys = List.of();
        } else {
            this.pastrys = pastrys;
        }
        this.status = status;
        this.remark = remark;
        this.orderDate = orderDate;
    }

    /**
     * Converts a given {@code CustomerOrder} into this class for Jackson use.
     */
    public JsonAdaptedCustomerOrder(CustomerOrder source) {
        person = new JsonAdaptedPerson(source.getPerson());
        pastrys = source.getItems().stream()
                .map(item -> (Pastry) item)
                .map(JsonAdaptedPastry::new)
                .collect(Collectors.toList());
        status = source.getStatus().toString().toUpperCase();
        remark = source.getRemark().toString();
        orderDate = source.getLocalDateTime().format(DATE_TIME_FORMATTER);
    }

    /**
     * Converts this Jackson-friendly adapted customer order object into the model's {@code CustomerOrder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer order.
     */
    public CustomerOrder toModelType() throws IllegalValueException {
        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "person"));
        }

        final Person modelPerson;
        try {
            modelPerson = person.toModelType();
        } catch (IllegalValueException e) {
            throw new IllegalValueException("Invalid person details: " + e.getMessage());
        }

        if (pastrys == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "pastrys"));
        }

        if (pastrys.isEmpty()) {
            throw new IllegalValueException(EMPTY_ORDER_MESSAGE);
        }

        List<Product> modelPastrys = pastrys.stream()
                .map(pastry -> {
                    try {
                        return pastry.toModelType();
                    } catch (IllegalValueException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "status"));
        }

        final OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            String validStatuses = String.join(", ",
                    java.util.Arrays.stream(OrderStatus.values())
                            .map(Enum::name)
                            .collect(Collectors.toList()));
            throw new IllegalValueException(String.format(INVALID_STATUS_MESSAGE, status, validStatuses));
        }

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "remark"));
        }

        final Remark modelRemark;
        try {
            modelRemark = new Remark(remark);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid remark: " + e.getMessage());
        }

        if (orderDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "orderDateTime"));
        }

        final LocalDateTime modelOrderDateTime;
        try {
            modelOrderDateTime = LocalDateTime.parse(orderDate, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid order date time: " + e.getMessage());
        }

        return new CustomerOrder(modelPerson, modelPastrys, orderStatus, modelRemark, modelOrderDateTime);
    }
}