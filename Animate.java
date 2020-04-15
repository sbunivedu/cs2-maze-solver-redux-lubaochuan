public class Animate{
  public static void main(String[] args){
    for (int i=10; i>=0; i--){
      System.out.print("\033\143"); //clear console
      System.out.println(i);
      try{
        Thread.sleep(1000);
      }catch(InterruptedException e){}
    }
  }
}
