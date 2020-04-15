# Maze Solver Redux

We will revisit the maze program to explore alternative maze solving strategies. Recall that a maze is defined by a two-dimensional grid of integers where each number represents either a path (1) or a wall (0) in the maze. We store each maze in a file, where the first line describes the number of rows and columns in the grid (maze).
```
5 6
1 0 0 0 0 1
1 1 1 1 0 1
0 1 0 0 0 1
0 1 1 1 0 1
0 0 0 1 1 1
```

Our goal is to start in the top-left corner of this grid and traverse to the bottom-right corner, traversing only positions that are marked as a path. Valid moves will be those that are within the bounds of the grid and are to cells in the grid marked with a 1.

To ensure our search is complete we must try all options - cells that can possibly lead us to the goal. Currently, the solver uses a stack to store such cell positions. We start by adding to the stack the top-left corner position, which is our only option at the beginning. Then, we repeat the following process till either the stack is empty or the goal is reached:
* remove a position from the stack
* use the position to discover valid (within bounds, not wall, and not visited) neighboring positions and add them to the stack to be processed (use it to discover other positions)

At anytime, the stack contains positions that we have discovered but haven't processed. Effectively, what's in the stack represents the "frontier" of our exploration. The order in which a position gets removed from the "frontier" can be arbitrary because we don't know which one may lead us to goal the fastest. Recall that a stack follows the LIFO (Last In First Out) rule, Using a stack, we always process the most recently discovered position (lastly added to stack) resulting in a specific strategy.

## Animate
Let's animate the maze solver to better understand this "stack" strategy. To animate on the command line we need to clear the console before we output each "frame" to create a "clean slate". A simple animation program is provided in "Animate.java":
```java
public class Animate{
  public static void main(String[] args) throws InterruptedException{
    for (int i=10; i>=0; i--){
      System.out.print("\033\143"); //clear console
      System.out.println(i);
      Thread.sleep(1000);
    }
  }
}
```
You can compile and run this program as follows:
```shell
javac Animate.java
java Animate
```
This program animates a count down timer. The `Thread.sleep(1000);` line pauses the current thread of execution for 1 second (1000 milliseconds) to create the animation effect. You can adjust this number to change the speed of the animation.

To animate your maze solver you can add this "clear console, print and pause" logic to `MazeSolver.java` (line 49):
```java
System.out.print("\033\143"); 
System.out.println(maze);
try{
  Thread.sleep(1000);
}catch(InterruptedException e){}
```

### Task 1

Run your maze solver with `testfile.txt` and `testfile1.txt`, observe, and explain why the animation makes sense:
```shell
javac *.java
java MazeTester
Enter the name of the file containing the maze: testfile1.txt
```

## Expand Strategies
To perform a complete search we must keep track of the "frontier", but we get to choose which frontier position to process next. As discussed earlier, the order in which we remove a position from the "frontier" results in a different strategy. There are only three operations we need to perform on the "frontier": add, remove, and size (so we know when its empty). First, lets abstract this interface (to a "frontier") using a Java Interface `Frontier.java` as follows:
```java
public interface Frontier<T>{
  public int size();
  public void add(T newItem);
  public T remove();
}
```
Then, we can interact with a 'frontier' in our `MazeSolver` through this interface so that we can switch between different implementations of this interface without changing the `MazeSolver`. For instance, we can implement the "stack-based frontier" in `MyStack.java` as follows:
```java
import java.util.LinkedList;

public class MyStack<T> implements Frontier<T>{
  private LinkedList<T> stack;

  public MyStack(){
    stack = new LinkedList<T>();
  }

  public void add(T newItem){
    stack.push(newItem);
  }

  public T remove(){
    return stack.pop();
  }
  
  public int size(){
    return stack.size();
  }
}
```
Each object of this `MyStack` class __is a__ `Frontier` and it has an instance object of the `LinkedList` class. As shown in the source code, a `MyStack` object performs the three operations (add, remove, size as defined in the `Frontier` interface) by turning around and delegating the operations to the `LinkedList` object. Essentially we are adapting an existing class to a new interface.

Change `MazeSolver` to use the Frontier interface and the `MyStack` implementation of the interface as follows:
* change `Deque<Position> stack = new LinkedList<Position>();` to `Frontier<Position> frontier = new MyStack<Position>();`
* change all references to `stack` to `frontier`
* change `push` to `add`
* change `pop` to `remove`
* change `!stack.isEmpty()` to `frontier.size()!=0`

Your `MazeTester` should still run and produce the same output as before. This is called refactoring - restructuring code without changing its behavior. Now, our `MazeSolver` can use any `Frontier` object that implements the `Frontier` interface.

### Task 3
Implement the `MyQueue` class so that it conform to the `Frontier` interface and behaves like a queue, which means when the `remove` method is called it should remove and return the least recently added item. The only change you need to make in `MazeSolver` is `Frontier<Position> frontier = new MyQueue<Position>();`. The rest of the code should remain the same.

Run `MazeSolver` again with animation, observe and describe the difference in behavior between the stack implementation and the queue implementation. Try to print the size of the frontier at each animation step. Do you notice any difference.

## Heuristic search
Instead of __blindly__ searching through the maze, we can try to be optimistic about the fact that a position __geographically closer__ to the goal will more likely lead us on a shorter path to the goal. This is evident in a maze that is empty (all path, no wall). Therefore, we could pick the position closest to the goal to remove from the frontier during the search. This is call Heuristic Search or Informed Search because we are using additional information (heuristics) to help guide our search. More specifically, this is called a best-first search algorithm similar to the [A* (A-star) Algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm)

To implement this algorithm, we need to calculate the distance between each position to the goal. A commonly used measure is the [manhattan distance](https://en.wiktionary.org/wiki/Manhattan_distance). The manhattan distance between `P1(x1, y1)` and `P2(x2, y2)` is defined as `(x1-x2)+(y1-y2)`. Then, we can remove `positions` from the `frontier` according to this distance measure.

### Task 4
Modify the `Position` class to store the distance to the goal. Make `Position` implement the `Comparable` interface by providing a `compareTo` method so that positions can be compared according to their distances to the goal. Implement a new `Frontier` called `AStar` that removes the position with the shortest distance first. Use our binary search tree as the underlying data structure in `AStar` and implement `remove` by removing the node with the least value from the tree (already implemented as a method on the binary search tree).

Run `MazeSolver` again with animation. Observe and describe the difference you see in the result from this new strategy.
