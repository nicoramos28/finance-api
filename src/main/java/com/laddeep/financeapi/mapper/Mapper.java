package com.laddeep.financeapi.mapper;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import java.util.List;
//import java.util.stream.Collectors;

public abstract class Mapper<DB, API> {
/*
    public abstract API map(DB db);

    public List<API> map(List<DB> entities){
        return entities.stream().map(this::map).collect(Collectors.toList());
    }

    public Page<API> map(Page<DB> entities){
        return new PageImpl<>(
                map(entities.getContent()),
                entities.getPageable(),
                entities.getTotalElements()
        );
    }

 */
}
