

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO user_table (id, username, password) VALUES (1,'admin', '$2a$10$SFsH23cE.bPHhP7JMJKa1OVSKFAVp7iD1XmzT1gUDzfVFK3xpNo/C');
INSERT INTO user_table (id, username, password) VALUES (2,'user', '$2y$10$gg27ycxXkuXuUBhm1eC2WOsz3mD/kDHdF3QI3c/xjcKtjYJCbe0nC');
INSERT INTO user_authority  VALUES (1, 1);
INSERT INTO user_authority  VALUES (2, 2);

INSERT INTO product_payment_data  VALUES (1, "-ePst6Ws2zcsr3naJEnxzkXHPpMBUkxV5Ps85_D5",
                                            "AV53WcoFcJBV8FmGKp_qz7ZFUk8nAoLODD5D5A-OsHfVsQVSB4WIxPm2JH63jUDkn-HGvd2HdqmTz6Sf",
                                            "EJgHCJKK3314ZbozPXB7p6okMQy8ohBMtA4cYrr2qsWnJbs9twn7c_Z1JmJhf1KXO1tZrV4mgxsVD2G8");

INSERT INTO conferences_payment_data VALUES (1, "-ePst6Ws2zcsr3naJEnxzkXHPpMBUkxV5Ps85_D5",
                                            "AV53WcoFcJBV8FmGKp_qz7ZFUk8nAoLODD5D5A-OsHfVsQVSB4WIxPm2JH63jUDkn-HGvd2HdqmTz6Sf",
                                            "EJgHCJKK3314ZbozPXB7p6okMQy8ohBMtA4cYrr2qsWnJbs9twn7c_Z1JmJhf1KXO1tZrV4mgxsVD2G8");




INSERT INTO web_shop  VALUES (1, "Conferences", 2);
INSERT INTO web_shop  VALUES (2, "Webshop for office material", 2);


