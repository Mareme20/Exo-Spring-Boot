package com.marieme.wallet.repository;

import com.marieme.wallet.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * Recherche paginee d'utilisateurs par nom (recherche partielle insensible a la casse).
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    Page<User> findByNomContaining(@Param("nom") String nom, Pageable pageable);

    /**
     * Recherche paginee d'utilisateurs par email (recherche partielle insensible a la casse).
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    Page<User> findByEmailContaining(@Param("email") String email, Pageable pageable);

    /**
     * Recherche paginee combinee sur le nom et l'email.
     */
    @Query(value = "SELECT u FROM User u WHERE LOWER(u.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))",
           countQuery = "SELECT COUNT(u) FROM User u WHERE LOWER(u.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<User> search(@Param("search") String search, Pageable pageable);
}
