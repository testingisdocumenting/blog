---
date: 2019-12-19
summary: How testing makes you fast day one and not in some unknown future
---

:include-meta: {allAtOnce: true}

# Tests Are Here To Speed You Up

*"I am not writing tests right now as they are going to slow me down"* is one of the most common excuses I heard in 
the projects I worked on.

A lack of test writing experience can make you feel this way, but I think there is another, 
bigger reason for the perception that tests slow you down. I believe this reason is actually an untapped superpower:
the power of exercising any piece of your software in `O(1)` keystrokes. By `O(1)` I mean that regardless of how big 
your application becomes you will always be one button away of seeing the impact of your changes.  

The superpower of having the tightest feedback loop. 
   
Let me try to explain.

# Building A Medieval Castle

We are going to build a Castle, add features to it, and do a manual inspection of our creation as we go.

Let's start small. All we need is a single room and a throne to sit on.

:include-svg: img/castle.svg {idsToReveal: ["one"], actualSize: true}

Perfect! So let's test it. Quickly get in, walk through the room, sit on the throne. All is good.

Let's add a few more rooms and a chest.  

:include-svg: img/castle.svg {idsToReveal: ["one", "two"], actualSize: true}

Our goal is to test if we can open that chest. This time we have to do a little bit more legwork.
This time, a simple check took us a few minutes longer.

Let's add a bit of security to our castle by adding keys and levers. 

:include-svg: img/castle.svg {idsToReveal: ["one", "two", "three"], actualSize: true}

We have to test each door and to do that we need to go through our entire castle. 
This makes a good test and a very important one.

For our next iteration we decided to add a new smart lock to our chest, so we can open it
with our retina.

:include-svg: img/castle.svg {idsToReveal: ["one", "two", "three", "smartLock"], actualSize: true}

We gotta test this feature. So we open a jar, to pick a key, to open first one door and then to open the other door, to pull the lever,
to run the stairs through the opened door. After catching our breath we try to open a chest. 

A day later, we decide to add an auto lock after three failed attempt. 
And so we bring a friend who is not registered with our castle to open a jar, to pick a key,
to open a door, ... to ..., ..., to try to fail to open a chest. 

A few iterations later the Security Department expressed the concern that our security could use an upgrade.
A trap, puzzle and a dragon later we are ready to deploy.   

:include-svg: img/castle.svg {idsToReveal: ["one", "two", "three", "smartLock", "four", "scissors"], actualSize: true}

As before, we gotta test every new added Security feature. This is a good test and a very important one. 
We get caught in a trap, we jump over the trap, we play rock/paper/scissors to lose and to win, open the door, 
get eaten by the dragon, defeat the dragon and finally reach the chest. All tests passed.

Another development iteration passes and we decided to make *scissors* game to be lefty friendly.  

:include-svg: img/castle.svg {idsToReveal: ["one", "two", "three", "smartLock", "four", "scissorsLeft"], actualSize: true}

And so we bring a friend who is a lefty, can jump over traps, to open a jar, to pick a key, to open a door, to jump over trap,
 to open a door, to play rock/paper/scissors
 
A day later we decided to add a fingerprint reader to our chest 

:include-svg: img/castle.svg {idsToReveal: ["one", "two", "three", "smartLock", "smartLock", "smartLockThumbs", "four", "scissorsLeft"], actualSize: true}

And so we bring another friend who is a lefty, who is not registered with our castle, who can jump over traps, 
who won't be scared of a dragon, to open a jar, to pick a key, to open a door, to jump over trap, to open a door, to 
play rock/paper/scissors, to open a lever, to challenge a dragon, to put a finger to a chest.
 
It should be clear by now that the more features we add to the castle, the more time it takes to do a check routine of a single feature. 
What is interesting is that the big portion of the time is spend getting to the feature we want to test and not the test itself. 

Having said all that let's add another floor to the castle. 

:include-svg: img/castle.svg {idsToReveal: ["one", "two", "three", "smartLock", "smartLock", "smartLockThumbs", "four", "scissorsLeft", "five", "cardGameTwo"], actualSize: true}

There is a new room where you can grab any gear you want and also play a **loot box** game. 
Don't worry we won't be running through the castle to test **loot box** game algorithm (one entire castle run for a single try)

Instead, we will create **teleports**

# Teleports System

Instead of running around the castle, fighting dragons and jumping over traps, wouldn't it be easier to just appear 
in certain castle places to perform our routines?

Let's add a `teleport` right next to our new loot box game.
Next time we tweak loot algorithm we won't run around the castle, we just show up right where we need to be. 

:include-svg: img/castle.svg {idsToReveal: ["t3", "one", "two", "three", "smartLock", "smartLock", "smartLockThumbs", "four", "scissorsLeft", "five", "cardGameOne"], actualSize: true}

Regardless of how big our castle will become, regardless of how many more traps and doors we will add between the castle entrance and 
the loot floor, when we need to tweak and test our loot box algorithm, we just show up right there. 

