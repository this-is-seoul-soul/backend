package com.seouldata.fest.domain.fest.client;

import com.seouldata.fest.domain.fest.config.OpenApiFeignConfig;
import com.seouldata.fest.domain.fest.dto.response.GetFestCntResDto;
import com.seouldata.fest.domain.fest.dto.response.GetFestResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "${feign.openApi.name}", url = "${feign.openApi.url}", configuration = OpenApiFeignConfig.class)

public interface OpenApiFeignClient {

    @GetMapping(value = "1/1")
    GetFestCntResDto getFestCnt();

    @RequestMapping(value = "{path}")
    GetFestResDto getFestList(@PathVariable("path") String path);

}
