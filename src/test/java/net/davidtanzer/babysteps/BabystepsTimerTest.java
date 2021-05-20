package net.davidtanzer.babysteps;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

class BabystepsTimerTest {

    private BabystepsTimer babystepsTimer = new BabystepsTimer();

    @Test
    void shouldDisplayCountDownAfterFewSeconds() throws InterruptedException {
        babystepsTimer.launch();
        clickStartLink();
        waitForSeconds(3);
        stopRunning();
        String secondsDisplayed = babystepsTimer.getTimerPane().getText();
        assertThat(secondsDisplayed).contains("01:57");

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
