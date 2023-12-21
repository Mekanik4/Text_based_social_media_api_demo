-- insert into role (id, name)
-- values (1, 'ADMIN'), (2, 'USER');

-- Insert admin with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (21, 'admin@demo.com', '$2a$12$uauNTH/NTiZDd4WXJwTCWeJq9I1kpA392fLy.8MYXclPUZ/.TDdYO', 1, true, now());

-- Insert free user1 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (22, 'user1@demo.com', '$2a$12$nNHPZ7gDTFZ/l8hXYC3LpeaarX.GFDbye9TU3b9MB8ZZkOAbTlNkm', 2, false, now());

-- Insert premium user2 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (23, 'user2@demo.com', '$2a$12$.qF1eLRPrbmLhCWiVCFpZOJg98KOjhsiPSC/LYASdRDwRrrdBFMx.', 2, true, now());

-- Insert premium user3 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (24, 'user3@demo.com', '$2a$12$AsCh9xL40QO7f0MBOxRKd.7OIcGvAg41ic7j.EfWElaHRI6LvixYS', 2, true, now());

-- Insert free user4 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (25, 'user4@demo.com', '$2a$12$AxaKCsJ8rGaOBU1B/8folOsNXgK7R1bZMjv7a5DOxt28uJXEaqRSO', 2, false, now());

-- Insert free user5 with password 12345678
insert into "user"(id, email, password, role_id, premium, created_at)
values (26, 'user5@demo.com', '$2a$12$2rBdxlQEOJ0z8FjaSrYfvOe8BsWbR6vPRQEzMNTacdofeZeyy6QH2', 2, false, now());


-- Insert follows
insert into "follow"(id, follower_id, following_id)
values (21, 22, 23);

insert into "follow"(id, follower_id, following_id)
values (22, 22, 24);

insert into "follow"(id, follower_id, following_id)
values (23, 22, 25);

insert into "follow"(id, follower_id, following_id)
values (24, 23, 25);

insert into "follow"(id, follower_id, following_id)
values (25, 23, 22);

insert into "follow"(id, follower_id, following_id)
values (26, 24, 22);

insert into "follow"(id, follower_id, following_id)
values (27, 26, 22);


-- Insert posts
insert into "post"(id, context, user_id, posted_at)
values (21, 'A post from user1', 22, now());

insert into "post"(id, context, user_id, posted_at)
values (22, 'A post from user2', 23, now());

insert into "post"(id, context, user_id, posted_at)
values (23, 'Another post from user2', 23, now());

insert into "post"(id, context, user_id, posted_at)
values (24, 'Again another post from user2', 23, now());

insert into "post"(id, context, user_id, posted_at)
values (25, 'A new post from user1', 22, now());


-- Insert comments
insert into "comment"(id, context, user_id, post_id, posted_at)
values (21, 'A comment from user2 at user1 post', 23, 21, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (22, 'A comment from user3 at user1 post', 24, 21, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (23, 'A comment from user4 at user1 post', 25, 21, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (24, 'A comment from user1 at user1 post', 22, 21, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (25, 'A comment from user2 at user1 post', 23, 21, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (26, 'A comment from user1 at user1 post', 22, 21, now());

insert into "comment"(id, context, user_id, post_id, posted_at)
values (27, 'A comment from user1 at user2 post', 22, 22, now());