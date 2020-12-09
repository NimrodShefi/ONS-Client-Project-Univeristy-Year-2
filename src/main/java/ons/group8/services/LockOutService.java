package ons.group8.services;

import ons.group8.domain.User;
import ons.group8.repositories.UserRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class LockOutService {

    public static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 1 * 60 * 60 * 1000;

    @Autowired
    private UserRepositoryJPA userRepositoryJPA;

    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userRepositoryJPA.updateFailedAttempts(newFailAttempts, user.getEmail());
    }

    public void resetFailedAttempts(String email) {
        userRepositoryJPA.updateFailedAttempts(0, email);
    }

    public void lock(User user) {
        user.setAccountNonLocked(false);
        LocalDateTime localDate = LocalDateTime.now();
        user.setLockTime(localDate);
        userRepositoryJPA.save(user);
    }

    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getMinute();
        long currentTimeInMillis = System.currentTimeMillis();
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            return true;
        }
        return false;
    }

    @Scheduled(fixedRate = 300000)
    public void checkLockedAccount() {
        List<User> listUsers = userRepositoryJPA.findAll();
        LocalDateTime localDate = LocalDateTime.now();
        LocalDate localDate1 = localDate.toLocalDate();
        for (User element : listUsers) {
            if (element.getLockTime() != null) {
                LocalDate localDate2 = element.getLockTime().toLocalDate();
                if (localDate1.isEqual(localDate2)
                        && (element.getLockTime().truncatedTo(ChronoUnit.DAYS).isEqual(localDate.truncatedTo(ChronoUnit.DAYS)))
                        && (element.getLockTime().truncatedTo(ChronoUnit.MINUTES).plusMinutes(LOCK_TIME_DURATION).isEqual(localDate.truncatedTo(ChronoUnit.MINUTES)))
                        || (localDate1.isEqual(localDate2) && (element.getLockTime().truncatedTo(ChronoUnit.MINUTES).plusMinutes(LOCK_TIME_DURATION).MAX.isAfter(localDate.truncatedTo(ChronoUnit.MINUTES))))) {
                    element.setAccountNonLocked(true);
                    element.setLockTime(null);
                    element.setFailedAttempt(0);
                    userRepositoryJPA.save(element);
                }
            }
        }
    }
}
