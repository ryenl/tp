package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.person.Person;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;

class JsonAdaptedSupplyOrder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "SupplyOrder's %s field is missing!";
    public static final String INVALID_STATUS_MESSAGE = "Invalid order status: %s. Valid statuses are: %s";
    public static final String EMPTY_ORDER_MESSAGE = "SupplyOrder must contain at least one ingredient item";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    private final JsonAdaptedPerson person;
    private final List<JsonAdaptedIngredient> ingredients;
    private final String status;
    private final String remark;
    private final String orderDate;

    /**
     * Constructs a {@code JsonAdaptedSupplyOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedSupplyOrder(@JsonProperty("person") JsonAdaptedPerson person,
                                  @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients,
                                  @JsonProperty("status") String status,
                                  @JsonProperty("remark") String remark,
                                  @JsonProperty("orderDate") String orderDate) {
        this.person = person;
        if (ingredients == null) {
            this.ingredients = List.of();
        } else {
            this.ingredients = ingredients;
        }
        this.status = status;
        this.remark = remark;
        this.orderDate = orderDate;
    }

    /**
     * Converts a given {@code SupplyOrder} into this class for Jackson use.
     */
    public JsonAdaptedSupplyOrder(SupplyOrder source) {
        person = new JsonAdaptedPerson(source.getPerson());
        ingredients = source.getItems().stream()
                .map(item -> (Ingredient) item)
                .map(JsonAdaptedIngredient::new)
                .collect(Collectors.toList());
        status = source.getStatus().toString().toUpperCase();
        remark = source.getRemark().toString();
        orderDate = source.getLocalDateTime().format(DATE_TIME_FORMATTER);
    }

    /**
     * Converts this Jackson-friendly adapted supply order object into the model's {@code SupplyOrder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted supply order.
     */
    public SupplyOrder toModelType() throws IllegalValueException {
        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "person"));
        }

        final Person modelPerson;
        try {
            modelPerson = person.toModelType();
        } catch (IllegalValueException e) {
            throw new IllegalValueException("Invalid person details: " + e.getMessage());
        }

        if (ingredients == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ingredients"));
        }

        if (ingredients.isEmpty()) {
            throw new IllegalValueException(EMPTY_ORDER_MESSAGE);
        }

        List<Product> modelIngredients = ingredients.stream()
                .map(ingredient -> {
                    try {
                        return ingredient.toModelType();
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

        final LocalDateTime modelOrderDateTime;
        try {
            modelOrderDateTime = LocalDateTime.parse(orderDate, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid order date time: " + e.getMessage());
        }

        return new SupplyOrder(modelPerson, modelIngredients, orderStatus, modelRemark, modelOrderDateTime);
    }
}