package perishing.constraint.concurrent.flow;

import lombok.RequiredArgsConstructor;
import perishing.constraint.concurrent.ExecutorIndustryOptions;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static perishing.constraint.concurrent.flow.RunState.TERMINATED;
import static perishing.constraint.concurrent.flow.RunState.TIDYING;

@RequiredArgsConstructor
abstract class AbstractService {

    protected final FlowExecutorIndustry industry;

    protected ControlDetails current() {
        return industry.stateService().snapshoot();
    }

    protected Control control() {
        return industry.control;
    }

    protected Metrics metrics() {
        return industry.metrics;
    }

    protected ExecutorIndustryOptions options() {
        return industry.options();
    }

    protected BlockingQueue<Runnable> tasks() {
        return industry.workerService().tasks();
    }

    protected Condition termination() {
        return industry.termination;
    }

    protected ReentrantLock mainLock() {
        return industry.mainLock;
    }

    void tryTerminate() {
        for (; ; ) {
            ControlDetails hereState = current();
            if (hereState.isRunning() && hereState.alreadyTidying()) {
                return;
            }

            if (hereState.notStopped() && !tasks().isEmpty()) {
                return;
            }

            // 如果当前worker数不为0，则尝试关闭最多一个worker
            if (hereState.existsWorker()) {
                industry.workerService().interruptIdleWorkers(true);
                return;
            }

            // 只有当worker数量为0的时候才会执行
            final ReentrantLock mainLock = mainLock();
            mainLock.lock();
            try {
                if (TIDYING.tryUpdate(hereState, 0)) {
                    try {
                        // TODO: Call Hooks
                    } finally {
                        TERMINATED.tryUpdate(hereState, 0);
                        termination().signalAll();
                    }
                }
            } finally {
                mainLock.unlock();
            }
        }
    }

}
