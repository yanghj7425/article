package chapter9.factorymethod;

public class Factories {
    public static void serviceComsumer(ServiceFactory serviceFactory){
        Service s = serviceFactory.getService();
        s.method1();
        s.method2();
    }
    public static void main(String[] args) {
        serviceComsumer(new ImplFactory1());
        serviceComsumer(new ImplFactory2());
    }
}
