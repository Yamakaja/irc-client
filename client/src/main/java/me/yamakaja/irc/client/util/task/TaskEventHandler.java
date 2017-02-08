package me.yamakaja.irc.client.util.task;

import java.util.function.Predicate;

/**
 * Created by Yamakaja on 08.02.17.
 */
class TaskEventHandler<E> extends Task {

    private Predicate<E> predicate;

    TaskEventHandler(Predicate<E> predicate) {
        super(null);
        this.predicate = predicate;
    }

    public Predicate<E> getPredicate() {
        return predicate;
    }

}
