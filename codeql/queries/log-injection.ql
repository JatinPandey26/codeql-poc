
import java
import semmle.code.java.dataflow.TaintTracking
import semmle.code.java.dataflow.TaintTracking::Configuration

class LogInjection extends TaintTracking::Configuration {
  LogInjection() {
    this = "LogInjection"
  }

  override predicate isSource(DataFlow::Node source) {
    source.asExpr() instanceof MethodInvocation and
    exists(MethodAccess logAccess |
      logAccess.getMethod().getName() = "info" and
      logAccess.getArgument(0) = source.asExpr()
    )
  }

  override predicate isSink(DataFlow::Node sink) {
    exists(MethodAccess logAccess |
      logAccess.getMethod().getName() = "info" and
      logAccess.getArgument(0) = sink.asExpr()
    )
  }
}

from LogInjection cfg, DataFlow::PathNode source, DataFlow::PathNode sink
where cfg.hasFlowPath(source, sink)
select source, sink, "Log injection detected from source to sink"
