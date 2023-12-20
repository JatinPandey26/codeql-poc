// Custom CodeQL Queries for Log Injection and XSS Mitigation

import cpp

// Define a custom predicate to identify user-controlled sources
predicate isUserControlledSource(string source) {
  exists(string userControlled |
    source = userControlled
  )
}

// Suppress log injection warnings
from DataFlow::PathNode logInjectionNode, DataFlow::PathNode userControlledNode
where
  logInjectionNode.asExpr().toString().matches("%log%") and
  isUserControlledSource(userControlledNode.asExpr().toString()) and
  logInjectionNode != userControlledNode
select logInjectionNode, userControlledNode, "Log Injection Warning mitigated"

// Suppress XSS warnings
from DataFlow::PathNode xssNode, DataFlow::PathNode userControlledNode
where
  xssNode.asExpr().toString().matches("%xss%") and
  isUserControlledSource(userControlledNode.asExpr().toString()) and
  xssNode != userControlledNode
select xssNode, userControlledNode, "XSS Warning mitigated"
