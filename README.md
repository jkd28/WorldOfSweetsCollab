# BitsPlease - World Of Sweets    
To run the program, clone this repo and run these commands:  
`gradle build`  
`gradle run`

## Manual Tests
### Timer
 *  Test Timer Run  
 **Execution:**  Begin game and draw first card.  
 **Expected Results:** The timer starts after the first draw.  

 *  Test Time Increment  
 **Execution**: Begin game and draw first card.  Let game sit until the timer ticks over to minutes, then to hours.  
 **Expected Results:** The timer increments every second with the format of HH:MM:SS.

### Tokens
 *  Test Token Display  
 **Execution:** Begin game and select tokens for each player.  
 **Expected Results:** The tokens are properly displayed on the first space before the first card is drawn.

 * Test Token Assignment  
 **Execution:** Begin game and select unique tokens for each player.  
 **Expected Results:** Each player's token is assigned properly by having each player draw a card for the first round of play, and their unique token is the one that moves.

### Music
 *  Test Music Play  
 **Execution:** Begin the game. All players enter their names.  
 **Expected Result:** The music begins playing immediately after the last player enters their name.

 *  Test Music Termination On Game End  
 **Execution:** Begin the game. After playing through a complete game with music in the background, exit the game.  
 **Expected Result:** The music terminates upon game end.  

 *  Test Music Termination On Game Exit  
 **Execution:** Begin the game.  Ensure music begins playing.  After a few card draws, exit the game.  
 **Expected Results:** The music terminates upon exit.  

 *  Test Missing Music  
 **Execution:** Remove the music file from the game files.  Start the program.  All players enter their names.  
 **Expected Results:** The game throws an error ("Music: file not found") and terminates.

### Save & Load
 * Test Save Game  
 **Execution:** Start a brand new game, draw several cards, select the "Save" button and enter a brand new, valid save-file name (e.g. "WorldOfSweets Save File.ser") and select "OK".  
 **Expected Results:** WorldOfSweets should display a "Game was saved successfully!" type of prompt, and the game should properly resume (game timer resumes, drawing cards works properly, player movement works properly, etc.).
Invalid Save-File Name

Procedure:
Start a brand new game
Draw several cards
Select the "Save" button
Enter a brand new, invalid save-file name (e.g. "FakeFile.rofl")
Expected result: The game informs the user that the entered file name is invalid, and asks them to try again.
Prompted for Save When Exiting Mid-Game

Procedure:
Start a brand new game
Draw several cards
Select the "X" (exit) button at the top of the game's frame
Expected result: The game asks the user if they want to save their progress before quitting.

 * Test Load Game  
  **Execution:** Begin the game and answer "Yes" to the prompt asking if you want to load a saved game.  Select a valid save file to load, and hit "OK". Verify that WorldOfSweets returns to the exact state as saved in the save-file, and that game will resume.

 * Test Load Invalid Game  
 **Execution:** Begin the game and answer "Yes" to the prompt asking if you want to load a saved game.  Select an invalid save file (e.g. type in a filename that doesn't exist, select a save-file from a previous iteration of the WorldOfSweets codebase, etc.) Verify that WorldOfSweets notifies you that it was unable to load the game successfully, and will instead start a new game.
