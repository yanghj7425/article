package chapter9.factorymethod;

public class ImplFactory1 implements  ServiceFactory {
    @Override
    public Service getService() {
            return new Implement1();
    }
}
