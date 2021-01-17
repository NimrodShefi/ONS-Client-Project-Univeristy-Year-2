# ONS Client Project - Group 8

## INSTRUCTIONS
Before running the application, seperately run the schema.sql and the data.sql externally on workbench.

When runnig the tests, the application needs to run in the background or the Selenium tests will not work.

## Manual Tests:
### Test 1
Create a new user using the following details:
**first name**: Johnny \
**last name**: Test \
**username**: JTest@cardiff.ac.uk \
**password**: Password1! \
**repeat password**: Password1! \
after that if you log in and go to 'My Checklists' in the nav bar, you shouldn't see any checklists as it is a brand new account. However, if you now log out and sign in with mahruk@cardiff.ac.uk (details below), you would be able to create a checklist by going to 'My Checklist Templates' in the nav bar and then click on 'Create Blank Checklist'.

Now input the following details: \
**Checklist Title**: Shopping List \
**Checklist Description**: The groceries I am going to buy for the following week \
Click Next to move to the next section. \
**Topic Title**: Fruits \
**Topic Description**: I need a lot of fruits this week because I am starting a new diet based on fruits \
There is a need for 7 items, so click on the plus sign 6 times \
**Topic Item**: 8 Apples \
**Topic Item**: 10 Bananas \ 
**Topic Item**: 5 Kiwi \
**Topic Item**: 3 Avocadoes \
**Topic Item**: 1kg of Cherries \
**Topic Item**: 4 Guava \
**Topic Item**: 20 Lemons \

Tick the Create another topic box to create another topic \
**Topic Title**: Vegetables \
**Topic Description**: I don't need a lot of vegetables this week because I am starting a new diet based on fruits \
There is a need for 2 items, so click on the plus sign once \
**Topic Item**: 3 Carrots \
**Topic Item**: 2 Corns  \

Don't tick Create another topic, and when you click next, you will be able to choose who to assign this to, and a deadline.

Choose the new user you created. Now if you return to 'My Checklist Templates' you will be able to see the new checklist, and if you click on it, you can see at the bottom everyone you assigned it to and theri progress (which for now is nothing as it was just assigned)

Log in to the user you created earlier, and if you go to 'My Checklists' now you should be able to see a checklist, which when clicked will let you to tick the items you completed and then save it. 


Application URL: https://localhost:8443/login

## Login in Details

**username**: nimrod@cardiff.ac.uk \
**password**: nimpass \
**roles**: User \
**use for**: filling out a personal checklist \

**username**: mahruk@cardiff.ac.uk \
**password**: mahpass \
**roles**: User & Author \
**use for**: filling out a personal checklist & creating new checklists template \

**username**: picard@cardiff.ac.uk \
**password**: earlgreyhot \
**roles**: User, Author, Admin \
**use for**: filling out a personal checklist & creating new checklists template & managing user roles \

all users created through the register page will be given a default User role
