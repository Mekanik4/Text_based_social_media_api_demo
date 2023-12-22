insert into role (id, name)
values (1, 'ADMIN'), (2, 'USER');

-- Insert admin with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (1, 'admin@demo.com', '$2a$12$uauNTH/NTiZDd4WXJwTCWeJq9I1kpA392fLy.8MYXclPUZ/.TDdYO', 1, true, now());

-- Insert free user1 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (2, 'user1@demo.com', '$2a$12$nNHPZ7gDTFZ/l8hXYC3LpeaarX.GFDbye9TU3b9MB8ZZkOAbTlNkm', 2, false, now());

-- Insert premium user2 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (3, 'user2@demo.com', '$2a$12$.qF1eLRPrbmLhCWiVCFpZOJg98KOjhsiPSC/LYASdRDwRrrdBFMx.', 2, true, now());

-- Insert premium user3 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (4, 'user3@demo.com', '$2a$12$AsCh9xL40QO7f0MBOxRKd.7OIcGvAg41ic7j.EfWElaHRI6LvixYS', 2, true, now());

-- Insert free user4 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (5, 'user4@demo.com', '$2a$12$AxaKCsJ8rGaOBU1B/8folOsNXgK7R1bZMjv7a5DOxt28uJXEaqRSO', 2, false, now());

-- Insert free user5 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (6, 'user5@demo.com', '$2a$12$2rBdxlQEOJ0z8FjaSrYfvOe8BsWbR6vPRQEzMNTacdofeZeyy6QH2', 2, false, now());


-- Insert follows
insert into "follow"(id, follower_id, following_id)
values (1, 2, 3);

insert into "follow"(id, follower_id, following_id)
values (2, 2, 4);

insert into "follow"(id, follower_id, following_id)
values (3, 2, 5);

insert into "follow"(id, follower_id, following_id)
values (4, 3, 5);

insert into "follow"(id, follower_id, following_id)
values (5, 3, 2);

insert into "follow"(id, follower_id, following_id)
values (6, 4, 2);

insert into "follow"(id, follower_id, following_id)
values (7, 6, 2);


-- Insert posts
insert into "post"(id, context, user_id, posted_at)
values (1, 'A post from user1', 2, now());

insert into "post"(id, context, user_id, posted_at)
values (2, 'A post from user2', 3, now());

insert into "post"(id, context, user_id, posted_at)
values (3, 'Another post from user2', 3, now());

insert into "post"(id, context, user_id, posted_at)
values (4, 'Again another post from user2', 3, now());

insert into "post"(id, context, user_id, posted_at)
values (5, 'A new post from user1', 2, now());


-- Insert comments
insert into "comment"(id, context, user_id, post_id, posted_at)
values (1, 'A comment from user2 at user1 post', 3, 1, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (2, 'A comment from user3 at user1 post', 4, 1, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (3, 'A comment from user4 at user1 post', 5, 1, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (4, 'A comment from user1 at user1 post', 2, 1, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (5, 'A comment from user2 at user1 post', 3, 1, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (6, 'A comment from user1 at user1 post', 2, 1, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (7, 'A comment from user1 at user2 post', 2, 2, now());