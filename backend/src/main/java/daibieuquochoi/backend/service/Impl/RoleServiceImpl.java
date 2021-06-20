package daibieuquochoi.backend.service.Impl;

import daibieuquochoi.backend.entity.RoleEntity;
import daibieuquochoi.backend.repository.RoleRepository;
import daibieuquochoi.backend.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<RoleEntity> findByKeyName(String keyName) {
        return roleRepository.findByKeyName(keyName);
    }
}
