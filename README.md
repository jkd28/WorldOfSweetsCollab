# BitsPlease - World Of Sweets    
To run the program, clone this repo and run these commands:  
`gradle build`  
`gradle run`

## Manual Tests
### Timer
 *  Test Timer Run  
 **Execution:**  Begin game and draw first card.  Ensure that time starts after the draw.
 *  Test Time Increment  
 **Execution**: Begin game and draw first card.  Let game sit until the timer ticks over to minutes, then to hours.  Ensure this is done as expected, with the format of HH:MM:SS

### Tokens
 *  Test Token Display  
 **Execution:** Begin game and select tokens for each player.  Ensure that the tokens are properly displayed on the first space before the first card is drawn.

 * Test Token Assignment  
 **Execution:** Begin game and select unique tokens for each player.  Verify that each player's token is assigned properly by having each player draw a card for the first round of play and verify that their unique token is the one that moves.

### Music
 *  Test Music Play  
 **Execution:** Begin the game.  Once the last player enters their name, ensure that music begins playing.

 *  Test Music Termination  
 **Execution:** Begin the game. After playing through a complete game with music in the background, exit the game.  The music should terminate upon game end.  

 **Secondary:** Begin the game.  Ensure music begins playing.  After a few card draws, exit the game.  The music should terminate upon exit.  

 *  Test Missing Music  
 **Execution:** Remove the music file from the game files.  Start the program.  After players enter their names, the game should throw an error "Music: file not found" and terminate.
