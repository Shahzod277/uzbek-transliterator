package uz.transliterator.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.transliterator.entity.ConversionHistory;
import uz.transliterator.entity.ConversionType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {

    List<ConversionHistory> findTop10ByChatIdOrderByCreatedAtDesc(Long chatId);

    Page<ConversionHistory> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<ConversionHistory> findByConversionTypeOrderByCreatedAtDesc(ConversionType conversionType, Pageable pageable);

    @Query("SELECT c.conversionType, COUNT(c) FROM ConversionHistory c GROUP BY c.conversionType")
    List<Object[]> countGroupByType();

    @Query("SELECT COUNT(c) FROM ConversionHistory c WHERE c.createdAt >= :start")
    long countSince(@Param("start") LocalDateTime start);

    @Query(value = "SELECT DATE(created_at) as d, COUNT(*) as cnt FROM conversion_history WHERE created_at >= :start GROUP BY DATE(created_at) ORDER BY d", nativeQuery = true)
    List<Object[]> countDailySince(@Param("start") LocalDateTime start);

    @Query("SELECT c.chatId, c.username, COUNT(c) FROM ConversionHistory c GROUP BY c.chatId, c.username ORDER BY COUNT(c) DESC")
    List<Object[]> findTopActiveUsers(Pageable pageable);

    @Query("SELECT c.chatId, COUNT(c) FROM ConversionHistory c GROUP BY c.chatId")
    List<Object[]> countPerUser();
}
