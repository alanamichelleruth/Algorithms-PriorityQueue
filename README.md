# Algorithms-PriorityQueue
Project for Algorithms and Data Structures-- management system with a priority queue.

## Problem
Provide a RESTful service which accepts as a POST of JSON a list of enqueue and dequeue statements onto an in-memory job queue.
To simulate a task management system with a priority queue.
<br />Each job definition contains a name and a priority, with 0 being the best priority and positive integers representing lower priorities.
<br />Return the JSON representing the state of the queue (the list of job names, in priority order), after all enqueue and dequeue statements have been processed.
•	Example input:<br />
{ “inList” : [ { “cmd” : “enqueue”, “name” : ”job1”, “pri” : 4 },
<br />{ “cmd” : “enqueue”, “name” : ”job2”, “pri” : 3 },{ 
<br />“cmd” : “dequeue” },
<br />{ “cmd” : “enqueue”, “name” : ”job3”, “pri” : 0 },
<br />{ “cmd” : “enqueue”, “name” : ”job4”, “pri” : 1 },
<br />{ “cmd” : “dequeue” }] } 
<br />Example output:
<br />{ “outList” : [ “job4”, “job1” ] }
<br />Erroneous input (e.g. malformed JSON) should be handled gracefully.  

## Deliverable
An HTTP URL was available for the class project yet was destroyed upon completion. Users invoked a RESTful service with a tool such as curl or Postman.
