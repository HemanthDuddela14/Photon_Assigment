CREATE TABLE product(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    price DOUBLE,
    image_url VARCHAR(255)
);

CREATE TABLE order(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255),
    order_date DATE
);

CREATE TABLE order_product_ids(
    order_id BIGINT,
    product_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES (order(id))
);