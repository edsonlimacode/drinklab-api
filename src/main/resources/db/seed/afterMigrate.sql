delete from groups;
delete from users;
delete from payment_methods;

INSERT INTO public.payment_methods (id,name) VALUES (1,'Pix'),(2,'cartão de crédito/débito'),(3,'dinheiro'),(4,'Outro');
SELECT setval('payment_methods_id_seq', (SELECT MAX(id) FROM payment_methods));

INSERT INTO groups ("id","name") VALUES (1,'MASTER'),(2,'ADMIN'),(3,'USER');
SELECT setval('groups_id_seq', (SELECT MAX(id) FROM groups));

INSERT INTO public.users (id, "name", "password", nick_name, email, active, group_id, created_at, updated_at) VALUES(1, 'edson', '$2a$10$a8dVboObDPt3Z6WxwTawSuALBplBveVz5z7blgfI9YNteWU55sm3a', 'Collier', 'admin@gmail.com', true, 2, '2025-12-03 16:21:12.509', '2025-12-03 16:21:12.509');
INSERT INTO public.users (id, "name", "password", nick_name, email, active, group_id, created_at, updated_at) VALUES(2, 'joao', '$2a$10$XKbAbrJOKLfw0jyilKSLD.g/b94Rmlsr/gDsT774/ZCFkGV8z6QA2', 'Conroy', 'master@gmail.com', true, 1, '2025-12-03 16:21:24.331', '2025-12-03 16:21:24.331');
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));