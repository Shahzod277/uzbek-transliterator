package uz.transliterator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.transliterator.entity.BotUser;

@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
}
