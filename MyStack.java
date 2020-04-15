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