package org.scoula.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.dto.AllHouseListDTO;
import org.scoula.dto.HouseListDTO;
import org.scoula.dto.PreferenceDTO;
import org.scoula.dto.RegionDTO;

import java.util.List;

public interface HouseListMapper {
    List<HouseListDTO> getAllHouseList(@Param("userIdx") int userIdx);

    List<HouseListDTO> getAptRecommendationList(
            @Param("region") RegionDTO region,
            @Param("preference") PreferenceDTO preference
    );

    List<HouseListDTO> getOfficetelRecommendationList(
            @Param("region") RegionDTO region,
            @Param("preference") PreferenceDTO preference
    );

    AllHouseListDTO getHouseDetailByPblancNo(@Param("pblancNo") String pblancNo);

}