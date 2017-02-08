package me.yamakaja.irc.client.util.task;

import com.google.inject.Inject;
import me.yamakaja.irc.client.IRCClient;
import net.lahwran.fevents.Event;
import net.lahwran.fevents.EventHandler;
import net.lahwran.fevents.Listener;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Yamakaja on 08.02.17.
 */
public class TaskChainFactory implements Listener {

    private static Method methodOnEvent = null;

    static {
        try {
            methodOnEvent = TaskChainFactory.class.getMethod("onEvent", Event.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private IRCClient client;
    private Map<Class<? extends Event>, List<TaskChain>> chains = new ConcurrentHashMap<>();
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public TaskChainFactory() {

    }

    public TaskChain getChain() {
        return new TaskChain(this);
    }

    @EventHandler
    public void onEvent(Event event) {
        chains.get(event.getClass()).forEach(chain -> {
            Task current = chain.getCurrentTask();
            if (current instanceof TaskEventHandler) {
                if(((TaskEventHandler<Event>) current).getPredicate().test(event)) {
//                    chain.
                }
            }
        });
    }

    public void execute(TaskChain tasks) {

    }

}
