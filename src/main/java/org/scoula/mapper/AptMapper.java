package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scoula.dto.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AptMapper {
    void insertApt(AptDTO aptDTO);
    List<AptIdxDTO> getIdxAndHouseMangeNo(); // apt 테이블에서  house_manage_no 와 apt_idx 만 가져옴
    void insertAptType(AptTypeDTO aptTypeResponseDTO);
    void deleteOld(@Param("firstDayOfMonth") LocalDate firstDayOfMonth);

    void insertOfficetel(OfficetelDTO officetelDTO);
    List<OfficetelIdxDTO>getIdxAndHouseManageNoFromOfficetel();
    void insertOfficetelType(OfficetelTypeDTO officetelTypeDTO);
    void deleteOldFromOfficetel(@Param("firstDayOfMonth") LocalDate firstDayOfMonth);
    AptDetailDTO getAptDetails(String pblanc_no);
    OfficetelDetailDTO getOfficetelDetails(String pblanc_no);

    List<AptDTO> findAllAptLocations();
    List<OfficetelDTO> findAllOfficetelLocations();
}
