package com.thomsonreuters.codes.codesbench.quality.utilities.robot;

import com.thomsonreuters.codes.codesbench.quality.utilities.dateAndTime.DateAndTimeUtils;
import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotUtils
{
    private static Robot robot = null;

    public static Robot getRobot()
    {
        if(robot == null)
        {
            try
            {
                robot = new Robot();
                return robot;
            }
            catch (AWTException e)
            {
                e.printStackTrace();
                Assertions.fail("Robot could not control the mouse.");
            }
        }
        return robot;
    }

    public static void leftClickWithRobot()
    {
        getRobot().mousePress(InputEvent.BUTTON1_DOWN_MASK);
        getRobot().mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void ctrlCUsingRobot()
    {
        try
        {
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_C);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_C);
        }
    }

    public static void ctrlVUsingRobot()
    {
        try
        {
            DateAndTimeUtils.takeNap(DateAndTimeUtils.THREE_SECONDS);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_V);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_V);
        }
    }

    public static void joinParagraphUsingRobot()
    {
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_J);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_J);
        }
    }

    public static void pasteUsingRobotAction()
    {
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_V);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_CONTROL);
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_V);
        }
    }

    public static void pressHomeUsingRobot()
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_HOME);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_HOME);
        }
    }

    public static void pressDownArrowUsingRobot()
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_DOWN);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_DOWN);
        }
    }

    public static void altAUsingRobot()
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_ALT);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_A);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_ALT);
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_A);
        }
    }

    public static void altBackQuoteUsingRobot()
    {
        try
        {
            RobotUtils.getRobot().keyPress(KeyEvent.VK_ALT);
            RobotUtils.getRobot().keyPress(KeyEvent.VK_BACK_QUOTE);
        }
        finally
        {
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_ALT);
            RobotUtils.getRobot().keyRelease(KeyEvent.VK_BACK_QUOTE);
        }
    }

    public static void resetMouseToUpperLeft()
    {
        getRobot().mouseMove(0, 0);
    }

    public static void moveMouse(int x, int y)
    {
        getRobot().mouseMove(x, y);
    }

    public static void delayOneSecond()
    {
        delayHelper(DateAndTimeUtils.ONE_SECOND);
    }

    public static void delayFifteenSeconds()
    {
        delayHelper(DateAndTimeUtils.FIFTEEN_SECONDS);
    }

    public static void delayHalfSecond()
    {
        delayHelper(DateAndTimeUtils.HALF_SECOND);
    }

    public static void delayFiveSeconds()
    {
        delayHelper(DateAndTimeUtils.FIVE_SECONDS);
    }

    public static void delayTenSeconds()
    {
        delayHelper(DateAndTimeUtils.TEN_SECONDS);
    }
    private static void delayHelper(int dateAndTimeMilliseconds)
    {
        getRobot().delay(dateAndTimeMilliseconds);
    }

    public static void shiftDownUsingRobot()
    {
        DateAndTimeUtils.takeNap(DateAndTimeUtils.ONE_SECOND);
        for (int i = 1; i <= 3; i++) {
            try {
                RobotUtils.getRobot().keyPress(KeyEvent.VK_SHIFT);
                RobotUtils.getRobot().keyPress(KeyEvent.VK_DOWN);
            } finally {
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_SHIFT);
                RobotUtils.getRobot().keyRelease(KeyEvent.VK_DOWN);
            }
        }
    }
}