package daibieuquochoi.backend.service.Impl;

import daibieuquochoi.backend.entity.AgencyEntity;
import daibieuquochoi.backend.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import daibieuquochoi.backend.service.IAgencyService;

import java.util.List;
import java.util.Optional;

@Service
public class AgencyServiceImpl implements IAgencyService {
    @Autowired
    public AgencyRepository agencyRepository;

    @Override
    public void create(AgencyEntity agencyEntity) {
        agencyRepository.save(agencyEntity);
    }

    @Override
    public Optional<AgencyEntity> findByAgencyName(String agencyName) {
        return agencyRepository.findByAgencyName(agencyName);
    }

    @Override
    public List<AgencyEntity> getAll() {
        return agencyRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION.DESC, "id"));
    }

    @Override
    public AgencyEntity getById(long id) {
        return agencyRepository.findById(id);
    }

    @Override
    public void updateById(AgencyEntity agencyEntity) {
        agencyRepository.save(agencyEntity);
    }

    @Override
    public void deleteById(long id) {
        agencyRepository.deleteById(id);
    }

    @Override
    public boolean isExistsById(long id) {
        return agencyRepository.existsById(id);
    }

    @Override
    public boolean isExistsByAgencyName(String agencyName) {
        return agencyRepository.existsByAgencyName(agencyName);
    }
}
