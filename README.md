# Dots-Swing
An attempt to recreate the pencil-and-paper children's game of Dots and Boxes using Java and a Swing GUI.

The game is also fully playable via Java console keyboard input.

Made as a part of coursework for Object Oriented Programming Methodology, during the second year of my Bachelor's Degree in Computer Engineering at K. J.Somaiya College of Engineering.

## Rules
* Players take turns joining two horizontally or vertically adjacent dots by a line.
* A player that completes the fourth side of a square (a box) colors that box and must play again.
* When all boxes have been colored, the game ends and the player who has colored more boxes wins.

(Courtesy of [math.ucla.edu](https://www.math.ucla.edu/~tom/Games/dots&boxes.html))

## Instructions
* To start a new game:
    1. Click on File -> New Game
    2. Specify number of rows
    3. Specify number of columns.
    4. Specify number of players. The player's number denotes the boxes they occupy the grid.
* To play the game:
	1. Enter the coordinates of the start point (x1, y1) and end point (x2, y2).
    2. Press Go. The line appears
    3. When a box is completed, the player number appears inside the box.
    4. The player with most boxes at the end wins the game
