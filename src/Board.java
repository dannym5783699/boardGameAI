
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * @author Danny Metcalfe
 * @Description:
 * The Board class handles setting up the visual board as well as a array representation.
 * It will also add Board Pieces and determine what moves are valid.
 * It handles most board related things.
 */
public class Board {

    /**
     * The grid where the pieces will be displayed.
     */
    private final TilePane gameGrid =  new TilePane();

    /**
     * The grid structure to handle board movements and coordinates.
     */
    private final BoardPiece[][] grid;

    /**
     * A copy of the grid so a mode class can validate moves while not changing the grid.
     * This is so that another class will not be able to make moves in the board.
     */
    private final BoardPiece[][] validationGrid;

    /**
     * How many rows are in the board.
     */
    private final int rows;

    /**
     * How many columns are in the board.
     */
    private final int columns;

    /**
     * Starting column and row for a clicked piece.
     */
    private int startColumn = -1;
    private int startRow = -1;

    /**
     * Has a piece been selected.
     */
    private boolean hasPiece = false;


    /**
     * Can we click to move pieces.
     */
    private boolean canClick = true;


    private Game game;

    //Holds an overlay to the board and also tile width and height.
    private final Pane overLay = new Pane();
    private final double tilePaneWidth;
    private final double tilePaneHeight;



    /**
     * Public class of board pieces. Only the board can use the constructor for pieces.
     * Other classes may see the pieces so that they can validate moves.
     * Everything in the class is private except the color to validate moves with.
     */
    public class BoardPiece{
        /**
         * Each piece has a Color.
         */
        private final Color pieceColor;

        /**
         * Each Piece has a circle to display.
         */
        private final Circle piece;

        /**
         * Each piece has a row and column in the board.
         */
        private int row;
        private int column;


        /**
         * Creates a Board piece with a Color, Circle, and it's location.
         * @param pieceColor Color of the piece.
         * @param column Column in board.
         * @param row Row in board.
         */
        private BoardPiece(Color pieceColor, int column, int row){
            this.row = row;
            this.column = column;
            this.pieceColor = pieceColor;
            this.piece = new Circle(200.0/((rows+columns)/2.0), pieceColor);
            if(!pieceColor.equals(Color.TRANSPARENT)) {
                this.piece.setStroke(Color.BLACK);
            }

            //When clicked must click another piece to move.
            this.piece.setOnMouseClicked(event -> {
                if(canClick) {
                    if ((!hasPiece) && (!this.pieceColor.equals(Color.TRANSPARENT))) {
                        startRow = this.row;
                        startColumn = this.column;
                        hasPiece = true;
                    } else if (hasPiece) {
                        game.getValidation(startColumn, startRow, this.column, this.row);
                        hasPiece = false;
                    }
                }

            });
        }

        /**
         * Get the circle from the board piece.
         * @return Returns the board piece circle object.
         */
        private Circle getCircle() {
            return piece;
        }

        /**
         * Checks if pieces are equal based on color.
         * @param otherPiece Another board piece.
         * @return Returns true if they are equal and false if not.
         */
        public boolean equals(BoardPiece otherPiece){
            return this.pieceColor.equals(otherPiece.pieceColor);
        }

        /**
         * Sets the column of the board piece.
         * @param column the new column to set.
         */
        private void setColumn(int column){
            this.column = column;
        }

        /**
         * Sets the row of the board piece.
         * @param row The new row to replace the old row.
         */
        private void setRow(int row){
            this.row = row;
        }

        /**
         * Get the color of this piece.
         * @return returns the color of the piece.
         */
        public Color getPieceColor(){
            return this.pieceColor;
        }



    }

    /**
     * Creates and sets up a new Board. Should work with a range of columns and rows.
     * Columns and rows should be the same and not too large to get a proper visual.
     * Will work with unequal rows and columns but a large difference will not give a good visual.
     * @param appPane The BorderPane to place the board in.
     * @param columns How many Columns in the board.
     * @param rows How many rows in the board.
     */
    public Board(BorderPane appPane, Game game, int rows, int columns){
        //Setup background and the board arrays and visual Board.
        this.columns = columns;
        this.rows = rows;
        grid = new BoardPiece[columns][rows];
        validationGrid = new BoardPiece[columns][rows];
        StackPane fullBoard = new StackPane();
        GridPane backGround = new GridPane();
        backGround.setGridLinesVisible(true);
        backGround.setVgap(1);
        backGround.setHgap(1);
        backGround.setMaxSize(500,500);
        fullBoard.getChildren().add(backGround);
        fullBoard.getChildren().add(gameGrid);
        fullBoard.getChildren().add(overLay);
        overLay.setMaxSize(500,500);
        overLay.setDisable(true);
        appPane.setCenter(fullBoard);
        gameGrid.setMaxSize(500+(2*columns),500 + (2*rows));
        gameGrid.setPrefColumns(columns);
        gameGrid.setPrefRows(rows);
        gameGrid.setHgap(2);
        gameGrid.setVgap(2);
        gameGrid.setPrefTileHeight(500.0/rows);
        gameGrid.setPrefTileWidth(500.0/columns);
        tilePaneHeight  = gameGrid.getTileHeight();
        tilePaneWidth = gameGrid.getTileWidth();
        this.game = game;

        //Adding board pieces to arrays and visual.
        for(int r = 0; r< rows; r++){
            for(int c = 0; c < columns; c++ ){
                Rectangle boardSquare = new Rectangle(( 500.0 / columns), (500.0/rows), Color.TRANSPARENT);
                backGround.add(boardSquare, c, r);
                grid[c][r] = new BoardPiece(Color.TRANSPARENT, c, r);
                validationGrid[c][r] = grid[c][r];
                gameGrid.getChildren().add(r*columns + c, grid[c][r].getCircle());
            }
        }


    }

