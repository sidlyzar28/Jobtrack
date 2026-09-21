package com.jobtrack.jobtrack.dto;

public class JobStats {

private final long total;
private final long applied;
private final long assessment;
private final long interview;
private final long offer;
private final long rejected;

    public JobStats(
            long total,
            long applied,
            long assessment,
            long interview,
            long offer,
            long rejected) {

        this.total = total;
        this.applied = applied;
        this.assessment = assessment;
        this.interview = interview;
        this.offer = offer;
        this.rejected = rejected;
    }

    public long getTotal() {
        return total;
    }

    public long getApplied() {
        return applied;
    }

    public long getAssessment() {
        return assessment;
    }

    public long getInterview() {
        return interview;
    }

    public long getOffer() {
        return offer;
    }

    public long getRejected() {
        return rejected;
    }
}