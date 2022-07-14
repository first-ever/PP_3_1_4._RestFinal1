package pp_3_1_4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pp_3_1_4.DAO.RoleDAO;
import pp_3_1_4.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getRoles() {
        return roleDAO.findAll();
    }
}
