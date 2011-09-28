package org.nickgrealy.commons.lang;

import org.nickgrealy.commons.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Faciliates programmatically executing a process.
 *
 * @author nickgrealy@gmail.com
 */
public final class CommandRunnerUtil {

    private CommandRunnerUtil() {
    }

    public static final CommandRunnerUtil INSTANCE = new CommandRunnerUtil();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static final int EXIT_VAL_SUCCESS = 0;
    public static final String SPACE = " ";

    public boolean execProcess(String[] command) {
        try {
            logger.info("Executing process: {}", StringUtil.concat(SPACE, command));
            Process proc = Runtime.getRuntime().exec(command);
            new StreamReader(proc.getInputStream(), System.out).start();
            new StreamReader(proc.getErrorStream(), System.err).start();
            int exitVal = proc.waitFor();
            boolean success = EXIT_VAL_SUCCESS == exitVal;
            if (!success) {
                logger.error("Process failed: {}", StringUtil.concat(SPACE, command));
            }
            return success;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    class StreamReader extends Thread {

        private byte[] newline = "\n".getBytes();
        private Scanner in;
        private OutputStream out;

        StreamReader(InputStream in, OutputStream out) {
            this.in = new Scanner(in);
            this.out = out;
        }

        @Override
        public void run() {
            try {
                while (in.hasNext()) {
                    out.write(in.nextLine().getBytes());
                    out.write(newline);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                in.close();
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
