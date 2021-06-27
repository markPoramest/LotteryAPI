package com.example.demo.Controllers;

import com.example.demo.Models.Lotteries;
import com.example.demo.Models.Lottery;
import com.example.demo.Services.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/getLotteryResult")
public class LotteryController {
    @Autowired
    private final LotteryService lotteryService;

    @GetMapping("/get")
    public Lottery getLottery() throws IOException {
        return lotteryService.getResultLottery();
    }

    @GetMapping("/getReward")
    public Lotteries getLottery2() throws IOException {
        return lotteryService.getLotteries();
    }

}
