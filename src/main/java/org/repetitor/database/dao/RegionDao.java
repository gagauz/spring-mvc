package org.repetitor.database.dao;

import org.repetitor.database.model.Region;
import org.repetitor.database.model.enums.RegionType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionDao extends AbstractDao<Integer, Region> {

    public Region findByName(String regionName) {
        return (Region) createQuery("select distinct r from Region r left join fetch r.children cc where r.name=:name")
                .setString("name", regionName).uniqueResult();
    }

    public List<Region> findAll(Region region) {
        return createQuery("select r from Region r where r.parent=:parent").setEntity("parent", region).list();
    }

    public List<Region> findByType(RegionType type) {
        return createQuery("select r from Region r where r.type=:type").setParameter("type", type).list();
    }

}
