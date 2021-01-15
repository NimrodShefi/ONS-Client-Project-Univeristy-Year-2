INSERT INTO user(first_name, last_name, email, password) VALUES("Nimrod", "Shefi", "nimrod@cardiff.ac.uk", "$2a$10$Hfi.30gNk2MQFht5FseEzOzMXp3p9WLDFam8NM5gQWSrHoUvrIEYa");            -- pw: "nimpass" roles: USER
INSERT INTO user(first_name, last_name, email, password) VALUES("Hannah", "Bonnett", "hannah@cardiff.ac.uk", "$2a$10$OMucrxeSJ.7dRqFy6u.TXe2RjE5fEnKPCrlYexWP/bTgTuFPi4x.K");          -- pw: "hanpass" roles: ADMIN
INSERT INTO user(first_name, last_name, email, password) VALUES("Mahruk", "Zulfiqar", "mahruk@cardiff.ac.uk", "$2a$10$ZL9Jwym3ALO5uYVhh7MtouquWswsuWAJHHXIQu1h1BYqKGyleuabm");         -- pw: "mahpass" roles: USER, AUTHOR
INSERT INTO user(first_name, last_name, email, password) VALUES("Sameer", "Shah", "sameer@cardiff.ac.uk", "$2a$10$4mab7bDkdp4TSbvYPcPzeu8Jm/P4vREk4AmJ8DCvxEYt0dCb/5/E.");             -- pw: "sampass" roles: AUTHOR
INSERT INTO user(first_name, last_name, email, password) VALUES("Jean-Luc", "Picard", "picard@cardiff.ac.uk", "$2a$10$GSn7LP4oN4CymVRkfU.YBOAFUjBmhl9B5vyp59yP/vjMeymEsNSVq");   		-- pw: "earlgreyhot" roles: USER, AUTHOR, ADMIN
INSERT INTO user(first_name, last_name, email, password) VALUES("Ellen", "Ripley", "ellen@cardiff.ac.uk", "$2a$10$Cqv3qCw6WwH02Oms89TDieWJbvrqqWWc6se7Ty7ocsMJ5EwswK7Tu");            -- pw: "password" roles: USER, AUTHOR
INSERT INTO user(first_name, last_name, email, password) VALUES("William", "Riker", "william@cardiff.ac.uk", "$2a$10$Cqv3qCw6WwH02Oms89TDieWJbvrqqWWc6se7Ty7ocsMJ5EwswK7Tu");            -- pw: "password" roles: USER
INSERT INTO user(first_name, last_name, email, password) VALUES("Deanna", "Troi", "deanna@cardiff.ac.uk", "$2a$10$Cqv3qCw6WwH02Oms89TDieWJbvrqqWWc6se7Ty7ocsMJ5EwswK7Tu");            -- pw: "password" roles: AUTHOR
INSERT INTO user(first_name, last_name, email, password) VALUES("Kara", "Thrace", "kara@cardiff.ac.uk", "$2a$10$Cqv3qCw6WwH02Oms89TDieWJbvrqqWWc6se7Ty7ocsMJ5EwswK7Tu");            -- pw: "password" roles: USER, AUTHOR
INSERT INTO user(first_name, last_name, email, password) VALUES("Sarah", "Connor", "sarah@cardiff.ac.uk", "$2a$10$Cqv3qCw6WwH02Oms89TDieWJbvrqqWWc6se7Ty7ocsMJ5EwswK7Tu");            -- pw: "password" roles: AUTHOR
INSERT INTO user(first_name, last_name, email, password) VALUES("Marty", "McFly", "marty@cardiff.ac.uk", "$2a$10$Cqv3qCw6WwH02Oms89TDieWJbvrqqWWc6se7Ty7ocsMJ5EwswK7Tu");            -- pw: "password" roles: USER
INSERT INTO user(first_name, last_name, email, password) VALUES("Han", "Solo", "han@cardiff.ac.uk", "$2a$10$Cqv3qCw6WwH02Oms89TDieWJbvrqqWWc6se7Ty7ocsMJ5EwswK7Tu");            	-- pw: "password" roles: USER, AUTHOR

INSERT INTO role(name) VALUES("USER");
INSERT INTO role(name) VALUES("AUTHOR");
INSERT INTO role(name) VALUES("ADMIN");

INSERT INTO user_role(user_id, role_id) VALUES(1, 1);
INSERT INTO user_role(user_id, role_id) VALUES(2, 3);
INSERT INTO user_role(user_id, role_id) VALUES(3, 1);
INSERT INTO user_role(user_id, role_id) VALUES(3, 2);
INSERT INTO user_role(user_id, role_id) VALUES(4, 2);
INSERT INTO user_role(user_id, role_id) VALUES(5, 1);
INSERT INTO user_role(user_id, role_id) VALUES(5, 2);
INSERT INTO user_role(user_id, role_id) VALUES(5, 3);
INSERT INTO user_role(user_id, role_id) VALUES(6, 1);
INSERT INTO user_role(user_id, role_id) VALUES(6, 2);
INSERT INTO user_role(user_id, role_id) VALUES(7, 1);
INSERT INTO user_role(user_id, role_id) VALUES(8, 2);
INSERT INTO user_role(user_id, role_id) VALUES(9, 1);
INSERT INTO user_role(user_id, role_id) VALUES(9, 2);
INSERT INTO user_role(user_id, role_id) VALUES(10, 2);
INSERT INTO user_role(user_id, role_id) VALUES(11, 1);
INSERT INTO user_role(user_id, role_id) VALUES(12, 1);
INSERT INTO user_role(user_id, role_id) VALUES(12, 2);


