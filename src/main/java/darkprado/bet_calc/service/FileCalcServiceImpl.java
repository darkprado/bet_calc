package darkprado.bet_calc.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import darkprado.bet_calc.dto.MonthResultDto;
import darkprado.bet_calc.dto.OneRowDto;
import darkprado.bet_calc.mapper.RowsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author s.melekhin
 * @since 06 янв. 2022 г.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileCalcServiceImpl implements FileCalcService {

    private final RowsMapper mapper;

    @Override
    public List<MonthResultDto> calc(MultipartFile file) {
        log.info(String.format("Convert file with name \"%s\" to list", file.getOriginalFilename()));
        List<OneRowDto> rowDtoList = mapper.fileToListMapping(file);
        Set<String> monthsSet = rowDtoList.stream().map(OneRowDto::getMonth).collect(Collectors.toSet());
        log.info("Calculate data");
        return calcMonthsData(monthsSet, rowDtoList);
    }

    private List<MonthResultDto> calcMonthsData(Set<String> monthsSet, List<OneRowDto> rowDtoList) {
        List<MonthResultDto> resultDtoList = new ArrayList<>();
        AtomicInteger monthResult = new AtomicInteger();
        monthsSet.forEach(month -> {
            MonthResultDto monthResultDto = new MonthResultDto();
            monthResultDto.setYears(rowDtoList.stream()
                    .filter(rowDto -> rowDto.getMonth().equals(month))
                            .findFirst().get().getYear());
            monthResultDto.setMonth(convertMonth(
                    rowDtoList.stream()
                    .filter(rowDto -> rowDto.getMonth().equals(month))
                    .findFirst().get().getMonth()));
            rowDtoList.stream()
                    .filter(rowDto -> rowDto.getMonth().equals(month))
                    .forEach(row -> monthResult.addAndGet(row.getCash()));
            monthResultDto.setResult(monthResult.get() * -1);
            resultDtoList.add(monthResultDto);
            monthResult.set(0);
        });
        return resultDtoList;
    }

    private String convertMonth(String month) {
        Map<String, String> months = new HashMap<>();
        months.put("января", "Январь");
        months.put("февраля", "Февраль");
        months.put("марта", "Март");
        months.put("апреля", "Апрель");
        months.put("мая", "Май");
        months.put("июня", "Июнь");
        months.put("июля", "Июль");
        months.put("августа", "Август");
        months.put("сентября", "Сентябрь");
        months.put("октября", "Октябрь");
        months.put("ноября", "Ноябрь");
        months.put("декабря", "Декабрь");
        return months.get(month);
    }

}
