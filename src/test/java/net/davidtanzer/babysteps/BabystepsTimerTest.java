package net.davidtanzer.babysteps;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class BabystepsTimerTest {

    private static final String START_LINK = "<a href=\"command://start\"><font color=\"#555555\">Start</font></a>";
    private static final String QUIT_LINK = "<a href=\"command://quit\"><font color=\"#555555\">Quit</font></a>";
    private static final String STOP_LINK = "<a href=\"command://stop\"><font color=\"#555555\">Stop</font></a>";
    private static final String RESET_LINK = "<a href=\"command://reset\"><font color=\"#555555\">Reset</font></a>";
    private BabystepsTimer babystepsTimer = new BabystepsTimer();

    @Test
    void shouldDisplayCountDownAfterFewSeconds() throws InterruptedException {
        babystepsTimer.launch();
        clickStartLink();
        waitForSeconds(3);
        stopRunning();
        String secondsDisplayed = babystepsTimer.getTimerPane().getText();
        assertThat(secondsDisplayed).contains("01:57");
        assertThat(secondsDisplayed).contains(STOP_LINK);
        assertThat(secondsDisplayed).contains(RESET_LINK);
        assertThat(secondsDisplayed).contains(QUIT_LINK);
    }

    @Test
    void shouldDisplayInitialTimeAndStartAndQuitLink() throws InterruptedException {
        babystepsTimer = new BabystepsTimer();
        babystepsTimer.launch();
        String secondsDisplayed = babystepsTimer.getTimerPane().getText();
        assertThat(secondsDisplayed).contains("02:00");
        assertThat(secondsDisplayed).contains(START_LINK);
        assertThat(secondsDisplayed).contains(QUIT_LINK);
    }

    @Test
    void shouldDisplayInitialTimeAfterStop() throws InterruptedException {
        babystepsTimer = new BabystepsTimer();
        babystepsTimer.launch();
        clickStartLink();
        waitForSeconds(3);
        clickStopLink();
        stopRunning();
        String secondsDisplayed = babystepsTimer.getTimerPane().getText();
        assertThat(secondsDisplayed).contains("02:00");
        assertThat(secondsDisplayed).doesNotContain("Reset");
        assertThat(secondsDisplayed).contains(START_LINK);
        assertThat(secondsDisplayed).contains(QUIT_LINK);
    }

    @Test
    public void shouldChangeBackground_5_SecondsBeforeEndOfCycle() throws InterruptedException {
        babystepsTimer = new BabystepsTimer();
        BabystepsTimer.setSECONDS_IN_CYCLE(8);
        babystepsTimer.launch();
        clickStartLink();
        waitForSeconds(8-5);
        stopRunning();
        String secondsDisplayed = babystepsTimer.getTimerPane().getText();
        assertThat(secondsDisplayed).contains("#ffffff");
    }


    private void clickStopLink() {
        HyperlinkListener[] listeners = babystepsTimer.getTimerPane().getListeners(HyperlinkListener.class);
        JFrame timerFrame = babystepsTimer.getTimerFrame();
        URL url = null;
        HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(timerFrame, HyperlinkEvent.EventType.ACTIVATED, url, "command://stop");
        listeners[0].hyperlinkUpdate(hyperlinkEvent);
    }

    private void clickStartLink() {
        HyperlinkListener[] listeners = babystepsTimer.getTimerPane().getListeners(HyperlinkListener.class);
        JFrame timerFrame = babystepsTimer.getTimerFrame();
        URL url = null;
        HyperlinkEvent hyperlinkEvent = new HyperlinkEvent(timerFrame, HyperlinkEvent.EventType.ACTIVATED, url, "command://start");
        listeners[0].hyperlinkUpdate(hyperlinkEvent);
    }

    private void stopRunning() {
        babystepsTimer.timerRunning = false;
    }

    private void waitForSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds* 1000 + 500);
    }
}
