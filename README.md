How to use:
- Once started you can click on a mode to start.
- You can click on a piece and then click where to move it to make a move.
- In slow mode when computer runs you can see the possible moves on the board.
- You can click any valid move yourself even a removed one or let the computer choose in slow mode.
- There is a button during a computer turn to allow the computer to choose a move in slow mode on the right side.
- In slow mode an empty match box does not immediately end the turn in case the user clicks for the computer.
- In slow mode if there is an empty match box the computer will lose only when allowing the computer to move.
- When a player has won you can click the button at the top to reset the board.
- For fast mode when entering a number of turns you must click enter after entering then click the fast mode.
- You can change the rows and columns of the board in the range 3-15 included.
- You can also change player one and player 2 colors. 
- When entering in a text field you must click enter.
- You must fill in a text field before clicking a mode to apply the change.

Project Description:
- This starts a hexapawn type of game with a computer player.
- The computer player will have a move that caused them to lose removed so they get better and learn.
- There are three different modes
- Slow mode allows you to choose moves for the computer and shows each move on the board with the chance.
- Fast mode is a normal game against the computer.
- Auto mode is a computer against a computer where you can enter how many games and see the results at the end.
- Auto Mode alternates each game between who goes first and displays stats at the end.
- You can select rows, columns, and player colors.
- Slow mode chances are rounded to a whole number using Math.round().

Possible Issues:
- Moves seem to be properly removed when encountering an empty box but in AutoMode one player always runs out of moves.
- I am not sure if the first player should be running out of moves after many matches, but it seems to work correctly.
- With larger boards the circles and move lines are not totally aligned correctly, but they are still in correct box.
- Larger boards do not look as good, but they should still work correctly with each mode.

Notes:
- I was told that I could receive some extra points for adding the ability to change rows, columns, and colors.
- Rows and Columns do not need to be the same and any color but transparent will work.
- It should never be transparent when entering since it is only rgb values entered.