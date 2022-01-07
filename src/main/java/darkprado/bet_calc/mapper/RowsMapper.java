package darkprado.bet_calc.mapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import darkprado.bet_calc.dto.OneRowDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author s.melekhin
 * @since 07 янв. 2022 г.
 */
@Slf4j
@Service
public class RowsMapper {

    public List<OneRowDto> fileToListMapping(MultipartFile file) {
        String stringFile = null;
        try {
            stringFile = IOUtils.toString(new ByteArrayInputStream(file.getBytes()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(String.format("Error getting bytes in file with name \"%s\"", file.getOriginalFilename()), e);
        }
        String[] mainArray = stringFile.split("\r\n");
        List<OneRowDto> rowsList = new ArrayList<>();
        for (String oneRow : mainArray) {
            String[] oneRowData = oneRow.split("\t");
            String[] dateArray = oneRowData[0].split(" ");
            rowsList.add(
                    new OneRowDto(
                            dateArray[0],
                            dateArray[1],
                            dateArray[2],
                            oneRowData[1],
                            Integer.parseInt(oneRowData[2].replaceAll(" ", "").replaceAll(",00c", "")),
                            oneRowData[5].trim()
                    )
            );
        }
        return rowsList;
    }

}
