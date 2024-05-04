My reasons for using the structures and their O(n)

posts:
I used a linked list since they are able to expand without needed to use unneeded data, there wouldn't be a point to add a tree map since it is always
liner because we want chronological order which is n and traversing the tree is nlogn which is not as as good. I thought about using the stack or queue
but then realized there are times when we need to get to the middle of the data and there is no point in popping all the data just to push it all back in
a linked list is the best solution

replies:
same idea as the posts because expanding is easy and we want time order so we leave it liner adding to the back using linked list for chronological order

accounts: 
for accounts I decided to use tree map and I organized it by User Name since thats the most mainly used thing and with that as the key I
set the actual account to the data part of it and it was simple use from there. Tree is able to grow with the data set and uses space very effciently
and is not wasted and the speed of O(log n) is great for getting users in fast.

dictionary: I made this a hashSet because it is a data that will never change and I need to have fast speed on it so it can quickly tell what is 
and is not spelled correctly. Being O(1) is a great help in checking the post for errors make it great.

subsriber's:
subsriber's I made them tree set because when checking to see if they are subsribed or not we are searching the data set to 
see if it contains the subscriber not going in order checking every single one so using a tree for O(log n) speed is much better and
great to use because it saves time instead of O(n) of a linkedlist which would take a much much longe time as the data grows larger and is not effective


likes: likes would just be a simple integer value since it is just counting the numbers of likes.

people who liked it: I used treeset again because it will be able to find the users quickly same idea for accounts. Keeps it all orgranized.