-- Mahruk (3) creates a checklist template 1
INSERT INTO checklist_template(author_id, list_name, description) VALUES(3, "A Checklist of Induction Activities and Knowledge for New Software Engineers", "So, you’ve joined the ranks of the Office for National Statistics and the Civil Service. What now?
This is a non-exhaustive (though possibly exhausting) checklist of things to do, or find out, or get hold of.
The corporate induction timeline, and links to many resources can be found here: http://intranet.ons.statistics.gov.uk/guidance/new-starter-timeline/
By the end of your extended induction and training period, if you don’t have ticks in all the boxes, something’s gone awry, and we want to know.
Please keep hold of this and keep it up to date- we’ll make use of it during your 1:1 sessions as part of checking the onboarding process- don’t worry, it’s more an assessment of us than it is you!");

INSERT INTO topic(checklist_template_id, topic_name, description) VALUES(1, "General Physical Environment", "To help you become familiar with the ONS office buildings and to help you get to know who’s who in your team and beyond:");
INSERT INTO topic(checklist_template_id, topic_name, description) VALUES(1, "Other Physical Environment", "More specific/complex features of the physical environment:");

INSERT INTO checklist_template_item(topic_id, description) VALUES(1, "a tour of the relevant building(s) and a map of them");
INSERT INTO checklist_template_item(topic_id, description) VALUES(1, "fire escape routes and assembly points");
INSERT INTO checklist_template_item(topic_id, description) VALUES(1, "location of flexi terminals");
INSERT INTO checklist_template_item(topic_id, description) VALUES(1, "use a flexi terminal");
INSERT INTO checklist_template_item(topic_id, description) VALUES(1, "where to buy food/drink (shop, canteen, etc) and where to keep food/drink (break out area, fridge)");
INSERT INTO checklist_template_item(topic_id, description) VALUES(1, "a place to keep your personal things safe (locker/pedestal)");

INSERT INTO checklist_template_item(topic_id, description) VALUES(2, "smart meetings rooms and how to use them");
INSERT INTO checklist_template_item(topic_id, description) VALUES(2, "how to get things delivered to the office");
INSERT INTO checklist_template_item(topic_id, description) VALUES(2, "clean desk policy");
INSERT INTO checklist_template_item(topic_id, description) VALUES(2, "location of ONS library and details of what facilities/services it offers");


-- Assign checklist template 1 to Nimrod
INSERT INTO personal_checklist(user_id, checklist_template_id, date_assigned, date_complete) VALUES(1, 1, DATE('2020-12-01'), null);

INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 1, true, '2020-12-02');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 2, true, '2020-12-03');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 3, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 4, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 5, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 6, true, '2020-12-02');

INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 7, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 8, true, '2020-12-04');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 9, true, '2020-12-03');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(1, 10, false, null);

-- Assign checklist template 1 to mahruk
INSERT INTO personal_checklist(user_id, checklist_template_id, date_assigned, date_complete) VALUES(3, 1, DATE('2020-12-01'), null);

INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 1, true, '2020-12-02');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 2, false, '2020-12-03');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 3, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 4, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 5, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 6, true, '2020-12-01');

INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 7, true, '2020-12-04');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 8, true, '2020-12-04');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 9, true, '2020-12-03');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(2, 10, false, null);



-- Sameer (4) creates a checklist template 1
INSERT INTO checklist_template(author_id, list_name, description) VALUES(4, "Daily Tasks", "Tasks to do on a daily basis");

INSERT INTO topic(checklist_template_id, topic_name, description) VALUES(2, "Before Lunch", "Tasks to be done before you go on your lunch break");
INSERT INTO topic(checklist_template_id, topic_name, description) VALUES(2, "After Lunch", "Tasks to be done before you go home");

INSERT INTO checklist_template_item(topic_id, description) VALUES(3, "check emails");
INSERT INTO checklist_template_item(topic_id, description) VALUES(3, "drink coffee");
INSERT INTO checklist_template_item(topic_id, description) VALUES(3, "drink more coffee");
INSERT INTO checklist_template_item(topic_id, description) VALUES(3, "drink even more coffee");
INSERT INTO checklist_template_item(topic_id, description) VALUES(3, "have daily scrum");

INSERT INTO checklist_template_item(topic_id, description) VALUES(4, "have afternoon snack");
INSERT INTO checklist_template_item(topic_id, description) VALUES(4, "tidy desk");
INSERT INTO checklist_template_item(topic_id, description) VALUES(4, "drink coffee");


-- Assign checklist template 2 to Nimrod
INSERT INTO personal_checklist(user_id, checklist_template_id, date_assigned, date_complete) VALUES(1, 2, DATE('2020-12-05'), null);

INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(3, 11, true, '2020-12-02');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(3, 12, true, '2020-12-03');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(3, 13, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(3, 14, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(3, 15, false, null);


INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(3, 16, false, null);
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(3, 17, true, '2020-12-04');
INSERT INTO checklist_item(personal_checklist_id, checklist_template_item_id, checked, date_checked) VALUES(3, 18, true, '2020-12-03');