import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.Thread.UncaughtExceptionHandler;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
 
    private static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER = new UncaughtExceptionHandler() {
 
        /** キャッチできない場合は、ログに書き込む */
        @Override public void uncaughtException(Thread thread, Throwable throwable) {
            //ログ出力
            log.error("キャッチできない例外発生", throwable);
            
            // TODO:ここでメール送信する。
        }
 
    };
 
    public static void main(String[] args) {
        
        // シャットダウンフックの登録（終了時に必ず出る）
        // 用途：必ず実施したい終了処理（DBの切断とか）
        Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    log.info("shutdownHook");
                }
        });
        
        // UncaughtExceptionHandlerの登録
        // catchされないExceptionが発生した場合に処理を行うHandlerを登録する。
        Thread.currentThread().setUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
		throw new RuntimeException("何らかの処理されないエラーが発生しました。");
    }
 
}