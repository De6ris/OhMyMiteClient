package com.github.debris.ommc.event.tick;

import com.github.debris.ommc.config.MainConfig;
import com.github.debris.ommc.task.AbstractTimedTask;
import com.github.debris.ommc.task.ClientTask;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Minecraft;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaskManager implements IClientTickHandler {
    private static final TaskManager Instance = new TaskManager();

    public static TaskManager getInstance() {
        return Instance;
    }

    private TaskManager() {
    }

    private final LinkedList<ClientTask<?>> taskQueue = new LinkedList<>();

    private final List<AbstractTimedTask> timedTasks = new ArrayList<>();

    public void addTaskToQueue(ClientTask<?> task) {
        if (task != null) {
            this.taskQueue.add(task);
        }
    }

    public void addTimedTask(AbstractTimedTask task) {
        if (task != null) {
            this.timedTasks.add(task);
        }
    }

    @Override
    public void onClientTick(Minecraft mc) {
        for (int i = 0; i < MainConfig.TasksPerTick.getIntegerValue(); i++) {
            ClientTask<?> poll = this.taskQueue.poll();
            if (poll != null && poll.shouldExecute(mc)) {
                poll.execute(mc);
            }
        }
        this.timedTasks.removeIf(abstractTimedTask -> {
            abstractTimedTask.onClientTick(mc);
            if (abstractTimedTask.shouldExecute(mc)) {
                abstractTimedTask.execute(mc);
                return true;
            } else {
                return false;
            }
        });
    }
}
