package nl.tudelft.oopp.group43.project;

import org.evosuite.runtime.FalsePositiveException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ViolatedAssumptionAnswer implements Answer<Object> {
    public ViolatedAssumptionAnswer() {
    }

    /**
     * return the answer.
     *
     * @param invocation the mock system.
     * @return the result of answer.
     * @throws Throwable can't mock some class.
     */
    public Object answer(InvocationOnMock invocation) throws Throwable {
        if (invocation.getMethod().getReturnType().equals(Void.TYPE)) {
            return null;
        } else {
            throw new FalsePositiveException("Mock call to " + invocation.getMethod().getName() + " which was not presented when the test was generated");
        }
    }
}
