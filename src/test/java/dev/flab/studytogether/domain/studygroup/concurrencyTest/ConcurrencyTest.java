package dev.flab.studytogether.domain.studygroup.concurrencyTest;

import dev.flab.studytogether.domain.studygroup.entity.StudyGroup;
import dev.flab.studytogether.domain.studygroup.repository.StudyGroupRepository;
import dev.flab.studytogether.domain.studygroup.service.StudyGroupJoinService;
import dev.flab.studytogether.util.TestFixtureUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootTest
@ActiveProfiles("test")
class ConcurrencyTest {
    @Autowired
    StudyGroupJoinService studyGroupJoinService;
    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Test
    void 동시_입장_테스트() throws InterruptedException {
        //given
        StudyGroup studyGroup = TestFixtureUtils.randomStudyGroup();
        studyGroupRepository.save(studyGroup);
        int memberCount = 30;

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(memberCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        AtomicLong memberId = new AtomicLong(1L);
        for(int i = 0; i < memberCount; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println("member id : " + memberId + " 입장 시도");
                    studyGroupJoinService.joinGroup(studyGroup.getId(), memberId.getAndIncrement());
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println("successCount = " + successCount);
        System.out.println("failCount = " + failCount);
    }
}