    /**
     * Sets up a row of board pieces.
     * @param row Which row from 0 to rows-1.
     * @param pieceColor What piece Color. Should not be Color.Transparent but can be any other color value.
     */
    public void initializePlayer(int row, Color pieceColor){
        for(int c = 0; c<columns; c++){
            BoardPiece piece = new BoardPiece(pieceColor, c, row);
            grid[c][row] = piece;
            validationGrid[c][row] = piece;
            gameGrid.getChildren().remove(row*columns + c);
            gameGrid.getChildren().add(row*columns + c, piece.getCircle());
        }

    }

    /**
     * Moves a piece on the board and deletes the old piece.
     * Will not move if pieces are in the same location or off the board.
     * @param fromColumn Column of piece being moved.
     * @param fromRow Row of piece being moved.
     * @param toColumn Column of new location.
     * @param toRow Row of new location.
     */
    public void movePiece(int fromColumn, int fromRow, int toColumn, int toRow){
        //Checking for valid rows and columns, cannot move a clear piece to another piece.
        boolean oldColumn = (fromColumn >= 0) && (fromColumn < columns);
        boolean notEqual = !(fromColumn == toColumn) || !(fromRow == toRow);
        boolean oldRow = (fromRow>=0) && (fromRow<rows);
        boolean newColumn = (toColumn >= 0) && (toColumn < columns);
        boolean newRow = (toRow >= 0) && (toRow < rows);
        boolean validPiece = !grid[fromColumn][fromRow].pieceColor.equals(Color.TRANSPARENT);
        if(oldColumn && notEqual && oldRow && newColumn && newRow && validPiece) {
            grid[toColumn][toRow] = grid[fromColumn][fromRow];
            validationGrid[toColumn][toRow] = grid[fromColumn][fromRow];
            grid[toColumn][toRow].setRow(toRow);
            grid[toColumn][toRow].setColumn(toColumn);


            BoardPiece newPiece = new BoardPiece(Color.TRANSPARENT, fromColumn, fromRow);
            grid[fromColumn][fromRow] = newPiece;
            validationGrid[fromColumn][fromRow] = newPiece;
            gameGrid.getChildren().remove((fromRow * columns) + fromColumn);
            gameGrid.getChildren().add((fromRow * columns) + fromColumn, newPiece.getCircle());
            gameGrid.getChildren().remove((toRow * columns) + toColumn);
            gameGrid.getChildren().add((toRow * columns) + toColumn, grid[toColumn][toRow].getCircle());
        }

    }

    /**
     * Set if we can click the baord or not.
     * @param click true if board should be clicked and false if not.
     */
    public void setCanClick(boolean click){
        this.canClick = click;
    }

    /**
     * See if the board can be clicked or not.
     * @return Return true if the board can be clicked and false if not.
     */
    public boolean getCanClick(){
        return canClick;
    }


    /**
     * Gets the number of rows in the board.
     * @return Returns the number of rows.
     */
    public int getRows(){
        return rows;
    }

    /**
     * Gets the number of columns in the board.
     * @return Returns the number of columns in the board.
     */
    public int getColumns(){
        return columns;
    }

    /**
     * Gets the Board representation.
     * @return Returns an array of board pieces showing the current board.
     */
    public BoardPiece[][] getValidationGrid(){
        return validationGrid;
    }

    /**
     * Returns the pane on top of the board to add any visuals.
     * @return Returns the top of the stack pane to add visuals above the board.
     */
    public Pane getOverlay(){
        return this.overLay;
    }

    /**
     * Gets the tile height of the board.
     * @return Returns the height of each tile in the board.
     */
    public double getTileHeight(){
        return this.tilePaneHeight;
    }

    /**
     * Gets the width of each tile in the board.
     * @return Returns the width of the tiles in the board.
     */
    public double getTileWidth(){
        return this.tilePaneWidth;
    }






}
