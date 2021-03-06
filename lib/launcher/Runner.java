public class Runner implements Runnable {
    public interface OutputListener {
        void print(String message);
    }
    
    private boolean draw = false;
    private boolean error = false;
    private boolean silent = false;
    private String errorLog = "";
    private Thread thread;
    private OutputListener listener;
    
    private long start;
    
    public Runner(OutputListener listener) {
        this.listener = listener;
    }
    
    public void run() {
        String loadString = "";
                
        while (draw) {
            if (loadString == "") loadString = "   ";
            else if (loadString == "   ") loadString = ".  ";
            else if (loadString == ".  ") loadString = ".. ";
            else if (loadString == ".. ") loadString = "...";
            else if (loadString == "...") loadString = "   ";
            
            listener.print(loadString + "\b\b\b");
                    
            try { thread.sleep(250); } catch (Exception e) { }
        }
    }
    
    public void start(boolean silent) {
        start = System.currentTimeMillis();
        draw = true;
        thread = new Thread(this);
        thread.start();
        this.silent = silent;
    }
    
    public void stop() {
        thread.interrupt();
        
        if (silent) return;
        
        if (error) {
            listener.print("\n" + errorLog);
            listener.print("\nBUILD FAILED\n");
        } else {
            listener.print("\nBUILD SUCCESFULL\n");
        }
        
        long end = (System.currentTimeMillis() - start) / 1000;
        
        if (end > 59) {
            end = end / 60;
            if (end > 59) listener.print("Total time: " + end / 60 + " hours\n");
            else listener.print("Total time: " + end + " minutes\n");
        } else {
            listener.print("Total time: " + end + " seconds\n");
        }
        
        System.exit(error ? -1 : 0);
    }
    
    public void warn(String msg) {
        listener.print("\n" + msg + "\n");
    }

    public void error(Exception e) {
        error = true;
        errorLog += e.getMessage() + "\n";
    }

    public void finish() {
        draw = false;
        thread.interrupt();
        if (error) stop();
    }
}