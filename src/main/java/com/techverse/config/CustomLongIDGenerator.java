package com.techverse.config;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomLongIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "234871254"; // You can set any prefix for your IDs if needed
        Connection connection = session.connection();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT MAX(order_id) as maxId FROM orders");

            if (rs.next()) {
                long id = rs.getLong("maxId");
                return Long.parseLong(prefix + String.format("%08d", id + 1)); // Assuming your IDs should have 9 digits (including the prefix)
            }
        } catch (SQLException e) {
            // Handle exception
        }

        return null;
    }
}
