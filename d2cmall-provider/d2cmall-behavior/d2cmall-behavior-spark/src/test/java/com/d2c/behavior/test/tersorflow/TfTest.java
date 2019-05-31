package com.d2c.behavior.test.tersorflow;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

public class TfTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() throws Exception {
        logger.info("开始执行测试....");
        try (Graph g = new Graph()) {
            final String value = "Hello from " + TensorFlow.version();
            // Construct the computation graph with a single operation, a
            // constant
            // named "MyConst" with a value "value".
            try (Tensor t = Tensor.create(value.getBytes("UTF-8"))) {
                // The Java API doesn't yet include convenience functions for
                // adding operations.
                g.opBuilder("Const", "MyConst").setAttr("dtype", t.dataType()).setAttr("value", t).build();
            }
            // Execute the "MyConst" operation in a Session.
            try (Session s = new Session(g); Tensor output = s.runner().fetch("MyConst").run().get(0)) {
                System.out.println(new String(output.bytesValue(), "UTF-8"));
            }
        }
    }

}
