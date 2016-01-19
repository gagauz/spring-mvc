package org.gagauz.shop.database.model.enums;

public enum RegionType {
    COUNTRY(null),
    CITY(COUNTRY),
    SUBWAY(CITY),
    DISTRICT(CITY),
    STREET(CITY);

    private RegionType parent;

    RegionType(RegionType parent) {
        this.parent = parent;
    }

    public RegionType getParent() {
        return parent;
    }
}
