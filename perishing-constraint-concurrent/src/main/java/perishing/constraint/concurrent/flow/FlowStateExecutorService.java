package perishing.constraint.concurrent.flow;

import lombok.RequiredArgsConstructor;
import perishing.constraint.concurrent.StateExecutorService;

@RequiredArgsConstructor
public class FlowStateExecutorService implements StateExecutorService {

    private final FlowExecutorIndustry industry;

    private final Control control;

    @Override
    public ControlDetails snapshoot() {
        return control.here();
    }

}
