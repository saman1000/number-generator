# number-generator
I have written this API for these reasons:
* generate lottery numbers when I would like to pay <= $10 for fun and remote hope of huge win!
* to keep coding in Java and Spring boot. Because of my leadership position I do not get the chance
to code in Java that often. Especially new Java features.
* I will link to a public Jira project as I plan to move this entirely to AWS, using AWS lambda functions.
  I expect this to cost less than $2 per month. However, lambda function should be in Python or node
  and not in Java, because of JVM startup delay. Therefore I have created roadmap and Kanban that I will
  share.

to run rest server on local:
JVM options
-Dspring.profiles.active=lottery,rest
working folder should be rest-server project folder
