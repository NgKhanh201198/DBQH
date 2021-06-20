package daibieuquochoi.backend.service;

import daibieuquochoi.backend.entity.RoleEntity;

import java.util.Optional;

public interface IRoleService {
    public Optional<RoleEntity> findByKeyName(String keyName);
}