Let's add `teleports` next to more points of interests, so next time when we tweak our features we can test them in an instance. 

:include-svg: img/castle.svg {idsToReveal: ["t1", "t2", "t3", "one", "two", "three", "smartLock", "smartLock", "smartLockThumbs", "four", "scissorsLeft", "five", "cardGameOne"], actualSize: true}

On our first day we only had one room and a throne at the end. 
But if on the same day we would add a teleport next to the throne - we would already be saving time running through the room.

Teleporting would make us faster day one.   

# Implementing Teleports

The castle we have built is your app. And the main entrance is your language `main` function. 

One way to build teleports is to implement more than one `main` file with its own `main` function.

```java {title: "Java example of Chest Teleport"}
class ChestTeleport {
    public static void main() {
        FingerAuth wrongAuth = new FingerAuth('wrongPerson');
        SmartLockChest chest = new Chest();
        assert !chest.open(wrongAuth);

        FingerAuth validAuth = new FingerAuth('validPerson');
        SmartLockChest chest = new Chest();
        assert chest.open(validAuth));
    }
}
``` 

If you do just that it is already going to be a time saver. Any time you want to test a different portion of your castle, you just use a 
different teleport - a different file to execute. 

As the number of teleports grow it may become harder to sort through them to pick one to use. 
Also teleports that are rarely used may become obsolete and point you to non existing parts of your castle. 

Fortunately most languages have a testing framework to define and structure your teleports. Moreover they will make sure
that teleports are still functioning.
 
```java {title: "Java example of Chest Test"}
class ChestTest {
    @Test
    public void invalidFingerAuth() {
        FingerAuth wrongAuth = new FingerAuth('wrongPerson');
        SmartLockChest chest = new Chest();
        Assert.assertFalse(chest.open(auth));
    }

    @Test
    public void validFingerAuth() {
        FingerAuth validAuth = new FingerAuth('validPerson');
        SmartLockChest chest = new Chest();
        Assert.assertTrue(chest.open(auth));
    }
}
``` 

This is the untapped power of tests I was talking about at the beginning - the power to exercise any piece of your software in one button press.
Regardless of how large or small you software is, tweaking your castle and validating it in a matters of seconds using 
an ever growing system of teleports will make you consistently productive on day one, day two, day hundred and day thousand. 

# Real Life Boring Example

You probably don't face castles and dragons every day at your work, so let me map it to a boring real life example.

Let's add a page to Enterprise Corporate Website. Page will list employees that have birthdays today. List must be in an alphabetic order.

Your fire up your IDE, write new page, deploy to DEV environment and test it. All looks good.
A minute later you realize that your company doesn't have employees with the same last name that have birthdays on the same day. 
So how do you test this scenario?

You create a SysAdmin Ticket to request `WRITE` access to `DEV` database. Wait for approval. Restart the server and check your code.

Note: You may say, but hey, I can run my database locally! Sure, but not every big company and not every software has moved on to the greatest tech.
And most excuses for not writing tests come from Enterprise engineers working on legacy applications. Plus it helps to make a point.   

Your code is in production for a month. Everyone is happy, the page is a great success and have many daily visitors. 
Hence a new request comes up: you need to change the sorting works.

````columns
left:
```text {title: "instead of"}
Smith Bo
Smith Bob
Smith Bobed
Smith Al
Smith Alice
```

right:
```text {title: "expected"}
Smith Alice
Smith Al
Smith Bobed
Smith Bob
Smith Bo
```    
````

Basically, still sort alphabetically, but put longer names at the top. It makes page looks better!

You fire up your `DEV` to realize the data is gone. You create a SysAdmin Ticket to request `WRITE` access to `DEV` database. 
Wait for approval. Restart the server and start coding. 

Let's rewind and try again. Instead of jumping to deploy and database manipulations, let's build a teleport.

```java {title: "Employees Sort Teleport"}
class EmployeesSorterTest {
    @Test
    public void matchingLastName() {
        EmployeesList list = new EmployeesList();
        list.add("Smith", "Bobed");
        list.add("Smith", "Alice");
        list.add("Smith", "Al");
        list.add("Smith", "Bob");

        EmployeesSorter employeesSorter = new EmployeesSorter();
        EmployeesList sorted = employeesSorter.sort(list);
        
        Assert.assertEqual("Alice", sorted.get(0).getFirstName());
        Assert.assertEqual("Al", sorted.get(1).getFirstName());
        Assert.assertEqual("Bobed", sorted.get(2).getFirstName());
        Assert.assertEqual("Bob", sorted.get(3).getFirstName());
    }
}
``` 

Whether you implement `EmployeesSorterTest` first or `EmployeesSorter` is not that important to me.

What important is you have a very tight feedback loop between your changes and the result. 
You bypass database admins (ahem, dragon, ahem, JK), web pages, internal auth systems and get straight to the business logic at hand.

# Summary

* Having a tight feedback loop is important for being efficient engineer.
* Tests are your teleports into any part of your application.
* Tests make you fast.
