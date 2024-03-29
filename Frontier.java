public interface Frontier<T>{
  public int size();
  public void add(T newItem);
  public T remove();
  public boolean contains(T newItem);
}