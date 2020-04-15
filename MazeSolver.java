import java.util.LinkedList;
import java.util.Deque;

/**
 * MazeSolver attempts to recursively traverse a Maze. The goal is to get from the
 * given starting position to the bottom right, following a path of 1's. Arbitrary
 * constants are used to represent locations in the maze that have been TRIED
 * and that are part of the solution PATH.
 *
 * @author Lewis and Chase
 * @version 4.0
 */
public class MazeSolver{
  private Maze maze;

  /**
   * Constructor for the MazeSolver class.
   */
  public MazeSolver(Maze maze){
    this.maze = maze;
  }

  /**
   * Traverse the maze using a stack to track cells, that need to be explored.
   * Inserts special characters indicating locations that have been TRIED and
   * that eventually become part of the solution PATH.
   *
   * @return true if the maze has been solved
   */
  public boolean traverse(){
    boolean done = false;
    int row, column;
    Position pos = new Position();
    //Deque<Position> stack = new LinkedList<Position>();
    Frontier<Position> frontier = new MyStack<Position>();
    //stack.push(pos);
    frontier.add(pos);
    
    while (!(done) && frontier.size() != 0){
      pos = frontier.remove();
      if (pos.getx() == maze.getRows()-1 && pos.gety() == maze.getColumns()-1){
        done = true;  // the maze is solved
      }else{
        push_new_pos(pos.getx() - 1,pos.gety(), frontier); 
        push_new_pos(pos.getx() + 1,pos.gety(), frontier);
        push_new_pos(pos.getx(),pos.gety() - 1, frontier);
        push_new_pos(pos.getx(),pos.gety() + 1, frontier); 
      }
      maze.tryPosition(pos.getx(),pos.gety());  // this cell has been tried
      //System.out.println(maze);
    }

    return done;
  }

  /**
   * Attempts to recursively traverse the maze. Inserts special
   * characters indicating locations that have been TRIED and that
   * eventually become part of the solution PATH.
   *
   * @param row row index of current location
   * @param column column index of current location
   * @return true if the maze has been solved
   */
   public boolean traverse_rc(int row, int column){
     return false;
   }

  /**
   * Push a new attempted move onto the stack
   * @param x represents x coordinate
   * @param y represents y coordinate
   * @param stack the working stack of moves within the grid
   */
  private void push_new_pos(int x, int y, Frontier<Position> frontier){
    Position npos = new Position();
    npos.setx(x);
    npos.sety(y);
    if (maze.validPosition(x,y)){
      frontier.add(npos);
    }
  }
}
