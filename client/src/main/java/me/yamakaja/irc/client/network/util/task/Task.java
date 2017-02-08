package me.yamakaja.irc.client.network.util.task;

/**
 * Created by Yamakaja on 08.02.17.
 */
class Task {

    private Runnable runnable;

    Task(Runnable runnable) {
        this.runnable = runnable;
    }

    public Runnable getRunnable() {
        return runnable;
    }

}
