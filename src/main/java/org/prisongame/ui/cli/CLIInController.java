package org.prisongame.ui.cli;

import java.util.Scanner;
import java.util.concurrent.SubmissionPublisher;

public class CLIInController extends SubmissionPublisher<String> implements Runnable {
    Scanner scanner = new Scanner(System.in);
    Boolean running = true;
    Boolean enabled = true;

    @Override
    public void run() {
        while (running) {
            if(enabled) submit(scanner.nextLine());
        }
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}
