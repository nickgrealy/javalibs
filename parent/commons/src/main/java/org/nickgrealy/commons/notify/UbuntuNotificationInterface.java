package org.nickgrealy.commons.notify;

import org.nickgrealy.commons.lang.CommandRunnerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Requires "notify-send".<br/>
 * <a href="http://www.galago-project.org/specs/notification/">http://www.galago-project.org/specs/notification/</a><br/>
 * <code>sudo apt-get install libnotify-bin</code><br/>
 * <ul>
 * <li>-u, --urgency=LEVEL               Specifies the urgency level (low, normal, critical).</li>
 * <li>-t, --expire-time=TIME            Specifies the timeout in milliseconds at which to expire the notification.</li>
 * <li>-i, --icon=ICON[,ICON...]         Specifies an icon filename or stock icon to display.</li>
 * <li>-c, --category=TYPE[,TYPE...]     Specifies the notification category.</li>
 * <li>-h, --hint=TYPE:NAME:VALUE        Specifies basic extra data to pass. Valid types are int, double, string and byte.</li>
 * <li>-v, --version                     Version of the package.</li>
 * </ul>
 *
 * @author nickgrealy@gmail.com
 */
public class UbuntuNotificationInterface {

    private static final Logger logger = LoggerFactory.getLogger(UbuntuNotificationInterface.class);

    private static final CommandRunnerUtil COMMAND_RUNNER = CommandRunnerUtil.INSTANCE;

    public static final String NOTIFY_SEND_CMD = "notify-send";
    public static final String[] NOTIFY_SEND_ARGS = {"-u", "-t", "-i"};
    public static final int EXIT_VAL_SUCCESS = 0;
    public static final String SPACE = " ";
    public static long NOTIFY_TIMEOUT = 5000;

    public static void main(String[] args) throws Exception {
        Thread.sleep(NOTIFY_TIMEOUT);
        if (args.length == 2) {
            //ResourceUtils.getFile("classpath:mail_icon.jpg")
            notify(null, 0L, IconEnum.info, "New Mail", "<i><a href='#'>Unread</a> (123)</i>");
        } else {
            logger.info("Args must be == 2 in length! args.length={}", args.length);
        }
    }

    /**
     * Also e.g. /usr/share/pixmaps/*.xpm/*.png
     */
    enum IconEnum {
        info
    }

    enum UrgencyEnum {
        low, normal, critical
    }


    public static void notify(UrgencyEnum urgency, Long expireTimeMillis, IconEnum icon, String header, String message) {
        String[] command = notifySendCommandBuilder(urgency, expireTimeMillis, icon, header, message);
        COMMAND_RUNNER.execProcess(command);
    }

    public static void notify(UrgencyEnum urgency, Long expireTimeMillis, File icon, String header, String message) {
        String[] command = notifySendCommandBuilder(urgency, expireTimeMillis, icon, header, message);
        COMMAND_RUNNER.execProcess(command);
    }

    public static void notify(String header, String message) {
        notify(UrgencyEnum.low, 2000L, IconEnum.info, header, message);
    }

    /* utility methods */

    private static String[] notifySendCommandBuilder(UrgencyEnum urgency, Long expireTimeMillis, Object icon, String header, String message) {
        Object[] params = {urgency, expireTimeMillis, icon};
        List<String> command = new LinkedList<String>();
        command.add(NOTIFY_SEND_CMD);
        for (int i = 0; i < NOTIFY_SEND_ARGS.length; i++) {
            if (params[i] != null) {
                command.add(NOTIFY_SEND_ARGS[i]);
                command.add(String.valueOf(params[i]));
            }
        }
        if (header != null) {
            command.add(header);
        }
        if (message != null) {
            command.add(message);
        }
        return command.toArray(new String[command.size()]);
    }
}
