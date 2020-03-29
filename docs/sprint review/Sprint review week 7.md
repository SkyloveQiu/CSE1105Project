# Sprint review #1

| Requirement | Task     | Assigned to | Expected duration (hours) | Actual duration (hours) | Done? | Notes |
| :---------- | :------- | :---------- | :------------------------ | :---------------------- | :---- | :---- |
| There needs to be a possibility to reserve bikes in our application. | redesign bike server side: bikes need to be reserved for dates. | Ziang |  |  | Yes |  |
| Food can be ordered eventhough you do not have reserved a room for that time slot,<br> or at that time | redesign food server side: you can order food even if you dont have a room reserved. (pick up option?) | Andrei |  |  | Yes? |  |
| It is necessary to test (properly) if our methods and apis work. | make server/api tests | Andrei & Ziang |  |  | Yes |  |
| The administrator must be able to add update and <br>delete rooms through the api | room CRUD functionality | Cristian |  |  | No |  |
| For adding, editing and deleting rooms a design and buttons are necessary | room CRUD design | Thom |  |  | Yes |  |
| The user should be able to change his/her reservations or even <br>cancel them when they change their minds or something had come up | reservation CRUD functionality | Einar? (& Andrei) |  |  | No |  |
| To do this ^ there needs to be some api's that make this possible | reservation back end | Andrei |  |  | Yes |  |
| The user should be able to see his/her current reservations. | reservation design | Niels |  |  | Yes |  |
| It would be nice if the user could make and note down <br>his own appointments beside the reservations in the app. | calendar design | Niels & Thom |  |  | Yes |  |
| If we have a calendar, we also need ot be able to save and read <br>the current entries in the calendar (if not what for useless calendar would it be, am i right?) <br>For privacy sake this does not get uploaded to the database but is stored locally | calendar functionality (no server side) | Niels & Thom |  |  | No |  |
| The user should have the possibility to change his or her password <br>(also for securities sake) | profile page change password backend + functionality | Cristian & Andrei |  |  | No? |  |
| For deciding what gets shown to the user (mainly for administrator buttons and actions)<br>we need to be able to get the users role through the database<br>therefore we need an api for this | get user role through token back end | Ziang |  |  | Yes |  |
| See cell 2 | client side tests | Einar |  |  | Yes |  |
| As an extra safety measure the CRUD operations should only be valid<br>when posted by a user with the same token as<br>the admins of the system,<br>that way we cant have someone randomly post a request and delete the whole database. | CRUD token verification backend + implemented frontend | Andrei & Einar |  |  | Yes |  |

## Improvements from last sprint
*
*

## Problems this sprint
Problem 1:
>
Improvement:
>

Problem 2:
>
Improvement:
>