import tree.LinkedBinarySearchTree;

public class AStar<T> implements Frontier<T>{
  private LinkedBinarySearchTree<T> tree;

  public AStar(){
    tree = new LinkedBinarySearchTree<T>();
  }

  public void add(T newItem){
    tree.addElement(newItem);
  }

  public T remove(){
    return tree.removeMin();
  }
  
  public int size(){
    return tree.size();
  }

  public boolean contains(T newItem){
    return tree.contains(newItem);
  }
}