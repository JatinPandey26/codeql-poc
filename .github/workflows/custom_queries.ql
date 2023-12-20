import semmle.code.java.security.dataflow.DataFlow
import semmle.code.java.security.log.LogInjectionConfig

class SuppressLogInjection extends LogInjectionConfig {
  SuppressLogInjection() {
    // Specify patterns or conditions to identify safe log statements
    this.setSafePattern("logger.info($Format, $*)");
    this.setSafePattern("logger.debug($Format, $*)");
    // Add more patterns as needed
  }

  override predicate isSafe(DataFlow::Node source, DataFlow::Node sink) {
    // Add additional conditions to determine if the data flow is safe
    exists(this.getSafePattern(), source, sink) or
    // Add more conditions as needed
    false
  }
}

// Exclude results related to CWE-117
from SuppressLogInjection
where not exists(DataFlow::PathNode source, DataFlow::PathNode sink |
  SuppressLogInjection.hasFlowPath(source, sink)
)
select SuppressLogInjection
