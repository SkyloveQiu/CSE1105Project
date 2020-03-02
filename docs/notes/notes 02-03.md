## Example agenda

This is the agenda for 2th of March. 

---

Date:           02/03/2020\
Main focus:     Discussing the current state 2.0 \
Chair:          Thom\
Note taker:     Niels

Notes:



# Opening
Present: Einar, Thom, Andrei, Cristian, Ziang, Niels and Jordy\
Absent: Alexis

# Approval of the agenda
Is everything in the agenda?

# Points of action

* Present Demo
* Making the API for login
* Make the HTTP request login
* Fix the checking methods in the login and register page
* Fixing the scrolling for the Main Page and Login Page and Register Page
* FIX THE API
* Integrate APIs into front-end
* Buildings and rooms in database with JSON

Are these things done?

Notes:

Demo not complete since CRUD not implemented in UI
it needs work since next week is meeting with CTA


# Action points for next week (Scrum board)
*Every week you fill the Scrum board with new action points for that week. See the to do list for the items you should implement.*
To be determined.


# Any other business
*If anybody has something that should be discussed but came up with that after the agenda was finalized (in point 2), he/she should bring that up now so that it can be discussed after all.*

# Questions for the TA
*Your TA will visit you in the second half of the lab session. Note down all questions that you have so that you can ask them then.*
Discuss rubrics.
Thom: testing
Cristian: checkstyle for server?

# Question round
Are there any questions?

Notes:

How to test?
    Server:
        Spring has some testing framework
        What to test in server?
            -test the accesspoints with the database
        you have jUnit and integration same as client

    Client side
        2 methods of testing. 
            1) integration test integratiion with server
               verifies that actual rooms are returned (json)
            2) jUnit using probably mocking (pretend there is a server) this is for excellent


Rubric question OD:
    Why is OD sufficient, on what did you base it?
    
    (TA)
    General fine
    Missed:
        Within entities is room for inheritance
        Is overlap in structure with similarities in entities
        example food order and other orders/reservable
        
        some had for example a class reservable with then food reservable and a bike reservable
        
        This inheritance is mainly what is missing


Rubric question S-R:
    Is insufficient right now, but was sufficient, what did we do differently/wrong?
    
    (TA)
    Discuss what went well or not at the end of a week.
    Do some sprint reviews including this.
    (TA is going to send some examples of what is good to do in sprint reviews DoT would also probably improve with this)
    
    Issue with last meeting:
        Order of agenda does not matter would like to have the demo first.
        But it needs a flow, it is interrupted a lot.
        The plan made for the meeting get disregarded a lot.


Hosting our server?
    localhosting our server is fine.


To what classes do you look for test coverage:
    Keep methods and classes that need to be checked in a different folder. Like how the ServerCommunication is in a different folder than the rest.
    UI does not need to be tested, but keep a clear distinction between what is UI and what is not UI.
    
    
How many approves for merge requests:
    For feature branches it is fine if two or three people approve it, also make sure the people that worked on the features approve of it.
    For development into master, four to five is good.
    Also for the people that approve of the request, write comments on it about what you think of it.




TA Feedback:
    Consider making a soft deadline before the actual deadline.
    Because as of now you merge an hour or something before the actual deadline,
    but right now if you do it like this you cannot fix any mistakes you find in it.
    
    About code division:
        I cannot see the work of Einar since you have been working on the back-end. Keep in mind this is a java project.
        
    Issue board:
        These are tickets for things that need to be implemented, not stating some things are done.
        It should be only for features with in description stuff as Definition of Done.

# Closing
Thank you for coming.


Note:
The next meeting will be on: Friday the 6th at 2 o'clock.
