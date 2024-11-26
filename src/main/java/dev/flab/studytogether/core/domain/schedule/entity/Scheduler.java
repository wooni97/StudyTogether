package dev.flab.studytogether.core.domain.schedule.entity;

import java.time.LocalDate;

public class Scheduler {

    private int schedulerSeq;
    private LocalDate date;
    private int memberSeqId;

    public Scheduler(int schedulerSeq, LocalDate date, int memberSeqId) {
        this.schedulerSeq = schedulerSeq;
        this.date = date;
        this.memberSeqId = memberSeqId;
    }

    public int getSchedulerSeq() {
        return schedulerSeq;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMemberSeqId() {
        return memberSeqId;
    }
}


