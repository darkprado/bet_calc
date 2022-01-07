package darkprado.bet_calc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author s.melekhin
 * @since 06 янв. 2022 г.
 */
@Data
@AllArgsConstructor
public class OneRowDto {

    private String day;
    private String month;
    private String year;
    private String time;
    private int cash;
    private String status;

}
