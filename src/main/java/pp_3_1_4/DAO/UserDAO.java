package pp_3_1_4.DAO;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp_3_1_4.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
