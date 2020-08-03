package entities;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long> {
//    @Query (value = "SELECT c FROM Completion c WHERE c.userId = ?1 ORDER BY date")
    Page<Completion> getAllByUserId(long userId, Pageable pageable);
}
