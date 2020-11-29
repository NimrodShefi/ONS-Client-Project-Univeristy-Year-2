package ons.group8.repository;

import ons.group8.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepositoryJPA extends JpaRepository<Users,Long> {

    

    @Transactional
    @Modifying
    @Query(value="UPDATE user_role SET role_id =:roleId WHERE user_id =:userId", nativeQuery=true)
    void updateUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    Users findUsersByEmail(String email);
}
