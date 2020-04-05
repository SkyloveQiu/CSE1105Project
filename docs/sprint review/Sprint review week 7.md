# Sprint review 1

| Requirement | Task     | Assigned to | Expected duration (hours) | Actual duration (hours) | Done? | Notes |
| :---------- | :------- | :---------- | :------------------------ | :---------------------- | :---- | :---- |
| There needs to be a possibility to reserve bikes in our application. | redesign bike server side: bikes need to be reserved for dates. | Ziang | 3 | 3 | Yes | There should be more communication with the front end on this part, <br>because we did not we had to redesign it|
| Food can be ordered eventhough you do not have reserved a room for that time slot,<br> or at that time | redesign food server side: you can order food even if you dont have a room reserved. (pick up option?) | Andrei | 3 | 2 | Yes | - |
| It is necessary to test (properly) if our methods and apis work. | make server/api tests | Andrei & Ziang | 7 | +-20 | Yes | Tests are almost done we are at about 50% to 70% |
| The administrator must be able to add update and <br>delete rooms through the api | room CRUD functionality | Cristian | not finished but is in 12 hours | 24 | No | Not finished yet, has to do warning labels and edit |
| For adding, editing and deleting rooms a design and buttons are necessary | room CRUD design | Thom | 15 | 7 | Yes | Ran into some problems during coding where the checkboxes<br>would not function properly + due to a mistake <br>the room page was really slow. |
| The user should be able to change his/her reservations or even <br>cancel them when they change their minds or something had come up | reservation CRUD functionality | Einar? (& Andrei) | - | - | No | Back end part was finished but due to a communication problem<br>the front end part is not yet done or started.<br>He will do it the following week |
| To do this ^ there needs to be some api's that make this possible | reservation back end | Andrei | 5mins | 10mins | Yes | - |
| The user should be able to see his/her current reservations. | reservation design | Niels | 6 | 6 | Yes | Learned some more about the apis and front end which he thought was nice. |
| It would be nice if the user could make and note down <br>his own appointments beside the reservations in the app. | calendar design | Niels & Thom | 12 | 3-4 | Yes | CSS for a library you have never used (and does not have any documentation for styling) really sucks |
| If we have a calendar, we also need ot be able to save and read <br>the current entries in the calendar (if not what for useless calendar would it be, am i right?) <br>For privacy sake this does not get uploaded to the database but is stored locally | calendar functionality (no server side) | Niels & Thom | 5-6 | 4-5 | No | Inserting entries for the room reservations and bike reservations from database. |
| The user should have the possibility to change his or her password <br>(also for securities sake) | profile page change password backend + functionality | Cristian & Andrei | 2 | 5 | Yes | - |
| For deciding what gets shown to the user (mainly for administrator buttons and actions)<br>we need to be able to get the users role through the database<br>therefore we need an api for this | get user role through token back end | Ziang | 10mins | 10mins | Yes | - |
| See cell 2 | client side tests | Einar | 20 | 30 | Yes | Tests for null and error code != 200 still on the way |
| As an extra safety measure the CRUD operations should only be valid<br>when posted by a user with the same token as<br>the admins of the system,<br>that way we cant have someone randomly post a request and delete the whole database. | CRUD token verification backend + implemented frontend | Andrei & Einar | - | 1 | Partly | Not yet implemented front end |

## Improvements from last sprint

* We made a sprint review.
* The assigning of the tasks.
* Worked harder than most week on this.
* Testing improved majorly
* Reviewing of code improved
* Started using WIP merge requests, but this is only implemented by some part of us, <br>can still be improved.


## Problems this sprint

### Problem 1:
> Forgot to notify Einar of his tasks this week.

Improvement:
> Don't forget to notify all members if they were not present during the meeting due to some thing.


### Problem 2:
> We did not have a discussion meeting after the TA meeting on monday to discuss what he told us. <br>We also did not take a second look at (and thus disregarded) the advice our TA gave us.

Improvement:
> Have a (mandatory) discussion after the meeting to discuss the notes and what was said and how we can implement this/improve this.