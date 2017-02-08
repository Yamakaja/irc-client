package me.yamakaja.irc.client.network.util.task;

import net.lahwran.fevents.Event;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

/**
 * Created by Yamakaja on 08.02.17.
 * <p>
 * Used to create semi-continuous code
 * <p>
 * Follows builder pattern
 */
public class TaskChain {

    private TaskChainFactory factory;

    private Queue<Task> tasks = new ConcurrentLinkedQueue<>();

    TaskChain(TaskChainFactory factory) {
        this.factory = factory;
    }

    Task getCurrentTask() {
        return tasks.peek();
    }

    /**
     * Add a runnable to the chain for execution
     *
     * @param runnable The runnable to execute
     * @return The current TaskChain to fulfill the builder pattern
     */
    public TaskChain execute(Runnable runnable) {
        tasks.add(new Task(runnable));
        return this;
    }

    /**
     * Halt chain execution until {@param predicate} is fulfilled
     *
     * @param type      The event class to listen for
     * @param predicate The predicate which determines whether or not the event should continue execution
     * @param <T>       The type of the event to wait for
     * @return The current TaskChain to fulfill the builder pattern
     */
    public <T extends Event> TaskChain event(Class<T> type, Predicate<T> predicate) {
        tasks.add(new TaskEventHandler<>(predicate));
        return this;
    }

    /**
     * Start to asynchronously execute the task chain
     * This method will return immediately
     */
    public void start() {
//        factory.start(this);
    }

}
