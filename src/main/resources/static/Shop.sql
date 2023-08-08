DROP DATABASE IF EXISTS Shop;
CREATE DATABASE Shop;
USE Shop;

drop TABLE Orders;
drop TABLE Users;

CREATE TABLE Users (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE Orders (
    orderId INT PRIMARY KEY,
    orderNumber VARCHAR(255),
    totalAmount DECIMAL(10, 2),
    userId INT,
    FOREIGN KEY (userId) REFERENCES Users(id)
);


INSERT INTO Users (id, name, email) VALUES
                                            (1, 'John Doe', 'john@example.com'),
                                            (2, 'Jane Smith', 'jane@example.com'),
                                            (3, 'Bob Johnson', 'bob@example.com');


INSERT INTO Orders (orderId, orderNumber, totalAmount, userId) VALUES
                                                                   (101, 'ORD-123', 100.50, 1),
                                                                   (102, 'ORD-456', 250.75, 2),
                                                                   (103, 'ORD-789', 50.00, 1),
                                                                   (104, 'ORD-987', 300.20, 3);