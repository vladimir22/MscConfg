package com.mscconfig.mvc;

/**
 * Created with IntelliJ IDEA.
 * User: win7srv
 * Date: 14.05.13
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
import com.mscconfig.entities.MgwData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MgwDataRepository extends JpaRepository<MgwData, Long> {
}
