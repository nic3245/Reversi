Overview:
This codebase implements the game Reversi, including a textual view and a playable GUI for the game.
It is assumed that anyone using our code will know the basic rules of the game as outlined in the assignment spec if they plan to work with our implementation of the model. In terms of assumptions about extensibility, it is assumed that each model that implements the interface keeps its own board and rules. This means that the interface is open to any board that can be represented with two axes, and any set of rules that utilize tile placement. However, it is slightly out of scope to try to implement more than two players/types of tiles. Our interface theoretically could support this; however, you would have to change the enum used to represent the tiles of the game as right now there are only two.
In adding our GUI, we did not restrict this extensibility at all. Our specific implementation of the view is tied to a pointy-top axial model; however, this makes sense as the view will be drawn differently if the board is differently shaped. The interfaces for the view itself are extensible to any board that can be represented with two axes, just like the model. Any new view can also implement the view features however they like; but, they must have the basic functionality of selecting (not yet in the features interface, but will be once controller is added), passing, and placing.
In adding our controller and listener functionality, this extensibility again remained the same - a model is extensible with the restrictions stated before, and views are extensible with restrictions relating to the model they are representing.

Quick Start (model with basic textual view):
ReversiModel model = new BasicReversiModel(7);
StringBuilder out = new StringBuilder();
ReversiTextualView view = new ReversiTextualViewImplementation(model, out);
this.model.placeTile(2, -1, PlayerTile.FIRST);

try {
  view.render();
} catch (IOException e) {
  e.printStackTrace();
}

After render() is called, out.toString() will look like the figure below: 
   _ _ _ _
  _ _ _ _ _
 _ _ X O X _
_ _ O _ X _ _
 _ _ X O _ _
  _ _ _ _ _
   _ _ _ _

Quick Start (model with GUI view, not playable b/c no controller):
Inside your main class, put:
  public static void main(String[] args) {
    ReversiModel model = new BasicReversiModel(11);
    SwingUtilities.invokeLater(() ->  new ReversiGraphicsView(model));
  }
Then, set up a main configuration and run the program. This will output a basic GUI for the starting board.

Quick Start (full game with two human players):
Inside your main class, put:
    public static void main(String[] args) {
	ReversiModel model = new BasicReversiModel(size);
	
	IView v = new ReversiGraphicsView(model);
	IView v2 = new ReversiGraphicsView(model);

	Player p1 = Reversi.getPlayer(model, PlayerTile.FIRST, "human");
	Player p2 = Reversi.getPlayer(model,PlayerTile.SECOND, "human");

	ReversiGraphicalController c = new ReversiGraphicalController(model, p1, v);
	ReversiGraphicalController c2 = new ReversiGraphicalController(model, p2, v2);
	model.startGame();
    }
Then, set up a main configuration and run the program. This will create two windows, with the one for player two on top of the first.

Running the game:
To run the game with the GUI, run the main method in Reversi.java. The command line arguments must have a length of 2 or 3.
Failure to provide the correct length of command line arguments will throw an IllegalArgumentException.
If the length of the arguments is 2, it is expected that the first argument is the player type of player one and the second argument is the player type of player two.
If the length of the arguments is 3, it is expected that the first argument is the desired size of the board followed by the type of player one and the type of player two.
A list of the valid player type arguments are as follows:
- "human": a human player
- "high-score": a machine player with the PlaceForHighestScore strategy
- "minimax": a machine player with the MiniMax strategy
- "corner": a machine player with the PlaceForCornerOptimalThenHighestScore strategy

A valid argument for the desired board size must be an integer and follow the class invariant of the model.
Failure to provide a valid argument for the board size argument will result in a default game board of size 11.
Failure to provide a valid argument for the player type will result in a default game with two human players.

Upon running successfully, two windows will be displayed. The one on top is for Player Two (WHITE tiles), the bottom one is for Player One (BLACK tiles).

GUI Functionality & Playing the Game:
The game can be fully played through the GUI. A spot can be selected by clicking on it, and deselected by clicking on it again or clicking off of the board.
The view treats the key presses space as passing and p as placing a tile on the selected spot. If there is some reason that the move is not valid, a message pops up 
with the reason the move was unable to be played. This includes playing on the opponent's turn, playing in an invalid spot, and playing without a tile selected.
This continues until the game is over, at which point a message stating the winner is displayed and interaction with the GUI is disallowed.

