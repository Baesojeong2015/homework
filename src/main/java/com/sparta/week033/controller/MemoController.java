package com.sparta.week033.controller;

import com.sparta.week033.model.Memo;
import com.sparta.week033.repository.MemoRepository;
import com.sparta.week033.dto.MemoRequestDto;
import com.sparta.week033.security.UserDetailsImpl;
import com.sparta.week033.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoRepository memoRepository;
    private final MemoService memoService;

    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails.getUser().getId() == null){
            throw new IllegalArgumentException("로그인 한 회원만 작성할 수 있습니다.");
        }
        Memo memo = new Memo(requestDto, userDetails.getUsername());
        return memoRepository.save(memo);
    }

    @GetMapping("/api/memos")
    public List<Memo> getMemos() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
//        return memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(yesterday, now);
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

//    @PutMapping("/api/memos/{id}")
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
//        memoService.update(id, requestDto);
//        return id;
//    }

//    @DeleteMapping("/api/memos/{id}")
//    public Long deleteMemo(@PathVariable Long id) {
//        memoRepository.deleteById(id);
//        return id;
//    }
//}

//    @PutMapping("/api/memos/{id}")
//    public String updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
//        String result;
//        result = memoService.update(id, requestDto);
//        return result;
//    }

        @PutMapping("/api/memos/{id}")
    public String updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        String result;
        result = memoService.update(id, requestDto);
        return result;
    }


    @DeleteMapping("/api/memos/{id}")
    public String deleteMemo(@PathVariable Long id) {
//        Memo memo = memoRepository.findById(id).get();
//        if(memo.getPassword().equals(requestDto.getPassword())) {
//            memoRepository.deleteById(id);
//            return "삭제 완료";
//        }
//        else {
//            return "비밀번호 불일치";
//        }
        memoRepository.deleteById(id);
        return "삭제 완료";
    }
}