package perishing.constraint.concurrent.flow;

import perishing.constraint.concurrent.ExecutorIndustry;
import perishing.constraint.concurrent.RejectedIndustryHandler;
import perishing.constraint.concurrent.StateExecutorService;

import java.util.concurrent.RejectedExecutionException;

final class RejectedHandlers {

    static class CallerRunsPolicy implements RejectedIndustryHandler {

        @Override
        public void rejectedExecution(Runnable r, ExecutorIndustry industry) {
            final StateExecutorService service = industry.stateService();
            if (!service.isShutdown()) {
                r.run();
            }
        }
    }

    static class AbortPolicy implements RejectedIndustryHandler {


        @Override
        public void rejectedExecution(Runnable r, ExecutorIndustry industry) {
            throw new RejectedExecutionException();
        }
    }

    static class DiscardPolicy implements RejectedIndustryHandler {

        @Override
        public void rejectedExecution(Runnable r, ExecutorIndustry industry) {

        }
    }

    static class DiscardOldestPolicy implements RejectedIndustryHandler {

        @Override
        public void rejectedExecution(Runnable r, ExecutorIndustry industry) {
            if (!industry.originalService().isShutdown()) {
                industry.workerService().tasks().poll();
                industry.originalService().execute(r);
            }
        }
    }

}
