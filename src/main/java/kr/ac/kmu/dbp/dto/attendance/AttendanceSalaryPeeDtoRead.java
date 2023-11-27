package kr.ac.kmu.dbp.dto.attendance;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AttendanceSalaryPeeDtoRead {
    private int nationalPension;
    private int healthInsurance;
    private int longTermHealthInsurance;
    private int employmentInsurance;
}
