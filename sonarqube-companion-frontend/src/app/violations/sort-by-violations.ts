export const SortByViolationsDesc = (a, b) => {
  if (a.violations.blockers !== b.violations.blockers) {
    return b.violations.blockers - a.violations.blockers;
  } else if (b.violations.criticals !== a.violations.criticals) {
    return b.violations.criticals - a.violations.criticals;
  } else {
    return b.violations.nonRelevant - a.violations.nonRelevant;
  }
};
