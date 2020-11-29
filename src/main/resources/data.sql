insert into USERS (id, firstName, lastName, email, password ) values (1, 'Tom', 'Davis', 'tomdavis@cardiff.ac.uk', 'password');
insert into USERS (id, firstName, lastName, email, password ) values (2, 'Ali', 'Davis', 'tomdavis12@cardiff.ac.uk', 'password');
insert into USERS (id, firstName, lastName, email, password ) values (3, 'farhan', 'Davis', 'tomdavis3@cardiff.ac.uk', 'password');

insert into ROLES(id, role) values(1, 'New Starter');
insert into ROLES(id, role) values(2, 'Admin');
insert into ROLES(id, role) values(3, 'Author');

insert into USER_ROLE(userId, roleId) values(1,2);
insert into USER_ROLE(userId, roleId) values(2,3);
insert into USER_ROLE(userId, roleId) values(3,1);


