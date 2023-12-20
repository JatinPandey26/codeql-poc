import semmle.code.java.security.dataflow.DataFlow
import semmle.code.java.security.log.LogInjectionConfig
import semmle.code.java.dataflow.TaintTracking

class SuppressLogInjection extends LogInjectionConfig {
  SuppressLogInjection() {
    // Specify patterns or conditions to identify safe log statements
    this.setSafePattern("logger.info($Format, $*)");
    this.setSafePattern("logger.debug($Format, $*)");
    this.setSafePattern("@Slf4j.*($*)"); // Include the actual annotation used in your codebase
    // Add more patterns as needed
  }

  override predicate isSafe(DataFlow::Node source, DataFlow::Node sink) {
    // Add additional conditions to determine if the data flow is safe
    exists(this.getSafePattern(), source, sink) or
    // Add more conditions as needed
    false
  }
}

from SuppressLogInjection
select SuppressLogInjection
