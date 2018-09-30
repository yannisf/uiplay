package fraglab.library.it.container.embedded;

public interface EmbeddedServer {

    Integer PORT = 52450;

    void start() throws Exception;

    void stop() throws Exception;
}
