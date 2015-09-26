var groups = [
    {
        name: 'Eximee',
        blockers: 0,
        criticals: 0,
        other: 70,
        status: 'healthy',
        statusCode: 5,
        id: 'eximee',
        violations: [
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'Avoid too complex method.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '12'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: '1 branches need to be covered by unit tests to reach the minimum treshold of 65.0% branch coverage.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '3'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'major',
                    rankCode: 15,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'OSGi-Bridge',
                    id: 'osgi-bridge'
                },
                violation: {
                    description: 'Switch without "default" clause.',
                    rank: 'critical',
                    rankCode: 20,
                    age: '2'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Unused import - org.sonar.api.resources.Project',
                    rank: 'minor',
                    rankCode: 10,
                    age: '5'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Must include both @java.lang.Deprecated annotation and #deprecated Javadoc tag with description.',
                    rank: 'trivial',
                    rankCode: 5,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '17'
                }
            }
        ],
        projects: [
            {
                name: 'Router',
                id: 'router',
                status: 'warning',
                statusCode: 10,
                blockers: 1,
                criticals: 12
            },
            {
                name: 'WebForms',
                id: 'webforms',
                status: 'critical',
                statusCode: 15,
                blockers: 0,
                criticals: 6
            },
            {
                name: 'OSGi-Bridge',
                id: 'osgi-bridge',
                status: 'healthy',
                statusCode: 5,
                blockers: 0,
                criticals: 0
            }
        ],
        healthyStreak: 3,
        historicalData: [
            { date: '2015-07-01', blocker: 0, critical: 10 },
            { date: '2015-07-02', blocker: 1, critical: 15 },
            { date: '2015-07-03', blocker: 0, critical: 13 },
            { date: '2015-07-04', blocker: 1, critical: 12 },
            { date: '2015-07-05', blocker: 2, critical: 20 },
            { date: '2015-07-06', blocker: 3, critical: 19 },
            { date: '2015-07-07', blocker: 4, critical: 18 },
            { date: '2015-07-08', blocker: 3, critical: 10 },
            { date: '2015-07-09', blocker: 2, critical: 9 },
            { date: '2015-07-10', blocker: 0, critical: 9 },
            { date: '2015-07-11', blocker: 0, critical: 9 },
            { date: '2015-07-12', blocker: 0, critical: 12 }
        ]
    },
    {
        name: 'iBiznes 24',
        blockers: 0,
        criticals: 3,
        other: 70,
        status: 'warning',
        statusCode: 10,
        id: 'ib24',
        violations: [
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'Avoid too complex method.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '12'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: '1 branches need to be covered by unit tests to reach the minimum treshold of 65.0% branch coverage.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '3'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'major',
                    rankCode: 15,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'OSGi-Bridge',
                    id: 'osgi-bridge'
                },
                violation: {
                    description: 'Switch without "default" clause.',
                    rank: 'critical',
                    rankCode: 20,
                    age: '2'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Unused import - org.sonar.api.resources.Project',
                    rank: 'minor',
                    rankCode: 10,
                    age: '5'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Must include both @java.lang.Deprecated annotation and #deprecated Javadoc tag with description.',
                    rank: 'trivial',
                    rankCode: 5,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '17'
                }
            }
        ],
        projects: [
            {
                name: 'Router',
                id: 'router',
                status: 'warning',
                statusCode: 10,
                blockers: 1,
                criticals: 12
            },
            {
                name: 'WebForms',
                id: 'webforms',
                status: 'critical',
                statusCode: 15,
                blockers: 0,
                criticals: 6
            },
            {
                name: 'OSGi-Bridge',
                id: 'osgi-bridge',
                status: 'healthy',
                statusCode: 5,
                blockers: 0,
                criticals: 0
            }
        ],
        healthyStreak: 35,
        historicalData: [
            { date: '2015-07-01', blocker: 0, critical: 10 },
            { date: '2015-07-02', blocker: 1, critical: 15 },
            { date: '2015-07-03', blocker: 0, critical: 13 },
            { date: '2015-07-04', blocker: 1, critical: 12 },
            { date: '2015-07-05', blocker: 2, critical: 20 },
            { date: '2015-07-06', blocker: 3, critical: 19 },
            { date: '2015-07-07', blocker: 4, critical: 18 },
            { date: '2015-07-08', blocker: 3, critical: 10 },
            { date: '2015-07-09', blocker: 2, critical: 9 },
            { date: '2015-07-10', blocker: 0, critical: 9 },
            { date: '2015-07-11', blocker: 0, critical: 9 },
            { date: '2015-07-12', blocker: 0, critical: 12 }
        ]
    },
    {
        name: 'Eximee - BZWBK',
        blockers: 0,
        criticals: 7,
        other: 12,
        status: 'warning',
        statusCode: 10,
        id: 'eximee-bzwbk',
        violations: [
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'Avoid too complex method.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '12'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: '1 branches need to be covered by unit tests to reach the minimum treshold of 65.0% branch coverage.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '3'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'major',
                    rankCode: 15,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'OSGi-Bridge',
                    id: 'osgi-bridge'
                },
                violation: {
                    description: 'Switch without "default" clause.',
                    rank: 'critical',
                    rankCode: 20,
                    age: '2'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Unused import - org.sonar.api.resources.Project',
                    rank: 'minor',
                    rankCode: 10,
                    age: '5'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Must include both @java.lang.Deprecated annotation and #deprecated Javadoc tag with description.',
                    rank: 'trivial',
                    rankCode: 5,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '17'
                }
            }
        ],
        projects: [
            {
                name: 'Router',
                id: 'router',
                status: 'warning',
                statusCode: 10,
                blockers: 1,
                criticals: 12
            },
            {
                name: 'WebForms',
                id: 'webforms',
                status: 'critical',
                statusCode: 15,
                blockers: 0,
                criticals: 6
            },
            {
                name: 'OSGi-Bridge',
                id: 'osgi-bridge',
                status: 'healthy',
                statusCode: 5,
                blockers: 0,
                criticals: 0
            }
        ],
        healthyStreak: 5,
        historicalData: [
            { date: '2015-07-01', blocker: 0, critical: 10 },
            { date: '2015-07-02', blocker: 1, critical: 15 },
            { date: '2015-07-03', blocker: 0, critical: 13 },
            { date: '2015-07-04', blocker: 1, critical: 12 },
            { date: '2015-07-05', blocker: 2, critical: 20 },
            { date: '2015-07-06', blocker: 3, critical: 19 },
            { date: '2015-07-07', blocker: 4, critical: 18 },
            { date: '2015-07-08', blocker: 3, critical: 10 },
            { date: '2015-07-09', blocker: 2, critical: 9 },
            { date: '2015-07-10', blocker: 0, critical: 9 },
            { date: '2015-07-11', blocker: 0, critical: 9 },
            { date: '2015-07-12', blocker: 0, critical: 12 }
        ]
    },
    {
        name: 'Eximee - PayU',
        blockers: 1,
        criticals: 12,
        other: 112,
        status: 'critical',
        statusCode: 15,
        id: 'eximee-payu',
        violations: [
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'Avoid too complex method.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '12'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: '1 branches need to be covered by unit tests to reach the minimum treshold of 65.0% branch coverage.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '3'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'major',
                    rankCode: 15,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'OSGi-Bridge',
                    id: 'osgi-bridge'
                },
                violation: {
                    description: 'Switch without "default" clause.',
                    rank: 'critical',
                    rankCode: 20,
                    age: '2'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Unused import - org.sonar.api.resources.Project',
                    rank: 'minor',
                    rankCode: 10,
                    age: '5'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Must include both @java.lang.Deprecated annotation and #deprecated Javadoc tag with description.',
                    rank: 'trivial',
                    rankCode: 5,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '17'
                }
            }
        ],
        projects: [
            {
                name: 'Router',
                id: 'router',
                status: 'warning',
                statusCode: 10,
                blockers: 1,
                criticals: 12
            },
            {
                name: 'WebForms',
                id: 'webforms',
                status: 'critical',
                statusCode: 15,
                blockers: 0,
                criticals: 6
            },
            {
                name: 'OSGi-Bridge',
                id: 'osgi-bridge',
                status: 'healthy',
                statusCode: 5,
                blockers: 0,
                criticals: 0
            }
        ],
        healthyStreak: 1,
        historicalData: [
            { date: '2015-07-01', blocker: 0, critical: 10 },
            { date: '2015-07-02', blocker: 1, critical: 15 },
            { date: '2015-07-03', blocker: 0, critical: 13 },
            { date: '2015-07-04', blocker: 1, critical: 12 },
            { date: '2015-07-05', blocker: 2, critical: 20 },
            { date: '2015-07-06', blocker: 3, critical: 19 },
            { date: '2015-07-07', blocker: 4, critical: 18 },
            { date: '2015-07-08', blocker: 3, critical: 10 },
            { date: '2015-07-09', blocker: 2, critical: 9 },
            { date: '2015-07-10', blocker: 0, critical: 9 },
            { date: '2015-07-11', blocker: 0, critical: 9 },
            { date: '2015-07-12', blocker: 0, critical: 12 }
        ]
    },
    {
        name: 'Eximee - mBank',
        blockers: 0,
        criticals: 0,
        other: 0,
        status: 'healthy',
        statusCode: 5,
        id: 'eximee-mbank',
        violations: [
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'Avoid too complex method.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '12'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: '1 branches need to be covered by unit tests to reach the minimum treshold of 65.0% branch coverage.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '3'
                }
            },
            {
                project: {
                    name: 'WebForms',
                    id: 'webforms'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'major',
                    rankCode: 15,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'OSGi-Bridge',
                    id: 'osgi-bridge'
                },
                violation: {
                    description: 'Switch without "default" clause.',
                    rank: 'critical',
                    rankCode: 20,
                    age: '2'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Unused import - org.sonar.api.resources.Project',
                    rank: 'minor',
                    rankCode: 10,
                    age: '5'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'Must include both @java.lang.Deprecated annotation and #deprecated Javadoc tag with description.',
                    rank: 'trivial',
                    rankCode: 5,
                    age: '130'
                }
            },
            {
                project: {
                    name: 'ServiceProxy',
                    id: 'serviceproxy'
                },
                violation: {
                    description: 'New exception is thrown in catch block, original stack trace may be lost.',
                    rank: 'blocker',
                    rankCode: 25,
                    age: '17'
                }
            }
        ],
        projects: [
            {
                name: 'Router',
                id: 'router',
                status: 'warning',
                statusCode: 10,
                blockers: 1,
                criticals: 12
            },
            {
                name: 'WebForms',
                id: 'webforms',
                status: 'critical',
                statusCode: 15,
                blockers: 0,
                criticals: 6
            },
            {
                name: 'OSGi-Bridge',
                id: 'osgi-bridge',
                status: 'healthy',
                statusCode: 5,
                blockers: 0,
                criticals: 0
            }
        ],
        healthyStreak: 12,
        historicalData: [
            { date: '2015-07-01', blocker: 0, critical: 10 },
            { date: '2015-07-02', blocker: 1, critical: 15 },
            { date: '2015-07-03', blocker: 0, critical: 13 },
            { date: '2015-07-04', blocker: 1, critical: 12 },
            { date: '2015-07-05', blocker: 2, critical: 20 },
            { date: '2015-07-06', blocker: 3, critical: 19 },
            { date: '2015-07-07', blocker: 4, critical: 18 },
            { date: '2015-07-08', blocker: 3, critical: 10 },
            { date: '2015-07-09', blocker: 2, critical: 9 },
            { date: '2015-07-10', blocker: 0, critical: 9 },
            { date: '2015-07-11', blocker: 0, critical: 9 },
            { date: '2015-07-12', blocker: 0, critical: 12 }
        ]
    }
];