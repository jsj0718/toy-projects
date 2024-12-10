package me.jsj.catalog.service;

import lombok.RequiredArgsConstructor;
import me.jsj.catalog.domain.CatalogEntity;
import me.jsj.catalog.domain.CatalogRepository;
import me.jsj.catalog.dto.CatalogDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public List<CatalogEntity> getCatalogsByAll() {
        ModelMapper mapper = getMapper();

        return catalogRepository.findAll();
    }

    private static ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
