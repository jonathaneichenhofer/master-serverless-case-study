package com.lambda.order.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.lambda.order.clients.CatalogClient;
import com.lambda.order.util.OrderLineConverter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "order")
public class Order {

    @DynamoDBHashKey
    private long id;

    @DynamoDBAttribute
    private long customerId;

    @DynamoDBTypeConverted(converter = OrderLineConverter.class)
    private List<OrderLine> orderLine;

    public Order() {
        super();
        orderLine = new ArrayList<OrderLine>();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    public Order(long customerId) {
        super();
        this.customerId = customerId;
        this.orderLine = new ArrayList<OrderLine>();
    }

    public void setOrderLine(List<OrderLine> orderLine) {
        this.orderLine = orderLine;
    }

    public void addLine(int count, long itemId) {
        this.orderLine.add(new OrderLine(count, itemId));
    }

    @DynamoDBIgnore
    public int getNumberOfLines() {
        return orderLine.size();
    }

    @DynamoDBIgnore
    public double totalPrice(CatalogClient itemClient) {
        return orderLine.stream()
                .map((ol) -> ol.getCount() * itemClient.price(ol.getItemId()))
                .reduce(0.0, (d1, d2) -> d1 + d2);
    }

    @DynamoDBIgnore
    public void setCustomer(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);

    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
