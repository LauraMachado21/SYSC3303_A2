SYSC3303 - Assignment 2
Laura Machado
100963217

Files:
ChefAgent.java
Ingredient.java
Table.java

To Run Program:
1) Run ChefAgent.java [this is where the main method is found]

Purpose:

The program will create 1 agent thread to place two items on the table and 3 chef threads with assigned ingredients.
The agent will pick two random ingredients and place them on the table. The put method for the table will verify that the table is
empty before placing the ingredients. Once the table is no longer empty, the chef threads will be notified. The chef threads will
use external synchronization within their run() method to check if the table is empty. If the table has ingredients, it will check
if the missing ingredient is the ingredient the chef was assigned. If it is the ingredient, it will grab the ingredients and notify
the agent that the table is empty. Otherwise, it will continue waiting.

This program will iterate until 20 total sandwiches have been made.
