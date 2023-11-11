-- some test users

# INSERT INTO user_roles (id, user_role)
# values
#     (1, 'ADMIN'),
#     (2, 'EMPLOYEE'),
#     (3, 'CUSTOMER');

INSERT INTO users (id, email, first_name, last_name, username, phone_number, password)
VALUES
    (2, 'admin@example.com', 'Admin', 'Adminov', 'admin2', 0888123451, '$2a$10$1PJxXUOOwgLlRpfDtoUTcOTwbCQpLkTVU4qvmPROr5.uRxHmqS3Ca'),
    (3, 'employee1@example.com', 'Rabotnik1', 'Rabotnikov1', 'employee1', 0888123452, '$2a$10$1PJxXUOOwgLlRpfDtoUTcOTwbCQpLkTVU4qvmPROr5.uRxHmqS3Ca'),
    (4, 'employee2@example.com', 'Rabotnik2', 'Rabotnikov2', 'employee2', 0888123453, '$2a$10$1PJxXUOOwgLlRpfDtoUTcOTwbCQpLkTVU4qvmPROr5.uRxHmqS3Ca'),
    (5, 'vladi@example.com', 'Vladimir', 'Mitrev', 'vladi', 0888123454, '$2a$10$1PJxXUOOwgLlRpfDtoUTcOTwbCQpLkTVU4qvmPROr5.uRxHmqS3Ca');


INSERT INTO users_user_roles (user_id, user_roles_id)
VALUES
    (2, 1),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 3);

INSERT INTO categories (id, name)
values (1, 'MEN'),
       (2, 'WOMEN'),
       (3, 'UNISEX');


INSERT INTO brands (id, name)
VALUES (1, 'Armani'),
       (2, 'Chanel'),
       (3, 'Paco Rabanne');

INSERT INTO products (id, name, price, milliliters, description, category_id, brand_id, image_url)
VALUES (1, 'Code', 77.00, 'FIFTY','Very good smell', 1, 1, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699350342/Perfumes/nsxrpyiwhdsg3l5lihhc.jpg'),
       (2, 'Invictus', 177.00, 'HUNDRED', 'Amazing smell', 1, 3, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699346924/Perfumes/vhnjlmxqdzyznnw64uo0.jpg'),
       (3, 'Number 5', 277.00, 'TWO_HUNDRED','Great smell', 2, 2, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699346989/Perfumes/pea5pgi3mw9l0wtvfgvp.jpg');

# INSERT INTO products(id, model_id)
# VALUES (1,1),
#        (2,2),
#        (3,3);
