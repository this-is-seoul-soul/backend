package com.seouldata.fest.domain.fest.client;

import com.seouldata.fest.domain.fest.config.OpenApiFeignConfig;
import com.seouldata.fest.domain.fest.dto.response.GetFestResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "${feign.openApi.name}", url = "${feign.openApi.url}", configuration = OpenApiFeignConfig.class)

public interface OpenApiFeignClient {

    @GetMapping(value = "1/2")
    GetFestResDto getFestList();

}
