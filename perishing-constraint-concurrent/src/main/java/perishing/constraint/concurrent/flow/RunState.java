package perishing.constraint.concurrent.flow;

import static perishing.constraint.concurrent.flow.Constants.COUNT_BITS;

/**
 * 高3位的状态值，按从运行到终止顺序排列的
 */
enum RunState {

    /**
     * 线程池处理任务队列：✔️
     * 任务队列接收新任务：✔️
     */
    RUNNING(-1),

    /**
     * 线程池处理任务队列：✔️
     * 任务队列接收新任务：✖️
     */
    SHUTDOWN(0),

    /**
     * 线程池处理任务队列：✖️
     * 任务队列接收新任务：✖️
     */
    STOP(1),

    /**
     * 线程池处理任务队列：✖️
     * 任务队列接收新任务：✖️
     */
    TIDYING(2),

    /**
     * 线程池处理任务队列：✖️
     * 任务队列接收新任务：✖️
     */
    TERMINATED(3);

    final int code;

    RunState(int value) {
        this.code = runState(value);
    }

    /**
     * 通过指定运行状态值和worker数量计算状态控制值
     *
     * @param workers worker数量
     * @return 状态控制值
     */
    int controlState(int workers) {
        return code | workers;
    }

    boolean tryUpdate(ControlDetails expected, int workers) {
        return expected.
                control().
                compareAndSet(expected.code, controlState(workers));
    }

    boolean tryUpdate(ControlDetails expected) {
        return expected.
                control().
                compareAndSet(expected.code, controlState(expected.workers()));
    }

    boolean already(ControlDetails details) {
        return details.code >= code;
    }

    // 私有帮助方法

    /**
     * 通过指定运行状态数值转变为控制运行状态值
     *
     * @param runValue 运行值
     * @return 控制运行状态值
     */
    static int runState(int runValue) {
        return runValue << COUNT_BITS;
    }

}
