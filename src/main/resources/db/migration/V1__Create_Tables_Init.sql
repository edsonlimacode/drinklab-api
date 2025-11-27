CREATE TABLE groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) UNIQUE NOT NULL
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE payment_methods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nick_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    active BOOLEAN DEFAULT true,
    group_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    FOREIGN KEY (group_id) REFERENCES groups(id)
);

CREATE TABLE distributors (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(200) NOT NULL,
    document VARCHAR (50) UNIQUE NOT NULL,
    street VARCHAR(200) NOT NULL,
    number VARCHAR(20) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    complement VARCHAR(100),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    contact VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(120) UNIQUE,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE distributor_users (
    user_id BIGINT NOT NULL,
    distributor_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (distributor_id) REFERENCES distributors(id),
    PRIMARY KEY (distributor_id)
);

CREATE TABLE distributor_payments (
    distributor_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    FOREIGN KEY (distributor_id) REFERENCES distributors(id),
    FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id),
    PRIMARY KEY (distributor_id)
);

--Armazem de estoque
CREATE TABLE warehouse (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    distributor_id BIGINT NOT NULL,
    address TEXT,
    FOREIGN KEY (distributor_id) REFERENCES distributors(id)
);

-- Clientes
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    distributor_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    document VARCHAR(20),
    contact VARCHAR(50),
    active BOOLEAN DEFAULT true,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    FOREIGN KEY (distributor_id) REFERENCES distributors(id)
);

--Fornecedor
CREATE TABLE suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    document VARCHAR(18) UNIQUE,
    phone VARCHAR(20),
    email VARCHAR(120),
    distributor_id BIGINT NOT NULL,
    active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    FOREIGN KEY (distributor_id) REFERENCES distributors(id)
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(50) UNIQUE,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(100),
    unit VARCHAR(20) NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    active BOOLEAN DEFAULT true,
    category_id BIGINT NOT NULL,
    distributor_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    FOREIGN KEY (distributor_id) REFERENCES distributors(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Compra do fornecedor
CREATE TABLE purchase_orders (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    distributor_id BIGINT NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (distributor_id) REFERENCES distributors(id)
);

-- item da compra do fornecedor
CREATE TABLE purchase_items (
    id BIGSERIAL PRIMARY KEY,
    purchase_order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    distributor_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    cost_price DECIMAL(12,2) NOT NULL,

    FOREIGN KEY (purchase_order_id) REFERENCES purchase_order(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (distributor_id) REFERENCES distributors(id)
);

-- Pedidos de venda
CREATE TABLE sale_orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    distributor_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL,
    total NUMERIC(10,2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (distributor_id) REFERENCES distributors(id)
);

CREATE TABLE sale_items (
    id BIGSERIAL PRIMARY KEY,
    sale_order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    distributor_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    qty INTEGER NOT NULL,
    unit_price NUMERIC(14,2) NOT NULL,
    foreign KEY (sale_order_id) REFERENCES sale_order(id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (distributor_id) REFERENCES distributors(id)
);

CREATE TABLE route_delivery (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    sale_order_id BIGINT NOT NULL,
    driver_name VARCHAR(50) NOT NULL,
    status BOOLEAN DEFAULT false,
    FOREIGN KEY (sale_order_id)  REFERENCES sale_order(id)
);

CREATE TABLE stocks (
    id BIGSERIAL PRIMARY KEY,
    quantity INTEGER DEFAULT 0,
    distributor_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    warehouse_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(id),
    FOREIGN KEY (distributor_id) REFERENCES distributors(id),
    UNIQUE(product_id, warehouse_id)
);

-- Movimentações de estoque
CREATE TABLE stock_movements (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    distributor_id BIGINT NOT NULL,
    qty NUMERIC(14,3) NOT NULL,
    type VARCHAR(10) NOT NULL,
    reference TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id)  REFERENCES products(id),
    FOREIGN KEY (distributor_id)  REFERENCES distributors(id)
);