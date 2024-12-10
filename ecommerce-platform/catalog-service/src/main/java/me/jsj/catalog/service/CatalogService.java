package me.jsj.catalog.service;

import me.jsj.catalog.domain.CatalogEntity;

import java.util.List;

public interface CatalogService {
    List<CatalogEntity> getCatalogsByAll();
}
