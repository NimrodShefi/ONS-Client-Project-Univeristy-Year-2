package ons.group8.repository;

import ons.group8.domain.Roles;
import java.util.List;


public interface UserRoleRepo {
    List<Roles> findAll();

}
