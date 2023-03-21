package mysql.batchjpa.repository;

import mysql.batchjpa.model.MultiUsers;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MulitUsersRepo extends JpaRepository<MultiUsers,Integer> {
}
