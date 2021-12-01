

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO user_table (id, username, password) VALUES (1,'admin', '$2a$10$SFsH23cE.bPHhP7JMJKa1OVSKFAVp7iD1XmzT1gUDzfVFK3xpNo/C');
INSERT INTO user_table (id, username, password) VALUES (2,'user', '$2y$10$gg27ycxXkuXuUBhm1eC2WOsz3mD/kDHdF3QI3c/xjcKtjYJCbe0nC');
INSERT INTO user_authority  VALUES (1, 1);
INSERT INTO user_authority  VALUES (2, 2);





