package ru.yastrebov.onemicr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yastrebov.onemicr.model.Emlpoyee;
import java.util.UUID;

@Repository
public interface EmlpoyeeRepository extends JpaRepository<Emlpoyee, UUID> {


}