Key components:
Our five largest design components are a model, view, player, strategy, and controller. They each have multiple interfaces.
- Model interfaces (ReversiModel, ReadOnlyReversiModel, ModelFeatures):
The model has been split into a read-only version and a full functionality version.  The read-only offers many observation functions to determine the state of the game, such as checking if the game is over, a player's possible moves, and what the score would be if a player played a move. The full functionality version (ReversiModel) adds the ability to pass a player's turn and place a tile for a player. It also enables any class that wants to receive notifications for when the turn has changed to subscribe for these notifications. These classes are represented through the ModelFeatures interface, which a class should implement if it wants to know when the turn has changed.
- View interfaces (IView, ViewFeatures, ReversiTextualView):
The view has two types of interface that represent the view itself. ReversiTextualView exists for the textual representation of the view. The IView is for GUI views, and contains the necessary functions for a GUI view. The final interface is ViewFeatures, which represents all the features a view wants to be able to do. This interface extends PlayerActions (as described below) because a human player essentially plays and makes their moves through the GUI, thus a view wants to be able to request to make these moves. Like with the model, a controller should implement the ViewFeatures interface if it intends to work with an IView.
 - Player interfaces (Player, PlayerActions):
  The Player interface has the ability to take a turn, which will tell any listeners registered with the subscribe method that it wants to move. The listeners will be of type PlayerActions, and must be able to handle passing and playing a move.
 - Strategy interfaces (BreakTies, MoveFilter, ReversiStrategy):
 The main outward facing interface for strategies is ReversiStrategy. This interface represents a full strategy from start to finish. That is, it takes in a model and a player, and will give the best move for the player based on the strategy. The other interfaces are used to build a strategy. MoveFilter takes in moves and reduces it down based on a condition, but still returns a set of possible moves (although it can be empty). BreakTies will take in possible moves, and will return a singular best move based on the tie breaking condition.
- The controller:
The Controller does not have any public methods of its own, and thus does not have its own interface. However, it does implement the ModelFeatures and ViewFeatures (and by extension PlayerActions) interfaces. In doing so, it essentially reacts to the other parts of the program, and does not need to have any standalone logic like a main game loop.
What Drives What? i.e. Program Flow:
 All controllers will tell their views to turn on, and then the model starts the game. In doing so, the model will notify all controllers that it is someone's turn. If the controller is for the player whose turn it is, the controller will request that the player moves. If the player is a machine, it will do so right away. If the player is a human, then nothing will happen until the user interacts with the GUI. When the user does, the view will attempt to move. When either the machine or view attempts to move, it notifies the controller that it wants to move. The controller tries this move with the model, and if successfull, the model will notify all controllers that now it is the other player's turn, and the cycle has restarted. This happens until the game is over, at which point the controller will tell its view to notify the user through a message, and the controller will disallow all interactions from players.

Key subcomponents:
Model components:
The PlayerTile enum exists as a representation of the two types of tiles. It is used to keep track of who has placed where in the board hashmap, as well as whose turn it is.
CustomPoint2D represents a point in a two-dimensional space. Our model specifically uses the AxialCustomPoint implementation of this. This implementation follows the axial coordinate system. Below is an explanation of the coordinate system:
The axial coordinate system uses q and r as its two dimensions. r is best described as the horizontal axis, and q is best described as the vertical axis rotated 45 degrees counter-clockwise. The center point is the origin (0,0). The q's and r's listed represent places where the value for that dimension is zero. That looks like the following example:
   q _ _ _
  _ q _ _ _
 _ _ q _ _ _
r r r _ r r r
 _ _ _ q _ _
  _ _ _ q _
   _ _ _ q
 Thus, r represents the rows of the hexagon. Going up a row is negative, and going down is positive. The below are the values for r:
   -3 -3 -3 -3
  -2 -2 -2 -2 -2
 -1 -1 -1 -1 -1 -1
0  0  0  0  0  0  0
  1  1  1  1  1  1
   2  2  2  2  2
    3  3  3  3
Because r is rows, Q must represent columns. However, because the size of the rows changes, the range for values of q changes. It will be zero on the axis, and to the left of it will be negative, and to the right of it will be positive. The below are the values for q:
     0  1  2  3
   -1  0  1  2  3
  -2 -1  0  1  2  3
-3 -2 -1  0  1  2  3
  -3 -2 -1  0  1  2
    -3 -2 -1  0  1
     -3 -2 -1  0


Player components:
At its most basic, a player must have a PlayerTile representing which tile this player is using. A machine player needs more information, and thus takes a read-only model as well as a strategy to use.
We also included a PlayerCreator factory class to aid in the creation of players. This is because it can get somewhat complex to create a player due to the various strategies, so we thought it would be a good idea to help out users of our program with this.

