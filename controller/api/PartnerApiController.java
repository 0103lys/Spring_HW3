package com.koreait.day3.controller.api;

import com.koreait.day3.ifs.CrudInterface;
import com.koreait.day3.model.network.Header;
import com.koreait.day3.model.network.request.PartnerApiRequest;
import com.koreait.day3.model.network.response.PartnerApiResponse;
import com.koreait.day3.service.PartnerApiLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/partner")
@RequiredArgsConstructor
public class PartnerApiController implements CrudInterface<PartnerApiRequest, PartnerApiResponse> {
    private final PartnerApiLogicService partnerApiLogicService;


    /*
    {
        "transaction_time":"2021-08-24",
                "resultCode":"OK",
                "description":"OK",
                "data":{
                    "name":"롯데백화점",
                    "status":"사용중",
                    "address":"서울시 중구",
                    "callCenter":"02-1234-1234",
                    "businessNumber":"010-1111-1111",
                    "ceoName":"신동빈"
                 }
     }

     */
    @Override
    @PostMapping("") // /api/partner // http://127.0.0.1:9900/api/partner
    public Header<PartnerApiResponse> create(@RequestBody Header<PartnerApiRequest> request) {
        System.out.println(request);
        return partnerApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") //  /api/partner/{id} (get방식) (get) http://127.0.0.1:9900/api/partner/7
    public Header<PartnerApiResponse> read(@PathVariable(name="id") Long id) {
        return partnerApiLogicService.read(id);
    }


    /*
    {
        "transaction_time":"2021-08-24",
        "resultCode":"OK",
        "description":"OK",
        "data":{
            "id":7,
            "name":"롯데그룹",
            "status":"사용중",
            "address":"서울시 중구",
            "callCenter":"02-1111-1111",
            "businessNumber":"070-1111-1111",
            "ceoName":"신동빈"
         }
    }

 */
    @Override
    @PutMapping("") // /api/partner // http://127.0.0.1:9900/api/partner
    public Header<PartnerApiResponse> update(@RequestBody Header<PartnerApiRequest> request) {
       return partnerApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")  //  /api/partner/{id} (delete방식) http://127.0.0.1:9900/api/partner/7
    public Header<PartnerApiResponse> delete(@PathVariable(name="id") Long id) {
       return partnerApiLogicService.delete(id);
    }
}
