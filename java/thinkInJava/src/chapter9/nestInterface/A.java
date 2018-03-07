package chapter9.nestInterface;

public class A {
    private interface D{
        void f();
    }
    public class DImp1 implements D{
        @Override
        public void f() {
            System.out.println("DImp1");
        }
    }
    private class DImp2 implements D{
       @Override
        public void f() {
           System.out.println("DImp@");
        }
    }
    private D  dRef;
    public void reciveD(D d){
        dRef = d;
        dRef.f();
    }
    public D getD(){
        return new DImp2();
    }
    public static void main(String[] args) {
        A  a =  new A();
       a.reciveD(a.getD());
    }
}
