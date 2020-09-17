package domain;

import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Log4j
public class SampleDTOList {

    private List<SampleDTO> list;

    public SampleDTOList() {
        this.list = new ArrayList<>();
        log.info("list 객체 생성");
    }
}
