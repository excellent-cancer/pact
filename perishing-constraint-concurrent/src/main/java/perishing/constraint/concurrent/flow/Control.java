package perishing.constraint.concurrent.flow;

import java.util.concurrent.atomic.AtomicInteger;

import static perishing.constraint.concurrent.flow.RunState.RUNNING;

/**
 * {@link FlowExecutorIndustry}的状态控制对象，主要分为运行状态和worker数量
 */
class Control {

    // 私有变量

    private final AtomicInteger code = new AtomicInteger(RUNNING.controlState(0));

    void reduceWorker() {
        code.addAndGet(-1);
    }

    ControlDetails here() {
        return new ControlDetails(this);
    }

    boolean compareAndSet(int expected, int newCode) {
        return code.compareAndSet(expected, newCode);
    }

    AtomicInteger code() {
        return code;
    }

}
