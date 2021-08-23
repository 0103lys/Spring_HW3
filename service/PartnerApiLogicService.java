package com.koreait.day3.service;

import com.koreait.day3.ifs.CrudInterface;
import com.koreait.day3.model.entity.Partner;
import com.koreait.day3.model.network.Header;
import com.koreait.day3.model.network.request.PartnerApiRequest;
import com.koreait.day3.model.network.response.PartnerApiResponse;
import com.koreait.day3.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service    // 서비스레이어, 내부에서 자바로직을 처리함
@RequiredArgsConstructor
public class PartnerApiLogicService implements CrudInterface<PartnerApiRequest, PartnerApiResponse> {

    private final PartnerRepository partnerRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {   // request에 정보가 들어옴
        PartnerApiRequest partnerApiRequest = request.getData();

        Partner partner = Partner.builder()
                .name(partnerApiRequest.getName())
                .status(partnerApiRequest.getStatus())
                .address(partnerApiRequest.getAddress())
                .callCenter(partnerApiRequest.getCallCenter())
                .businessNumber(partnerApiRequest.getBusinessNumber())
                .ceoName(partnerApiRequest.getCeoName())
                .build();
        Partner newPartner = partnerRepository.save(partner);
        return response(newPartner);

    }



    @Override
    public Header<PartnerApiResponse> read(Long id) {    // id를 전달 받음
        return partnerRepository.findById(id)
                .map(partner -> response(partner))  // 반복해서 돌림 partner 데이터를 받아서 response함수에 partner를 넣음(partner에 있는 데이터에서 id를 찾으면 response(partner)에 저장)
                .orElseGet(()-> Header.ERROR("찾는 데이터 없음")); // 데이터가 없다면 Header에 에러를 넣어서 보냄

    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {
        PartnerApiRequest partnerApiRequest = request.getData();
        Optional<Partner> optional = partnerRepository.findById(partnerApiRequest.getId()); // findById로 넘어온 데이터에서 id 찾아서 optional 객체를 만들고 저장
        return optional.map(partner -> {    // 돌면서 partner객체에서 데이터를 가져와서 set
            partner.setName(partnerApiRequest.getName());
            partner.setStatus(partnerApiRequest.getStatus());
            partner.setAddress(partnerApiRequest.getAddress());
            partner.setCallCenter(partnerApiRequest.getCallCenter());
            partner.setBusinessNumber(partnerApiRequest.getBusinessNumber());
            partner.setCeoName(partnerApiRequest.getCeoName());
            return partner;
        }).map(partner -> partnerRepository.save(partner))  // partner를 받아서 repository에 save(partner)하여 저장
                .map(partner -> response(partner))          // partner객체를 받아서 response(partner)쪽으로 전달
                .orElseGet(()->Header.ERROR("찾는 데이터 없음"));  // 데이터가 없다면 Header에 에러를 넣어서 보냄
    }

    @Override
    public Header delete(Long id) {  // Header<PartnerApiResponse> -> Header : 객체를 보내는 것이 아니라 OK만 보내줬기 때문에 Header객체에 전달하도록 함
        Optional<Partner> optional = partnerRepository.findById(id);    // id를 찾아서 객체에 저장
        return optional.map(partner -> {    // 돌면서 partnerRepository에서 partner를 찾아서 삭제
            partnerRepository.delete(partner);
            return Header.OK();                             // Header에 OK를 보냄
        }).orElseGet(()->Header.ERROR("찾는 데이터 없음"));    //  데이터가 없으면 Header에 Error를 전달

    }


    private Header<PartnerApiResponse> response(Partner partner) {
        PartnerApiResponse partnerApiResponse = PartnerApiResponse.builder()
                .id(partner.getId())
                .name(partner.getName())
                .status(partner.getStatus())
                .address(partner.getAddress())
                .callCenter(partner.getCallCenter())
                .businessNumber(partner.getBusinessNumber())
                .ceoName(partner.getCeoName())
                .build();
        return Header.OK(partnerApiResponse);
    }
}
