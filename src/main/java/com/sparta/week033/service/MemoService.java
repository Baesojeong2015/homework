package com.sparta.week033.service;

import com.sparta.week033.domain.Memo;
import com.sparta.week033.domain.MemoRepository;
import com.sparta.week033.domain.MemoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    @Transactional
    public String update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if(memo.getPassword().equals(requestDto.getPassword())){
            memo.update(requestDto);
            return "업데이트 완료";
        }
        else {
            return "비밀번호 불일치";
        }

    }
}