DROP TABLE IF EXISTS customer_order;
DROP TABLE IF EXISTS customer_order_item;
DROP TABLE IF EXISTS order_item_summary;
DROP TABLE IF EXISTS order_item_summary_products;
DROP TABLE IF EXISTS order_summary;
DROP TABLE IF EXISTS product;
    CREATE TABLE customer_order
(
    customer_order_id BIGINT       NOT NULL,
    loyalty_id        VARCHAR(255) NULL,
    first_name        VARCHAR(255) NULL,
    last_name         VARCHAR(255) NULL,
    store_id          VARCHAR(255) NULL,
    CONSTRAINT pk_customer_order PRIMARY KEY (customer_order_id)
);

CREATE TABLE customer_order_item
(
    id                BIGINT NOT NULL,
    quantity          INT    NOT NULL,
    customer_order_id BIGINT NULL,
    CONSTRAINT pk_customer_order_item PRIMARY KEY (id)
);

CREATE TABLE order_item_summary
(
    order_summary_id       BIGINT       NOT NULL,
    product_name           VARCHAR(255) NULL,
    quantity               INT          NOT NULL,
    unit_cost              DOUBLE       NOT NULL,
    unit_price              DOUBLE       NOT NULL,
    image_url              VARCHAR(255) NULL,
    order_summary_order_id BIGINT       NULL,
    CONSTRAINT pk_order_item_summary PRIMARY KEY (order_summary_id)
);

CREATE TABLE order_item_summary_products
(
    order_item_summary_id BIGINT NOT NULL,
    products_product_id   BIGINT NOT NULL
);

CREATE TABLE order_summary
(
    order_id             BIGINT       NOT NULL,
    order_completed_date date         NULL,
    loyalty_id           VARCHAR(255) NULL,
    first_name           VARCHAR(255) NULL,
    last_name            VARCHAR(255) NULL,
    store_id             VARCHAR(255) NULL,
    order_date           date         NULL,
    order_total          DOUBLE       NOT NULL,
    CONSTRAINT pk_order_summary PRIMARY KEY (order_id)
);

CREATE TABLE product
(
    product_id    BIGINT       NOT NULL AUTO_INCREMENT,
    product_name  VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    category_id   VARCHAR(255) NULL,
    unit_price    DOUBLE       NOT NULL,
    unit_cost     DOUBLE       NOT NULL,
    image_url     VARCHAR(255) NULL,
    CONSTRAINT pk_product PRIMARY KEY (product_id)
);

ALTER TABLE customer_order_item
    ADD CONSTRAINT FK_CUSTOMER_ORDER_ITEM_ON_CUSTOMER_ORDER FOREIGN KEY (customer_order_id) REFERENCES customer_order (customer_order_id);

ALTER TABLE order_item_summary
    ADD CONSTRAINT FK_ORDER_ITEM_SUMMARY_ON_ORDER_SUMMARY_ORDER FOREIGN KEY (order_summary_order_id) REFERENCES order_summary (order_id);

ALTER TABLE order_item_summary_products
    ADD CONSTRAINT fk_orditesumpro_on_order_item_summary FOREIGN KEY (order_item_summary_id) REFERENCES order_item_summary (order_summary_id);

ALTER TABLE order_item_summary_products
    ADD CONSTRAINT fk_orditesumpro_on_product FOREIGN KEY (products_product_id) REFERENCES product (product_id);

ALTER TABLE product AUTO_INCREMENT=1;