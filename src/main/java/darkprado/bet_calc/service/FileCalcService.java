package darkprado.bet_calc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import darkprado.bet_calc.dto.MonthResultDto;

/**
 * @author s.melekhin
 * @since 06 янв. 2022 г.
 */
public interface FileCalcService {

    List<MonthResultDto> calc(MultipartFile file);

}
