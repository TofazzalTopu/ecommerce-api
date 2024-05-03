CREATE TABLE IF NOT EXISTS CUSTOMERS (id bigint not null, name varchar(255) not null, phone varchar(14) not null, primary
key (id)) engine=InnoDB;

CREATE TABLE IF NOT EXISTS CUSTOMERS_WISH_ITEMS (id bigint not null, created_at date not null, updated_at date not null, customer_id
bigint not null, item_id bigint not null, primary key (id)) engine=InnoDB;


CREATE TABLE IF NOT EXISTS ITEM (id bigint not null, description varchar(500), name varchar(255) not null, sku varchar
(20) not null, unit_price float(53) not null, primary key (id)) engine=InnoDB;

CREATE TABLE IF NOT EXISTS SALES (id bigint not null, created_at date not null, updated_at date not null, customer_id bigint not null,
 primary key (id)) engine=InnoDB;

CREATE TABLE IF NOT EXISTS SALES_ITEMS (id bigint not null, quantity integer not null, total_price float(53) not null, item_id bigint
not null, SALES_id bigint not null, primary key (id)) engine=InnoDB;

ALTER TABLE CUSTOMERS ADD CONSTRAINT UK_customer_name unique (name);

ALTER TABLE CUSTOMERS ADD CONSTRAINT UK_customer_phone unique (phone);

ALTER TABLE ITEM ADD CONSTRAINT UK_item_name unique (name);

ALTER TABLE ITEM ADD CONSTRAINT UK_item_sku unique (sku);

ALTER TABLE CUSTOMERS_WISH_ITEMS ADD CONSTRAINT FK_customer_wish_items_customer_id foreign key (customer_id) references
CUSTOMERS (id);

ALTER TABLE CUSTOMERS_WISH_ITEMS ADD CONSTRAINT FK_customer_wish_items_item_id foreign key (item_id) references item (id);

ALTER TABLE SALES ADD CONSTRAINT FK_sales_customer_id foreign key (customer_id) references CUSTOMERS (id);

ALTER TABLE SALES_ITEMS ADD CONSTRAINT FK_sales_items_item_id foreign key (item_id) references item (id);

ALTER TABLE SALES_ITEMS ADD CONSTRAINT FK_sales_items_sales_id foreign key (sales_id) references sales (id);