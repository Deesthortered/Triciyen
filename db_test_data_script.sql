INSERT INTO message_content_type (message_content_type_id, name) VALUES (1, 'Text Message');
INSERT INTO message_content_type (message_content_type_id, name) VALUES (2, 'Binary');

INSERT INTO user_account (login, email, name, password) VALUES ('test123', 'test123', 'test123', 'test123');
INSERT INTO user_account (login, email, name, password) VALUES ('anatoly', 'anatoly@mail.com', 'Anatoly', 'pass');
INSERT INTO user_account (login, email, name, password) VALUES ('andrii',  'andrii@mail.com',  'Andrii',  'pass');
INSERT INTO user_account (login, email, name, password) VALUES ('anna',    'anna@mail.com',    'Anna',    'pass');

INSERT INTO conversation (conversation_id, name) VALUES (1, 'First conversation');
INSERT INTO conversation (conversation_id, name) VALUES (2, 'Second conversation');


INSERT INTO user_conversation (conversation_id, login) VALUES (1, 'anatoly');
INSERT INTO user_conversation (conversation_id, login) VALUES (1, 'andrii');
INSERT INTO user_conversation (conversation_id, login) VALUES (1, 'anna');


INSERT INTO user_conversation (conversation_id, login) VALUES (2, 'anatoly');
INSERT INTO user_conversation (conversation_id, login) VALUES (2, 'andrii');
INSERT INTO user_conversation (conversation_id, login) VALUES (2, 'anna');


INSERT INTO message (message_content_type_id, creation_time, login, conversation_id, content) VALUES (1, '2018-01-29', 'anatoly', 1, 'First message!');
INSERT INTO message (message_content_type_id, creation_time, login, conversation_id, content) VALUES (1, '2018-01-30', 'anna', 1, 'Second message!');
INSERT INTO message (message_content_type_id, creation_time, login, conversation_id, content) VALUES (1, '2018-01-31', 'andrii', 1, 'Yeah, Im hear too.');

INSERT INTO message (message_content_type_id, creation_time, login, conversation_id, content) VALUES (1, '2018-01-29', 'anatoly', 2, 'First message in 2!');
INSERT INTO message (message_content_type_id, creation_time, login, conversation_id, content) VALUES (1, '2018-01-30', 'anna', 2, 'Second message in 2!');
INSERT INTO message (message_content_type_id, creation_time, login, conversation_id, content) VALUES (1, '2018-01-31', 'andrii', 2, 'Yeah, Im hear too. And in 2 too.');