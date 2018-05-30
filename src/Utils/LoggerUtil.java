package Utils;

public class LoggerUtil {
    public static void log(Object o){
        System.out.println("\n------------------------------------");
        StackTraceElement[] trace = new Throwable().getStackTrace();
        for (StackTraceElement traceElement : trace)
            System.out.println("\t---at " + traceElement);
        System.out.println(o.toString());
        System.out.println("------------------------------------\n");
    }
}
