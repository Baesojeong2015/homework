package com.sparta.week033.controller;

import com.sparta.week033.domain.Memo;
import com.sparta.week033.domain.MemoRepository;
import com.sparta.week033.domain.MemoRequestDto;
import com.sparta.week033.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemoController {

    private final MemoRepository memoRepository;
    private final MemoService memoService;

    @PostMapping("/api/memos")
    public Memo createMemo(@RequestBody MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        return memoRepository.save(memo);
    }

    @GetMapping("/api/memos")
    public List<Memo> getMemos() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        return memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(yesterday, now);
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

    @PutMapping("/api/memos/{id}")
    public String updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        String result;
        result = memoService.update(id, requestDto);
        return result;
    }

    @DeleteMapping("/api/memos/{id}")
    public String deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).get();
        if(memo.getPassword().equals(requestDto.getPassword())) {
            memoRepository.deleteById(id);
            return "삭제 완료";
        }
        else {
            return "비밀번호 불일치";
        }
    }
}