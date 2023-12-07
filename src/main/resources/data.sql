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
    (1, 2),
    (2, 1),
    (2, 2),
    (3, 2),
    (3, 3),
    (4, 2),
    (4, 3),
    (5, 3);

INSERT INTO categories (id, name)
values (1, 'MEN'),
       (2, 'WOMEN'),
       (3, 'UNISEX');


INSERT INTO brands (id, name)
VALUES (1, 'Armani'),
       (2, 'Chanel'),
       (3, 'Paco Rabanne'),
       (4, 'Dior'),
       (5, 'Bvlgari'),
       (6, 'Valentino'),
       (7, 'Versace'),
       (8, 'Carolina Herrera'),
       (9, 'Azzaro'),
       (10, 'Hermes'),
       (11, 'Yves Saint Laurent'),
       (12, 'Giorgio Armani'),
       (13, 'Burberry'),
       (14, 'Gucci');


# INSERT INTO brands (id, name)
# VALUES  (7, 'Versace');


INSERT INTO products (id, name, price, milliliters, description, category_id, brand_id, image_url)
VALUES (1, 'Code', 77.00, 'FIFTY','Very good smell', 1, 1, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699350342/Perfumes/nsxrpyiwhdsg3l5lihhc.jpg'),
       (2, 'Invictus', 88.00, 'HUNDRED', 'Amazing smell', 1, 3, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699346924/Perfumes/vhnjlmxqdzyznnw64uo0.jpg'),
       (3, 'One Million', 99.00, 'HUNDRED', 'Amazing smell', 1, 3, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1700411994/Perfumes/pywwejdmjdgm3q8r9njt.jpg'),
       (4, 'Number 5', 149.00, 'TWO_HUNDRED','Great smell', 2, 2, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699346989/Perfumes/pea5pgi3mw9l0wtvfgvp.jpg'),
       (5, 'Chance', 88.00, 'HUNDRED','Great smell', 2, 2, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701865180/x51hefwrmyrpuayg5rvr.jpg'),
       (6, 'Donna Born In Roma', 99.00, 'HUNDRED','Warm Florals', 2, 6, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701166475/v6vhwmlivh8hmlbiytjo.jpg'),
       (7, 'Rose Goldea', 119.00, 'HUNDRED','Mild fresh', 2, 5, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701517265/tnmqosxy8ncc568utdo2.avif'),
       (8, 'Hypnotic Poison', 109.00, 'HUNDRED','Anchored in extreme naturalness', 2, 4, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701164451/iaxg4obnw3vodkf3pkee.jpg'),
       (9, 'Blue', 95.00, 'HUNDRED','For real men...', 1, 2, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701167802/tpvnamzfi3zhgdgvhj5b.png'),
       (10, 'Omnia Amethyste', 87.00, 'HUNDRED','Inspired by the shimmering hues of the amethyst gemstone, this floral Eau de Toilette captures the myriad scents of iris and rose gardens caressed with morning dew', 2, 5, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701171386/ecjd0ayti5skklgvmiys.webp'),
       (11, 'Coco', 139.00, 'HUNDRED','COCO expresses the intensity of Gabrielle Chanel''s personality and her love of all things baroque', 2, 2, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701331288/zmblgwl1jtdkcae3azqt.jpg'),
       (12, 'Eros', 105.00, 'HUNDRED','Masculine and confident', 1, 7, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701709643/qjvlmsnjkt4xczp02mj6.png'),
       (13, 'Glacial Essence', 67.00, 'HUNDRED', 'Fresh, sparkling & spicy', 1, 5, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701165027/kgnprvhemxe63eewflux.png'),
       (14, 'Olympea', 99.00, 'HUNDRED', 'Oriental floral scent for women. It features top notes of green mandarin, water jasmine and ginger lily. Middle note is salted vanilla. Base notes are sandalwood, cashmere and ambergris.', 2, 3, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699619577/Perfumes/d0gdhus4kmula74vxjgd.jpg'),
       (15, 'Good Girl', 109.00, 'HUNDRED', 'Combines almond with tuberose, jasmine sambac, tonka bean and cocoa, for an incomparable and inspired perfume experience. This eau de parfum is a striking and chic floral fragrance, which is presented in an iconic dark navy shoe-shaped perfume bottle.', 2, 8, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699347598/Perfumes/zjswflkvdwtjtq64tqvt.jpg'),
       (16, 'Very Good Girl', 109.00, 'HUNDRED', 'Very Good Girl by Carolina Herrera is a Floral Fruity fragrance for women. Very Good Girl was launched in 2021. Very Good Girl was created by Quentin Bisch, Louise Turner and Shyamala Maisondieu. Top notes are Litchi and Red Currant; middle note is Rose; base notes are Vanilla and Vetiver.', 2, 8, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699347599/Perfumes/rffni7kcycxmscf9hgzd.jpg'),
       (17, 'Wanted', 79.00, 'HUNDRED', 'The fragrance for men with unfailing confidence, who succeed the impossible and follow their instinct. With the Eau de toilette Azzaro Wanted, a fragrance with woody and spicy notes, discover an unfailing weapon of seduction. Its entrancing aromas reveal notes of vetiver, cardamom and lemon.', 1, 9, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699347464/Perfumes/cncjaznbhrhjmw9x1dm5.jpg'),
       (18, 'Chrome', 89.00, 'HUNDRED', 'Citrus, aquatic notes that are blended with intense woods to create a fresh and long-lasting men''s fragrance. The citrus accord of bergamot essential oils bestows vibrancy and light.', 1, 9, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699347463/Perfumes/aqsantsen3vquitqwpxh.jpg'),
       (19, 'Terre d`Hermès', 89.00, 'HUNDRED', 'Mineral, woody fragrance, Terre d''Hermès combines the strength of cedar and the radiance of grapefruit with a vibrant touch of flint. At its base, the bottle rests on an orange H, leaving its imprint on the earth. At the top, the light is reflected by metal shoulders.', 1, 10, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699347407/Perfumes/phntgbcdedso6rr6f66p.jpg'),
       (20, 'L`Homme', 99.00, 'HUNDRED', 'A fresh woody fragrance with notes of citrus peel, ginger, basil blossoms, violet leaves, vetiver and tonka bean. Intense, charismatic freshness contrasts against a powerful body with a bold and sexy tradition.', 1, 11, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699347176/Perfumes/rq4b4fug6w0grk7s6zva.jpg'),
       (21, 'Acqua di Gioia', 99.00, 'HUNDRED', 'This refreshing aquatic fragrance opens with a beautiful blend of jasmine and zesty lemon, warmed with woody cedar at the base. Inspired by Italy`s Mediterranean coast, this fresh perfume is a singular blend of serenity and exhilaration.', 2, 12, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699347176/Perfumes/rq4b4fug6w0grk7s6zva.jpg'),
       (22, 'For Her', 49.00, 'HUNDRED', 'It evokes the iconic Burberry woman - a simple and charming Londoner who enjoys the long walks in the streets of the city she loves.', 2, 13, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1699347532/Perfumes/zstwzqp8p1bpivutexy8.jpg'),
       (23, 'Guilty', 79.00, 'HUNDRED', 'A distinctive ambery floral accord, Gucci Guilty For Her Eau de Parfum is defined by the light, fresh femininity of lilac petals with a sparkling citrus Mandora twist on a trail of rich and sensual amber notes and Patchouli.', 1, 14, 'https://res.cloudinary.com/dvj9qondf/image/upload/v1701949185/sgv7jviw1fksgexhqpnu.jpg');



# INSERT INTO products(id, model_id)
# VALUES (1,1),
#        (2,2),
#        (3,3);
