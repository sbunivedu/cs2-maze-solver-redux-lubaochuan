import java.util.LinkedList;

public class MyQueue<T> implements Frontier<T>{
  private LinkedList<T> queue;

  public MyQueue(){
    queue = new LinkedList<T>();
  }

  public void add(T newItem){
    // TO BE IMPLEMENTED
  }

  public T remove(){
    // TO BE IMPLEMENTED
    return null;
  }

  public int size(){
    // TO BE IMPLEMENTED
    return 0;
  }
}