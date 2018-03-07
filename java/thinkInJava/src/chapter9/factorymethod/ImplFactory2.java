package chapter9.factorymethod;

public class ImplFactory2 implements ServiceFactory {
    @Override
    public Service getService() {
            return new Implement2();
    }
}