Strategy components:
The filter and tie-break interfaces work together to make a full strategy. Any full strategy can write their own or use existing classes that implement these two in order to fulfill their need. An overall strategy takes a model and turn so that it can figure out the best move for its implementation.

View components:
A view is made mainly of a class that extends JFrame and will implement IView. From there, it should use any helper classes it needs, such as a Panel. Our implementation has ReversiGraphicsView as our JFrame that implements IView. Our HexagonPanel is used by this class to draw the board and handle mouse events. The JFrame class itself handles the key presses.

Source Organization:
The project has a src/ and test/ directory, both following the same package structure. The entire project is in the 3500.reversi package. There is a main class called Reversi inside, as well as five packages: model, containing the model interface, our hexagonal, pointy-top, basic rules implementation of the model, the PlayerTile enum, a mock model for testing, and our coordinate system classes; player, containing the player interface, the player actions interface, a human player, a machine player, and a player creator factor; view, containing the view interfaces and our implementations; strategy, containing the strategy interfaces and our implementations; and controller, containing our controller class.

--- Old information from Part 2, kept for reference. Player Plan is obviously dated. ---

Player Plan:
Right now, we have our Player interface, as described above. Computer players will be made using this interface as well. The interface allows for basic play with placeTile and pass, as these are the basic actions a player (computer or human) could take. We believe the implementation should take in a model and a PlayerTile, as when players play a game in real life, they sit down at a board and receive a set of tiles. This then enables the implementation to call the corresponding methods in model. 
In the future, we could possibly need to implement a startGame method in model. This would be used to verify that there are two players corresponding to the current model, and then start the game. We did not attempt this yet as we did not know if it would be necessary in the future with how the controller is working; however, it is a possibility.
Overall, we see the model being created, the two Players being created with the model and their appropriate playerTiles, and then the players can play the game by calling their functions. A computer could select its move at random using the playerMoves() function in the model, then play the selected move.
Our plan with automated/computer players is fairly straightforward - they will be extremely similar to human players, except that when they are told it is their turn, they will execute a strategy to choose what move to do. Thus, they will be constructed with a strategy that they will use when choosing.

Changes for part 2:
Remember, these are the changes from our initial design, and do not include added functionality like the GUI.
The classes that were added:
- The coordinate class (AxialCustomPoint) with the CustomPoint2D interface. This is a good design decision because it abstracts away the idea that there can only be one coordinate system.
- ReadOnlyReversiModel. This is a good design decision because it enables the model to be read (without being changed) by other classes.
A getTurn function was added to the model. This is a good idea as in the future it will allow the controller to be able to determine whose turn it is to drive the model easier.
A getScoreIfMovePlayed(PlayerTile) function was also added to the model. This enabled the strategies to test moves on the model without actually changing the model.

Extra Credit:
Strategies 2 and 3 were implemented as filters. They are named accordingly:
2 -> FilterMovesRemoveNextToCorners
3 -> FilterMovesToOnlyCorners
4 -> MiniMax
The filters representing strategies 2 and 3 are combined with strategy 1 into an overall strategy inside the PlaceForCornerOptimalThenHighestScore class.

For our strategy-transcript.txt file, because we use sets of points for our strategies, they are not guaranteed order. Thus, the transcript may not be exactly the same as the output when you run it. However, it will have the same contents every time, just not necessarily in the same order. This is reflected in our model test as we use assertTrue(s.contains()) as opposed assertEquals.

--- New information for Part 3 (running the game was placed above as it made more sense in the flow of the README) ---

Changes for part 3:
The classes that were added:
- A controller (ReversiGraphicalController). This is good design because it upholds the idea of single-purpose classes
- ModelFeatures. This is good design because it allows the model to interact with the controller asynchronously.
- PlayerActions. This is good design because it allows players to interact with the controller asynchronously.
- ReversiPlayer, ReversiMachine. These are the human and machine player implementations.
- ReversiPlayerCreator. This is the factory class for a Player. This is good design as it encapsulates the idea of a player into four main types, as opposed to the mishmash of constructor calls needed without this factory.
- ReversiGraphicsViewMock. Used for testing.
Some additions to the model were made to accomodate the changeTurn notification. These functions are the private function alertTurn and the public subscribe function. These are necessary for the full implementation of asynchronous interactions with the controller.
Basically every function in the view interface is new, but each of them was carefully considered and added as necessary and self-explanatory for asynchronous interaction with the controller.