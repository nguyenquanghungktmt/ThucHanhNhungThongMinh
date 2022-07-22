public class Mutex {
    /* Chương trình khởi tạo 2 luồng cùng truy cập trực tiếp và thay đổi giá trị
   của đối tượng Counter */
   public static void main(String[] args) {
   Counter c = new Counter();
   try {
   Thread thread1 = new Thread(new Runnable() {
   @Override
   public void run() {
   for (int i = 0; i < 20000; i++) {
   c.increment();
   }
   }
   });
   Thread thread2 = new Thread(new Runnable() {
   @Override
   public void run() {
   for (int i = 0; i < 20000; i++) {
   c.increment();
   }
   }
   });
   thread1.start();
   thread2.start();
   thread1.join();
   thread2.join();
    /* In ra kết quả cuối cùng của biến bên trong đối tượng */
   System.out.println("Counter = " + c.getCount());
   } catch (Exception e) {
    e.printStackTrace();
}
}
}
class Counter {
private int count;
 /* Từ khóa synchronized đóng vai trò như khóa mutex để đồng bộ dữ liệu giữa 2 
luồng */
public synchronized void increment() {
    count++;
}
public int getCount() {
    return count;
    }
}
   