package logic;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {
	    public Pool() {

	        ExecutorService hilosModificacion = Executors.newFixedThreadPool(10);
	    }
}