package com.mscconfig.repository;

/**
 * Created with IntelliJ IDEA.
 * User: win7srv
 * Date: 14.05.13
 * Time: 12:14
 * Интерфейс-Репозиторий для работы с сущностью MgwData
 */
import com.mscconfig.entities.MgwData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MgwDataRepository extends JpaRepository<MgwData, Long> {
}
