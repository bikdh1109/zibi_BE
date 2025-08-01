package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scoula.dto.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface SelectedMapper {

    void updateHomePrice(@Param("userIdx") int userIdx, @Param("homePrice") HomePriceDTO homePrice);

    void deleteSelectedRegion(int userInfoIdx);
    void deleteSelectedHomeSize(int userInfoIdx);
    void deleteSelectedHomeType(int userInfoIdx);

    void insertSelectedRegion(@Param("userIdx") int userInfoIdx, @Param("region") RegionDTO region);
    void insertSelectedHomeSize(@Param("userIdx") int userInfoIdx, @Param("homesize") HomeSizeDTO homesize);
    void insertSelectedHomeType(@Param("userIdx") int userInfoIdx, @Param("hometype") HomeTypeDTO hometype);

    HomePriceDTO selectHomePriceByUserIdx(int userIdx);
    int findUserInfoIdxByUserIdx(int userIdx);

    List<RegionDTO> selectSelectedRegion(int userInfoIdx);
    List<HomeSizeDTO> selectSelectedHomesize(int userInfoIdx);
    List<HomeTypeDTO> selectSelectedHometype(int userInfoIdx);

    List<Map<String, Object>> findAptNotices(@Param("homePrice") HomePriceDTO homePrice,
                                             @Param("regions") List<RegionDTO> regions,
                                             @Param("homesizes") List<HomeSizeDTO> homesizes,
                                             @Param("hometypes") List<HomeTypeDTO> hometypes);

    List<Map<String, Object>> findOfficetelNotices(@Param("homePrice") HomePriceDTO homePrice,
                                                   @Param("regions") List<RegionDTO> regions,
                                                   @Param("homesizes") List<HomeSizeDTO> homesizes,
                                                   @Param("hometypes") List<HomeTypeDTO> hometypes);
}
}