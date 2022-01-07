package darkprado.bet_calc.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import darkprado.bet_calc.dto.MonthResultDto;
import darkprado.bet_calc.service.FileCalcService;
import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 06 янв. 2022 г.
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileCalcService fileCalcService;

    @PostMapping("/calc")
    public ResponseEntity<List<MonthResultDto>> calcFileBetting(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(fileCalcService.calc(file));
    }

}
