package uz.transliterator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.transliterator.entity.UserState;

@Repository
public interface UserStateRepository extends JpaRepository<UserState, Long> {
}
